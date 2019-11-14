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
//@Path("/mars/getconvlist")
//public class GetConversationListCgi {
//
//    private String[][] conDetails = new String[][]{
//            new String[] {"Mars", "0", "STN Discuss"},
//            new String[] {"Mars", "1", "Xlog Discuss"},
//            new String[] {"Mars", "2", "SDT Discuss"}
//    };
//
//    @POST()
//    @Consumes("application/octet-stream")
//    @Produces("application/octet-stream")
//    public Response getconvlist(InputStream is) {
//        try {
//            final Main.ConversationListRequest request = Main.ConversationListRequest.parseFrom(is);
//
//            log.info("request from access_token={}, type={}", request.getAccessToken(), request.getType());
//
//            List<Main.Conversation> conversationList = null;
//            if (request.getType() == Main.ConversationListRequest.FilterType.DEFAULT_VALUE) {
//                conversationList = new LinkedList<Main.Conversation>();
//
//                for (String[] conv : conDetails) {
//                    conversationList.add(Main.Conversation.newBuilder()
//                            .setName(conv[0])
//                            .setTopic(conv[1])
//                            .setNotice(conv[2])
//                            .build());
//                }
//            }
//
//            final Main.ConversationListResponse response = Main.ConversationListResponse.newBuilder()
//                    .addAllList(conversationList).build();
//
//            final StreamingOutput stream = new StreamingOutput() {
//                public void write(OutputStream os) throws IOException {
//                    response.writeTo(os);
//                }
//            };
//            return Response.ok(stream).build();
//
//        } catch (Exception e) {
//            log.info("request invalid", e);
//        }
//
//        return null;
//    }
//}