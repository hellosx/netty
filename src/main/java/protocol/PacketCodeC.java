package protocol;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import serializer.JsonSerializer;
import serializer.Serializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/2 16:34
 */
public class PacketCodeC {

    public static volatile PacketCodeC INSTANCE;

    public static Map<Byte, Class<? extends Packet>> packetMap = null;

    public static Map<Byte, Serializer> serializerMap = null;

    public static final int MAGIC_NUM = 0x12345678;

    static {
        serializerMap = new ConcurrentHashMap<>();
        Serializer serializer = new JsonSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);

        packetMap = new ConcurrentHashMap<>();
        packetMap.put(Command.LOGIN_COMMAND, LoginPacketRequest.class);
        packetMap.put(Command.LOGIN_RESPONSE_COMMAND, LoginPacketResponse.class);
        packetMap.put(Command.MESSAGE_COMMAND, MessagePackRequest.class);
    }

    public static PacketCodeC getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (PacketCodeC.class) {
                if (INSTANCE == null) {
                    return new PacketCodeC();
                }
            }
        }
        return INSTANCE;
    }

    private PacketCodeC(){}

    public ByteBuf enCode(ByteBuf byteBuf, Packet packet) {
        // 写入魔数
        byteBuf.writeInt(MAGIC_NUM);

        // 写入版本号
        byteBuf.writeByte(packet.getVersion());

        // 写入序列化算法，使用JSON序列化
        Serializer serializer = Serializer.DEFAULT;
        byteBuf.writeByte(serializer.getSerializerAlgorithm());

        // 写入指令
        byteBuf.writeByte(packet.getCommand());

        byte[] data = serializer.serializer(packet);

        // 写入数据长度
        byteBuf.writeInt(data.length);

        // 写入数据
        byteBuf.writeBytes(data);

        return byteBuf;
    }

    public ByteBuf enCode(Packet packet) {

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 写入魔数
        byteBuf.writeInt(MAGIC_NUM);

        // 写入版本号
        byteBuf.writeByte(packet.getVersion());

        // 写入序列化算法，使用JSON序列化
        Serializer serializer = Serializer.DEFAULT;
        byteBuf.writeByte(serializer.getSerializerAlgorithm());

        // 写入指令
        byteBuf.writeByte(packet.getCommand());

        byte[] data = serializer.serializer(packet);

        // 写入数据长度
        byteBuf.writeInt(data.length);

        // 写入数据
        byteBuf.writeBytes(data);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔数
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 获取序列化算法
        byte serializerType = byteBuf.readByte();

        Serializer serializer = serializerMap.get(serializerType);

        // 获取指令
        byte command = byteBuf.readByte();
        Class<? extends Packet> clazz = packetMap.get(command);

        // 跳过数据长度
        int length = byteBuf.readInt();

        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        // 获取数据
        return serializer.deserializer(clazz, data);
    }

    public static void main(String[] args) {
        PacketCodeC packetCodeC = new PacketCodeC();

        Packet packet = new LoginPacketRequest();
        ((LoginPacketRequest) packet).setUserName("name");
        ((LoginPacketRequest) packet).setPassword("paw");

        ByteBuf byteBuf = packetCodeC.enCode(packet);
        Packet packet1 = packetCodeC.decode(byteBuf);

        System.out.println(JSON.toJSONString(packet1));
    }
}
