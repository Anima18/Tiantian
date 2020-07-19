package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-7-19
 */
public class UserData {
    private int err_code;
    private String err_msg;
    private User user;

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
