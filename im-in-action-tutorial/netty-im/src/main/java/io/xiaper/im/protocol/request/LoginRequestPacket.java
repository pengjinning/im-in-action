package io.xiaper.im.protocol.request;

import io.xiaper.im.protocol.Packet;
import lombok.Data;

import static io.xiaper.im.protocol.command.Command.LOGIN_REQUEST;


@Data
public class LoginRequestPacket extends Packet {

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
