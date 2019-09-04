package io.xiaper.im.protocol.response;

import io.xiaper.im.protocol.Packet;
import lombok.Data;


import static io.xiaper.im.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return LOGIN_RESPONSE;
    }
}
