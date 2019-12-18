package com.lsm.common.db.impl;

import com.lsm.common.annotation.Column;
import com.lsm.common.annotation.Id;
import com.lsm.common.annotation.Table;
import com.lsm.common.dao.BaseDao;
import com.lsm.common.db.BaseClient;
import com.lsm.common.db.Where;
import com.lsm.common.entity.BaseEntity;
import com.lsm.common.util.MapUtil;
import com.lsm.common.util.UnderlineHumpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(value = "BaseClient")
public class BaseClientImpl<T> implements BaseClient<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseClientImpl.class);

    @Resource
    private BaseDao baseDao;

    private Map<String, Object> params;

    private HashMap<String, Object> baseMap;


    public Integer save(T t) {
        params = buildParams(t, null, "insert");
        logger.info("Function Save.Params:" + params);
        return baseDao.save(params);
    }

    public Integer remove(Where where) {
        params = buildParams(null, where, null);
        logger.info("Function Remove.Params:" + params);
        return baseDao.remove(params);
    }

    public Integer update(T t, Where where) {
        params = buildParams(t, where, "update");
        logger.info("Function Update.Params:" + params);
        return baseDao.update(params);
    }

    public BaseEntity get(Where where) {
        params = buildParams(null, where, null);
        logger.info("Function Get.Params:" + params);
        return buildBaseEntity(baseDao.get(params), where);
    }

    private Map<String, Object> buildParams(T t, Where where, String type) {
        params = new HashMap<>();
        if (null != t) {
            //获取表名
            if (null == t.getClass().getAnnotation(Table.class)) {
                throw new RuntimeException("Error Input Object! Error @Table Annotation.");
            }
            params.put("TABLE_NAME", t.getClass().getAnnotation(Table.class).value());
            Method[] m = t.getClass().getMethods();
            if (null == m || m.length <= 0) {
                throw new RuntimeException("Error Input Object! No Method.");
            }
            switch (type) {
                case "insert":
                    List k = new ArrayList();//存放列名
                    List v = new ArrayList();//存放列值
                    for (Method i : m) {
                        //获取列名和值
                        if (null != i.getAnnotation(Column.class)) {
                            k.add(i.getAnnotation(Column.class).value());
                            v.add(getInvokeValue(t, i));
                            continue;
                        }
                        if (null != i.getAnnotation(Id.class)) {
                            params.put("KEY_ID", i.getAnnotation(Id.class).value());
                            params.put("KEY_VALUE", getInvokeValue(t, i));
                        }
                    }
                    if (k.size() != v.size()) {
                        throw new RuntimeException("Error Input Object! Internal Error.");
                    }
                    params.put("COLUMNS", k);
                    params.put("VALUES", v);
                    break;
                case "update":
                    List data = new ArrayList();
                    for (Method i : m) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        if (null != i.getAnnotation(Column.class) && null != getInvokeValue(t, i)) {
                            map.put("COLUMN", i.getAnnotation(Column.class).value());
                            map.put("VALUE", getInvokeValue(t, i));
                            data.add(map);
                            continue;
                        }
                        if (null != i.getAnnotation(Id.class)) {
                            params.put("KEY_ID", i.getAnnotation(Id.class).value());
                            params.put("KEY_VALUE", getInvokeValue(t, i));
                        }
                    }
                    params.put("DATA", data);
                    break;
            }
        }
        if (null != where) {
            params.put("TABLE_NAME", getTableName(where.getClazz()));
            params.put("WHERES", where.getWheres());
        }
        return params;
    }

    private Object getInvokeValue(Object t, Method i) {
        try {
            return i.invoke(t, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error Input Object! Error Invoke Get Method.", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Error Input Object! Error Invoke Get Method.", e);
        }
    }

    public BaseEntity buildBaseEntity(HashMap<String, Object> hashMap, Where where) {
        baseMap = new HashMap<>();
        for (Map.Entry<String, Object> item : hashMap.entrySet()) {
            baseMap.put(UnderlineHumpUtil.lineToHump(item.getKey()), item.getValue());
        }
        return (BaseEntity) MapUtil.mapToEntity(baseMap, where.getClazz());
    }

    public String getTableName(Class<?> clazz) {
        // 判断是否有Table注解
        if (clazz.isAnnotationPresent(Table.class)) {
            // 获取注解对象
            Table table = clazz.getAnnotation(Table.class);
            return table.value();
        }
        return null;
    }
}
