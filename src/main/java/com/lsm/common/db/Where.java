package com.lsm.common.db;


public class Where {

    private String relation;
    private String field;
    private String expression;
    private String value;

    public String getRelation() {
        return relation;
    }

    public String getField() {
        return field;
    }

    public String getExpression() {
        return expression;
    }

    public String getValue() {
        return value;
    }

    public Where(String relation, String field, String expression, String value) {
        this.relation = relation;
        this.field = field;
        this.expression = expression;
        this.value = value;
    }

}
