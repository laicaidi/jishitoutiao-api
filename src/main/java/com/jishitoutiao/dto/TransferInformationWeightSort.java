package com.jishitoutiao.dto;

import java.io.Serializable;
import java.util.Date;

import com.jishitoutiao.domain.InformationWeightSort;

/**
 * 得分排序页
 * @author leitianxiang
 *
 */
public class TransferInformationWeightSort implements Serializable {
	private String weightSortId;		// 爬虫得分表id
	private String bid;		//源id
	private String bkey;	//源key
	private String bname;		//源名称
	
	private String crawlerId;		//爬虫id
	private String crawlerName;		//爬虫名
	private int crawlerWeightScore;		//爬虫权重分
	private double crawlerWeightFactor;		//爬虫权重系数	
	private double crawlerScore;	//爬虫得分
	
	private String informationId;		//资讯id
	private String title;		//标题
	
	private int readCount;		//阅读数
	private String readWeight;		//阅读权重
	private double readWeightFactor;		//阅读权重系数
	private double readScore;		//阅读得分
	
	private int commentCount;		//评论数
	private String commentWeight;		//评论权重
	private double commentWeightFactor;		//评论权重系数
	private double commentScore;		//评论得分
	
	private int likeCount;		//点赞次数
	private String likeWeight;		//点赞权重
	private double likeWeightFactor;		//点赞权重系数
	private double likeScore;		//点赞得分
	
	private int wordCount;		//文章字数
	private String wordWeight;		//文章字数权重
	private double wordWeightFactor;		//字数权重系数
	private double wordScore;		//字数得分
	
	private String url;		//资讯url
	private Date createdTime;		//资讯创建时间
	
	private double informationScore;		//资讯总得分
	
	private String cid;		//类别id
	private String ckey;		//类别key
	private String cname;		//类别名称
	
	public String getWeightSortId() {
		return weightSortId;
	}
	public void setWeightSortId(String weightSortId) {
		this.weightSortId = weightSortId;
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
	public int getCrawlerWeightScore() {
		return crawlerWeightScore;
	}
	public void setCrawlerWeightScore(int crawlerWeightScore) {
		this.crawlerWeightScore = crawlerWeightScore;
	}
	public double getCrawlerWeightFactor() {
		return crawlerWeightFactor;
	}
	public void setCrawlerWeightFactor(double crawlerWeightFactor) {
		this.crawlerWeightFactor = crawlerWeightFactor;
	}
	public double getCrawlerScore() {
		return crawlerScore;
	}
	public void setCrawlerScore(double crawlerScore) {
		this.crawlerScore = crawlerScore;
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
	public String getReadWeight() {
		return readWeight;
	}
	public void setReadWeight(String readWeight) {
		this.readWeight = readWeight;
	}
	public double getReadWeightFactor() {
		return readWeightFactor;
	}
	public void setReadWeightFactor(double readWeightFactor) {
		this.readWeightFactor = readWeightFactor;
	}
	public double getReadScore() {
		return readScore;
	}
	public void setReadScore(double readScore) {
		this.readScore = readScore;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getCommentWeight() {
		return commentWeight;
	}
	public void setCommentWeight(String commentWeight) {
		this.commentWeight = commentWeight;
	}
	public double getCommentWeightFactor() {
		return commentWeightFactor;
	}
	public void setCommentWeightFactor(double commentWeightFactor) {
		this.commentWeightFactor = commentWeightFactor;
	}
	public double getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(double commentScore) {
		this.commentScore = commentScore;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public String getLikeWeight() {
		return likeWeight;
	}
	public void setLikeWeight(String likeWeight) {
		this.likeWeight = likeWeight;
	}
	public double getLikeWeightFactor() {
		return likeWeightFactor;
	}
	public void setLikeWeightFactor(double likeWeightFactor) {
		this.likeWeightFactor = likeWeightFactor;
	}
	public double getLikeScore() {
		return likeScore;
	}
	public void setLikeScore(double likeScore) {
		this.likeScore = likeScore;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public String getWordWeight() {
		return wordWeight;
	}
	public void setWordWeight(String wordWeight) {
		this.wordWeight = wordWeight;
	}
	public double getWordWeightFactor() {
		return wordWeightFactor;
	}
	public void setWordWeightFactor(double wordWeightFactor) {
		this.wordWeightFactor = wordWeightFactor;
	}
	public double getWordScore() {
		return wordScore;
	}
	public void setWordScore(double wordScore) {
		this.wordScore = wordScore;
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
	public double getInformationScore() {
		return informationScore;
	}
	public void setInformationScore(double informationScore) {
		this.informationScore = informationScore;
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
	
	public TransferInformationWeightSort() {
		super();
	}
	public TransferInformationWeightSort(String weightSortId, String bid, String bkey, String bname, String crawlerId,
			String crawlerName, int crawlerWeightScore, double crawlerWeightFactor, double crawlerScore,
			String informationId, String title, int readCount, String readWeight, double readWeightFactor,
			double readScore, int commentCount, String commentWeight, double commentWeightFactor, double commentScore,
			int likeCount, String likeWeight, double likeWeightFactor, double likeScore, int wordCount,
			String wordWeight, double wordWeightFactor, double wordScore, String url, Date createdTime,
			double informationScore, String cid, String ckey, String cname) {
		super();
		this.weightSortId = weightSortId;
		this.bid = bid;
		this.bkey = bkey;
		this.bname = bname;
		this.crawlerId = crawlerId;
		this.crawlerName = crawlerName;
		this.crawlerWeightScore = crawlerWeightScore;
		this.crawlerWeightFactor = crawlerWeightFactor;
		this.crawlerScore = crawlerScore;
		this.informationId = informationId;
		this.title = title;
		this.readCount = readCount;
		this.readWeight = readWeight;
		this.readWeightFactor = readWeightFactor;
		this.readScore = readScore;
		this.commentCount = commentCount;
		this.commentWeight = commentWeight;
		this.commentWeightFactor = commentWeightFactor;
		this.commentScore = commentScore;
		this.likeCount = likeCount;
		this.likeWeight = likeWeight;
		this.likeWeightFactor = likeWeightFactor;
		this.likeScore = likeScore;
		this.wordCount = wordCount;
		this.wordWeight = wordWeight;
		this.wordWeightFactor = wordWeightFactor;
		this.wordScore = wordScore;
		this.url = url;
		this.createdTime = createdTime;
		this.informationScore = informationScore;
		this.cid = cid;
		this.ckey = ckey;
		this.cname = cname;
	}
	//根据<domain>构造DTO对象
	public TransferInformationWeightSort(InformationWeightSort informationWeightSort) {
		this.weightSortId = informationWeightSort.getWeightSortId();
		this.bid = informationWeightSort.getCrawlerSource().getBid();
		this.bkey = informationWeightSort.getCrawlerSource().getBkey();
		this.bname = informationWeightSort.getCrawlerSource().getBname();
		this.crawlerId = informationWeightSort.getCrawlerManagement().getCrawlerId();
		this.crawlerName = informationWeightSort.getCrawlerManagement().getCrawlerName();
		this.crawlerWeightScore = informationWeightSort.getCrawlerWeightScore();
		this.crawlerWeightFactor = informationWeightSort.getCrawlerWeightFactor();
		this.crawlerScore = informationWeightSort.getCrawlerScore();
		this.informationId = informationWeightSort.getInformationId();
		this.title = informationWeightSort.getTitle();
		this.readCount = informationWeightSort.getReadCount();
		this.readScore = informationWeightSort.getReadScore();
		this.readWeight = informationWeightSort.getReadWeight();
		this.readWeightFactor = informationWeightSort.getReadWeightFactor();
		this.commentCount = informationWeightSort.getCommentCount();
		this.commentScore = informationWeightSort.getCommentScore();
		this.commentWeight = informationWeightSort.getCommentWeight();
		this.commentWeightFactor = informationWeightSort.getCommentWeightFactor();
		this.likeCount = informationWeightSort.getLikeCount();
		this.likeScore = informationWeightSort.getLikeScore();
		this.likeWeight = informationWeightSort.getLikeWeight();
		this.likeWeightFactor = informationWeightSort.getLikeWeightFactor();
		this.wordCount = informationWeightSort.getWordCount();
		this.wordScore = informationWeightSort.getWordScore();
		this.wordWeight = informationWeightSort.getWordWeight();
		this.wordWeightFactor = informationWeightSort.getWordWeightFactor();
		this.url = informationWeightSort.getUrl();
		this.createdTime = informationWeightSort.getCreatedTime();
		this.informationScore = informationWeightSort.getInformationScore();
		this.cid = informationWeightSort.getCrawlerCategory().getCid();
		this.ckey = informationWeightSort.getCrawlerCategory().getCkey();
		this.cname = informationWeightSort.getCrawlerCategory().getCname();
	}
}