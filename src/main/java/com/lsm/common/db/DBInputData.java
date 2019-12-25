package com.lsm.common.db;


import java.util.List;

public class DBInputData {
    private String tableName;
    private List<String> colums;
    private List<String> selectColumns;
    private PK pk;
    private List<DBInputDataInfo> dbInputDataInfoList;
    private Where where;
    private Integer createrId;
    private Integer updaterId;

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

    public List<String> getSelectColumns() {
        return selectColumns;
    }

    public DBInputData setSelectColumns(List<String> selectColumns) {
        this.selectColumns = selectColumns;
        return this;
    }

    public PK getPk() {
        return pk;
    }

    public DBInputData setPk(PK pk) {
        this.pk = pk;
        return this;
    }

    public List<DBInputDataInfo> getDbInputDataInfoList() {
        return dbInputDataInfoList;
    }

    public DBInputData setDbInputDataInfoList(List<DBInputDataInfo> dbInputDataInfoList) {
        this.dbInputDataInfoList = dbInputDataInfoList;
        return this;
    }

    public Where getWhere() {
        return where;
    }

    public DBInputData setWhere(Where where) {
        this.where = where;
        return this;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public DBInputData setCreaterId(Integer createrId) {
        this.createrId = createrId;
        return this;
    }

    public Integer getUpdaterId() {
        return updaterId;
    }

    public DBInputData setUpdaterId(Integer updaterId) {
        this.updaterId = updaterId;
        return this;
    }
}
