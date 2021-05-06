package netty.proto.custom;

import io.netty.buffer.ByteBuf;

/**
 * @author chen
 * @date 2021/4/17
 **/
public abstract class LandboatProto implements Proto {
    /**
     * 魔数占4个bit,主版本占4个bit,副版本占4个节点,命令类型占4个byte
     */
    public static int MAGIC_LEN = 4;
    public static int MAJOR_LEN = 4;
    public static int MINOR_LEN = 4;
    public static int TYPE_LEN = 4;
    public static int BODY_LENGTH = 32;
    public static int HEADER_MINIMUM_LENGTH = 6;
    public static final byte MAGIC = 0b1101;
    public static final byte CURRENT_VERSION = 0b00010001;

    public enum ProtoType {
        /**
         * 方法调用类型
         */
        INVOKE("方法调用", (byte) 0b0001, InvokeProto.class);

        String desc;
        byte type;
        Class<?> clazz;

        ProtoType(String desc, byte type, Class<?> clazz) {
            this.desc = desc;
            this.type = type;
            this.clazz = clazz;
        }

        public static ProtoType valueOf(byte code) {
            if (code == INVOKE.type) {
                return INVOKE;
            }
            throw new IllegalArgumentException("Unknown Proto type");
        }
    }

    @Override
    public void encode(ByteBuf buf) {

    }

    @Override
    public Proto decode(ByteBuf buf) {
        return null;
    }
}
