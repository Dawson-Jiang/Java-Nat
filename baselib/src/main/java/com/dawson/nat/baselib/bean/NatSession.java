package com.dawson.nat.baselib.bean;

import com.dawson.nat.baselib.net.AbstractClient;

/**
 * 一次连接会话
 */
public class NatSession {
    /**
     * 会话id
     */
    private String id;
    private String client1Id;
    /**
     * 绑定的客户端1 数据转发到客户端2
     */
    private AbstractClient client1;

    private String client2Id;
    /**
     * 绑定的客户端2 数据转发到客户端1
     */
    private AbstractClient client2;
}
