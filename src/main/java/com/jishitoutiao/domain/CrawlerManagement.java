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
import com.jishitoutiao.rely.CrawlerStatus;
import com.jishitoutiao.rely.CrawlerSwitch;

/**
 * 爬虫管理表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="crawler_management")
public class CrawlerManagement implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	@Column(name="crawler_id",length=40,nullable=false)
	private String crawlerId;
	
	@ManyToOne
	@JoinColumn(name="bid",nullable=true)
	private CrawlerSource crawlerSource;
	
	@Column(name="sld",length=20,nullable=true)
	private String sld;
	
	@ManyToOne
	@JoinColumn(name="cid",nullable=true)
	private CrawlerCategory crawlerCategory;
	
	@Column(name="crawler_name",length=30,nullable=false)
	private String crawlerName;
	
	@Column(name="base_url",length=100,nullable=false)
	private String baseUrl;
	
	@Column(name="crawler_directory",length=100,nullable=false)
	private String crawlerDirectory;
	
	@Column(name="crawler_weight_factor",nullable=false)
	private double crawlerWeightFactor;
	
	@Column(name="crawler_weight_score",nullable=false)
	private int crawlerWeightScore;
	
	@Enumerated(EnumType.STRING)
	@Column(name="crawler_status",nullable=false)
	private CrawlerStatus crawlerStatus;
	
	@Column(name="remark",length=100,nullable=true)
	private String remark;
	
	@Enumerated(EnumType.STRING)
	@Column(name="crawler_switch",nullable=false)
	private CrawlerSwitch crawlerSwitch;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getSld() {
		return sld;
	}
	public void setSld(String sld) {
		this.sld = sld;
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
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getCrawlerDirectory() {
		return crawlerDirectory;
	}
	public void setCrawlerDirectory(String crawlerDirectory) {
		this.crawlerDirectory = crawlerDirectory;
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
	public CrawlerStatus getCrawlerStatus() {
		return crawlerStatus;
	}
	public void setCrawlerStatus(CrawlerStatus crawlerStatus) {
		this.crawlerStatus = crawlerStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public CrawlerSwitch getCrawlerSwitch() {
		return crawlerSwitch;
	}
	public void setCrawlerSwitch(CrawlerSwitch crawlerSwitch) {
		this.crawlerSwitch = crawlerSwitch;
	}

	public CrawlerCategory getCrawlerCategory() {
		return crawlerCategory;
	}
	public void setCrawlerCategory(CrawlerCategory crawlerCategory) {
		this.crawlerCategory = crawlerCategory;
	}
	public CrawlerSource getCrawlerSource() {
		return crawlerSource;
	}
	public void setCrawlerSource(CrawlerSource crawlerSource) {
		this.crawlerSource = crawlerSource;
	}
	
	public CrawlerManagement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CrawlerManagement(String crawlerId, CrawlerSource crawlerSource, String sld, CrawlerCategory crawlerCategory,
			String crawlerName, String baseUrl, String crawlerDirectory, double crawlerWeightFactor,
			int crawlerWeightScore, CrawlerStatus crawlerStatus, String remark, CrawlerSwitch crawlerSwitch,
			Date lastUpdate) {
		super();
		this.crawlerId = crawlerId;
		this.crawlerSource = crawlerSource;
		this.sld = sld;
		this.crawlerCategory = crawlerCategory;
		this.crawlerName = crawlerName;
		this.baseUrl = baseUrl;
		this.crawlerDirectory = crawlerDirectory;
		this.crawlerWeightFactor = crawlerWeightFactor;
		this.crawlerWeightScore = crawlerWeightScore;
		this.crawlerStatus = crawlerStatus;
		this.remark = remark;
		this.crawlerSwitch = crawlerSwitch;
		this.lastUpdate = lastUpdate;
	}
	@Override
	public String toString() {
		return "CrawlerManagement [crawlerId=" + crawlerId + ", crawlerSource=" + crawlerSource + ", sld=" + sld
				+ ", crawlerCategory=" + crawlerCategory + ", crawlerName=" + crawlerName + ", baseUrl=" + baseUrl
				+ ", crawlerDirectory=" + crawlerDirectory + ", crawlerWeightFactor=" + crawlerWeightFactor
				+ ", crawlerWeightScore=" + crawlerWeightScore + ", crawlerStatus=" + crawlerStatus + ", remark="
				+ remark + ", crawlerSwitch=" + crawlerSwitch + ", lastUpdate=" + lastUpdate + "]";
	}
}
