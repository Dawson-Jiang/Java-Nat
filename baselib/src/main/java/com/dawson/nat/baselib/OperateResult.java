package com.dawson.nat.baselib;

/**
 * @author dawson
 * date:2019/4/18
 */
public class OperateResult<T> {

    public OperateResult() {

    }

    public OperateResult(T data) {
        code = 0;
        this.data = data;
    }

    /**
     * 0成功 其他失败
     */
    private int code = -1;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
