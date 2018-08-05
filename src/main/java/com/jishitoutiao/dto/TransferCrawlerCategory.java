package com.jishitoutiao.dto;

import com.jishitoutiao.domain.CrawlerCategory;

import java.io.Serializable;

/**
 * 爬虫类别管理页
 * @author leitianxiang
 *
 */
public class TransferCrawlerCategory implements Serializable {
	private String cid;		//类别id
	private String ckey;		//类别key
	private String cname;		//类别名称
	private String remark;		//备注
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCkey() {
		return ckey;
	}
	public void setCkey(String ckey) {
		this.ckey = ckey;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
		
	public TransferCrawlerCategory(String cid, String ckey, String cname, String remark) {
		super();
		this.cid = cid;
		this.ckey = ckey;
		this.cname = cname;
		this.remark = remark;
	}
	public TransferCrawlerCategory() {
		super();
	}
	//根据<domain>构造DTO对象
	public TransferCrawlerCategory(CrawlerCategory crawlerCategory) {
		this.cid = crawlerCategory.getCid();
		this.ckey = crawlerCategory.getCkey();
		this.cname = crawlerCategory.getCname();
		this.remark = crawlerCategory.getRemark();
	}
}