package com.lsm.common.db;


import com.github.pagehelper.PageInfo;
import com.lsm.entity.entity.BaseEntity;

import java.util.List;

public interface BaseClient<T> {

    Integer saveBatch(List<T> list, Integer userId);

    Integer remove(T t, Where where, Integer userId);

    Integer removeBatch(Class<?> clazz, List<Integer> ids, Integer userId);

    Integer delete(T t, Where where);

    Integer deleteBatch(Class<?> clazz, List<Integer> ids);

    Integer update(T t, Where where, Integer userId);

    Integer updateBatch(List<T> list, Integer userId, Where where);

    Integer getCount(T t, Where where);

    BaseEntity get(T t, Where where, List<String> selectColumns);

    List<BaseEntity> list(T t, Where where, List<String> selectColumns);

    PageInfo listPage(T t, Where where, List<String> selectColumns, Integer pageNum, Integer pageSize);
}
