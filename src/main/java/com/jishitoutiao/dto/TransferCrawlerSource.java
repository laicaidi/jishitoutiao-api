package com.jishitoutiao.dto;

import com.jishitoutiao.domain.CrawlerSource;

import java.io.Serializable;

/**
 * 爬虫源管理页
 * @author leitianxiang
 *
 */
public class TransferCrawlerSource implements Serializable {
	private String bid;		//源id
	private String bkey;	//源key
	private String bname;		//源名称
	private String homepage;		//源网站首页
	private String logo;		//品牌logo
	private String remark;		//介绍
		
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
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public TransferCrawlerSource() {
		super();
	}
	public TransferCrawlerSource(String bid, String bkey, String bname, String homepage, String logo, String remark) {
		super();
		this.bid = bid;
		this.bkey = bkey;
		this.bname = bname;
		this.homepage = homepage;
		this.logo = logo;
		this.remark = remark;
	}
	
	//根据<domain>构造DTO对象
	public TransferCrawlerSource(CrawlerSource crawlerSource) {
		this.bid = crawlerSource.getBid();
		this.bkey = crawlerSource.getBkey();
		this.bname = crawlerSource.getBname();
		this.homepage = crawlerSource.getHomepage();
		this.logo = crawlerSource.getLogo();
		this.remark = crawlerSource.getRemark();
	}
}