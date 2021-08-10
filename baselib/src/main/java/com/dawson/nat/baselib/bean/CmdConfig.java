package com.dawson.nat.baselib.bean;

/**
 * 支持命令 端口配置
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
     * 操作命令的客户端程序启动参数
     */
    private String clientParam;

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

    public String getClientParam() {
        return clientParam;
    }

    public void setClientParam(String clientParam) {
        this.clientParam = clientParam;
    }
}
