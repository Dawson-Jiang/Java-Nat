package com.dawson.nat.baselib;

import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.AbstractClient;
import com.dawson.nat.baselib.net.BaseCmdWrap;

import java.util.Objects;
import java.util.function.Function;

/**
 * 一次连接会话
 */
public class NatSession {
    /**
     * 会话id
     */
    private String id;
    private TerminalAndClientInfo client1Info;
    /**
     * 绑定的客户端1 数据转发到客户端2
     */
    protected AbstractClient client1;

    private TerminalAndClientInfo client2Info;
    /**
     * 绑定的客户端2 数据转发到客户端1
     */
    protected AbstractClient client2;

    /**
     * 会话状态
     */
    private String state = CommonBean.SessionStateConst.STATE_READY;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TerminalAndClientInfo getClient1Info() {
        return client1Info;
    }

    public void setClient1Info(TerminalAndClientInfo client1Info) {
        this.client1Info = client1Info;
    }

    public TerminalAndClientInfo getClient2Info() {
        return client2Info;
    }

    public void setClient2Info(TerminalAndClientInfo client2Info) {
        this.client2Info = client2Info;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    protected Function<NatSession,Object>  closeCallback;

    /**
     * 客户端收到数据回调
     */
    public void registerOnClosed(Function<NatSession, Object> callback) {
        this.closeCallback = callback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NatSession that = (NatSession) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
