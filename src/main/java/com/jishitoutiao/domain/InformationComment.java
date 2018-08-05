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
 * 评论表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="information_comment")
public class InformationComment implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="comment_id",nullable=false)
	private String commentId;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Column(name="content",length=1000,nullable=false)
	private String content;
	
	@ManyToOne
	@JoinColumn(name="output_article_id")
	private InformationOutputArticle informationOutputArticle;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="comment_time")
	private Date commentTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;

	public Date getLastUpdate() {
		return lastUpdate;
	}


	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public String getCommentId() {
		return commentId;
	}


	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public InformationOutputArticle getInformationOutputArticle() {
		return informationOutputArticle;
	}


	public void setInformationOutputArticle(InformationOutputArticle informationOutputArticle) {
		this.informationOutputArticle = informationOutputArticle;
	}


	public Date getCommentTime() {
		return commentTime;
	}


	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}


	public InformationComment() {
		super();
		// TODO Auto-generated constructor stub
	}


	public InformationComment(String commentId, User user, String content,
			InformationOutputArticle informationOutputArticle, Date commentTime, Date lastUpdate) {
		super();
		this.commentId = commentId;
		this.user = user;
		this.content = content;
		this.informationOutputArticle = informationOutputArticle;
		this.commentTime = commentTime;
		this.lastUpdate = lastUpdate;
	}


	@Override
	public String toString() {
		return "InformationComment [commentId=" + commentId + ", user=" + user + ", content=" + content
				+ ", informationOutputArticle=" + informationOutputArticle + ", commentTime=" + commentTime
				+ ", lastUpdate=" + lastUpdate + "]";
	}
}
