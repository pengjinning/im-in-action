package io.xiaper.im.protocol.response;

import io.xiaper.im.protocol.Packet;
import lombok.Data;

import static io.xiaper.im.protocol.command.Command.QUIT_GROUP_RESPONSE;


@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_RESPONSE;
    }
}
