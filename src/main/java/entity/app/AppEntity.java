package entity.app;


import annotation.Column;
import annotation.CustomAnnotation;
import annotation.Id;
import annotation.Table;

/**
 * 自定义注解
 */
//@CustomAnnotation("entity.app.AppEntity")
@Table(value = "app")
public class AppEntity {
    private Integer id;
    private String appName;

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

    @Override
    public String toString() {
        return "AppEntity{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                '}';
    }
}
