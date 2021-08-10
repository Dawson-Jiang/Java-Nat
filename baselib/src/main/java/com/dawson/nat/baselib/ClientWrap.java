package com.dawson.nat.baselib;

import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.ControlClient;

public class ClientWrap {
    private TerminalAndClientInfo info;
    private ControlClient client;

    public TerminalAndClientInfo getInfo() {
        return info;
    }

    public void setInfo(TerminalAndClientInfo info) {
        this.info = info;
    }

    public ControlClient getClient() {
        return client;
    }

    public void setClient(ControlClient client) {
        this.client = client;
    }
}
