package protocol;

import lombok.Data;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/4 10:49
 */
@Data
public class LoginPacketResponse extends Packet{

    private Boolean success;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE_COMMAND;
    }
}
