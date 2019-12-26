package com.lsm.common.db;


import java.util.List;

public class PK {
    private String keyId;
    private Object keyValue;
    private List<Integer> keyValues;

    public String getKeyId() {
        return keyId;
    }

    public PK setKeyId(String keyId) {
        this.keyId = keyId;
        return this;
    }

    public Object getKeyValue() {
        return keyValue;
    }

    public PK setKeyValue(Object keyValue) {
        this.keyValue = keyValue;
        return this;
    }

    public List<Integer> getKeyValues() {
        return keyValues;
    }

    public PK setKeyValues(List<Integer> keyValues) {
        this.keyValues = keyValues;
        return this;
    }

    @Override
    public String toString() {
        return "PK{" +
                "keyId='" + keyId + '\'' +
                ", keyValue=" + keyValue +
                ", keyValues=" + keyValues +
                '}';
    }
}
