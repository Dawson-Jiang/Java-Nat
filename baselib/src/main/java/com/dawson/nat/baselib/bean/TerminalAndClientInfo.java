package com.dawson.nat.baselib.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 终端或客户端信息
 *
 * @author dawson
 */
public class TerminalAndClientInfo implements Serializable, Comparable<TerminalAndClientInfo> {
    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;
    /**
     * mac
     */
    private String mac;
    private String ip;
    private short port;

    public TerminalAndClientInfo() {
    }


    @Override
    public int compareTo(TerminalAndClientInfo o) {
        return o.name.compareTo(this.name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public short getPort() {
        return port;
    }

    public void setPort(short port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + mac + "," + ip;
    }
}
