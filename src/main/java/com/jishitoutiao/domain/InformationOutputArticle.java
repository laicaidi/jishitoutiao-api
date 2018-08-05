package com.jishitoutiao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.jishitoutiao.rely.Type;

/**
 * 资讯内容输出表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="information_output_article")
public class InformationOutputArticle implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="output_article_id",length=40, nullable=false)
	private String outputArticleId;
	
	@ManyToOne
	@JoinColumn(name="bid",nullable=true)
	private CrawlerSource crawlerSource;
	
	@ManyToOne
	@JoinColumn(name="crawler_id",nullable=true)
	private CrawlerManagement crawlerManagement;
	
	@Column(name="information_id",nullable=false)
	private String informationId;
	
	@Column(name="title",length=40,nullable=false)
	private String title;
	
	@Column(name="author",length=20,nullable=true)
	private String author;
	
	@Column(name="content",length=10000,nullable=false)
	private String content;
	
	@Column(name="read_count",nullable=true)
	private int readCount;
	
	@Column(name="origin",length=20,nullable=true)
	private String origin;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type",length=8,nullable=false)
	private Type type;
	
	@Column(name="picurl",length=100,nullable=true)
	private String picurl;
	
	@Column(name="comment_count",nullable=true)
	private int commentCount;
	
	@Column(name="like_count",nullable=true)
	private int likeCount;
	
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
	
	public String getOutputArticleId() {
		return outputArticleId;
	}

	public void setOutputArticleId(String outputArticleId) {
		this.outputArticleId = outputArticleId;
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


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
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


	public InformationOutputArticle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InformationOutputArticle(String outputArticleId, CrawlerSource crawlerSource,
			CrawlerManagement crawlerManagement, String informationId, String title, String author, String content,
			int readCount, String origin, Type type, String picurl, int commentCount, int likeCount, String url,
			Date createdTime, CrawlerCategory crawlerCategory, Date lastUpdate) {
		super();
		this.outputArticleId = outputArticleId;
		this.crawlerSource = crawlerSource;
		this.crawlerManagement = crawlerManagement;
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
		this.crawlerCategory = crawlerCategory;
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "InformationOutputArticle [outputArticleId=" + outputArticleId + ", crawlerSource=" + crawlerSource
				+ ", crawlerManagement=" + crawlerManagement + ", informationId=" + informationId + ", title=" + title
				+ ", author=" + author + ", content=" + content + ", readCount=" + readCount + ", origin=" + origin
				+ ", type=" + type + ", picurl=" + picurl + ", commentCount=" + commentCount + ", likeCount="
				+ likeCount + ", url=" + url + ", createdTime=" + createdTime + ", crawlerCategory=" + crawlerCategory
				+ ", lastUpdate=" + lastUpdate + "]";
	}
}