package protocol;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/2 16:22
 */
public interface Command {

    byte LOGIN_COMMAND = 1;

    byte LOGIN_RESPONSE_COMMAND = 2;

    byte MESSAGE_COMMAND = 3;

}
