package com.dawson.nat.baselib;

import com.dawson.nat.baselib.bean.CommonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dawson
 */
public abstract class NatSessionManage {
    protected List<NatSession> sessions = new ArrayList<>();

    public void cleanSession() {
        for (int i = 0; i < sessions.size(); ) {
            if (sessions.get(i).getState().equals(CommonBean.SessionStateConst.STATE_CLOSE)) {
                sessions.remove(i);
            } else {
                i++;
            }
        }
    }
}
