package com.chris.tiantian.entity.dataparser;

/**
 * Created by jianjianhong on 20-8-9
 */
public class ObjectStatusData<T> extends StatusData {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
