package com.lsm.common.db;


import java.util.ArrayList;
import java.util.List;

public class Where {
    private List<String> orderFields;
    private String type;
    private List<WherePO> wherePOS = new ArrayList<>();

    public List<String> getOrderFields() {
        return orderFields;
    }

    public Where setOrderFields(List<String> orderFields) {
        this.orderFields = orderFields;
        return this;
    }

    public String getType() {
        return type;
    }

    public Where setType(String type) {
        this.type = type;
        return this;
    }

    public List<WherePO> getWherePOS() {
        return wherePOS;
    }

    public Where setWherePOS(List<WherePO> wherePOS) {
        this.wherePOS = wherePOS;
        return this;
    }

    public Where() {

    }

    public Where(List<String> orderFields, String type) {
        this.orderFields = orderFields;
        this.type = type;
    }

    public void and(String field, String expression) {
        this.wherePOS.add(new WherePO().setRelation("and").setField(field).setExpression(expression));
    }

    public void and(String field, String expression, String frontValue) {
        this.wherePOS.add(new WherePO().setRelation("and").setField(field).setExpression(expression).setFrontValue(frontValue));
    }

    public void and(String field, String expression, String frontValue, String afterValue) {
        this.wherePOS.add(new WherePO().setRelation("and").setField(field).setExpression(expression).setFrontValue(frontValue).setAfterValue(afterValue));
    }

    public void or(String field, String expression) {
        this.wherePOS.add(new WherePO().setRelation("or").setField(field).setExpression(expression));
    }

    public void or(String field, String expression, String frontValue) {
        this.wherePOS.add(new WherePO().setRelation("or").setField(field).setExpression(expression).setFrontValue(frontValue));
    }

    public void or(String field, String expression, String frontValue, String afterValue) {
        this.wherePOS.add(new WherePO().setRelation("or").setField(field).setExpression(expression).setFrontValue(frontValue).setAfterValue(afterValue));
    }

    @Override
    public String toString() {
        return "Where{" +
                "orderFields=" + orderFields +
                ", type='" + type + '\'' +
                ", wherePOS=" + wherePOS +
                '}';
    }
}
