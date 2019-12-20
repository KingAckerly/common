package com.lsm.common.entity.app;


import com.lsm.common.annotation.Column;
import com.lsm.common.annotation.Table;
import com.lsm.common.entity.BaseEntity;

/**
 * 自定义注解
 */
@Table(value = "app")
public class AppEntity extends BaseEntity {

    @Column(value = "APP_NAME")
    private String appName;
    @Column(value = "APP_INFO")
    private String appInfo;
    @Column(value = "APP_KEY")
    private String appKey;

    public String getAppName() {
        return appName;
    }

    public AppEntity setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getAppInfo() {
        return appInfo;
    }

    public AppEntity setAppInfo(String appInfo) {
        this.appInfo = appInfo;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public AppEntity setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    @Override
    public String toString() {
        return "AppEntity{" +
                "appName='" + appName + '\'' +
                ", appInfo='" + appInfo + '\'' +
                ", appKey='" + appKey + '\'' +
                "} " + super.toString();
    }
}
