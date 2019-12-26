package com.lsm.common.db.impl;

import annotation.Column;
import annotation.Id;
import annotation.Table;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsm.common.dao.BaseDao;
import com.lsm.common.db.*;
import com.lsm.common.util.FieldsUtil;
import com.lsm.common.util.MapUtil;
import com.lsm.common.util.UnderlineHumpUtil;
import com.lsm.entity.entity.BaseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service(value = "BaseClient")
public class BaseClientImpl<T> implements BaseClient<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseClientImpl.class);

    @Resource
    private BaseDao baseDao;

    private HashMap<String, Object> baseMap;

    private DBInputData dbInputData;

    private DBInputDataInfo dbInputDataInfo;

    private List<DBInputDataInfo> dbInputDataInfoList;


    @Override
    public Integer saveBatch(List<T> list, Integer userId) {
        buildParams("saveBatch", list, null, null, null, null, null, userId);
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
        buildParams("removeBatch", list, null, t.getClass(), ids, null, where, userId);
        logger.info("Function Remove.Params:" + dbInputData);
        return baseDao.removeBatch(dbInputData);
    }

    @Override
    public Integer removeBatch(Class<?> clazz, List<Integer> ids, Integer userId) {
        buildParams("removeBatch", null, null, clazz, ids, null, null, userId);
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
        buildParams("deleteBatch", list, null, t.getClass(), ids, null, where, null);
        logger.info("Function Delete.Params:" + dbInputData);
        return baseDao.deleteBatch(dbInputData);
    }

    public Integer deleteBatch(Class<?> clazz, List<Integer> ids) {
        buildParams("deleteBatch", null, null, clazz, ids, null, null, null);
        logger.info("Function DeleteBatch.Params:" + dbInputData);
        return baseDao.deleteBatch(dbInputData);
    }

    public Integer update(T t, Where where, Integer userId) {
        List<T> list = new ArrayList<>();
        list.add(t);
        buildParams("updateBatch", list, null, null, null, null, where, userId);
        logger.info("Function Update.Params:" + dbInputData);
        return baseDao.updateBatch(dbInputData);
    }

    public Integer updateBatch(List<T> list, Integer userId, Where where) {
        buildParams("updateBatch", list, null, null, null, null, where, userId);
        logger.info("Function UpdateBatch.Params:" + dbInputData);
        return baseDao.updateBatch(dbInputData);
    }

    @Override
    public Integer getCount(T t, Where where) {
        //buildParams(t, null, where, "common");
        buildParams("select", null, t, null, null, null, where, null);
        logger.info("Function GetCount.Params:" + dbInputData);
        return baseDao.getCount(dbInputData);
    }

    @Override
    public BaseEntity get(T t, Where where, List<String> selectColumns) {
        buildParams("select", null, t, null, null, selectColumns, where, null);
        logger.info("Function Get.Params:" + dbInputData);
        return buildBaseEntity(t, baseDao.get(dbInputData));
    }

    @Override
    public List<BaseEntity> list(T t, Where where, List<String> selectColumns) {
        buildParams("select", null, t, null, null, selectColumns, where, null);
        logger.info("Function List.Params:" + dbInputData);
        return buildBaseEntity(t, baseDao.list(dbInputData));
    }

    @Override
    public PageInfo listPage(T t, Where where, List<String> selectColumns, Integer pageNum, Integer pageSize) {
        buildParams("select", null, t, null, null, selectColumns, where, null);
        logger.info("Function List.Params:" + dbInputData);
        //指定页码,每页显示条数
        PageHelper.startPage(pageNum, pageSize);
        //查询方法必须紧跟上一行代码
        List list = baseDao.list(dbInputData);
        //new PageInfo必须对查出的原始数据进行,如果查出的原始数据有变动过,会导致PageInfo的基本属性如pageNum那些不准确
        //所以这里通过查出的原始数据list来得到PageInfo对象
        PageInfo<BaseEntity> pageInfo = new PageInfo<BaseEntity>(list);
        //接着在这里处理原始数据list,封装成我们最终想要返回的集合
        List<BaseEntity> baseEntityList = buildBaseEntity(t, list);
        //覆盖掉得到PageInfo对象时里面的list,来得到最终想要返回的PageInfo
        pageInfo.setList(baseEntityList);
        return pageInfo;
    }

    private void buildParams(String type, List<T> list, T t, Class<?> clazz, List<Integer> ids, List<String> selectColumns, Where where, Integer userId) {
        try {
            List<Field> fields;
            T temp;
            List<String> colums;
            switch (type) {
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
                    for (T item : list) {
                        List<Object> values = new ArrayList<>();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != field.getAnnotation(Column.class)) {
                                values.add(field.get(item));
                            }
                        }
                        dbInputDataInfo = new DBInputDataInfo();
                        dbInputDataInfo.setValues(values);
                        dbInputDataInfoList.add(dbInputDataInfo);
                    }
                    dbInputData.setDbInputDataInfoList(dbInputDataInfoList);
                    break;
                case "removeBatch":
                case "deleteBatch":
                    if (null == clazz) {
                        throw new RuntimeException("Class<?> clazz IS NULL.");
                    }
                    //防止既无ID也无WHERE条件,导致全表误删
                    if (CollectionUtils.isEmpty(ids) && null == where) {
                        throw new RuntimeException("(List<Integer> ids IS NULL OR IS EMPTY) AND WHERE where IS NULL.");
                    }
                    dbInputData = new DBInputData();
                    String tableName = getTableName(clazz);
                    if (StringUtils.isEmpty(tableName)) {
                        throw new RuntimeException("Error Input Object! Error @Table Annotation.");
                    }
                    dbInputData.setTableName(tableName);
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
                case "updateBatch":
                    if (CollectionUtils.isEmpty(list)) {
                        throw new RuntimeException("List<T> list IS NULL OR IS EMPTY.");
                    }
                    dbInputData = new DBInputData();
                    dbInputDataInfoList = new ArrayList<>();
                    for (T item : list) {
                        if (null == item.getClass().getAnnotation(Table.class)) {
                            throw new RuntimeException("Error Input Object! Error @Table Annotation.");
                        }
                        //校验每个tID必传
                        if (null == ((BaseEntity) item).getId()) {
                            throw new RuntimeException("ID IS NULL OR IS EMPTY.");
                        }
                        dbInputDataInfo = new DBInputDataInfo();
                        dbInputDataInfo.setTableName(item.getClass().getAnnotation(Table.class).value());
                        if (null != userId) {
                            dbInputDataInfo.setUpdaterId(userId);
                        }
                        fields = FieldsUtil.getFields(item.getClass());
                        List<UpdateColumns> updateColumns = new ArrayList<>();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != field.getAnnotation(Column.class) && null != field.get(item)) {
                                updateColumns.add(new UpdateColumns().setColumn(field.getAnnotation(Column.class).value()).setValue(field.get(item)));
                            }
                            if (null != field.getAnnotation(Id.class) && null != field.get(item)) {
                                dbInputDataInfo.setPk(new PK().setKeyId(field.getAnnotation(Id.class).value()).setKeyValue(field.get(item)));
                            }
                        }
                        dbInputDataInfo.setUpdateColumns(updateColumns);
                        dbInputDataInfoList.add(dbInputDataInfo);
                    }
                    //批量更新目前仅支持多条数据共用同一个WHERE条件
                    if (null != where) {
                        dbInputData.setWhere(where);
                    }
                    dbInputData.setDbInputDataInfoList(dbInputDataInfoList);
                    break;
                case "select":
                    if (null == t) {
                        throw new RuntimeException("T t IS NULL.");
                    }
                    if (null == t.getClass().getAnnotation(Table.class)) {
                        throw new RuntimeException("Error Input Object! Error @Table Annotation.");
                    }
                    dbInputData = new DBInputData();
                    dbInputData.setTableName(t.getClass().getAnnotation(Table.class).value());
                    fields = FieldsUtil.getFields(t.getClass());
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (null != field.getAnnotation(Id.class) && null != field.get(t)) {
                            dbInputData.setPk(new PK().setKeyId(field.getAnnotation(Id.class).value()).setKeyValue(field.get(t)));
                        }
                    }
                    if (!CollectionUtils.isEmpty(selectColumns)) {
                        dbInputData.setSelectColumns(selectColumns);
                    }
                    if (null != where) {
                        dbInputData.setWhere(where);
                    }
                    break;
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
