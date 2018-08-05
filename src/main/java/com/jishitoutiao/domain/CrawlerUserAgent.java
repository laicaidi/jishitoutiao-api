package com.jishitoutiao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 爬虫useragent表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="crawler_user_agent")
public class CrawlerUserAgent implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="user_agent_id",length=40,nullable=false)
	private String userAgentId;
	
	@Column(name="accept",length=200,nullable=true)
	private String accept;
	
	@Column(name="accept_encoding",length=50,nullable=true)
	private String acceptEncoding;
	
	@Column(name="accept_language",length=50,nullable=true)
	private String acceptLanguage;
	
	@Column(name="connection",length=20,nullable=true)
	private String connection;
	
	@Column(name="host",length=100,nullable=true)
	private String host;
	
	@Column(name="user_agent",length=200,nullable=false,unique=true)
	private String userAgent;
	
	@Column(name="remark",length=100,nullable=true)
	private String remark;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getUserAgentId() {
		return userAgentId;
	}
	public void setUserAgentId(String userAgentId) {
		this.userAgentId = userAgentId;
	}
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public String getAcceptEncoding() {
		return acceptEncoding;
	}
	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}
	public String getAcceptLanguage() {
		return acceptLanguage;
	}
	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public CrawlerUserAgent(String userAgentId, String accept, String acceptEncoding, String acceptLanguage,
			String connection, String host, String userAgent, String remark, Date lastUpdate) {
		super();
		this.userAgentId = userAgentId;
		this.accept = accept;
		this.acceptEncoding = acceptEncoding;
		this.acceptLanguage = acceptLanguage;
		this.connection = connection;
		this.host = host;
		this.userAgent = userAgent;
		this.remark = remark;
		this.lastUpdate = lastUpdate;
	}
	public CrawlerUserAgent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "CrawlerUserAgent [userAgentId=" + userAgentId + ", accept=" + accept + ", acceptEncoding="
				+ acceptEncoding + ", acceptLanguage=" + acceptLanguage + ", connection=" + connection + ", host="
				+ host + ", userAgent=" + userAgent + ", remark=" + remark + ", lastUpdate=" + lastUpdate + "]";
	}
}
