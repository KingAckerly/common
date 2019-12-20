package com.lsm.common.db;


import java.util.List;

public class DBCommonPO {
    private String tableName;
    private PK pk;
    private List<String> selectColumns;
    private SaveColumns saveColumns;
    private List<UpdateColumns> updateColumns;
    private List<Where> wheres;

    public String getTableName() {
        return tableName;
    }

    public DBCommonPO setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public PK getPk() {
        return pk;
    }

    public DBCommonPO setPk(PK pk) {
        this.pk = pk;
        return this;
    }

    public List<String> getSelectColumns() {
        return selectColumns;
    }

    public DBCommonPO setSelectColumns(List<String> selectColumns) {
        this.selectColumns = selectColumns;
        return this;
    }

    public SaveColumns getSaveColumns() {
        return saveColumns;
    }

    public DBCommonPO setSaveColumns(SaveColumns saveColumns) {
        this.saveColumns = saveColumns;
        return this;
    }

    public List<UpdateColumns> getUpdateColumns() {
        return updateColumns;
    }

    public DBCommonPO setUpdateColumns(List<UpdateColumns> updateColumns) {
        this.updateColumns = updateColumns;
        return this;
    }

    public List<Where> getWheres() {
        return wheres;
    }

    public DBCommonPO setWheres(List<Where> wheres) {
        this.wheres = wheres;
        return this;
    }

    @Override
    public String toString() {
        return "DBCommonPO{" +
                "tableName='" + tableName + '\'' +
                ", pk=" + pk +
                ", selectColumns=" + selectColumns +
                ", saveColumns=" + saveColumns +
                ", updateColumns=" + updateColumns +
                ", wheres=" + wheres +
                '}';
    }
}
