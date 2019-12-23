package com.lsm.common.db;


public enum ExpressionEnum {
    EQ("="),
    NEQ("<>"),
    GT(">"),
    LT("<"),
    LT_EQ("<="),
    GT_EQ(">="),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN"),
    IN("IN"),
    NOT_IN("NOT IN"),
    LIKE("LIKE"),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL");

    private String exp;

    ExpressionEnum(String exp) {
        this.exp = exp;
    }

    public String getExp() {
        return exp;
    }
}
