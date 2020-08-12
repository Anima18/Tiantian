package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-4-29
 */
public class User {

    /**
     * id : 4
     * name : u-13480806082
     * password : null
     * chName : null
     * phoneNumber : 13480806082
     * token : token
     * level : 初级
     * levelInt : 1
     * msg : null
     */

    private int id;
    private String name;
    private Object password;
    private Object chName;
    private String phoneNumber;
    private String token;
    private String level;
    private int levelInt;
    private String msg;
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getChName() {
        return chName;
    }

    public void setChName(Object chName) {
        this.chName = chName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLevelInt() {
        return levelInt;
    }

    public void setLevelInt(int levelInt) {
        this.levelInt = levelInt;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
