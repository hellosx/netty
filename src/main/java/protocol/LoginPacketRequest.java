package protocol;

import lombok.Data;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/2 16:21
 */
@Data
public class LoginPacketRequest extends Packet {

    private Integer userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_COMMAND;
    }
}
