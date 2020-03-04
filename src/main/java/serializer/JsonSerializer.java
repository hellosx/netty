package serializer;

import com.alibaba.fastjson.JSON;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/2 16:29
 */
public class JsonSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON_SERIALIZER;
    }

    @Override
    public byte[] serializer(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserializer(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
