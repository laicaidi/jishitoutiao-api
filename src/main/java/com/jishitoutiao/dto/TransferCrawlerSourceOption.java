package com.jishitoutiao.dto;

import java.io.Serializable;

/**
 * 元数据-爬虫源下拉选项
 * @author leitianxiang
 *
 */
public class TransferCrawlerSourceOption implements Serializable {
	private String bid;		// 选项bid
	private String bkey;		// 选项key
	private String bname;		// 选项中文名
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBkey() {
		return bkey;
	}
	public void setBkey(String bkey) {
		this.bkey = bkey;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public TransferCrawlerSourceOption(String bid, String bkey, String bname) {
		super();
		this.bid = bid;
		this.bkey = bkey;
		this.bname = bname;
	}
	public TransferCrawlerSourceOption() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "TransferCrawlerSourceOption [bid=" + bid + ", bkey=" + bkey + ", bname=" + bname + "]";
	}
}