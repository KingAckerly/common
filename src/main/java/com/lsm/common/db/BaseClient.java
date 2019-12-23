package com.lsm.common.db;


import com.lsm.common.entity.BaseEntity;

import java.util.List;

/**
 * 常见通用操作
 * 1.新增
 * 1.1.单个插入不返回主键
 * 1.2.单个插入并返回主键
 * 1.3.批量插入不返回主键
 * 1.4.批量操作并返回主键
 * 2.删除
 * 2.1.主键删除
 * 2.2.批量主键删除
 * 2.3.条件删除
 * 2.4.批量条件删除
 * 3.修改
 * 3.1.单个修改
 * 3.2.批量修改
 * 3.3.条件修改
 * 3.4.批量条件修改
 * 4.查询
 * 4.1.查总条数
 * 4.2.根据主键查询
 * 4.3.全量查询
 * 4.4.条件查询
 * 此接口待完善
 *
 * @param <T>
 */
public interface BaseClient<T> {

    Integer save(T t);

    Integer remove(T t, Where where);

    Integer update(T t, Where where);

    BaseEntity get(T t, Where where, List<String> selectColumns);
}
