package protocol;

import io.netty.util.AttributeKey;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/4 11:37
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN_FLAG = AttributeKey.newInstance("LOGIN");

}
