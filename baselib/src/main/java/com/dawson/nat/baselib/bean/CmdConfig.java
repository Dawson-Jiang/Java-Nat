package com.dawson.nat.baselib.bean;

/**
 * 支持命令 端口配置
 */
public class CmdConfig {
    /**
     * 命令类型 如ssh
     */
    private String cmd;
    /**
     * 命令终端端口 如22
     */
    private String port;
    /**
     * 操作命令的客户端程序 启动路径
     */
    private String client;
    /**
     * 操作命令的客户端程序启动参数，ip和端口需要使用占位符
     */
    private String clientParam;

    /**
     * 服务端对ip
     */
    private String serverIp;

    /**
     * 服务端对终端监听端口 5030-5039
     */
    private String serverPort1;
    /**
     * 服务端对用户客户端监听端口 5040-5049
     */
    private String serverPort2;

    public CmdConfig() {
    }

    public CmdConfig(String cmd) {
        this.cmd = cmd;
    }
}
