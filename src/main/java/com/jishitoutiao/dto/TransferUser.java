package com.jishitoutiao.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.jishitoutiao.domain.Role;
import com.jishitoutiao.domain.User;
import com.jishitoutiao.rely.Sex;

/**
 * 用户页
 * @author leitianxiang
 *
 */
public class TransferUser implements Serializable {
	private String userId;		// 用户id
	private String username;		// 用户名
	private String phone;		// 手机号
	private Sex sex;		// 性别
	private String headPortrait;		// 头像
	private Date registrationTime;		// 注册时间
	private Set<String> roles;		// 角色列表
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHeadPortrait() {
		return headPortrait;
	}
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public Date getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public TransferUser() {
		super();
	}

	public TransferUser(String userId, String username, String phone, Sex sex, String headPortrait, Date registrationTime, Set<String> roles) {
		this.userId = userId;
		this.username = username;
		this.phone = phone;
		this.sex = sex;
		this.headPortrait = headPortrait;
		this.registrationTime = registrationTime;
		this.roles = roles;
	}

	//根据<domain>构造DTO对象
	public TransferUser(User user) {
		this.userId = user.getUserId();
		this.username = user.getUsername();
		this.phone = user.getPhone();
		this.sex = user.getSex();
		this.headPortrait = user.getHeadPortrait();
		this.registrationTime = user.getRegistrationTime();
		Set<String> rolesNameSet = new HashSet<String>();
		for (Role role : user.getRoles()) {
			rolesNameSet.add(role.getRoleName());
		}
		this.roles = rolesNameSet;
	}
}