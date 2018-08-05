package com.jishitoutiao.rely;

import java.io.Serializable;
import java.util.List;

import com.jishitoutiao.dto.TableColumn;

/**
 * 封装页面数据
 * @author leitianxiang
 *
 */
public class PageObj<T> implements Serializable {
    private int totalPage;		// 总页数
    private int pageSize = 10;		// 每页显示数量

    private int totalRecord;		// 总记录数
    private int pageNum;		// 当前页码

    private int startIndex;		// 用户想看的数据从数据库哪条开始取

    private List<TableColumn> columns;	// 表格标题数据
    private List<T> list;		// 当前页面显示数据

    private int startPage;		// 起始页
    private int endPage;		// 结束页

    /**
     * 构造函数
     * @param pageNum 当前页码
     * @param totalRecord 总记录数
     */
    public PageObj(int pageNum, int totalRecord) {
        this.pageNum = pageNum;
        this.totalRecord = totalRecord;

        //算出共显示的页数
        this.totalPage = (this.totalRecord + this.pageSize - 1) / this.pageSize;

        //算出用户想看的页从数据库哪条记录开始取
        this.startIndex = (this.pageNum - 1) * pageSize;

        //计算起始页和结束页
        if (this.totalPage <= 10) {
            //如果总页数小于10，则起始页为1，结束页为总页
            this.startPage = 1;
            this.endPage = totalPage;
        } else {
            this.startPage = pageNum - 4;
            this.endPage = pageNum + 5;

            //如果得出的起始页小于1，则用1做起始页，10做结束页
            if (this.startPage < 1) {
                this.startPage = 1;
                this.endPage = 10;
            }

            //如果得出的结束页大于总页数，则用总页数作为结束页，总页数-9作为起始页
            if (this.endPage > this.totalPage) {
                this.endPage = this.totalPage;
                this.startPage = this.totalPage - 9;
            }
        }
    }

    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getTotalRecord() {
        return totalRecord;
    }
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getStartIndex() {
        return startIndex;
    }
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }

    public int getStartPage() {
        return startPage;
    }
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }
    public int getEndPage() {
        return endPage;
    }
    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}