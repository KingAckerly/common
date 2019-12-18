package com.lsm.common.db;


import com.lsm.common.entity.BaseEntity;

import java.util.HashMap;
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

    /**
     * 单个插入不返回主键
     *
     * @param t entity
     * @return 受影响行数
     */
    Integer save(T t);

    /**
     * 单个插入并返回主键
     *
     * @param t entity
     * @return 主键
     */
    String saveRetPK(T t);

    /**
     * 批量插入不返回主键
     *
     * @param list entity
     * @return 受影响行数
     */
    Integer saveBatch(List<T> list);

    /**
     * 批量插入并返回主键
     *
     * @param list entity
     * @return 主键
     */
    List<String> saveBatchRetPK(List<T> list);

    /**
     * 根据主键单个删除
     *
     * @param t entity
     * @return 受影响行数
     */
    Integer removeByPK(T t);

    /**
     * 根据主键批量删除
     *
     * @param list entity
     * @return 受影响行数
     */
    Integer removeBatchByPK(List<T> list);

    /**
     * 根据条件删除
     *
     * @param t         entity
     * @param condition 条件
     * @return
     */
    Integer removeByCondition(T t, String condition);

    /**
     * 根据主键单个修改,可根据条件
     *
     * @param t         entity
     * @param condition 条件
     * @return 受影响行数
     */
    Integer update(T t, String condition);

    /**
     * 批量修改
     *
     * @param t entity
     * @return 受影响行数
     */
    Integer updateBatch(T t);

    /**
     * 查总条数,可根据条件
     *
     * @param t         entity
     * @param condition 条件
     * @return
     */
    Integer count(T t, String condition);

    /**
     * 查询,可根据条件
     *
     * @param t         entity
     * @param condition 条件
     * @return
     */
    HashMap list(T t, String condition);


    BaseEntity get(Where where);
}
