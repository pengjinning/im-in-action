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

package io.xiaper.socketio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class RouteController {

    @GetMapping("/cloudMsg.html")
    public String cloudMsg(Principal principal, Model model) {

        return "cloudMsg";
    }

    @GetMapping("/createTeam.html")
    public String createTeam(Principal principal, Model model) {

        return "createTeam";
    }

    @GetMapping("/")
    public String index(Principal principal, Model model) {

        return "index";
    }

    @GetMapping("/index.html")
    public String indexHtml(Principal principal, Model model) {

        return "index";
    }

    @GetMapping("/login.html")
    public String login(Principal principal, Model model) {

        return "login";
    }

    @GetMapping("/main.html")
    public String main(Principal principal, Model model) {

        return "main";
    }

    @GetMapping("/netcall_meeting.html")
    public String netcall_meeting(Principal principal, Model model) {

        return "netcall_meeting";
    }

    @GetMapping("/register.html")
    public String register(Principal principal, Model model) {

        return "register";
    }

    @GetMapping("/selectCallMethod.html")
    public String selectCallMethod(Principal principal, Model model) {

        return "selectCallMethod";
    }

    @GetMapping("/speakBan.html")
    public String speakBan(Principal principal, Model model) {

        return "speakBan";
    }

    @GetMapping("/teamInfo.html")
    public String teamInfo(Principal principal, Model model) {

        return "teamInfo";
    }

    @GetMapping("/teamMember.html")
    public String teamMember(Principal principal, Model model) {

        return "teamMember";
    }

    @GetMapping("/test")
    public String test(Principal principal, Model model) {

        return "test";
    }

}
