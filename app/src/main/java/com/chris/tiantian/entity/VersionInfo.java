package com.chris.tiantian.entity;

public class VersionInfo implements Comparable<VersionInfo> {

    /**
     * id : 2
     * applicationName : ttapp
     * packageName : com.tt.mapp
     * installFileName : protect.eye_9.6_88.apk
     * platform : Android
     * link : http://114.67.65.199:8080/software/Android/protect.eye_9.6_88.apk
     * time : 2020.01.15 11:00:00
     * timeInLong : 39489347
     * changes : 1.添加了多种策略
     2.细化了信号的提示
     3.增加了很多新特性
     protect.eye_9.6_88.apk
     * version : 1.1.0
     * releaseType : A/B test
     */

    private int id;
    private String applicationName;
    private String packageName;
    private String installFileName;
    private String platform;
    private String link;
    private String time;
    private int timeInLong;
    private String changes;
    private String version;
    private String releaseType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getInstallFileName() {
        return installFileName;
    }

    public void setInstallFileName(String installFileName) {
        this.installFileName = installFileName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTimeInLong() {
        return timeInLong;
    }

    public void setTimeInLong(int timeInLong) {
        this.timeInLong = timeInLong;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    @Override
    public int compareTo(VersionInfo o) {
        return version.compareTo(o.getVersion());
    }
}
