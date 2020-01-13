package io.xiaper.im.protocol.response;


import io.xiaper.im.protocol.Packet;

import static io.xiaper.im.protocol.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }

}
