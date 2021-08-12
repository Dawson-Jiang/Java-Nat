package com.dawson.nat.baselib;

import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.ControlClient;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientWrap wrap = (ClientWrap) o;
        return info.equals(wrap.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }
}
