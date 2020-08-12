package com.chris.tiantian.entity;

import java.util.Comparator;

/**
 * Created by jianjianhong on 20-8-9
 */
public class UserPointLog implements Comparable<UserPointLog> {
    private String target;
    private String time;
    private String body;
    private int count;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(UserPointLog o) {
        return o.getTime().compareTo(this.time);
    }
}
