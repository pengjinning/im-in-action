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


import io.xiaper.longpolling.service.AsyncRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/async")
public class AsyncController {


    @Autowired
    private AsyncRequestService asyncRequestService;

    /**
     * 先请求：
     * http://localhost:7080/async/get
     *
     * @return
     */
    @GetMapping("/get")
    public String getValue() {

        String msg =  null;
        Future<String> result = null;
        try {
            result = asyncRequestService.getValue();
            msg = result.get(10, TimeUnit.SECONDS);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (result != null){
                result.cancel(true);
            }
        }

        return msg;
    }

    /**
     * 再设置
     * http://localhost:7080/async/set?msg=test
     *
     * @param msg
     */
    @GetMapping("/set")
    public void setValue(@RequestParam(value = "msg") String msg) {
        asyncRequestService.postValue(msg);
    }

}
