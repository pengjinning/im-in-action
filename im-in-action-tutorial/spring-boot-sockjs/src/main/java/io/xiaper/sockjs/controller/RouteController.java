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

package io.xiaper.sockjs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class RouteController {

    @GetMapping("/")
    public String index(Model model) {

        return "index";
    }

    @GetMapping("/index.html")
    public String indexHtml(Model model, @RequestParam(value = "code") String code) {

        model.addAttribute("code", code);

        return "index";
    }

    @GetMapping("/piggy")
    public String piggy(Model model) {

        return "piggy";
    }

    @GetMapping("/mq")
    public String mq(Model model) {

        return "mq";
    }

    @GetMapping("/mqf")
    public String mqf(Model model) {

        return "mqf";
    }

}
