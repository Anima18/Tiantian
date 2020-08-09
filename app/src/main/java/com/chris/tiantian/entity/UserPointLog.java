package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-8-9
 */
public class UserPointLog {
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
}
