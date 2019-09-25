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

package io.xiaper.longpolling.service;

import io.xiaper.longpolling.model.DeferredResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
public class DeferredResultService {

    private Map<String, Consumer<DeferredResultResponse>> taskMap;

    public DeferredResultService() {
        taskMap = new ConcurrentHashMap<>();
    }

    /**
     * 将请求id与setResult映射
     *
     * @param requestId
     * @param deferredResult
     */
    public void process(String requestId, DeferredResult<DeferredResultResponse> deferredResult) {

        // 请求超时的回调函数
        deferredResult.onTimeout(() -> {
            //
            taskMap.remove(requestId);
            //
            DeferredResultResponse deferredResultResponse = new DeferredResultResponse();
            deferredResultResponse.setCode(HttpStatus.REQUEST_TIMEOUT.value());
            deferredResultResponse.setMsg(DeferredResultResponse.Msg.TIMEOUT.getDesc());
            //
            deferredResult.setResult(deferredResultResponse);
        });

        Optional.ofNullable(taskMap)
                .filter(t -> !t.containsKey(requestId))
                .orElseThrow(() -> new IllegalArgumentException(String.format("requestId=%s is existing", requestId)));

        // 这里的Consumer，就相当于是传入的DeferredResult对象的地址
        // 所以下面settingResult方法中"taskMap.get(requestId)"就是Controller层创建的对象
        taskMap.putIfAbsent(requestId, deferredResult::setResult);
    }

    /**
     * 这里相当于异步的操作方法
     * 设置DeferredResult对象的setResult方法
     *
     * @param requestId
     * @param deferredResultResponse
     */
    public void settingResult(String requestId, DeferredResultResponse deferredResultResponse) {

        if (taskMap.containsKey(requestId)) {

            Consumer<DeferredResultResponse> deferredResultResponseConsumer = taskMap.get(requestId);

            // 这里相当于DeferredResult对象的setResult方法
            deferredResultResponseConsumer.accept(deferredResultResponse);

            taskMap.remove(requestId);
        }
    }

}
