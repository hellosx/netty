package protocol;

import lombok.Data;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/3 17:14
 */
@Data
public class MessagePackRequest extends Packet {

    private String content;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_COMMAND;
    }
}
