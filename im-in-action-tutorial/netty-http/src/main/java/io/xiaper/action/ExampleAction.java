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

package io.xiaper.action;

import io.xiaper.handler.Request;
import io.xiaper.handler.Response;

/**
 * Action对象用于处理业务流程，类似于Servlet对象<br>
 * 在启动服务器前必须将path和此Action加入到ServerSetting的ActionMap中<br>
 * 在浏览器中访问http://localhost:3090/example?a=b既可在页面上显示response a: b
 * @author Looly
 */
public class ExampleAction implements Action{

    /**
     * http://localhost:3090/example?a=b
     *
     * @param request
     * @param response
     */
    @Override
    public void doAction(Request request, Response response) {
        String a = request.getParam("a");
        response.setContent("response a: " + a);
    }



}
