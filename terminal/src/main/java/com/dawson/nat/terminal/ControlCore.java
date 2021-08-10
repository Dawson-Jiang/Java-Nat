package com.dawson.nat.terminal;

import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.ControlClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.function.Function;

/**
 * 处理 控制命令 的核心逻辑
 *
 * @author dawson
 */
public class ControlCore {
    private SessionManager sessionManager = new SessionManager();
    private ControlClient controlClient = new ControlClient();
    private boolean isConnect;
    private TerminalAndClientInfo clientInfo;
    public static final String SERVER_IP = "192.168.3.4";
    public static final short SERVER_PORT = 5025;
    private List<CmdConfig> configs;
    private Gson gson = new Gson();

    public void start() {
        controlClient.init(SERVER_IP, SERVER_PORT);
        controlClient.addOnDataReceived(new Function<BaseCmdWrap, Object>() {
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
        if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_REG_INFO) {
            //回复的配置信息
            configs = gson.fromJson(baseCmdWrap.getStringValue(), new TypeToken<List<CmdConfig>>() {
            }.getType());
        } else if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_NEW_CONN) {
            JsonObject jsonObject
                    = gson.fromJson(baseCmdWrap.getStringValue(), JsonObject.class);
            String sessionId = jsonObject.getAsJsonObject("sessionId").getAsString();
            String cmd = jsonObject.getAsJsonObject("cmd").getAsString();

            sessionManager.createSession(controlClient, sessionId, findConfig(cmd));
        }
    }

    private CmdConfig findConfig(String cmd) {
        for (CmdConfig config : configs) {
            if (config.getCmd().equals(cmd)) {
                return config;
            }
        }
        return null;
    }

    public void setClientInfo(TerminalAndClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
}
