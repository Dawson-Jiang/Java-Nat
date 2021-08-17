package com.dawson.nat.user;

import com.dawson.nat.baselib.Common;
import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.ControlClient;
import com.dawson.nat.baselib.net.SocketServer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.channels.SocketChannel;
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
    private TerminalAndClientInfo userInfo;
    private Gson gson = new Gson();
    private SessionManager sessionManager = new SessionManager();
    public static final String SERVER_IP = "192.168.0.3";
    public static final short SERVER_PORT = 5025;

    /**
     * 当前需连接操作的终端 同一时刻只能连接操作一个终端
     */
    private String currentTerminal;

    /**
     * @param t_num 终端序号
     */
    public void setCurrentTerminal(int t_num) {
        if (teminals == null || teminals.size() <= 0) {
            getTerminals();
        }
        if ((t_num - 1) < 0 || (t_num - 1) >= teminals.size()) {
            GLog.println("终端序号错误：" + t_num);
        }
        TerminalAndClientInfo tinfo = teminals.get(t_num - 1);
        setCurrentTerminal(tinfo.getId());
    }

    public void setCurrentTerminal(String currentTerminal) {
        this.currentTerminal = currentTerminal;
        GLog.println("已经连接终端:" + currentTerminal + "请启动第三方客户端连接操作该终端");
        printConfigs();
    }

    /**
     * 启动客户端 连接控制服务
     */
    public void start() {
        controlClient.init(SERVER_IP, SERVER_PORT);
        controlClient.addOnDataReceived(baseCmdWrap -> {
            handleData(baseCmdWrap);
            return true;
        });
        controlClient.registerConnect(aBoolean -> {
            //disconnect->connect
            if (!isConnect && aBoolean) {
                GLog.println("user controlClient connect success");
                //注册信息
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
        BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_REG_INFO, userInfo);
        controlClient.sendData(baseCmdWrap);
    }

    public void getTerminals() {
        BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_GET_TERMINALS, "");
        controlClient.sendData(baseCmdWrap);
    }

    /**
     * 终端列表
     */
    List<TerminalAndClientInfo> teminals;
    /**
     * cmd 服务配置 如shell
     */
    List<CmdConfig> configs;

    private void handleData(BaseCmdWrap baseCmdWrap) {
        if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_GET_TERMINALS) {
            teminals = new Gson().fromJson(baseCmdWrap.getStringValue(), new TypeToken<List<TerminalAndClientInfo>>() {
            }.getType());
            //打印信息
            printInfos();
        } else if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_REG_INFO) {
            //回复配置信息
            configs = gson.fromJson(baseCmdWrap.getStringValue(), new TypeToken<List<CmdConfig>>() {
            }.getType());
            //启动监听服务
            for (CmdConfig config : configs) {
                startListen(config);
            }
            //打印信息
            printConfigs();
        }
    }

    public void setUserInfo(TerminalAndClientInfo userInfo) {
        this.userInfo = userInfo;
        this.userInfo.setType(CommonBean.ClientType.CLIENT_USER);
    }

    private void startListen(final CmdConfig config) {
        SocketServer thirdServer = new SocketServer();
        thirdServer.init(config.getClientPort());
        thirdServer.registerOnClientConnect(socketChannel -> {
            GLog.println("new " + config.getCmd() + " conn");
            if (Common.isEmpty(currentTerminal)) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GLog.println("new thirdClient conn but not appoint terminal");
                return true;
            }
            sessionManager.createSession(controlClient, socketChannel, currentTerminal,userInfo.getId(), config);
            return true;
        });
        thirdServer.start();
    }

    private void printInfos() {
        System.out.println("number      id            name   ip");
        for (int i = 0; i < teminals.size(); i++) {
            System.out.println(" " + (i + 1) + "     " + teminals.get(i));
        }
    }

    public void printConfigs() {
        System.out.println("支持的服务及端口:");
        if (configs == null || configs.size() <= 0) {
            System.out.print("无");
            return;
        }
        for (int i = 0; i < configs.size(); i++) {
            System.out.println(configs.get(i).getCmd() + " " + configs.get(i).getPort() + "->" + configs.get(i).getClientPort());
        }
    }
}
