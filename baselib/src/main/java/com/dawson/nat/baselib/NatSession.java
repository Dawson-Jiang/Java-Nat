package com.dawson.nat.baselib;

import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.AbstractClient;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.ControlClient;
import com.dawson.nat.baselib.net.TransportClient;

import java.util.Objects;
import java.util.function.Function;

/**
 * 一次连接会话
 *
 * @author dawson
 */
public class NatSession {
    /**
     * 会话id
     */
    private String id;
    private ClientWrap clientWrap1;
    /**
     * 绑定的客户端1 数据转发到客户端2
     */
    protected TransportClient client1;

    private ClientWrap clientWrap2;
    /**
     * 绑定的客户端2 数据转发到客户端1
     */
    protected TransportClient client2;

    private CmdConfig config;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public synchronized void start() {
        if (client2 == null || client1 == null) {
            return;
        }
        if (getState().equals(CommonBean.SessionStateConst.STATE_RUNNING) ||
                getState().equals(CommonBean.SessionStateConst.STATE_CLOSE)) {
            return;
        }
        state = CommonBean.SessionStateConst.STATE_RUNNING;
        client1.registerConnect(aBoolean -> {
            if (!aBoolean) {
                closeSession();
            }
            return true;
        });
        client1.registerOnDataReceived(new Function<byte[], Object>() {
            @Override
            public Object apply(byte[] bytes) {
                GLog.println("receive client1 cmd data:" + bytes.length);
                return client2.sendData(bytes);
            }
        });
        client1.startReceiveDataMethod();

        client2.registerConnect(aBoolean -> {
            if (!aBoolean) {
                closeSession();
            }
            return true;
        });
        client2.registerOnDataReceived(new Function<byte[], Object>() {
            @Override
            public Object apply(byte[] bytes) {
                GLog.println("receive client2 cmd data:" + bytes.length);
                return client1.sendData(bytes);
            }
        });
        client2.startReceiveDataMethod();
    }

    protected Function<NatSession, Object> closeCallback;

    /**
     * 关闭会话回调
     */
    public void registerOnClosed(Function<NatSession, Object> callback) {
        this.closeCallback = callback;
    }

    protected void closeSession() {
        if (state.equals(CommonBean.SessionStateConst.STATE_CLOSE)) {
            return;//防止循环close
        }
        setState(CommonBean.SessionStateConst.STATE_CLOSE);
        client1.close();
        client2.close();
        if (closeCallback != null) {
            closeCallback.apply(this);
        }
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

    public CmdConfig getConfig() {
        return config;
    }

    public void setConfig(CmdConfig config) {
        this.config = config;
    }

    public ClientWrap getClientWrap1() {
        return clientWrap1;
    }

    public void setClientWrap1(ClientWrap clientWrap1) {
        this.clientWrap1 = clientWrap1;
    }

    public ClientWrap getClientWrap2() {
        return clientWrap2;
    }

    public void setClientWrap2(ClientWrap clientWrap2) {
        this.clientWrap2 = clientWrap2;
    }
}
