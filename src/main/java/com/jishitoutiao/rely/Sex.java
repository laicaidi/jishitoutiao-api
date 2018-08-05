package com.jishitoutiao.rely;
/**
 * 用户性别，所属于domain层User
 * @author leitianxiang
 *
 */
public enum Sex {
	MALE("MALE"),FEMALE("FEMALE");
	
	private String name;
	
	private Sex(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
