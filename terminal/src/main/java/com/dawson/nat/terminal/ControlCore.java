package com.dawson.nat.terminal;

import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.ControlClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.function.Function;

/**
 * 处理 控制命令 的核心逻辑
 */
public class ControlCore {
    private SessionManager sessionManager = new SessionManager();
    private ControlClient controlClient = new ControlClient();
    private boolean isConnect;
    private TerminalAndClientInfo clientInfo;

    public void start() {
        controlClient.init("192.168.3.4", 5025);
        controlClient.registerOnDataReceived(new Function<BaseCmdWrap, Object>() {
            @Override
            public Object apply(BaseCmdWrap baseCmdWrap) {
                handleData(baseCmdWrap);
                return true;
            }
        });
        controlClient.registerConnect(new Function<Boolean, Object>() {
            @Override
            public Object apply(Boolean aBoolean) {
                //disconnect->connect
                if (!isConnect && aBoolean) {
                    //注册信息
                    rgInfo();
                }
                isConnect = aBoolean;
                return true;
            }
        });
        controlClient.start();
    }

    /**
     * 注册信息
     */
    private void rgInfo() {
        BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_REG_INFO, clientInfo);
        controlClient.sendData(baseCmdWrap);
    }

    private void handleData(BaseCmdWrap baseCmdWrap) {
        if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_NEW_CONN) {
            JsonObject jsonObject
                    = new Gson().fromJson(baseCmdWrap.getStringValue(), JsonObject.class);
            short p = jsonObject.getAsJsonObject("port").getAsShort();
            short sp = jsonObject.getAsJsonObject("serverPort").getAsShort();
            sessionManager.createSession(p, sp);
        }
    }

    public void setClientInfo(TerminalAndClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
}
