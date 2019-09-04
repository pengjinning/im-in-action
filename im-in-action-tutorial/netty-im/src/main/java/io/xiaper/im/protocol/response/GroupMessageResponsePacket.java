package io.xiaper.im.protocol.response;

import io.xiaper.im.protocol.Packet;
import io.xiaper.im.session.Session;
import lombok.Data;

import static io.xiaper.im.protocol.command.Command.GROUP_MESSAGE_RESPONSE;


@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
