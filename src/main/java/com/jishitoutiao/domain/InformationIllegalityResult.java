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
 * 滤非法结果表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="information_illegality_result")
public class InformationIllegalityResult implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="illegality_result_id",length=40, nullable=false)
	private String illegalityResultId;
	
	@Column(name="repetition_num",length=40)
	private String repetitionNum;
	
	@ManyToOne
	@JoinColumn(name="bid",nullable=true)
	private CrawlerSource crawlerSource;
	
	@ManyToOne
	@JoinColumn(name="crawler_id",nullable=true)
	private CrawlerManagement crawlerManagement;
	
	@Column(name="information_id",length=40,nullable=false)
	private String informationId;
	
	@Column(name="title",length=40,nullable=false)
	private String title;
	
	@Column(name="read_count",nullable=true)
	private int readCount;
	
	@Column(name="comment_count",nullable=true)
	private int commentCount;
	
	@Column(name="like_count",nullable=true)
	private int likeCount;
	
	@Column(name="word_count",nullable=false)
	private int wordCount;
	
	@Column(name="url",length=100,nullable=false)
	private String url;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time",nullable=false)
	private Date createdTime;
	
	@ManyToOne
	@JoinColumn(name="cid",nullable=true)
	private CrawlerCategory crawlerCategory;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getIllegalityResultId() {
		return illegalityResultId;
	}
	public void setIllegalityResultId(String illegalityResultId) {
		this.illegalityResultId = illegalityResultId;
	}
	public String getRepetitionNum() {
		return repetitionNum;
	}
	public void setRepetitionNum(String repetitionNum) {
		this.repetitionNum = repetitionNum;
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
	public CrawlerCategory getCrawlerCategory() {
		return crawlerCategory;
	}
	public void setCrawlerCategory(CrawlerCategory crawlerCategory) {
		this.crawlerCategory = crawlerCategory;
	}
	
	public InformationIllegalityResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InformationIllegalityResult(String illegalityResultId, String repetitionNum, CrawlerSource crawlerSource,
			CrawlerManagement crawlerManagement, String informationId, String title, int readCount, int commentCount,
			int likeCount, int wordCount, String url, Date createdTime, CrawlerCategory crawlerCategory,
			Date lastUpdate) {
		super();
		this.illegalityResultId = illegalityResultId;
		this.repetitionNum = repetitionNum;
		this.crawlerSource = crawlerSource;
		this.crawlerManagement = crawlerManagement;
		this.informationId = informationId;
		this.title = title;
		this.readCount = readCount;
		this.commentCount = commentCount;
		this.likeCount = likeCount;
		this.wordCount = wordCount;
		this.url = url;
		this.createdTime = createdTime;
		this.crawlerCategory = crawlerCategory;
		this.lastUpdate = lastUpdate;
	}
	@Override
	public String toString() {
		return "InformationIllegalityResult [illegalityResultId=" + illegalityResultId + ", repetitionNum="
				+ repetitionNum + ", crawlerSource=" + crawlerSource + ", crawlerManagement=" + crawlerManagement
				+ ", informationId=" + informationId + ", title=" + title + ", readCount=" + readCount
				+ ", commentCount=" + commentCount + ", likeCount=" + likeCount + ", wordCount=" + wordCount + ", url="
				+ url + ", createdTime=" + createdTime + ", crawlerCategory=" + crawlerCategory + ", lastUpdate="
				+ lastUpdate + "]";
	}
}