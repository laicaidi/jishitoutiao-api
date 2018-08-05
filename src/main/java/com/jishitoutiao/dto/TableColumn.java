package com.jishitoutiao.dto;

import java.io.Serializable;

/**
 * 表格标题
 * @author leitianxiang
 *
 */
public class TableColumn implements Serializable {
    private String title;		//显示标题
    private String dataIndex;		//列数据在数据项中对应的 key
    private String key;		//React 需要的 key，如果已经设置了唯一的 dataIndex，可以忽略这个属性
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDataIndex() {
        return dataIndex;
    }
    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public TableColumn() {
        super();
        // TODO Auto-generated constructor stub
    }
    public TableColumn(String title, String dataIndex, String key) {
        super();
        this.title = title;
        this.dataIndex = dataIndex;
        this.key = key;
    }
}