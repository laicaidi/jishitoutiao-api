package com.jishitoutiao.dto;

import java.io.Serializable;
import java.util.Date;

import com.jishitoutiao.domain.InformationOutputArticle;
import com.jishitoutiao.rely.Type;

/**
 * 资讯内容输出页
 * @author leitianxiang
 *
 */
public class TransferInformationOutputArticle implements Serializable {
	private String outputArticleId;		// 输出资讯内容id
	private String bid;		//源id
	private String bkey;	//源key
	private String bname;		//源名称
	
	private String crawlerId;		//爬虫id
	private String crawlerName;		//爬虫名
	
	private String informationId;		//资讯id
	private String title;		//标题
	
	private String author;		//作者
	private String content;		//资讯内容
	
	private int readCount;		//阅读数
	
	private String origin;		//来源
	
	private Type type;		//文章类型
	private String picurl;		//图片url
	
	private int commentCount;		//评论数
	private int likeCount;		//点赞次数
	
	private String url;		//资讯url
	private Date createdTime;		//资讯创建时间
	
	private String cid;		//类别id
	private String ckey;		//类别key
	private String cname;		//类别名称
	
	public String getOutputArticleId() {
		return outputArticleId;
	}
	public void setOutputArticleId(String outputArticleId) {
		this.outputArticleId = outputArticleId;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
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
	
	public TransferInformationOutputArticle() {
		super();
	}
	public TransferInformationOutputArticle(String outputArticleId, String bid, String bkey, String bname,
			String crawlerId, String crawlerName, String informationId, String title, String author, String content,
			int readCount, String origin, Type type, String picurl, int commentCount, int likeCount, String url,
			Date createdTime, String cid, String ckey, String cname) {
		super();
		this.outputArticleId = outputArticleId;
		this.bid = bid;
		this.bkey = bkey;
		this.bname = bname;
		this.crawlerId = crawlerId;
		this.crawlerName = crawlerName;
		this.informationId = informationId;
		this.title = title;
		this.author = author;
		this.content = content;
		this.readCount = readCount;
		this.origin = origin;
		this.type = type;
		this.picurl = picurl;
		this.commentCount = commentCount;
		this.likeCount = likeCount;
		this.url = url;
		this.createdTime = createdTime;
		this.cid = cid;
		this.ckey = ckey;
		this.cname = cname;
	}
	//根据<domain>构造DTO对象
	public TransferInformationOutputArticle(InformationOutputArticle informationOutputArticle) {
		this.outputArticleId = informationOutputArticle.getOutputArticleId();
		this.bid = informationOutputArticle.getCrawlerSource().getBid();
		this.bkey = informationOutputArticle.getCrawlerSource().getBkey();
		this.bname = informationOutputArticle.getCrawlerSource().getBname();
		this.crawlerId = informationOutputArticle.getCrawlerManagement().getCrawlerId();
		this.crawlerName = informationOutputArticle.getCrawlerManagement().getCrawlerName();
		this.informationId = informationOutputArticle.getInformationId();
		this.title = informationOutputArticle.getTitle();
		this.author = informationOutputArticle.getAuthor();
		this.content = informationOutputArticle.getContent();
		this.readCount = informationOutputArticle.getReadCount();
		this.origin = informationOutputArticle.getOrigin();
		this.type = informationOutputArticle.getType();
		this.picurl = informationOutputArticle.getPicurl();
		this.commentCount = informationOutputArticle.getCommentCount();
		this.likeCount = informationOutputArticle.getLikeCount();
		this.url = informationOutputArticle.getUrl();
		this.createdTime = informationOutputArticle.getCreatedTime();
		this.cid = informationOutputArticle.getCrawlerCategory().getCid();
		this.ckey = informationOutputArticle.getCrawlerCategory().getCkey();
		this.cname = informationOutputArticle.getCrawlerCategory().getCname();
	}
}