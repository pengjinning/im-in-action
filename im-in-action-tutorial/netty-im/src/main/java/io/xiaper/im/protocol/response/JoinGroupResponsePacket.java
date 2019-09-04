package io.xiaper.im.protocol.response;

import io.xiaper.im.protocol.Packet;
import lombok.Data;

import static io.xiaper.im.protocol.command.Command.JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_RESPONSE;
    }
}
