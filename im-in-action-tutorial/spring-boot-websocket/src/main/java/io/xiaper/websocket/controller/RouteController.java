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

package io.xiaper.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class RouteController {

    @GetMapping("/")
    public String index(Principal principal, Model model) {

        return "index";
    }

    @GetMapping("/text")
    public String text(Principal principal, Model model) {

        return "text";
    }

    @GetMapping("/binary")
    public String binary(Principal principal, Model model) {

        return "binary";
    }

    @GetMapping("/protobuf")
    public String protobuf(Principal principal, Model model) {

        return "protobuf";
    }

    @GetMapping("/performance")
    public String performance(Principal principal, Model model) {

        return "performance";
    }

}
