package com.jishitoutiao.dto;

import com.jishitoutiao.domain.CrawlerUserAgent;

import java.io.Serializable;

/**
 * 爬虫useragent管理页
 * @author leitianxiang
 *
 */
public class TransferCrawlerUserAgent implements Serializable {
	private String userAgentId;		//user_agent_id
	private String accept;		//Accept
	private String acceptEncoding;		//Accept-Encoding
	private String acceptLanguage;		//Accept-Language
	private String connection;		//Connection
	private String host;		//Host
	private String userAgent;		//User-Agent
	private String remark;		//备注
	
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
	
	public TransferCrawlerUserAgent(String userAgentId, String accept, String acceptEncoding, String acceptLanguage,
			String connection, String host, String userAgent, String remark) {
		super();
		this.userAgentId = userAgentId;
		this.accept = accept;
		this.acceptEncoding = acceptEncoding;
		this.acceptLanguage = acceptLanguage;
		this.connection = connection;
		this.host = host;
		this.userAgent = userAgent;
		this.remark = remark;
	}
	public TransferCrawlerUserAgent() {
		super();
	}	
	//根据<domain>构造DTO对象
	public TransferCrawlerUserAgent(CrawlerUserAgent crawlerUserAgent) {
		this.userAgentId = crawlerUserAgent.getUserAgentId();
		this.accept = crawlerUserAgent.getAccept();
		this.acceptEncoding = crawlerUserAgent.getAcceptEncoding();
		this.acceptLanguage = crawlerUserAgent.getAcceptLanguage();
		this.connection = crawlerUserAgent.getConnection();
		this.host = crawlerUserAgent.getHost();
		this.userAgent = crawlerUserAgent.getUserAgent();
		this.remark = crawlerUserAgent.getRemark();
	}
}