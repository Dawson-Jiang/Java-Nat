package com.dawson.nat.terminal;

import com.dawson.nat.baselib.GLog;
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
    public static String SERVER_IP = "";
    public static short SERVER_PORT;
    private List<CmdConfig> configs;
    private Gson gson = new Gson();

    public void init(Object... params) {
        if (params != null && params.length > 1) {
            SERVER_IP = (String) params[0];
            SERVER_PORT = (short) params[1];
        }
        controlClient.init(SERVER_IP, SERVER_PORT);
    }

    public void start() {
        controlClient.addOnDataReceived(baseCmdWrap -> {
            handleData(baseCmdWrap);
            return true;
        });
        controlClient.registerConnect(aBoolean -> {
            //disconnect->connect
            if (!isConnect && aBoolean) {
                //注册信息
                GLog.println("terminal controlClient connect success");
                rgInfo();
            }
            isConnect = aBoolean;
            return true;
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
            GLog.println("reg info success");
            configs = gson.fromJson(baseCmdWrap.getStringValue(), new TypeToken<List<CmdConfig>>() {
            }.getType());
        } else if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_NEW_SESSION) {
            JsonObject jsonObject
                    = gson.fromJson(baseCmdWrap.getStringValue(), JsonObject.class);
            String sessionId = jsonObject.get("sessionId").getAsString();
            String cmd = jsonObject.get("cmd").getAsString();
            GLog.println("new session id:" + sessionId + " cmd:" + cmd);
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
        this.clientInfo.setType(CommonBean.ClientType.CLIENT_TERMINAL);
    }
}
