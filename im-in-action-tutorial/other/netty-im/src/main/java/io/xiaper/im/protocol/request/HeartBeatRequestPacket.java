package io.xiaper.im.protocol.request;


import io.xiaper.im.protocol.Packet;

import static io.xiaper.im.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }

}
