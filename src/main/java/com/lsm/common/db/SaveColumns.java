package com.lsm.common.db;


import java.util.List;

public class SaveColumns {
    private List<String> colums;
    private List<Object> values;

    public List<String> getColums() {
        return colums;
    }

    public SaveColumns setColums(List<String> colums) {
        this.colums = colums;
        return this;
    }

    public List<Object> getValues() {
        return values;
    }

    public SaveColumns setValues(List<Object> values) {
        this.values = values;
        return this;
    }

    @Override
    public String toString() {
        return "SaveColumns{" +
                "colums=" + colums +
                ", values=" + values +
                '}';
    }
}
