package com.jishitoutiao.dto;

import java.io.Serializable;

public class AuthenticationResult implements Serializable {
	private boolean passport;		// 授权(token)是否有效
	private String accessToken;		// token
	public boolean isPassport() {
		return passport;
	}
	public void setPassport(boolean passport) {
		this.passport = passport;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}