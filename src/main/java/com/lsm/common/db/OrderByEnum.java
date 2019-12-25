package com.lsm.common.db;


public enum OrderByEnum {
    ASC("ASC"),
    DESC("DESC");

    private String type;

    OrderByEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
