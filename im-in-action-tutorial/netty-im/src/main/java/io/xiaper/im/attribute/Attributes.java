package io.xiaper.im.attribute;

import io.xiaper.im.session.Session;
import io.netty.util.AttributeKey;


public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
