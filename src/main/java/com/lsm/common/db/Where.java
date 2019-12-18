package com.lsm.common.db;


import java.util.ArrayList;
import java.util.List;

public class Where {

    private String relation;
    private String field;
    private String expression;
    private String value;
    private Class<?> clazz;
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

    public String getValue() {
        return value;
    }

    public Where setValue(String value) {
        this.value = value;
        return this;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Where setClazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    public List<Where> getWheres() {
        return wheres;
    }

    public Where setWheres(List<Where> wheres) {
        this.wheres = wheres;
        return this;
    }

    public void and(String field, String expression, String value) {
        this.wheres.add(new Where().setRelation("and").setField(field).setExpression(expression).setValue(value));
    }

    public void or(String field, String expression, String value) {
        this.wheres.add(new Where().setRelation("or").setField(field).setExpression(expression).setValue(value));
    }

    public Where(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Where() {

    }
}
