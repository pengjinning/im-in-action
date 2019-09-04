package io.xiaper.im.protocol.response;

import io.xiaper.im.protocol.Packet;
import lombok.Data;

import static io.xiaper.im.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
