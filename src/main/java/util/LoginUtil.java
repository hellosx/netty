package util;


import io.netty.channel.Channel;
import io.netty.util.Attribute;

import static protocol.Attributes.LOGIN_FLAG;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/4 11:40
 */
public class LoginUtil {

    public static void markLogin(Channel channel) {
        channel.attr(LOGIN_FLAG).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> attribute = channel.attr(LOGIN_FLAG);
        return attribute.get() != null;
    }

}
