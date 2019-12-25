package com.lsm.common.db;


import java.util.List;

public class DBInputDataInfo {
    private List<Object> values;
    private PK pk;
    private List<String> selectColumns;
    private SaveColumns saveColumns;
    private List<UpdateColumns> updateColumns;
    private Where where;

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

    public List<String> getSelectColumns() {
        return selectColumns;
    }

    public DBInputDataInfo setSelectColumns(List<String> selectColumns) {
        this.selectColumns = selectColumns;
        return this;
    }

    public SaveColumns getSaveColumns() {
        return saveColumns;
    }

    public DBInputDataInfo setSaveColumns(SaveColumns saveColumns) {
        this.saveColumns = saveColumns;
        return this;
    }

    public List<UpdateColumns> getUpdateColumns() {
        return updateColumns;
    }

    public DBInputDataInfo setUpdateColumns(List<UpdateColumns> updateColumns) {
        this.updateColumns = updateColumns;
        return this;
    }

    public Where getWhere() {
        return where;
    }

    public DBInputDataInfo setWhere(Where where) {
        this.where = where;
        return this;
    }
}
