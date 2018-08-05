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
 * 爬虫设置表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="crawler_set")
public class CrawlerSet implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="sid",length=40,nullable=false)
	private String sid;
	
	@Column(name="ckey",length=20,nullable=false)
	private String ckey;
	
	@Column(name="cvalue",length=20,nullable=false)
	private String cvalue;
	
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
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getCkey() {
		return ckey;
	}
	public void setCkey(String ckey) {
		this.ckey = ckey;
	}
	public String getCvalue() {
		return cvalue;
	}
	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
		
	public CrawlerSet(String sid, String ckey, String cvalue, String remark, Date lastUpdate) {
		super();
		this.sid = sid;
		this.ckey = ckey;
		this.cvalue = cvalue;
		this.remark = remark;
		this.lastUpdate = lastUpdate;
	}
	public CrawlerSet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "CrawlerSet [sid=" + sid + ", ckey=" + ckey + ", cvalue=" + cvalue + ", remark=" + remark
				+ ", lastUpdate=" + lastUpdate + "]";
	}
}
