package serializer;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/2 16:24
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * 获取序列号算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * 对象转二进制
     * @param object
     * @return
     */
    byte[] serializer(Object object);

    /**
     * 二进制转对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserializer(Class<T> clazz, byte[] bytes);
}
