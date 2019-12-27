package com.lsm.common.db;


import java.util.List;

public class DBInputData {
    private String tableName;
    private List<String> colums;
    private PK pk;
    private List<String> selectColumns;
    private Where where;
    private Integer createId;
    private Integer updateId;
    private List<DBInputDataInfo> dbInputDataInfoList;

    public String getTableName() {
        return tableName;
    }

    public DBInputData setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public List<String> getColums() {
        return colums;
    }

    public DBInputData setColums(List<String> colums) {
        this.colums = colums;
        return this;
    }

    public PK getPk() {
        return pk;
    }

    public DBInputData setPk(PK pk) {
        this.pk = pk;
        return this;
    }

    public List<String> getSelectColumns() {
        return selectColumns;
    }

    public DBInputData setSelectColumns(List<String> selectColumns) {
        this.selectColumns = selectColumns;
        return this;
    }

    public Where getWhere() {
        return where;
    }

    public DBInputData setWhere(Where where) {
        this.where = where;
        return this;
    }

    public Integer getCreateId() {
        return createId;
    }

    public DBInputData setCreateId(Integer createId) {
        this.createId = createId;
        return this;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public DBInputData setUpdateId(Integer updateId) {
        this.updateId = updateId;
        return this;
    }

    public List<DBInputDataInfo> getDbInputDataInfoList() {
        return dbInputDataInfoList;
    }

    public DBInputData setDbInputDataInfoList(List<DBInputDataInfo> dbInputDataInfoList) {
        this.dbInputDataInfoList = dbInputDataInfoList;
        return this;
    }

    @Override
    public String toString() {
        return "DBInputData{" +
                "tableName='" + tableName + '\'' +
                ", colums=" + colums +
                ", pk=" + pk +
                ", selectColumns=" + selectColumns +
                ", where=" + where +
                ", createId=" + createId +
                ", updateId=" + updateId +
                ", dbInputDataInfoList=" + dbInputDataInfoList +
                '}';
    }
}
