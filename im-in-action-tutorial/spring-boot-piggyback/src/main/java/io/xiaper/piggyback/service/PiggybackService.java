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

package io.xiaper.piggyback.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
@Getter
public class PiggybackService {

    private final Random random = new Random();

    private final BlockingQueue<String> messages = new LinkedBlockingQueue<>();

    private final Thread generator = new Thread("Event generator") {

        @Override
        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    Thread.sleep(random.nextInt(5000));

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                messages.offer("At " + new Date());
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
