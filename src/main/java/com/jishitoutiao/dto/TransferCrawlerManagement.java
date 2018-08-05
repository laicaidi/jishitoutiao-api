package com.jishitoutiao.dto;

import com.jishitoutiao.domain.CrawlerManagement;
import com.jishitoutiao.rely.CrawlerStatus;
import com.jishitoutiao.rely.CrawlerSwitch;

import java.io.Serializable;

/**
 * 爬虫管理页
 * @author leitianxiang
 *
 */
public class TransferCrawlerManagement implements Serializable {
	private String crawlerId;		//爬虫id

	private String bid;		//源id
	private String bkey;	//源key
	private String bname;		//源名称
	
	private String sld;		//二级源名
	
	private String cid;		//类别id
	private String ckey;		//类别key
	private String cname;		//类别名称
	
	private String crawlerName;		//爬虫名
	private String baseUrl;		//爬取base_url
	private String crawlerDirectory;		//爬虫文件所在目录
	private double crawlerWeightFactor;		//爬虫权重系数
	private int crawlerWeightScore;		//爬虫权重分
	private CrawlerStatus crawlerStatus;		//爬虫状态,0:ABNOMAL; 1:NORMAL
	private String remark;		//爬虫备注
	private CrawlerSwitch crawlerSwitch;		//爬虫开关,0:OFF; 1:ON
	
	public String getCrawlerId() {
		return crawlerId;
	}
	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}
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
	public String getSld() {
		return sld;
	}
	public void setSld(String sld) {
		this.sld = sld;
	}
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
	public String getCrawlerName() {
		return crawlerName;
	}
	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getCrawlerDirectory() {
		return crawlerDirectory;
	}
	public void setCrawlerDirectory(String crawlerDirectory) {
		this.crawlerDirectory = crawlerDirectory;
	}
	public double getCrawlerWeightFactor() {
		return crawlerWeightFactor;
	}
	public void setCrawlerWeightFactor(double crawlerWeightFactor) {
		this.crawlerWeightFactor = crawlerWeightFactor;
	}
	public int getCrawlerWeightScore() {
		return crawlerWeightScore;
	}
	public void setCrawlerWeightScore(int crawlerWeightScore) {
		this.crawlerWeightScore = crawlerWeightScore;
	}
	public CrawlerStatus getCrawlerStatus() {
		return crawlerStatus;
	}
	public void setCrawlerStatus(CrawlerStatus crawlerStatus) {
		this.crawlerStatus = crawlerStatus;
	}
	public CrawlerSwitch getCrawlerSwitch() {
		return crawlerSwitch;
	}
	public void setCrawlerSwitch(CrawlerSwitch crawlerSwitch) {
		this.crawlerSwitch = crawlerSwitch;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public TransferCrawlerManagement() {
		super();
	}
	public TransferCrawlerManagement(String crawlerId, String bid, String bkey, String bname, String sld, String cid,
			String ckey, String cname, String crawlerName, String baseUrl, String crawlerDirectory,
			double crawlerWeightFactor, int crawlerWeightScore, CrawlerStatus crawlerStatus, String remark,
			CrawlerSwitch crawlerSwitch) {
		super();
		this.crawlerId = crawlerId;
		this.bid = bid;
		this.bkey = bkey;
		this.bname = bname;
		this.sld = sld;
		this.cid = cid;
		this.ckey = ckey;
		this.cname = cname;
		this.crawlerName = crawlerName;
		this.baseUrl = baseUrl;
		this.crawlerDirectory = crawlerDirectory;
		this.crawlerWeightFactor = crawlerWeightFactor;
		this.crawlerWeightScore = crawlerWeightScore;
		this.crawlerStatus = crawlerStatus;
		this.remark = remark;
		this.crawlerSwitch = crawlerSwitch;
	}
	//根据<domain>构造DTO对象
	public TransferCrawlerManagement(CrawlerManagement crawlerManagement) {
		this.crawlerId = crawlerManagement.getCrawlerId();
		this.bid = crawlerManagement.getCrawlerSource().getBid();
		this.bkey = crawlerManagement.getCrawlerSource().getBkey();
		this.bname = crawlerManagement.getCrawlerSource().getBname();
		this.sld = crawlerManagement.getSld();
		this.cid = crawlerManagement.getCrawlerCategory().getCid();
		this.ckey = crawlerManagement.getCrawlerCategory().getCkey();
		this.cname = crawlerManagement.getCrawlerCategory().getCname();
		this.crawlerName = crawlerManagement.getCrawlerName();
		this.baseUrl = crawlerManagement.getBaseUrl();
		this.crawlerDirectory = crawlerManagement.getCrawlerDirectory();
		this.crawlerWeightFactor = crawlerManagement.getCrawlerWeightFactor();
		this.crawlerWeightScore = crawlerManagement.getCrawlerWeightScore();
		this.crawlerStatus = crawlerManagement.getCrawlerStatus();
		this.remark = crawlerManagement.getRemark();
		this.crawlerSwitch = crawlerManagement.getCrawlerSwitch();
	}
}