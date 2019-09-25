/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xiaper.longpolling.controller;

import io.xiaper.longpolling.model.DeferredResultResponse;
import io.xiaper.longpolling.service.DeferredResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 参考：
 * https://www.cnblogs.com/theRhyme/p/10846349.html
 */
@RestController
@RequestMapping(value = "/deferred-result")
public class DeferredResultController {

    @Autowired
    private DeferredResultService deferredResultService;

    /**
     * 为了方便测试，简单模拟一个
     * 多个请求用同一个requestId会出问题
     */
    private final String requestId = "haha";

    /**
     * 8s后过期：
     * http://localhost:7080/deferred-result/get?timeout=8000
     *
     * @param timeout
     * @return
     */
    @GetMapping(value = "/get")
    public DeferredResult<DeferredResultResponse> get(@RequestParam(value = "timeout", required = false, defaultValue = "10000") Long timeout) {

        DeferredResult<DeferredResultResponse> deferredResult = new DeferredResult<>(timeout);

        deferredResultService.process(requestId, deferredResult);

        return deferredResult;
    }

    /**
     * 首先在浏览器请求上面的结果，分别测试8s之内和之后，再请求此接口
     * http://localhost:7080/deferred-result/result?desired=失败
     *
     * 设置DeferredResult对象的result属性，模拟异步操作
     * @param desired
     * @return
     */
    @GetMapping(value = "/result")
    public String settingResult(@RequestParam(value = "desired", required = false, defaultValue = "成功") String desired) {

        DeferredResultResponse deferredResultResponse = new DeferredResultResponse();
        //
        if (DeferredResultResponse.Msg.SUCCESS.getDesc().equals(desired)) {
            //
            deferredResultResponse.setCode(HttpStatus.OK.value());
            deferredResultResponse.setMsg(desired);
        } else {
            //
            deferredResultResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            deferredResultResponse.setMsg(DeferredResultResponse.Msg.FAILED.getDesc());
        }

        deferredResultService.settingResult(requestId, deferredResultResponse);

        return "Done";
    }
}