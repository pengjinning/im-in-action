package io.xiaper.streaming.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * https://www.baeldung.com/spring-mvc-sse-streams
 * https://golb.hplar.ch/2017/03/Server-Sent-Events-with-Spring.html
 * streaming
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-async-http-streaming
 *
 * 这个SseEmitter对象和DeferredResult有异曲同工之妙,只是SseEmitter可以在一次请求中返回多条数据,而DeferredResult只能返回一条.
 *
 * @author bytedesk.com on 2019/2/9
 */
@Slf4j
@RestController
@RequestMapping("/sse")
public class SSEController {

    @Autowired
    ThreadPoolTaskExecutor mvcTaskExecutor;

    @RequestMapping(value="/push",produces="text/event-stream;charset=utf-8")
    @ResponseBody
    public String push() {
        //
        Random random = new Random();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // field可以取四个值：“data”, “event”, “id”, or “retry”，也就是说有四类头信息
        //SSE返回数据格式是固定的以data:开头，以\n\n结束
        return "data:Testing 1,2,3-" + random.nextInt() +"\n\n";
    }

    /**
     * http://localhost:7002/test
     *
     * @return
     */
    @GetMapping("/test")
    public SseEmitter handleSse() {

        final SseEmitter emitter = new SseEmitter(0L); //timeout设置为0表示不超时
        //
        mvcTaskExecutor.execute(() -> {

            try {
                //
                for(int i = 0; i < 100; i++) {
                    //
                    emitter.send("hello " + i);
                    //
                    log.info("emit:{}", "hello " + i);
                    //
                    Thread.sleep(1000*1);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        //
        return emitter;
    }


    /**
     * 测试步骤:
     *  1.请求 http://localhost:7002/sse/start?clientId=111 接口,浏览器会阻塞,等待服务器返回结果;
     *  2.请求 http://localhost:7002/sse/send?clientId=111 接口,可以请求多次,并观察第1步的浏览器返回结果;
     *  3.请求 http://localhost:7002/sse/end?clientId=111 接口结束某个请求,第1步的浏览器将结束阻塞;
     *
     *  其中clientId代表请求的唯一标志;
     *
     *  用于保存每个请求对应的 SseEmitter
     */
    private Map<String, Result> sseEmitterMap = new ConcurrentHashMap<>();

    /**
     * 返回SseEmitter对象
     *
     * @param clientId
     * @return
     */
    @RequestMapping("/start")
    public SseEmitter testSseEmitter(String clientId) {
        // 默认30秒超时,设置为0L则永不超时
        // 如果不设置为0，那么如果SseEmitter在指定的时间（AsyncSupportConfigurer设置的timeout,默认为30秒)未完成会抛出异常
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitterMap.put(clientId, new Result(clientId, System.currentTimeMillis(), sseEmitter));
        return sseEmitter;
    }

    /**
     * 向SseEmitter对象发送数据
     *
     * @param clientId
     * @return
     */
    @RequestMapping("/send")
    public String setSseEmitter(String clientId) {
        //
        try {
            Result result = sseEmitterMap.get(clientId);
            if (result != null && result.sseEmitter != null) {
                long timestamp = System.currentTimeMillis();
                result.sseEmitter.send(timestamp);
            }
        } catch (IOException e) {
            log.error("IOException!", e);
            return "error";
        }

        return "Succeed!";
    }

    /**
     * 将SseEmitter对象设置成完成
     *
     * @param clientId
     * @return
     */
    @RequestMapping("/end")
    public String completeSseEmitter(String clientId) {
        Result result = sseEmitterMap.get(clientId);
        if (result != null) {
            sseEmitterMap.remove(clientId);
            result.sseEmitter.complete();
        }
        return "Succeed!";
    }

    private class Result {
        public String clientId;
        public long timestamp;
        public SseEmitter sseEmitter;

        public Result(String clientId, long timestamp, SseEmitter sseEmitter) {
            this.clientId = clientId;
            this.timestamp = timestamp;
            this.sseEmitter = sseEmitter;
        }
    }

}
