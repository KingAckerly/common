package com.lsm.common.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lsm.common.annotation.Column;
import com.lsm.common.annotation.Id;

import java.util.Date;

public class BaseEntity {

    @Id(value = "ID")
    private Integer id;
    @Column(value = "CREATER_ID")
    private Integer createrId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(value = "CREATER_TIME")
    private Date createrTime;
    @Column(value = "UPDATER_ID")
    private Integer updaterId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(value = "UPDATER_TIME")
    private Date updaterTime;
    @Column(value = "IS_DELETE")
    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public BaseEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public BaseEntity setCreaterId(Integer createrId) {
        this.createrId = createrId;
        return this;
    }

    public Date getCreaterTime() {
        return createrTime;
    }

    public BaseEntity setCreaterTime(Date createrTime) {
        this.createrTime = createrTime;
        return this;
    }

    public Integer getUpdaterId() {
        return updaterId;
    }

    public BaseEntity setUpdaterId(Integer updaterId) {
        this.updaterId = updaterId;
        return this;
    }

    public Date getUpdaterTime() {
        return updaterTime;
    }

    public BaseEntity setUpdaterTime(Date updaterTime) {
        this.updaterTime = updaterTime;
        return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public BaseEntity setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
        return this;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createrId=" + createrId +
                ", createrTime=" + createrTime +
                ", updaterId=" + updaterId +
                ", updaterTime=" + updaterTime +
                ", isDelete=" + isDelete +
                '}';
    }
}
