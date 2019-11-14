/*
* Tencent is pleased to support the open source community by making Mars available.
* Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
*
* Licensed under the MIT License (the "License"); you may not use this file except in 
* compliance with the License. You may obtain a copy of the License at
* http://opensource.org/licenses/MIT
*
* Unless required by applicable law or agreed to in writing, software distributed under the License is
* distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
* either express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*/

package io.xiaper.mars.utils.jersey.cgi;

//@Slf4j
//@Path("/mars/hello")
//public class EchoCgi {
//
//    @POST()
//    @Consumes("application/octet-stream")
//    @Produces("application/octet-stream")
//    public Response hello(InputStream is) {
//        log.info("post hello");
//
//        try {
//            final Main.HelloRequest request = Main.HelloRequest.parseFrom(is);
//
//            log.info("request from user={}, text={}", request.getUser(), request.getText());
//
//            int retCode = 0;
//            String errMsg = "hello, " + request.getUser();
//            final Main.HelloResponse response = Main.HelloResponse.newBuilder().setRetcode(retCode).setErrmsg(errMsg).build();
//
//            final StreamingOutput stream = new StreamingOutput() {
//                public void write(OutputStream os) throws IOException {
//                    response.writeTo(os);
//                }
//            };
//            return Response.ok(stream).build();
//
//        } catch (Exception e) {
//            log.info("{}", e);
//        }
//
//        return null;
//    }
//}