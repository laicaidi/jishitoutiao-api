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
 * 非法词设置表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="information_illegality_set")
public class InformationIllegalitySet implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="sid",length=40,nullable=false)
	private String sid;
	
	@Column(name="ikey",length=20,nullable=false)
	private String ikey;
	
	@Column(name="ivalue",length=20,nullable=false)
	private String ivalue;
	
	@Column(name="remark",length=100)
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
	public String getIkey() {
		return ikey;
	}
	public void setIkey(String ikey) {
		this.ikey = ikey;
	}
	public String getIvalue() {
		return ivalue;
	}
	public void setIvalue(String ivalue) {
		this.ivalue = ivalue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public InformationIllegalitySet(String sid, String ikey, String ivalue, String remark, Date lastUpdate) {
		super();
		this.sid = sid;
		this.ikey = ikey;
		this.ivalue = ivalue;
		this.remark = remark;
		this.lastUpdate = lastUpdate;
	}
	public InformationIllegalitySet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "InformationIllegalitySet [sid=" + sid + ", ikey=" + ikey + ", ivalue=" + ivalue + ", remark=" + remark
				+ ", lastUpdate=" + lastUpdate + "]";
	}
}
