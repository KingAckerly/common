package com.lsm.common.db;


import java.util.ArrayList;
import java.util.List;

public class Where {

    private String relation;
    private String field;
    private String expression;
    private String frontValue;
    private String afterValue;
    private List<Where> wheres = new ArrayList<Where>();

    public String getRelation() {
        return relation;
    }

    public Where setRelation(String relation) {
        this.relation = relation;
        return this;
    }

    public String getField() {
        return field;
    }

    public Where setField(String field) {
        this.field = field;
        return this;
    }

    public String getExpression() {
        return expression;
    }

    public Where setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public String getFrontValue() {
        return frontValue;
    }

    public Where setFrontValue(String frontValue) {
        this.frontValue = frontValue;
        return this;
    }

    public String getAfterValue() {
        return afterValue;
    }

    public Where setAfterValue(String afterValue) {
        this.afterValue = afterValue;
        return this;
    }

    public List<Where> getWheres() {
        return wheres;
    }

    public Where setWheres(List<Where> wheres) {
        this.wheres = wheres;
        return this;
    }

    public void and(String field, String expression) {
        this.wheres.add(new Where().setRelation("and").setField(field).setExpression(expression));
    }

    public void and(String field, String expression, String frontValue) {
        this.wheres.add(new Where().setRelation("and").setField(field).setExpression(expression).setFrontValue(frontValue));
    }

    public void and(String field, String expression, String frontValue, String afterValue) {
        this.wheres.add(new Where().setRelation("and").setField(field).setExpression(expression).setFrontValue(frontValue).setAfterValue(afterValue));
    }

    public void or(String field, String expression) {
        this.wheres.add(new Where().setRelation("or").setField(field).setExpression(expression));
    }

    public void or(String field, String expression, String frontValue) {
        this.wheres.add(new Where().setRelation("or").setField(field).setExpression(expression).setFrontValue(frontValue));
    }

    public void or(String field, String expression, String frontValue, String afterValue) {
        this.wheres.add(new Where().setRelation("or").setField(field).setExpression(expression).setFrontValue(frontValue).setAfterValue(afterValue));
    }

    @Override
    public String toString() {
        return "Where{" +
                "relation='" + relation + '\'' +
                ", field='" + field + '\'' +
                ", expression='" + expression + '\'' +
                ", frontValue='" + frontValue + '\'' +
                ", afterValue='" + afterValue + '\'' +
                ", wheres=" + wheres +
                '}';
    }
}
