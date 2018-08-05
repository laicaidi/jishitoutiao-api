package com.jishitoutiao.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.jishitoutiao.rely.Sex;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="user")
public class User implements Serializable{

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="user_id",length=40,nullable=false)
	private String userId;
	
	@Column(name="username",length=20,nullable=false)
	private String username;
	
	@Column(name="phone",length=20,nullable=false)
	private String phone;
	
	@Column(name="password",length=40,nullable=true)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="sex",length=8,nullable=true)
	private Sex sex;
	
	@Column(name="head_portrait",length=100,nullable=true)
	private String headPortrait;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="registration_time",nullable=false)
	private Date registrationTime;

	@Column(name="access_token",length=100,nullable=true)
	private String accessToken;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="issued_at",nullable=true)
	private Date issuedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expires_in",nullable=true)
	private Date expiresIn;

	@Column(name="passport",length=8,nullable=true)
	private String passport;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;

	@ManyToMany
	@JoinTable(
			name = "users_roles",		// 中间表的名字
			joinColumns = {@JoinColumn(name="user_id")},		// 外键名字
			inverseJoinColumns = {@JoinColumn(name="role_id")}		// 反转控制字段的名字
	)
	private Set<Role> roles;		// 用户持有的角色列表

	public Date getLastUpdate() {
		return lastUpdate;
	}


	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Sex getSex() {
		return sex;
	}


	public void setSex(Sex sex) {
		this.sex = sex;
	}


	public String getHeadPortrait() {
		return headPortrait;
	}


	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}


	public Date getRegistrationTime() {
		return registrationTime;
	}


	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public Date getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Date expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(String username, String phone, String password, Sex sex, String headPortrait, Date registrationTime, String accessToken, Date issuedAt, Date expiresIn, String passport, Date lastUpdate) {
		this.username = username;
		this.phone = phone;
		this.password = password;
		this.sex = sex;
		this.headPortrait = headPortrait;
		this.registrationTime = registrationTime;
		this.accessToken = accessToken;
		this.issuedAt = issuedAt;
		this.expiresIn = expiresIn;
		this.passport = passport;
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId='" + userId + '\'' +
				", username='" + username + '\'' +
				", phone='" + phone + '\'' +
				", password='" + password + '\'' +
				", sex=" + sex +
				", headPortrait='" + headPortrait + '\'' +
				", registrationTime=" + registrationTime +
				", accessToken='" + accessToken + '\'' +
				", issuedAt=" + issuedAt +
				", expiresIn=" + expiresIn +
				", passport='" + passport + '\'' +
				", lastUpdate=" + lastUpdate +
				'}';
	}
}