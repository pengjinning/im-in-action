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

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@Slf4j
@RestController
@RequestMapping(value = "/callable")
public class CallableController {

    /**
     * 请求端基本和普通请求一样，但是日志输出就有差别了。主线程完成后副线程才开始。
     * http://localhost:7080/callable/get
     *
     * 日志：
     * 主线程开始！
     * 主线程结束！
     * 副线程开始！
     * 副线程结束！
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/get")
    public Callable<String> testCallable() throws InterruptedException {
        log.info("主线程开始！{}", Thread.currentThread().getName());

        Callable<String> result = () -> {
            log.info("副线程开始！{}", Thread.currentThread().getName());
            Thread.sleep(1000);
            log.info("副线程结束！{}", Thread.currentThread().getName());
            return "SUCCESS";
        };

        log.info("主线程结束！{}", Thread.currentThread().getName());

        return result;
    }

}
