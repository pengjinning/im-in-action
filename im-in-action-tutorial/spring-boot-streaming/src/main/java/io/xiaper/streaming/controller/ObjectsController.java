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

package io.xiaper.streaming.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-async-http-streaming
 * https://howtodoinjava.com/spring-boot2/rest/spring-async-controller-responsebodyemitter/
 */
@RestController
public class ObjectsController {

//    @GetMapping("/rbe")
//    public ResponseEntity<ResponseBodyEmitter> handleRbe() {
//        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
//        nonBlockingService(() -> {
//            try {
//                emitter.send(
//                        "/rbe" + " @ " + new Date(), MediaType.TEXT_PLAIN);
//                emitter.complete();
//            } catch (Exception ex) {
//                emitter.completeWithError(ex);
//            }
//        });
//        return new ResponseEntity(emitter, HttpStatus.OK);
//    }

    @GetMapping("/events")
    public ResponseBodyEmitter handle() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        // Save the emitter somewhere..
        return emitter;
    }

//    // In some other thread
//    emitter.send("Hello once");
//
//    // and again later on
//    emitter.send("Hello again");
//
//    // and done at some point
//    emitter.complete();

}
