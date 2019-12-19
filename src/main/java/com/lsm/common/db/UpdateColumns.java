package com.lsm.common.db;


public class UpdateColumns {
    private String column;
    private Object value;

    public String getColumn() {
        return column;
    }

    public UpdateColumns setColumn(String column) {
        this.column = column;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public UpdateColumns setValue(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "UpdateColumns{" +
                "column='" + column + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
