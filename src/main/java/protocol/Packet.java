package protocol;

import lombok.Data;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/2 16:16
 */
@Data
public abstract class Packet {

    /** 协议版本 */
    public Byte version = 1;

    /** 指令 */
    public abstract Byte getCommand();

}
