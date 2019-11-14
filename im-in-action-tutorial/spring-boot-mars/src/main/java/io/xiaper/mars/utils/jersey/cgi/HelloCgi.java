package io.xiaper.mars.utils.jersey.cgi;

//@Slf4j
//@Path("/mars/hello2")
//public class HelloCgi {
//
//    @GET
//    @Produces("text/plain;charset=UTF-8")
//    @Path("/hello")
//    public String sayHello(@Context SecurityContext sc) {
//        if (sc.isUserInRole("admin"))
//            return "Hello World!";
//        throw new SecurityException("User is unauthorized.");
//    }
//
//    @POST()
//    @Consumes("*/*")
//    @Produces("application/octet-stream")
//    public Response hello(InputStream is) {
//        try {
//            final Main.HelloRequest request = Main.HelloRequest.parseFrom(is);
//
//            log.info("mars3 request from user={}, text={}", request.getUser(), request.getText());
//            if(request.hasDumpContent())
//                log.info("dump content length:%d", request.getDumpContent().size());
//
//            int retCode = 0;
//            String errMsg = "hello mars";
//            int size = 0;
//            if("64KB".equals(request.getText()))
//                size = 64*1024;
//            if("128KB".equals(request.getText()))
//                size = 128*1024;
//            Main.HelloResponse response = null;
//            if(size > 0) {
//                byte[] dump = new byte[size];
//                Random rand = new Random();
//                rand.nextBytes(dump);
//                response = Main.HelloResponse.newBuilder().setRetcode(retCode).setErrmsg(errMsg).setDumpContent(ByteString.copyFrom(dump)).build();
//            } else {
//                response = Main.HelloResponse.newBuilder().setRetcode(retCode).setErrmsg(errMsg).build();
//            }
//            final Main.HelloResponse resp = response;
//
//            final StreamingOutput stream = new StreamingOutput() {
//                public void write(OutputStream os) throws IOException {
//                    resp.writeTo(os);
//                }
//            };
//            return Response.ok(stream).header("Content-Length", response.getSerializedSize()).build();
//
//        } catch (Exception e) {
//            log.info("error mars hello3");
//            log.info("{}", e);
//        }
//
//        return null;
//    }
//}
