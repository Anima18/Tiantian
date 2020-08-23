package com.chris.tiantian.entity.eventmessage;

/**
 * Created by jianjianhong on 20-1-14
 */
public class PolicySignalMessage {
    private boolean isReload = false;
    public PolicySignalMessage() {}
    public PolicySignalMessage(boolean isReload) {
        this.isReload = isReload;
    }

    public boolean isReload() {
        return isReload;
    }

    public void setReload(boolean reload) {
        isReload = reload;
    }
}
