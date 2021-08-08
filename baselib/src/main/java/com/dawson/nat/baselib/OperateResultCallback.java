package com.dawson.nat.baselib;

/**
 * @author dawson
 * date:2019/4/18
 */
public interface OperateResultCallback<T> {
    /**
     * 回调函数
     *
     * @param result
     */
    void callback(OperateResult<T> result);
}
