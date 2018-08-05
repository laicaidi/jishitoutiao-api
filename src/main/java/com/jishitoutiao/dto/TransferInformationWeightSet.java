package com.jishitoutiao.dto;

import com.jishitoutiao.domain.InformationWeightSet;

import java.io.Serializable;

/**
 * 权重设置页
 * @author leitianxiang
 *
 */
public class TransferInformationWeightSet implements Serializable {
	private String sid;		//设置id
	private String wkey;		//键
	private double wvalue;		//值
	private String remark;		//备注
	
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

	public TransferInformationWeightSet(String sid, String wkey, double wvalue, String remark) {
		super();
		this.sid = sid;
		this.wkey = wkey;
		this.wvalue = wvalue;
		this.remark = remark;
	}
	public TransferInformationWeightSet() {
		super();
	}
	//根据<domain>构造DTO对象
	public TransferInformationWeightSet(InformationWeightSet informationWeightSet) {
		this.sid = informationWeightSet.getSid();
		this.wkey = informationWeightSet.getWkey();
		this.wvalue = informationWeightSet.getWvalue();
		this.remark = informationWeightSet.getRemark();
	}
}