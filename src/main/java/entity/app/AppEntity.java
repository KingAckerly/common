package entity.app;


import annotation.CustomAnnotation;

/**
 * 自定义注解
 */
@CustomAnnotation("entity.app.AppEntity")
public class AppEntity {
    private Integer id;
    private String appName;

    public Integer getId() {
        return id;
    }

    public AppEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public AppEntity setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    @Override
    public String toString() {
        return "AppEntity{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                '}';
    }
}
