package com.dawson.nat.baselib.net;

import com.google.gson.Gson;

import java.nio.ByteBuffer;

/**
 * 控制命令数据
 *
 * @author dawson
 * date:2018/11/7
 */
public class BaseCmdWrap {
    /**
     * 类型
     */
    private byte type;
    /**
     * 长度 包含类型和长度
     */
    private short length = BASIC_LEN;
    /**
     * 数据
     */
    private byte[] value;
    /**
     * json字符串数据
     */
    private String stringValue;
    /**
     * 数据对象
     */
    private Object objValue;

    public BaseCmdWrap() {
    }

    public BaseCmdWrap(byte type, Object d) {
        init(type, d);
    }

    public BaseCmdWrap(byte type, String d) {
        init(type, d);
    }

    public BaseCmdWrap(byte type, byte[] d) {
        init(type, d);
    }

    private void init(byte type, Object d) {
        this.objValue = d;
        init(type, new Gson().toJson(d));
    }

    private void init(byte type, String d) {
        this.stringValue = d;
        init(type, d.getBytes());
    }

    private void init(byte type, byte[] d) {
        this.type = type;
        this.value = d;
    }

    public byte[] encode() {
        length = BASIC_LEN;
        length = (short) (value == null ? length : length + value.length);
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        byteBuffer.put(type);
        byteBuffer.putShort(length);
        if (value != null) {
            byteBuffer.put(value);
        }
        return byteBuffer.array();
    }

    public BaseCmdWrap decode(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return decode(byteBuffer);
    }

    private static final int BASIC_LEN = 3;

    public BaseCmdWrap decode(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() < BASIC_LEN) {
            return null;
        }
        byteBuffer.mark();
        type = byteBuffer.get();
        length = byteBuffer.getShort();
        if (length < BASIC_LEN) {
            //错误帧 所以数据丢弃
            byteBuffer.position(byteBuffer.limit());
            return null;
        }
        length = (short) (length - BASIC_LEN);
        if (length > byteBuffer.remaining()) {
            byteBuffer.reset();
            return null;
        }
        value = new byte[length];
        byteBuffer.get(value);
        stringValue = new String(value);
        return this;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Object getObjValue() {
        return objValue;
    }

    public void setObjValue(Object objValue) {
        this.objValue = objValue;
    }
}
