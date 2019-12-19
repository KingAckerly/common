package com.lsm.common.db.impl;

import com.lsm.common.annotation.Column;
import com.lsm.common.annotation.Id;
import com.lsm.common.annotation.Table;
import com.lsm.common.dao.BaseDao;
import com.lsm.common.db.*;
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

    private HashMap<String, Object> baseMap;

    private DBCommonPO dbCommonPO;


    public Integer save(T t) {
        buildParams(t, null, "insert");
        logger.info("Function Save.Params:" + dbCommonPO);
        return baseDao.save(dbCommonPO);
    }

    public Integer remove(Where where) {
        buildParams(null, where, null);
        logger.info("Function Remove.Params:" + dbCommonPO);
        return baseDao.remove(dbCommonPO);
    }

    public Integer update(T t, Where where) {
        buildParams(t, where, "update");
        logger.info("Function Update.Params:" + dbCommonPO);
        return baseDao.update(dbCommonPO);
    }

    public BaseEntity get(Where where) {
        buildParams(null, where, null);
        logger.info("Function Get.Params:" + dbCommonPO);
        return buildBaseEntity(baseDao.get(dbCommonPO), where);
    }

    private void buildParams(T t, Where where, String type) {
        dbCommonPO = new DBCommonPO();
        if (null != t) {
            //获取表名
            if (null == t.getClass().getAnnotation(Table.class)) {
                throw new RuntimeException("Error Input Object! Error @Table Annotation.");
            }
            dbCommonPO.setTableName(t.getClass().getAnnotation(Table.class).value());
            Method[] m = t.getClass().getMethods();
            if (null == m || m.length <= 0) {
                throw new RuntimeException("Error Input Object! No Method.");
            }
            switch (type) {
                case "insert":
                    List<String> saveColumns = new ArrayList<>();
                    List<Object> saveValues = new ArrayList<>();
                    for (Method i : m) {
                        //获取列名和值
                        if (null != i.getAnnotation(Column.class) && null != getInvokeValue(t, i)) {
                            saveColumns.add(i.getAnnotation(Column.class).value());
                            saveValues.add(getInvokeValue(t, i));
                            continue;
                        }
                        if (null != i.getAnnotation(Id.class)) {
                            dbCommonPO.setPk(new PK().setKeyId(i.getAnnotation(Id.class).value()).setKeyValue(getInvokeValue(t, i)));
                        }
                    }
                    if (saveColumns.size() != saveValues.size()) {
                        throw new RuntimeException("Error Input Object! Internal Error.");
                    }
                    dbCommonPO.setSaveColumns(new SaveColumns().setColums(saveColumns).setValues(saveValues));
                    break;
                case "update":
                    List<UpdateColumns> updateColumnsList = new ArrayList<>();
                    for (Method i : m) {
                        if (null != i.getAnnotation(Column.class) && null != getInvokeValue(t, i)) {
                            updateColumnsList.add(new UpdateColumns().setColumn(i.getAnnotation(Column.class).value()).setValue(getInvokeValue(t, i)));
                            continue;
                        }
                        if (null != i.getAnnotation(Id.class)) {
                            dbCommonPO.setPk(new PK().setKeyId(i.getAnnotation(Id.class).value()).setKeyValue(getInvokeValue(t, i)));
                        }
                    }
                    dbCommonPO.setUpdateColumns(updateColumnsList);
                    break;
            }
        }
        if (null != where) {
            dbCommonPO.setTableName(getTableName(where.getClazz()));
            dbCommonPO.setWheres(where.getWheres());
        }
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
