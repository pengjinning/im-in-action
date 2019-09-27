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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Getter
@Service
public class ReverseAjaxService {

    private final Queue<AsyncContext> asyncContexts = new ConcurrentLinkedQueue<>();

    private final Random random = new Random();

    private final Thread generator = new Thread("Event generator") {

        @Override
        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    Thread.sleep(random.nextInt(5000));

                    if (asyncContexts.isEmpty()) {
                        log.info("reverse is empty");
                    }

                    while (!asyncContexts.isEmpty()) {
                        log.info("reverse poll");

                        AsyncContext asyncContext = asyncContexts.poll();
                        //
                        HttpServletResponse peer = (HttpServletResponse) asyncContext.getResponse();

                        peer.getWriter().write("At " + new Date());

                        peer.setStatus(HttpServletResponse.SC_OK);
                        peer.setContentType("application/json");
                        //
                        asyncContext.complete();
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    };

    @PostConstruct
    public void init() throws ServletException {
        generator.start();
    }

    @PreDestroy
    public void destroy() {
        generator.interrupt();
    }

}
