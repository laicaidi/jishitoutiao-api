package com.jishitoutiao.dto;

import com.jishitoutiao.domain.CrawlerDynamicIp;
import com.jishitoutiao.rely.Protocol;

import java.io.Serializable;

/**
 * 爬虫动态ip页
 * @author leitianxiang
 *
 */
public class TransferCrawlerDynamicIp implements Serializable {
	private String dynamicId;		//动态ip_id
	private String ipAddress;		//IP地址
	private int port;		//端口号
	private String serverAddress;		//服务器地址
	private String anonymity;		//是否匿名
	private Protocol protocol;		//类型
	private String speed;		//速度
	private String connectTime;		//连接时间
	private String aliveDuration;		//存活时间
	private String verifyTime;		//验证时间
	
	public String getDynamicId() {
		return dynamicId;
	}	
	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public String getAnonymity() {
		return anonymity;
	}
	public void setAnonymity(String anonymity) {
		this.anonymity = anonymity;
	}
	public Protocol getProtocol() {
		return protocol;
	}
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(String connectTime) {
		this.connectTime = connectTime;
	}
	public String getAliveDuration() {
		return aliveDuration;
	}
	public void setAliveDuration(String aliveDuration) {
		this.aliveDuration = aliveDuration;
	}
	public String getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public TransferCrawlerDynamicIp() {
		super();
	}
	public TransferCrawlerDynamicIp(String dynamicId, String ipAddress, int port, String serverAddress,
			String anonymity, Protocol protocol, String speed, String connectTime, String aliveDuration,
			String verifyTime) {
		super();
		this.dynamicId = dynamicId;
		this.ipAddress = ipAddress;
		this.port = port;
		this.serverAddress = serverAddress;
		this.anonymity = anonymity;
		this.protocol = protocol;
		this.speed = speed;
		this.connectTime = connectTime;
		this.aliveDuration = aliveDuration;
		this.verifyTime = verifyTime;
	}
	//根据<domain>构造DTO对象
	public TransferCrawlerDynamicIp(CrawlerDynamicIp crawlerDynamicIp) {
		this.dynamicId = crawlerDynamicIp.getDynamicId();
		this.ipAddress = crawlerDynamicIp.getIpAddress();
		this.port = crawlerDynamicIp.getPort();
		this.serverAddress = crawlerDynamicIp.getServerAddress();
		this.anonymity = crawlerDynamicIp.getAnonymity();
		this.protocol = crawlerDynamicIp.getProtocol();
		this.speed = crawlerDynamicIp.getSpeed();
		this.connectTime = crawlerDynamicIp.getConnectTime();
		this.aliveDuration = crawlerDynamicIp.getAliveDuration();
		this.verifyTime = crawlerDynamicIp.getVerifyTime();
	}
}