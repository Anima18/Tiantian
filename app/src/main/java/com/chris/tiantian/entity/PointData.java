package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-8-9
 */
public class PointData {
    public int point;
    public boolean selected;
    public int iconId;
    public boolean enabled = true;

    public PointData(int point, int iconId) {
        this.point = point;
        this.iconId = iconId;
    }
}
