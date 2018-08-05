package com.jishitoutiao.dto;

import com.jishitoutiao.domain.CrawlerSet;

import java.io.Serializable;

/**
 * 爬虫设置页
 * @author leitianxiang
 *
 */
public class TransferCrawlerSet implements Serializable {
	private String sid;		//设置id
	private String ckey;		//键
	private String cvalue;		//值
	private String remark;		//备注
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getCkey() {
		return ckey;
	}
	public void setCkey(String ckey) {
		this.ckey = ckey;
	}
	public String getCvalue() {
		return cvalue;
	}
	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
		
	public TransferCrawlerSet(String sid, String ckey, String cvalue, String remark) {
		super();
		this.sid = sid;
		this.ckey = ckey;
		this.cvalue = cvalue;
		this.remark = remark;
	}
	public TransferCrawlerSet() {
		super();
	}
	//根据<domain>构造DTO对象
	public TransferCrawlerSet(CrawlerSet crawlerSet) {
		this.sid = crawlerSet.getSid();
		this.ckey = crawlerSet.getCkey();
		this.cvalue = crawlerSet.getCvalue();
		this.remark = crawlerSet.getRemark();
	}
}