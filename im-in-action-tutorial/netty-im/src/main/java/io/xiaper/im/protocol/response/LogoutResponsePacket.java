package io.xiaper.im.protocol.response;

import io.xiaper.im.protocol.Packet;
import lombok.Data;

import static io.xiaper.im.protocol.command.Command.LOGOUT_RESPONSE;


@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
