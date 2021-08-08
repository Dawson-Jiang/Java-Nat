package com.dawson.nat.baselib.net;

import java.util.function.Function;

public class ControlClient extends AbstractClient {
    private Function<BaseCmdWrap, Object> dataCallback2;

    /**
     * 客户端收到数据回调
     */
    public void registerOnDataReceived2(Function<BaseCmdWrap, Object> callback) {
        this.dataCallback2 = callback;
    }

    @Override
    public void handleData() {
        BaseCmdWrap cmd = new BaseCmdWrap();
        cmd.decode(buffer);
        if (dataCallback2 != null) {
            dataCallback2.apply(cmd);
        }
    }
}
