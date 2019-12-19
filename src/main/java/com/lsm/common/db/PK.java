package com.lsm.common.db;


public class PK {
    private String keyId;
    private Object keyValue;

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

    @Override
    public String toString() {
        return "PK{" +
                "keyId=" + keyId +
                ", keyValue=" + keyValue +
                '}';
    }
}
