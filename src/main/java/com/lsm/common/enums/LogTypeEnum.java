package com.lsm.common.enums;


public enum LogTypeEnum {
    DEFAULT("DEFAULT"),
    INSERT("INSERT"),
    REMOVE("REMOVE"),
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    SELECT("SELECT");

    private String type;

    LogTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
