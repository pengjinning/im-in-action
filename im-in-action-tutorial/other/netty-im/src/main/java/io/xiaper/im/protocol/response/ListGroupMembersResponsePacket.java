package io.xiaper.im.protocol.response;

import io.xiaper.im.protocol.Packet;
import io.xiaper.im.session.Session;
import lombok.Data;

import java.util.List;

import static io.xiaper.im.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;


@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
