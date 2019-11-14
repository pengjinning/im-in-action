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

package io.xiaper.mars.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * Simple proxy server for mars
 *
 * Created by zhaoyuan on 16/2/2.
 */
@Component
@ConfigurationProperties(prefix = "mars")
@Slf4j
public class MarsServer {

    @Value("${mars.bind_address}")
    private String host;

    @Value("${mars.bind_port}")
    private Integer port;

    @Value("${mars.adaptor}")
    private String adaptorName;

    @Value("${mars.netty.leak_detector_level}")
    private String leakDetectorLevel;

    @Value("${mars.netty.parent_event_loop_group_thread_count}")
    private Integer parentEventLoopGroupThreadCount;

    @Value("${mars.netty.child_event_loop_group_thread_count}")
    private Integer childEventLoopGroupThreadCount;

    @Value("${mars.netty.max_payload_size}")
    private Integer maxPayloadSize;

    private Channel serverChannel;

    private EventLoopGroup parentEventLoopGroup;

    private EventLoopGroup childEventLoopGroup;

    @PostConstruct
    public void init() throws InterruptedException {
        log.info("starting mars server");

        parentEventLoopGroup = new NioEventLoopGroup(parentEventLoopGroupThreadCount);

        childEventLoopGroup = new NioEventLoopGroup(childEventLoopGroupThreadCount);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(parentEventLoopGroup, childEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new MarsServerInitializer());

        serverChannel = serverBootstrap.bind(host, port).sync().channel();

        log.info("Mars transport started! port {}", port);
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        log.info("Stopping Mars transport!");

        try {
            serverChannel.close().sync();
        } finally {
            childEventLoopGroup.shutdownGracefully();
            parentEventLoopGroup.shutdownGracefully();
        }

        log.info("Mars transport stopped!");
    }

}

