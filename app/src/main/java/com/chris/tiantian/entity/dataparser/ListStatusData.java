package com.chris.tiantian.entity.dataparser;

import java.util.List;

/**
 * Created by jianjianhong on 20-8-9
 */
public class ListStatusData<T> extends StatusData {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
