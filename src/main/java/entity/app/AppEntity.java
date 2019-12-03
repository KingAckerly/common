package entity.app;


import annotation.Column;
import annotation.Id;
import annotation.Table;

/**
 * 自定义注解
 */
@Table(value = "app")
public class AppEntity {
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
