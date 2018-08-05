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
 * 爬虫类别表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="crawler_category")
public class CrawlerCategory implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	@Column(name="cid",length=40,nullable=false)
	private String cid;
	
	@Column(name="ckey",length=20,nullable=false)
	private String ckey;
	
	@Column(name="cname",length=10,nullable=false)
	private String cname;
	
	@Column(name="remark",length=50,nullable=true)
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
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCkey() {
		return ckey;
	}
	public void setCkey(String ckey) {
		this.ckey = ckey;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public CrawlerCategory(String cid, String ckey, String cname, String remark, Date lastUpdate) {
		super();
		this.cid = cid;
		this.ckey = ckey;
		this.cname = cname;
		this.remark = remark;
		this.lastUpdate = lastUpdate;
	}
	public CrawlerCategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "CrawlerCategory [cid=" + cid + ", ckey=" + ckey + ", cname=" + cname + ", remark=" + remark
				+ ", lastUpdate=" + lastUpdate + "]";
	}
}
