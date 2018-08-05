package com.jishitoutiao.dto;

import java.io.Serializable;
import java.util.Date;

import com.jishitoutiao.domain.InformationIllegalityPond;

/**
 * 滤非法池页
 * @author leitianxiang
 *
 */
public class TransferInformationIllegalityPond implements Serializable {
	private String illegalityPondId;		// 滤非法池id
	private String repetitionNum;		//滤重号
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
	
	public String getIllegalityPondId() {
		return illegalityPondId;
	}
	public void setIllegalityPondId(String illegalityPondId) {
		this.illegalityPondId = illegalityPondId;
	}
	public String getRepetitionNum() {
		return repetitionNum;
	}
	public void setRepetitionNum(String repetitionNum) {
		this.repetitionNum = repetitionNum;
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
	
	public TransferInformationIllegalityPond() {
		super();
	}
	public TransferInformationIllegalityPond(String illegalityPondId, String repetitionNum, String bid, String bkey,
			String bname, String crawlerId, String crawlerName, String informationId, String title, int readCount,
			int commentCount, int likeCount, int wordCount, String url, Date createdTime, String cid, String ckey,
			String cname) {
		super();
		this.illegalityPondId = illegalityPondId;
		this.repetitionNum = repetitionNum;
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
	public TransferInformationIllegalityPond(InformationIllegalityPond informationIllegalityPond) {
		this.illegalityPondId = informationIllegalityPond.getIllegalityPondId();
		this.repetitionNum = informationIllegalityPond.getRepetitionNum();
		this.bid = informationIllegalityPond.getCrawlerSource().getBid();
		this.bkey = informationIllegalityPond.getCrawlerSource().getBkey();
		this.bname = informationIllegalityPond.getCrawlerSource().getBname();
		this.crawlerId = informationIllegalityPond.getCrawlerManagement().getCrawlerId();
		this.crawlerName = informationIllegalityPond.getCrawlerManagement().getCrawlerName();
		this.informationId = informationIllegalityPond.getInformationId();
		this.title = informationIllegalityPond.getTitle();
		this.readCount = informationIllegalityPond.getReadCount();
		this.commentCount = informationIllegalityPond.getCommentCount();
		this.likeCount = informationIllegalityPond.getLikeCount();
		this.wordCount = informationIllegalityPond.getWordCount();
		this.url = informationIllegalityPond.getUrl();
		this.createdTime = informationIllegalityPond.getCreatedTime();
		this.cid = informationIllegalityPond.getCrawlerCategory().getCid();
		this.ckey = informationIllegalityPond.getCrawlerCategory().getCkey();
		this.cname = informationIllegalityPond.getCrawlerCategory().getCname();
	}
}