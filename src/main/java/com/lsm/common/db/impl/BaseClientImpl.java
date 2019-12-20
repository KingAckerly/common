package com.lsm.common.db.impl;

import com.lsm.common.annotation.Column;
import com.lsm.common.annotation.Id;
import com.lsm.common.annotation.Table;
import com.lsm.common.dao.BaseDao;
import com.lsm.common.db.*;
import com.lsm.common.entity.BaseEntity;
import com.lsm.common.util.FieldsUtil;
import com.lsm.common.util.MapUtil;
import com.lsm.common.util.UnderlineHumpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@Service(value = "BaseClient")
public class BaseClientImpl<T> implements BaseClient<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseClientImpl.class);

    @Resource
    private BaseDao baseDao;

    private HashMap<String, Object> baseMap;

    private DBCommonPO dbCommonPO;


    public Integer save(T t) {
        buildParams(t, null, null, "insert");
        logger.info("Function Save.Params:" + dbCommonPO);
        return baseDao.save(dbCommonPO);
    }

    public Integer remove(Where where) {
        buildParams(null, null, where, null);
        logger.info("Function Remove.Params:" + dbCommonPO);
        return baseDao.remove(dbCommonPO);
    }

    public Integer update(T t, Where where) {
        buildParams(t, null, where, "update");
        logger.info("Function Update.Params:" + dbCommonPO);
        return baseDao.update(dbCommonPO);
    }

    public BaseEntity get(Where where) {
        buildParams(null, null, where, null);
        logger.info("Function Get.Params:" + dbCommonPO);
        return buildBaseEntity(baseDao.get(dbCommonPO), where);
    }

    @Override
    public BaseEntity get(List<String> selectColumns, Where where) {
        buildParams(null, selectColumns, where, null);
        logger.info("Function Get.Params:" + dbCommonPO);
        return buildBaseEntity(baseDao.get(dbCommonPO), where);
    }

    private void buildParams(T t, List<String> selectColumns, Where where, String type) {
        dbCommonPO = new DBCommonPO();
        if (null != t) {
            //获取表名
            if (null == t.getClass().getAnnotation(Table.class)) {
                throw new RuntimeException("Error Input Object! Error @Table Annotation.");
            }
            dbCommonPO.setTableName(t.getClass().getAnnotation(Table.class).value());
            List<Field> fields = FieldsUtil.getFields(t.getClass());
            switch (type) {
                case "insert":
                    List<String> saveColumns = new ArrayList<>();
                    List<Object> saveValues = new ArrayList<>();
                    try {
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != field.getAnnotation(Column.class) && null != field.get(t)) {
                                saveColumns.add(field.getAnnotation(Column.class).value());
                                saveValues.add(field.get(t));
                            }
                            if (null != field.getAnnotation(Id.class) && null != field.get(t)) {
                                dbCommonPO.setPk(new PK().setKeyId(field.getAnnotation(Id.class).value()).setKeyValue(field.get(t)));
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (saveColumns.size() != saveValues.size()) {
                        throw new RuntimeException("Error Input Object! Internal Error.");
                    }
                    dbCommonPO.setSaveColumns(new SaveColumns().setColums(saveColumns).setValues(saveValues));
                    break;
                case "update":
                    List<UpdateColumns> updateColumnsList = new ArrayList<>();
                    try {
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != field.getAnnotation(Column.class) && null != field.get(t)) {
                                updateColumnsList.add(new UpdateColumns().setColumn(field.getAnnotation(Column.class).value())
                                        .setValue(field.get(t)));
                                continue;
                            }
                            if (null != field.getAnnotation(Id.class) && null != field.get(t)) {
                                dbCommonPO.setPk(new PK().setKeyId(field.getAnnotation(Id.class).value()).setKeyValue(field.get(t)));
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    dbCommonPO.setUpdateColumns(updateColumnsList);
                    break;
            }
        }
        if (!CollectionUtils.isEmpty(selectColumns)) {
            dbCommonPO.setSelectColumns(selectColumns);
        }
        if (null != where) {
            dbCommonPO.setTableName(getTableName(where.getClazz()));
            dbCommonPO.setWheres(where.getWheres());
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
