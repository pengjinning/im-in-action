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

package io.xiaper.piggyback.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.xiaper.piggyback.service.PiggybackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class PiggybackController {

    @Autowired
    PiggybackService piggybackService;

    @PostMapping("/piggyback")
    public JSONObject postPiggyback() {
        log.info("post piggyback");
        //
        List<String> messages = new LinkedList<>();
        piggybackService.getMessages().drainTo(messages);
        //
        JSONObject jsonObject = new JSONObject();
        //
        JSONArray messagesArray = new JSONArray();
        messagesArray.addAll(messages);
        jsonObject.put("events", messagesArray);
        jsonObject.put("formValid", true);

        return jsonObject;
    }

    @GetMapping("/piggyback")
    public List<String> getPiggyback() {
        log.info("get piggyback");
        //
        List<String> messages = new LinkedList<>();
        piggybackService.getMessages().drainTo(messages);
        //
        return messages;
    }

}
