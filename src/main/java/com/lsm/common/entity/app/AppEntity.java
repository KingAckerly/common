package com.lsm.common.entity.app;


import com.lsm.common.annotation.Column;
import com.lsm.common.annotation.Id;
import com.lsm.common.annotation.Table;
import com.lsm.common.entity.BaseEntity;

/**
 * 自定义注解
 */
@Table(value = "app")
public class AppEntity extends BaseEntity {
    private Integer id;
    private String appName;
    private String appInfo;
    private String appKey;

    @Id(value = "ID")
    public Integer getId() {
        return id;
    }

    public AppEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    @Column(value = "APP_NAME")
    public String getAppName() {
        return appName;
    }

    public AppEntity setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    @Column(value = "APP_INFO")
    public String getAppInfo() {
        return appInfo;
    }

    public AppEntity setAppInfo(String appInfo) {
        this.appInfo = appInfo;
        return this;
    }

    @Column(value = "APP_KEY")
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
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", appInfo='" + appInfo + '\'' +
                ", appKey='" + appKey + '\'' +
                '}';
    }
}
