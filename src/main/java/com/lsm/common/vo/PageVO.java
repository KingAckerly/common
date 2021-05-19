package com.lsm.common.vo;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 封装mybatis-pagehelper返回对象
 * 使用示例
 * List<xxxResp> list = dao.list();
 * PageInfo<xxxResp> page = new PageInfo<>(list);
 * PageVO<PageInfo> pageVO = new PageVO<>(page);
 *
 * @param <T>
 */
public class PageVO<T> {
    private List<T> list;
    private int pageNum;
    private int pageSize;
    private Long total;

    public PageVO(PageInfo pageInfo) {
        this.list = pageInfo.getList();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.total = pageInfo.getTotal();
    }

    public List<T> getList() {
        return list;
    }

    public PageVO<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public int getPageNum() {
        return pageNum;
    }

    public PageVO<T> setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageVO<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public PageVO<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        return "PageVO{" +
                "list=" + list +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                '}';
    }
}
