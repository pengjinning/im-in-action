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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * https://github.com/swathisprasad/download-using-streaming-response-body
 * https://github.com/swathisprasad/download-using-streaming-response-body
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-async-output-stream
 */
@RestController
public class DownloadController {

//    @GetMapping("/download")
//    public StreamingResponseBody handle() {
//        //
//        return new StreamingResponseBody() {
//            @Override
//            public void writeTo(OutputStream outputStream) throws IOException {
//                // write...
//            }
//        };
//    }

    /**
     *
     * @return
     */
    @GetMapping("/srb")
    public ResponseEntity<StreamingResponseBody> handleRbe() {

        StreamingResponseBody stream = out -> {

            String msg = "/srb" + " @ " + new Date();

            out.write(msg.getBytes());
        };

        return new ResponseEntity(stream, HttpStatus.OK);
    }


    private final Logger logger = LoggerFactory.getLogger(DownloadController.class);

    @GetMapping (value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> download(final HttpServletResponse response) {

        response.setContentType("application/zip");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename=sample.zip");

        StreamingResponseBody stream = out -> {

            final String home = System.getProperty("user.home");
            final File directory = new File(home + File.separator + "Documents" + File.separator + "sample");
            final ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

            if(directory.exists() && directory.isDirectory()) {
                try {
                    for (final File file : directory.listFiles()) {
                        final InputStream inputStream=new FileInputStream(file);
                        final ZipEntry zipEntry=new ZipEntry(file.getName());
                        zipOut.putNextEntry(zipEntry);
                        byte[] bytes=new byte[1024];
                        int length;
                        while ((length=inputStream.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                        inputStream.close();
                    }
                    zipOut.close();
                } catch (final IOException e) {
                    logger.error("Exception while reading and streaming data {} ", e);
                }
            }
        };
        logger.info("steaming response {} ", stream);
        return new ResponseEntity(stream, HttpStatus.OK);
    }

}
