package com.jishitoutiao.dto;

import java.io.Serializable;
import java.util.Date;

import com.jishitoutiao.domain.InformationWeightSource;

/**
 * 权重源数据页
 * @author leitianxiang
 *
 */
public class TransferInformationWeightSource implements Serializable {
	private String weightSourceId;		// 权重源id
	private String bid;		//源id
	private String bkey;	//源key
	private String bname;		//源名称
	
	private String crawlerId;		//爬虫id
	private String crawlerName;		//爬虫名
	
	private String informationId;		//资讯id
	private String title;		//标题
	private int readCount;		//阅读数
	private int commentCount;		//评论数
	private int likeCount;		//点赞次数
	private int wordCount;		//文章字数
	private String url;		//资讯url
	private Date createdTime;		//资讯创建时间
	
	private String cid;		//类别id
	private String ckey;		//类别key
	private String cname;		//类别名称
	
	public String getWeightSourceId() {
		return weightSourceId;
	}
	public void setWeightSourceId(String weightSourceId) {
		this.weightSourceId = weightSourceId;
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
	public String getCrawlerId() {
		return crawlerId;
	}
	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}
	public String getCrawlerName() {
		return crawlerName;
	}
	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}
	public String getInformationId() {
		return informationId;
	}
	public void setInformationId(String informationId) {
		this.informationId = informationId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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
	
	public TransferInformationWeightSource() {
		super();
	}
	public TransferInformationWeightSource(String weightSourceId, String bid, String bkey, String bname,
			String crawlerId, String crawlerName, String informationId, String title, int readCount, int commentCount,
			int likeCount, int wordCount, String url, Date createdTime, String cid, String ckey, String cname) {
		super();
		this.weightSourceId = weightSourceId;
		this.bid = bid;
		this.bkey = bkey;
		this.bname = bname;
		this.crawlerId = crawlerId;
		this.crawlerName = crawlerName;
		this.informationId = informationId;
		this.title = title;
		this.readCount = readCount;
		this.commentCount = commentCount;
		this.likeCount = likeCount;
		this.wordCount = wordCount;
		this.url = url;
		this.createdTime = createdTime;
		this.cid = cid;
		this.ckey = ckey;
		this.cname = cname;
	}
	//根据<domain>构造DTO对象
	public TransferInformationWeightSource(InformationWeightSource informationWeightSource) {
		this.weightSourceId = informationWeightSource.getWeightSourceId();
		this.bid = informationWeightSource.getCrawlerSource().getBid();
		this.bkey = informationWeightSource.getCrawlerSource().getBkey();
		this.bname = informationWeightSource.getCrawlerSource().getBname();
		this.crawlerId = informationWeightSource.getCrawlerManagement().getCrawlerId();
		this.crawlerName = informationWeightSource.getCrawlerManagement().getCrawlerName();
		this.informationId = informationWeightSource.getInformationId();
		this.title = informationWeightSource.getTitle();
		this.readCount = informationWeightSource.getReadCount();
		this.commentCount = informationWeightSource.getCommentCount();
		this.likeCount = informationWeightSource.getLikeCount();
		this.wordCount = informationWeightSource.getWordCount();
		this.url = informationWeightSource.getUrl();
		this.createdTime = informationWeightSource.getCreatedTime();
		this.cid = informationWeightSource.getCrawlerCategory().getCid();
		this.ckey = informationWeightSource.getCrawlerCategory().getCkey();
		this.cname = informationWeightSource.getCrawlerCategory().getCname();
	}
}