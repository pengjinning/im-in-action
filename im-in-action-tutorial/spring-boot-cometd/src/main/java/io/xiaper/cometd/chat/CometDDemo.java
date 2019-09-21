/*
 * Copyright (c) 2008-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.xiaper.cometd.chat;

import org.cometd.annotation.AnnotationCometDServlet;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerMessage.Mutable;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.DefaultSecurityPolicy;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/**
 * Main class for CometD demo.
 */
public class CometDDemo {
    public static void main(String[] args) throws Exception {
        int port = args.length == 0 ? 8080 : Integer.parseInt(args[0]);

        QueuedThreadPool qtp = new QueuedThreadPool();
        qtp.setMinThreads(5);
        qtp.setMaxThreads(200);
        Server server = new Server(qtp);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setIdleTimeout(120000);
        connector.setAcceptQueueSize(5000);
        server.addConnector(connector);

        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath("src/main/resources/keystore.p12");
        sslContextFactory.setKeyStoreType("pkcs12");
        sslContextFactory.setKeyStorePassword("storepwd");
        ServerConnector sslConnector = new ServerConnector(server, sslContextFactory);
        sslConnector.setPort(port - 80 + 443);
        server.addConnector(connector);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);

        ServletContextHandler context = new ServletContextHandler(contexts, "/", ServletContextHandler.SESSIONS);
        context.setBaseResource(new ResourceCollection(
                Resource.newResource("../../../cometd-demo/src/main/webapp/"),
                Resource.newResource("../../../cometd-javascript/common/src/main/webapp/"),
                Resource.newResource("../../../cometd-javascript/jquery/src/main/webapp/"),
                Resource.newResource("../../../cometd-javascript/examples-jquery/src/main/webapp/"),
                Resource.newResource("../../../cometd-javascript/dojo/src/main/webapp/"),
                Resource.newResource("../../../cometd-javascript/examples-dojo/src/main/webapp/"),
                Resource.newResource("../../../cometd-demo/target/war/work/org.cometd.javascript/cometd-javascript-dojo/"),
                Resource.newResource("../../../cometd-demo/target/war/work/org.cometd.javascript/cometd-javascript-jquery/"))
        );

        ServletHolder dftServlet = context.addServlet(DefaultServlet.class, "/");
        dftServlet.setInitOrder(1);

        // CometD servlet
        AnnotationCometDServlet cometdServlet = new AnnotationCometDServlet();
        ServletHolder cometd = new ServletHolder(cometdServlet);
        context.addServlet(cometd, "/cometd/*");
        cometd.setInitParameter("timeout", "20000");
        cometd.setInitParameter("interval", "100");
        cometd.setInitParameter("maxInterval", "10000");
        cometd.setInitParameter("multiSessionInterval", "5000");
        cometd.setInitParameter("services", ChatService.class.getName());
        cometd.setInitOrder(2);

        ServletHolder demo = context.addServlet(CometDDemoServlet.class, "/demo");
        demo.setInitOrder(3);

        server.start();

        BayeuxServer bayeux = cometdServlet.getBayeux();
        bayeux.setSecurityPolicy(new DefaultSecurityPolicy());

        // Demo lazy messages
        if (Boolean.getBoolean("LAZY")) {
            bayeux.addExtension(new BayeuxServer.Extension() {
                @Override
                public boolean rcv(ServerSession from, Mutable message) {
                    if (message.getChannel().startsWith("/chat/") && !message.isPublishReply() && message.getData().toString().contains("lazy")) {
                        message.setLazy(true);
                    }
                    return true;
                }
            });
        }

        // Demo lazy messages
        if (Boolean.getBoolean("LAZYCHAT")) {
            String channelName = "/chat/demo";
            final ServerChannel chat_demo = bayeux.createChannelIfAbsent(channelName).getReference();
            chat_demo.setLazy(true);
            chat_demo.setPersistent(true);
        }
    }
}
