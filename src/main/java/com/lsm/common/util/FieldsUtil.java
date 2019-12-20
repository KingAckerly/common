package com.lsm.common.util;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldsUtil {
    /**
     * 获取子类及父类的fields
     *
     * @param clazz
     * @return
     */
    public static List<Field> getFields(Class clazz) {
        List<Field> fields = new ArrayList<>();
        Class tempClass = clazz;
        while (tempClass != null) {
            //当父类为null的时候说明到达了最上层的父类(Object类).
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return fields;
    }
}
