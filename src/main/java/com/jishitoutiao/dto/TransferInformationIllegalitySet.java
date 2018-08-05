package com.jishitoutiao.dto;

import com.jishitoutiao.domain.InformationIllegalitySet;

import java.io.Serializable;

/**
 * 非法词设置页
 * @author leitianxiang
 *
 */
public class TransferInformationIllegalitySet implements Serializable {
	private String sid;		//设置id
	private String ikey;		//键
	private String ivalue;		//值
	private String remark;		//备注
	
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
	
	public TransferInformationIllegalitySet(String sid, String ikey, String ivalue, String remark) {
		super();
		this.sid = sid;
		this.ikey = ikey;
		this.ivalue = ivalue;
		this.remark = remark;
	}
	public TransferInformationIllegalitySet() {
		super();
	}
	//根据<domain>构造DTO对象
	public TransferInformationIllegalitySet(InformationIllegalitySet informationIllegalitySet) {
		this.sid = informationIllegalitySet.getSid();
		this.ikey = informationIllegalitySet.getIkey();
		this.ivalue = informationIllegalitySet.getIvalue();
		this.remark = informationIllegalitySet.getRemark();
	}
}