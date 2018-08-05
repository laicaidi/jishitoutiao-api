package com.jishitoutiao.dto;

import java.io.Serializable;
import java.util.Date;

import com.jishitoutiao.domain.InformationOutputList;

/**
 * 资讯列表输出页
 * @author leitianxiang
 *
 */
public class TransferInformationOutputList implements Serializable {
	private String outputListId;		// 列表输出表id
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
	
	public String getOutputListId() {
		return outputListId;
	}
	public void setOutputListId(String outputListId) {
		this.outputListId = outputListId;
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
	
	public TransferInformationOutputList() {
		super();
	}
	
	public TransferInformationOutputList(String outputListId, String bid, String bkey, String bname, String crawlerId,
			String crawlerName, int crawlerWeightScore, double crawlerWeightFactor, double crawlerScore,
			String informationId, String title, int readCount, String readWeight, double readWeightFactor,
			double readScore, int commentCount, String commentWeight, double commentWeightFactor, double commentScore,
			int likeCount, String likeWeight, double likeWeightFactor, double likeScore, int wordCount,
			String wordWeight, double wordWeightFactor, double wordScore, String url, Date createdTime,
			double informationScore, String cid, String ckey, String cname) {
		super();
		this.outputListId = outputListId;
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
	public TransferInformationOutputList(InformationOutputList informationOutputList) {
		this.outputListId = informationOutputList.getOutputListId();
		this.bid = informationOutputList.getCrawlerSource().getBid();
		this.bkey = informationOutputList.getCrawlerSource().getBkey();
		this.bname = informationOutputList.getCrawlerSource().getBname();
		this.crawlerId = informationOutputList.getCrawlerManagement().getCrawlerId();
		this.crawlerName = informationOutputList.getCrawlerManagement().getCrawlerName();
		this.crawlerWeightScore = informationOutputList.getCrawlerWeightScore();
		this.crawlerWeightFactor = informationOutputList.getCrawlerWeightFactor();
		this.crawlerScore = informationOutputList.getCrawlerScore();
		this.informationId = informationOutputList.getInformationId();
		this.title = informationOutputList.getTitle();
		this.readCount = informationOutputList.getReadCount();
		this.readScore = informationOutputList.getReadScore();
		this.readWeight = informationOutputList.getReadWeight();
		this.readWeightFactor = informationOutputList.getReadWeightFactor();
		this.commentCount = informationOutputList.getCommentCount();
		this.commentScore = informationOutputList.getCommentScore();
		this.commentWeight = informationOutputList.getCommentWeight();
		this.commentWeightFactor = informationOutputList.getCommentWeightFactor();
		this.likeCount = informationOutputList.getLikeCount();
		this.likeScore = informationOutputList.getLikeScore();
		this.likeWeight = informationOutputList.getLikeWeight();
		this.likeWeightFactor = informationOutputList.getLikeWeightFactor();
		this.wordCount = informationOutputList.getWordCount();
		this.wordScore = informationOutputList.getWordScore();
		this.wordWeight = informationOutputList.getWordWeight();
		this.wordWeightFactor = informationOutputList.getWordWeightFactor();
		this.url = informationOutputList.getUrl();
		this.createdTime = informationOutputList.getCreatedTime();
		this.informationScore = informationOutputList.getInformationScore();
		this.cid = informationOutputList.getCrawlerCategory().getCid();
		this.ckey = informationOutputList.getCrawlerCategory().getCkey();
		this.cname = informationOutputList.getCrawlerCategory().getCname();
	}
}