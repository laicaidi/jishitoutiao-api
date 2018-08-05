package com.jishitoutiao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 资讯列表输出表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="information_output_list")
public class InformationOutputList implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="output_list_id",length=40, nullable=false)
	private String outputListId;
	
	@ManyToOne
	@JoinColumn(name="bid",nullable=true)
	private CrawlerSource crawlerSource;
	
	@ManyToOne
	@JoinColumn(name="crawler_id",nullable=true)
	private CrawlerManagement crawlerManagement;
	
	@Column(name="crawler_weight_factor",nullable=true)
	private double crawlerWeightFactor;
	
	@Column(name="crawler_weight_score",nullable=true)
	private int crawlerWeightScore;
	
	@Column(name="crawler_score",nullable=true)
	private double crawlerScore;
	
	@Column(name="information_id",nullable=false)
	private String informationId;
	
	@Column(name="title",length=40,nullable=false)
	private String title;
	
	@Column(name="read_weight",length=20,nullable=true)
	private String readWeight;
	
	@Column(name="read_weight_factor",nullable=true)
	private double readWeightFactor;
	
	@Column(name="read_count",nullable=true)
	private int readCount;
	
	@Column(name="read_score",nullable=true)
	private double readScore;
	
	@Column(name="comment_weight",length=20,nullable=true)
	private String commentWeight;
	
	@Column(name="comment_weight_factor",nullable=true)
	private double commentWeightFactor;
	
	@Column(name="comment_count",nullable=true)
	private int commentCount;
	
	@Column(name="comment_score",nullable=true)
	private double commentScore;
	
	@Column(name="like_weight",length=20,nullable=true)
	private String likeWeight;
	
	@Column(name="like_weight_factor",nullable=true)
	private double likeWeightFactor;
	
	@Column(name="like_count",nullable=true)
	private int likeCount;
	
	@Column(name="like_score",nullable=true)
	private double likeScore;
	
	@Column(name="word_weight",length=20,nullable=true)
	private String wordWeight;
	
	@Column(name="word_weight_factor",nullable=true)
	private double wordWeightFactor;
	
	@Column(name="word_count",nullable=true)
	private int wordCount;
	
	@Column(name="word_score",nullable=true)
	private double wordScore;
	
	@Column(name="url",length=100,nullable=false)
	private String url;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time",nullable=false)
	private Date createdTime;
	
	@Column(name="information_score",nullable=false)
	private double informationScore;
	
	@ManyToOne
	@JoinColumn(name="cid",nullable=true)
	private CrawlerCategory crawlerCategory;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;

	public String getOutputListId() {
		return outputListId;
	}

	public void setOutputListId(String outputListId) {
		this.outputListId = outputListId;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public CrawlerSource getCrawlerSource() {
		return crawlerSource;
	}

	public void setCrawlerSource(CrawlerSource crawlerSource) {
		this.crawlerSource = crawlerSource;
	}

	public CrawlerManagement getCrawlerManagement() {
		return crawlerManagement;
	}

	public void setCrawlerManagement(CrawlerManagement crawlerManagement) {
		this.crawlerManagement = crawlerManagement;
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

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public double getReadScore() {
		return readScore;
	}

	public void setReadScore(double readScore) {
		this.readScore = readScore;
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

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public double getCommentScore() {
		return commentScore;
	}

	public void setCommentScore(double commentScore) {
		this.commentScore = commentScore;
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

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public double getLikeScore() {
		return likeScore;
	}

	public void setLikeScore(double likeScore) {
		this.likeScore = likeScore;
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

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
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

	public CrawlerCategory getCrawlerCategory() {
		return crawlerCategory;
	}

	public void setCrawlerCategory(CrawlerCategory crawlerCategory) {
		this.crawlerCategory = crawlerCategory;
	}

	public InformationOutputList() {
		super();
	}

	public InformationOutputList(String outputListId, CrawlerSource crawlerSource, CrawlerManagement crawlerManagement,
			double crawlerScore, String informationId, String title, String readWeight, double readWeightFactor,
			int readCount, double readScore, String commentWeight, double commentWeightFactor, int commentCount,
			double commentScore, String likeWeight, double likeWeightFactor, int likeCount, double likeScore,
			String wordWeight, double wordWeightFactor, int wordCount, double wordScore, String url, Date createdTime,
			double informationScore, CrawlerCategory crawlerCategory, Date lastUpdate) {
		super();
		this.outputListId = outputListId;
		this.crawlerSource = crawlerSource;
		this.crawlerManagement = crawlerManagement;
		this.crawlerScore = crawlerScore;
		this.informationId = informationId;
		this.title = title;
		this.readWeight = readWeight;
		this.readWeightFactor = readWeightFactor;
		this.readCount = readCount;
		this.readScore = readScore;
		this.commentWeight = commentWeight;
		this.commentWeightFactor = commentWeightFactor;
		this.commentCount = commentCount;
		this.commentScore = commentScore;
		this.likeWeight = likeWeight;
		this.likeWeightFactor = likeWeightFactor;
		this.likeCount = likeCount;
		this.likeScore = likeScore;
		this.wordWeight = wordWeight;
		this.wordWeightFactor = wordWeightFactor;
		this.wordCount = wordCount;
		this.wordScore = wordScore;
		this.url = url;
		this.createdTime = createdTime;
		this.informationScore = informationScore;
		this.crawlerCategory = crawlerCategory;
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "InformationOutputList [outputListId=" + outputListId + ", crawlerSource=" + crawlerSource
				+ ", crawlerManagement=" + crawlerManagement + ", crawlerScore=" + crawlerScore + ", informationId="
				+ informationId + ", title=" + title + ", readWeight=" + readWeight + ", readWeightFactor="
				+ readWeightFactor + ", readCount=" + readCount + ", readScore=" + readScore + ", commentWeight="
				+ commentWeight + ", commentWeightFactor=" + commentWeightFactor + ", commentCount=" + commentCount
				+ ", commentScore=" + commentScore + ", likeWeight=" + likeWeight + ", likeWeightFactor="
				+ likeWeightFactor + ", likeCount=" + likeCount + ", likeScore=" + likeScore + ", wordWeight="
				+ wordWeight + ", wordWeightFactor=" + wordWeightFactor + ", wordCount=" + wordCount + ", wordScore="
				+ wordScore + ", url=" + url + ", createdTime=" + createdTime + ", informationScore=" + informationScore
				+ ", crawlerCategory=" + crawlerCategory + ", lastUpdate=" + lastUpdate + "]";
	}
}