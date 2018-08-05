package com.jishitoutiao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.jishitoutiao.rely.Protocol;

/**
 * 爬虫动态ip表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="crawler_dynamic_ip")
public class CrawlerDynamicIp implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="dynamic_id",length=40,nullable=false)
	private String dynamicId;
	
	@Column(name="ip_address",length=40,nullable=false)
	private String ipAddress;
	
	@Column(name="port",nullable=false)
	private int port;
	
	@Column(name="server_address",length=20,nullable=true)
	private String serverAddress;
	
	@Column(name="anonymity",nullable=true)
	private String anonymity;
	
	@Enumerated(EnumType.STRING)
	@Column(name="protocol",length=8,nullable=false)
	private Protocol protocol;
	
	@Column(name="speed",length=20,nullable=true)
	private String speed;
	
	@Column(name="connect_time",length=20,nullable=true)
	private String connectTime;
	
	@Column(name="alive_duration",length=20,nullable=true)
	private String aliveDuration;
	
	@Column(name="verify_time",length=20,nullable=true)
	private String verifyTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
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

	public CrawlerDynamicIp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CrawlerDynamicIp(String dynamicId, String ipAddress, int port, String serverAddress, String anonymity,
			Protocol protocol, String speed, String connectTime, String aliveDuration, String verifyTime,
			Date lastUpdate) {
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
		this.lastUpdate = lastUpdate;
	}
	
	@Override
	public String toString() {
		return "CrawlerDynamicIp [dynamicId=" + dynamicId + ", ipAddress=" + ipAddress + ", port=" + port
				+ ", serverAddress=" + serverAddress + ", anonymity=" + anonymity + ", protocol=" + protocol
				+ ", speed=" + speed + ", connectTime=" + connectTime + ", aliveDuration=" + aliveDuration
				+ ", verifyTime=" + verifyTime + ", lastUpdate=" + lastUpdate + "]";
	}
}
