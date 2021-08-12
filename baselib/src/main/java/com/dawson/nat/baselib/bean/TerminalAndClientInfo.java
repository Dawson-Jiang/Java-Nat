package com.dawson.nat.baselib.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
     * 类型 terminal or user
     */
    private String type;

    /**
     * 名称
     */
    private String name;

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
        return id + "  " + name  + "  " + ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TerminalAndClientInfo that = (TerminalAndClientInfo) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
