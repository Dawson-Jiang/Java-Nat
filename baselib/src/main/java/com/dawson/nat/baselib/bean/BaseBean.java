package com.dawson.nat.baselib.bean;

/**
 * @author dawson
 */
public abstract class BaseBean {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BaseBean() {
    }

    public BaseBean(String id) {
        this();
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseBean) {
            return this.getId() != null && this.getId().equals(((BaseBean) obj).getId());
        } else {
            return false;
        }
    }
}
