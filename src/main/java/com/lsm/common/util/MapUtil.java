package com.lsm.common.util;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapUtil {

    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;
        try {
            //从子类开始遍历字段添加至集合
            List<Field> fields = new ArrayList<>();
            Class tempClass = entity;
            while (tempClass != null) {
                //当父类为null的时候说明到达了最上层的父类(Object类).
                fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                //得到父类,然后赋给自己
                tempClass = tempClass.getSuperclass();
            }
            t = entity.newInstance();
            for (Field field : fields) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object != null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
