package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 19-6-14
 */
public class ActionMenuItem {

    private String title;
    private String value;
    private Integer iconResId;
    private Integer iconColor;
    private boolean make = false;

    public ActionMenuItem(String title) {
        this.title = title;
    }

    public ActionMenuItem(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public ActionMenuItem(String title, Integer iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public ActionMenuItem(String title, Integer iconResId, Integer iconColor) {
        this.title = title;
        this.iconResId = iconResId;
        this.iconColor = iconColor;
    }

    public ActionMenuItem(String title, String value, Integer iconResId) {
        this.title = title;
        this.value = value;
        this.iconResId = iconResId;
    }

    public ActionMenuItem(String title, String value, Integer iconResId, Integer iconColor) {
        this.title = title;
        this.value = value;
        this.iconResId = iconResId;
        this.iconColor = iconColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIconResId() {
        return iconResId;
    }

    public void setIconResId(Integer iconResId) {
        this.iconResId = iconResId;
    }

    public Integer getIconColor() {
        return iconColor;
    }

    public void setIconColor(Integer iconColor) {
        this.iconColor = iconColor;
    }

    public boolean isMake() {
        return make;
    }

    public void setMake(boolean make) {
        this.make = make;
    }
}
