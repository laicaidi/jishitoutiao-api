package com.jishitoutiao.dto;

import java.io.Serializable;
import java.util.Date;

import com.jishitoutiao.domain.InformationComment;

/**
 * 评论页
 * @author leitianxiang
 *
 */
public class TransferInformationComment implements Serializable {
	private String commentId;		//评论id
	private String userId;		//用户id
	private String username;	//用户名
	private String content;		//评论内容
	private String informationId;		//资讯id
	private Date commentTime;		//评论时间
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInformationId() {
		return informationId;
	}
	public void setInformationId(String informationId) {
		this.informationId = informationId;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	public TransferInformationComment() {
		super();
	}

	public TransferInformationComment(String commentId, String userId, String username, String content, String informationId, Date commentTime) {
		this.commentId = commentId;
		this.userId = userId;
		this.username = username;
		this.content = content;
		this.informationId = informationId;
		this.commentTime = commentTime;
	}

	//根据<domain>构造DTO对象
	public TransferInformationComment(InformationComment informationComment) {
		this.commentId = informationComment.getCommentId();
		this.userId = informationComment.getUser().getUserId();
		this.username = informationComment.getUser().getUsername();
		this.content = informationComment.getContent();
		this.informationId = informationComment.getInformationOutputArticle().getInformationId();
		this.commentTime = informationComment.getCommentTime();
	}
}