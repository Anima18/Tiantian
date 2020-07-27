package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-4-29
 */
public class UserPoint {

    /**
     * userId : 4
     * id : 1
     * points : 1000
     */

    private int userId;
    private int id;
    private int freedomPoints;
    private int lockedPoints;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFreedomPoints() {
        return freedomPoints;
    }

    public void setFreedomPoints(int freedomPoints) {
        this.freedomPoints = freedomPoints;
    }

    public int getLockedPoints() {
        return lockedPoints;
    }

    public void setLockedPoints(int lockedPoints) {
        this.lockedPoints = lockedPoints;
    }
}
