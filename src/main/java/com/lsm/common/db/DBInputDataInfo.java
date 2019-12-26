package com.lsm.common.db;


import java.util.List;

public class DBInputDataInfo {
    private String tableName;
    private List<Object> values;
    private PK pk;
    private List<UpdateColumns> updateColumns;
    private Integer updaterId;

    public String getTableName() {
        return tableName;
    }

    public DBInputDataInfo setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public List<Object> getValues() {
        return values;
    }

    public DBInputDataInfo setValues(List<Object> values) {
        this.values = values;
        return this;
    }

    public PK getPk() {
        return pk;
    }

    public DBInputDataInfo setPk(PK pk) {
        this.pk = pk;
        return this;
    }

    public List<UpdateColumns> getUpdateColumns() {
        return updateColumns;
    }

    public DBInputDataInfo setUpdateColumns(List<UpdateColumns> updateColumns) {
        this.updateColumns = updateColumns;
        return this;
    }

    public Integer getUpdaterId() {
        return updaterId;
    }

    public DBInputDataInfo setUpdaterId(Integer updaterId) {
        this.updaterId = updaterId;
        return this;
    }

    @Override
    public String toString() {
        return "DBInputDataInfo{" +
                "tableName='" + tableName + '\'' +
                ", values=" + values +
                ", pk=" + pk +
                ", updateColumns=" + updateColumns +
                ", updaterId=" + updaterId +
                '}';
    }
}
