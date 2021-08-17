package com.dawson.nat.baselib.bean;

/**
 * 支持命令 端口配置
 *
 * @author dawson
 */
public class CmdConfig {
    /**
     * 命令类型 如ssh
     */
    private String cmd;
    /**
     * 命令终端端口 如22
     */
    private short port;
    /**
     * 操作命令的客户端程序 启动路径
     */
    private String client;
    /**
     * 映射的端口 如22->10248 建议10000-20000
     */
    private short clientPort;
    /**
     * 第三方客户端程序启动后操作说明
     */
    private String startDes;

    public CmdConfig() {
    }

    public CmdConfig(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public short getPort() {
        return port;
    }

    public void setPort(short port) {
        this.port = port;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public short getClientPort() {
        return clientPort;
    }

    public void setClientPort(short clientPort) {
        this.clientPort = clientPort;
    }

    public String getStartDes() {
        return startDes;
    }

    public void setStartDes(String startDes) {
        this.startDes = startDes;
    }
}
