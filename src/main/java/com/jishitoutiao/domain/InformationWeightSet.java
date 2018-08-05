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
 * 权重设置表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="information_weight_set")
public class InformationWeightSet implements Serializable {
	/*
create table information_weight_set
(
	sid varchar(40) comment '设置id' primary key,
	wkey varchar(20) comment '键' unique not null,
	wvalue double comment '值' not null,
	remark varchar(100) comment '备注',
	last_update timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)character set utf8 collate utf8_general_ci;
	 */
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="sid",length=40,nullable=false)
	private String sid;
	
	@Column(name="wkey",length=20,nullable=false)
	private String wkey;
	
	@Column(name="wvalue",nullable=false)
	private double wvalue;
	
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
	public String getWkey() {
		return wkey;
	}
	public void setWkey(String wkey) {
		this.wkey = wkey;
	}
	public double getWvalue() {
		return wvalue;
	}
	public void setWvalue(double wvalue) {
		this.wvalue = wvalue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public InformationWeightSet(String sid, String wkey, double wvalue, String remark, Date lastUpdate) {
		super();
		this.sid = sid;
		this.wkey = wkey;
		this.wvalue = wvalue;
		this.remark = remark;
		this.lastUpdate = lastUpdate;
	}
	public InformationWeightSet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "InformationWeightSet [sid=" + sid + ", wkey=" + wkey + ", wvalue=" + wvalue + ", remark=" + remark
				+ ", lastUpdate=" + lastUpdate + "]";
	}
}