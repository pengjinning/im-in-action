package io.xiaper.im.protocol.request;

import io.xiaper.im.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.xiaper.im.protocol.command.Command.GROUP_MESSAGE_REQUEST;


@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
