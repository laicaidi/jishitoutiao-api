package com.jishitoutiao.dto;

import java.io.Serializable;

public class TransferCrawlerCategoryList implements Serializable {
	private String cid;		// 类别id
	private String ckey;		// 类别key
	private String cname;		// 类别名称
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
	public TransferCrawlerCategoryList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransferCrawlerCategoryList(String cid, String ckey, String cname) {
		super();
		this.cid = cid;
		this.ckey = ckey;
		this.cname = cname;
	}
	@Override
	public String toString() {
		return "TransferCrawlerCategoryList [cid=" + cid + ", ckey=" + ckey + ", cname=" + cname + "]";
	}
}
