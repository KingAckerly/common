package com.lsm.common.db.impl;

import com.lsm.common.annotation.Column;
import com.lsm.common.annotation.Id;
import com.lsm.common.annotation.Table;
import com.lsm.common.dao.BaseDao;
import com.lsm.common.db.*;
import com.lsm.common.entity.BaseEntity;
import com.lsm.common.entity.app.AppEntity;
import com.lsm.common.util.FieldsUtil;
import com.lsm.common.util.MapUtil;
import com.lsm.common.util.UnderlineHumpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
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

    private List<DBCommonPO> dbCommonPOList;

    private DBInputData dbInputData;

    private DBInputDataInfo dbInputDataInfo;

    private List<DBInputDataInfo> dbInputDataInfoList;


    /*public Integer save(T t) {
        buildParams(t, null, null, "insert");
        logger.info("Function Save.Params:" + dbCommonPO);
        baseDao.save(dbCommonPO);
        if (null != dbCommonPO.getPk() && null != dbCommonPO.getPk().getKeyValue()) {
            return Integer.valueOf(dbCommonPO.getPk().getKeyValue().toString());
        } else {
            return 0;
        }
    }*/

    @Override
    public Integer saveBatch(List<T> list, Integer userId) {
        buildParams("saveBatch", list, null, null, null, null, userId);
        logger.info("Function SaveBatch.Params:" + dbInputData);
        return baseDao.saveBatch(dbInputData);
    }

    @Override
    public Integer remove(T t, Where where, Integer userId) {
        Integer id = ((BaseEntity) t).getId();
        List<Integer> ids = new ArrayList<>();
        if (null != id) {
            ids.add(id);
        }
        List<T> list = new ArrayList<>();
        list.add(t);
        buildParams("remove", list, t.getClass(), ids, null, where, userId);
        logger.info("Function Remove.Params:" + dbInputData);
        return baseDao.removeBatch(dbInputData);
    }

    @Override
    public Integer removeBatch(Class<?> clazz, List<Integer> ids, Integer userId) {
        buildParams("removeBatch", null, clazz, ids, null, null, userId);
        logger.info("Function RemoveBatch.Params:" + dbInputData);
        return baseDao.removeBatch(dbInputData);
    }

    public Integer delete(T t, Where where) {
        Integer id = ((BaseEntity) t).getId();
        List<Integer> ids = new ArrayList<>();
        if (null != id) {
            ids.add(id);
        }
        List<T> list = new ArrayList<>();
        list.add(t);
        buildParams("delete", list, t.getClass(), ids, null, where, null);
        logger.info("Function Delete.Params:" + dbInputData);
        return baseDao.deleteBatch(dbInputData);
    }

    public Integer deleteBatch(Class<?> clazz, List<Integer> ids) {
        buildParams("deleteBatch", null, clazz, ids, null, null, null);
        logger.info("Function DeleteBatch.Params:" + dbInputData);
        return baseDao.deleteBatch(dbInputData);
    }

    /*public Integer update(T t, Where where) {
        buildParams(t, null, where, "update");
        logger.info("Function Update.Params:" + dbCommonPO);
        return baseDao.update(dbCommonPO);
    }*/

    public Integer updateBatch(List<T> list, Integer userId, Where where) {
        buildParams("updateBatch", list, null, null, null, where, userId);
        logger.info("Function UpdateBatch.Params:" + dbInputData);
        return baseDao.updateBatch(dbInputData);
    }

    /*@Override
    public Integer getCount(T t, Where where) {
        buildParams(t, null, where, "common");
        logger.info("Function GetCount.Params:" + dbCommonPO);
        return baseDao.getCount(dbCommonPO);
    }*/

    /*@Override
    public BaseEntity get(T t, Where where, List<String> selectColumns) {
        if (CollectionUtils.isEmpty(selectColumns)) {
            buildParams(t, null, where, "common");
        } else {
            buildParams(t, selectColumns, where, "common");
        }
        logger.info("Function Get.Params:" + dbCommonPO);
        return buildBaseEntity(t, baseDao.get(dbCommonPO));
    }*/

    /*@Override
    public List<BaseEntity> list(T t, Where where, List<String> selectColumns) {
        if (CollectionUtils.isEmpty(selectColumns)) {
            buildParams(t, null, where, "common");
        } else {
            buildParams(t, selectColumns, where, "common");
        }
        logger.info("Function List.Params:" + dbCommonPO);
        return buildBaseEntity(t, baseDao.list(dbCommonPO));
    }*/

    /*private void buildParams(T t, List<String> selectColumns, Where where, String type) {
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
                case "remove":
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
                case "common":
                    try {
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != field.getAnnotation(Id.class) && null != field.get(t)) {
                                dbCommonPO.setPk(new PK().setKeyId(field.getAnnotation(Id.class).value()).setKeyValue(field.get(t)));
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            dbCommonPO.setSelectColumns(selectColumns);
            if (null != where) {
                dbCommonPO.setWhere(where);
            }
        }
    }*/

    /*private void buildParams(List<T> list, List<String> selectColumns, Where where, String type) {
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("List<T> list IS NULL OR IS EMPTY.");
        }
        try {
            T temp = list.get(0);
            //获取表名
            if (null == temp.getClass().getAnnotation(Table.class)) {
                throw new RuntimeException("Error Input Object! Error @Table Annotation.");
            }
            dbInputData = new DBInputData();
            dbInputData.setTableName(temp.getClass().getAnnotation(Table.class).value());
            List<Field> fields = FieldsUtil.getFields(temp.getClass());
            //要操作的列
            List<String> colums = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                if (null != field.getAnnotation(Column.class) && null != field.get(temp)) {
                    colums.add(field.getAnnotation(Column.class).value());
                }
            }
            dbInputData.setColums(colums);
            //遍历
            dbInputDataInfoList = new ArrayList<>();
            for (T t : list) {
                List<Object> values = new ArrayList<>();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (null != field.getAnnotation(Column.class) && null != field.get(t)) {
                        values.add(field.get(t));
                    }
                }
                dbInputDataInfo = new DBInputDataInfo();
                dbInputDataInfo.setValues(values);
                dbInputDataInfoList.add(dbInputDataInfo);
            }
            dbInputData.setDbInputDataInfoList(dbInputDataInfoList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void buildParams(String type, List<T> list, Class<?> clazz, List<Integer> ids, List<String> selectColumns, Where where, Integer userId) {
        try {
            List<Field> fields;
            T temp;
            List<String> colums;
            switch (type) {
                case "save":
                case "saveBatch":
                    if (CollectionUtils.isEmpty(list)) {
                        throw new RuntimeException("List<T> list IS NULL OR IS EMPTY.");
                    }
                    temp = list.get(0);
                    //获取表名
                    if (null == temp.getClass().getAnnotation(Table.class)) {
                        throw new RuntimeException("Error Input Object! Error @Table Annotation.");
                    }
                    dbInputData = new DBInputData();
                    dbInputData.setCreaterId(userId);
                    dbInputData.setTableName(temp.getClass().getAnnotation(Table.class).value());
                    fields = FieldsUtil.getFields(temp.getClass());
                    //要操作的列
                    colums = new ArrayList<>();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (null != field.getAnnotation(Column.class)) {
                            colums.add(field.getAnnotation(Column.class).value());
                        }
                    }
                    dbInputData.setColums(colums);
                    //遍历
                    dbInputDataInfoList = new ArrayList<>();
                    for (T t : list) {
                        List<Object> values = new ArrayList<>();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != field.getAnnotation(Column.class)) {
                                values.add(field.get(t));
                            }
                        }
                        dbInputDataInfo = new DBInputDataInfo();
                        dbInputDataInfo.setValues(values);
                        dbInputDataInfoList.add(dbInputDataInfo);
                    }
                    dbInputData.setDbInputDataInfoList(dbInputDataInfoList);
                    break;
                case "remove":
                case "removeBatch":
                case "delete":
                case "deleteBatch":
                    if (null == clazz) {
                        throw new RuntimeException("Class<?> clazz IS NULL.");
                    }
                    //防止既无ID也无WHERE条件,导致全表误删
                    if (CollectionUtils.isEmpty(ids) && null == where) {
                        throw new RuntimeException("(List<Integer> ids IS NULL OR IS EMPTY) AND WHERE where IS NULL.");
                    }
                    dbInputData = new DBInputData();
                    dbInputData.setTableName(getTableName(clazz));
                    //取主键ID
                    fields = FieldsUtil.getFields(clazz);
                    PK pk = new PK();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (null != field.getAnnotation(Id.class)) {
                            pk.setKeyId(field.getAnnotation(Id.class).value());
                        }
                    }
                    pk.setKeyValues(ids);
                    dbInputData.setPk(pk);
                    if (null != where) {
                        dbInputData.setWhere(where);
                    }
                    if (null != userId) {
                        dbInputData.setUpdaterId(userId);
                    }
                    break;
                case "update":
                case "updateBatch":
                    if (CollectionUtils.isEmpty(list)) {
                        throw new RuntimeException("List<T> list IS NULL OR IS EMPTY.");
                    }
                    dbInputData = new DBInputData();
                    dbInputDataInfoList = new ArrayList<>();
                    for (T t : list) {
                        if (null == t.getClass().getAnnotation(Table.class)) {
                            throw new RuntimeException("Error Input Object! Error @Table Annotation.");
                        }
                        //校验每个tID必传
                        if (null == ((BaseEntity) t).getId()) {
                            throw new RuntimeException("ID IS NULL OR IS EMPTY.");
                        }
                        dbInputDataInfo = new DBInputDataInfo();
                        dbInputDataInfo.setTableName(t.getClass().getAnnotation(Table.class).value());
                        if (null != userId) {
                            dbInputDataInfo.setUpdaterId(userId);
                        }
                        fields = FieldsUtil.getFields(t.getClass());
                        List<UpdateColumns> updateColumns = new ArrayList<>();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != field.getAnnotation(Column.class) && null != field.get(t)) {
                                updateColumns.add(new UpdateColumns().setColumn(field.getAnnotation(Column.class).value()).setValue(field.get(t)));
                            }
                            if (null != field.getAnnotation(Id.class) && null != field.get(t)) {
                                dbInputDataInfo.setPk(new PK().setKeyId(field.getAnnotation(Id.class).value()).setKeyValue(field.get(t)));
                            }
                        }
                        dbInputDataInfo.setUpdateColumns(updateColumns);
                        dbInputDataInfoList.add(dbInputDataInfo);
                    }
                    dbInputData.setDbInputDataInfoList(dbInputDataInfoList);
                    break;
                case "select":
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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

    public BaseEntity buildBaseEntity(T t, HashMap<String, Object> hashMap) {
        if (null == hashMap) {
            return null;
        }
        baseMap = new HashMap<>();
        for (Map.Entry<String, Object> item : hashMap.entrySet()) {
            baseMap.put(UnderlineHumpUtil.lineToHump(item.getKey()), item.getValue());
        }
        return (BaseEntity) MapUtil.mapToEntity(baseMap, t.getClass());
    }

    public List<BaseEntity> buildBaseEntity(T t, List<HashMap> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<BaseEntity> baseEntityList = new ArrayList<>();
        for (HashMap<String, Object> map : list) {
            baseMap = new HashMap<>();
            for (Map.Entry<String, Object> item : map.entrySet()) {
                baseMap.put(UnderlineHumpUtil.lineToHump(item.getKey()), item.getValue());
            }
            baseEntityList.add((BaseEntity) MapUtil.mapToEntity(baseMap, t.getClass()));
        }
        return baseEntityList;
    }
}
