package com.lsm.common.db;


public class WherePO {
    private String relation;
    private String field;
    private String expression;
    private String frontValue;
    private String afterValue;

    public String getRelation() {
        return relation;
    }

    public WherePO setRelation(String relation) {
        this.relation = relation;
        return this;
    }

    public String getField() {
        return field;
    }

    public WherePO setField(String field) {
        this.field = field;
        return this;
    }

    public String getExpression() {
        return expression;
    }

    public WherePO setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public String getFrontValue() {
        return frontValue;
    }

    public WherePO setFrontValue(String frontValue) {
        this.frontValue = frontValue;
        return this;
    }

    public String getAfterValue() {
        return afterValue;
    }

    public WherePO setAfterValue(String afterValue) {
        this.afterValue = afterValue;
        return this;
    }

    @Override
    public String toString() {
        return "WherePO{" +
                "relation='" + relation + '\'' +
                ", field='" + field + '\'' +
                ", expression='" + expression + '\'' +
                ", frontValue='" + frontValue + '\'' +
                ", afterValue='" + afterValue + '\'' +
                '}';
    }
}
