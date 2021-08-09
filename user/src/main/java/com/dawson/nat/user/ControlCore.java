package com.dawson.nat.user;

import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.ControlClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

/**
 * 处理 控制命令 的核心逻辑
 *
 * @author dawson
 */
public class ControlCore {
    private ControlClient controlClient = new ControlClient();
    private boolean isConnect;
    private TerminalAndClientInfo clientInfo;

    /**
     * 启动客户端 连接控制服务
     */
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

    public void getTerminals() {
        BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_GET_TERMINALS, "");
        controlClient.sendData(baseCmdWrap);
    }

    public void getCmdConfigs() {
        BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_GET_CMD_CONFIGS, "");
        controlClient.sendData(baseCmdWrap);
    }

    /**
     * 发起新连接
     */
    public void newClient(int num, String cmd) {
        TerminalAndClientInfo clientInfo = infos.get(num);

        JsonObject req = new JsonObject();
        req.addProperty("client_id", clientInfo.getId());
        req.addProperty("cmd", cmd);
        BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_NEW_CONN, req);
        controlClient.sendData(baseCmdWrap);
        //带参数启动客户端
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CmdConfig config = null;
        for (CmdConfig c : configs) {
            if (c.getCmd().equals(cmd)) {
                config = c;
                break;
            }
        }
        //带参数启动客户端
        try {
            Runtime.getRuntime().exec("C:/Windows/System32/cmd.exe /c " + config.getClientParam());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<TerminalAndClientInfo> infos;
    List<CmdConfig> configs;

    private void handleData(BaseCmdWrap baseCmdWrap) {
        if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_GET_TERMINALS) {
            infos = new Gson().fromJson(baseCmdWrap.getStringValue(), new TypeToken<List<TerminalAndClientInfo>>() {
            }.getType());
            //打印信息
            printInfos();
        } else if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_GET_CMD_CONFIGS) {
            configs = new Gson().fromJson(baseCmdWrap.getStringValue(), new TypeToken<List<CmdConfig>>() {
            }.getType());
            //打印信息
            printConfigs();
        }
    }

    public void setClientInfo(TerminalAndClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    private void printInfos() {
        GLog.println("number   id    name   mac    ip");
        for (int i = 0; i < infos.size(); i++) {
            GLog.println(i + " " + infos.get(i));
        }
    }

    private void printConfigs() {
        System.out.print("support cmd:");
        for (int i = 0; i < configs.size(); i++) {
            System.out.print(configs.get(i).getCmd() + " ");
        }
    }
}
