package com.jishitoutiao.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component("jwtTokenUtil")
public class JwtTokenUtil implements Serializable {
	// 公用密钥，保存在服务器，客户端不会知道，以防被攻击
	@Value("${jwt.secret}")
	private String SECRET;
	
	// 过期时间(秒数，多少秒后过期)
	@Value("${jwt.expiration}")
	private Long expiration;

	@Value("${jwt.created}")
	private String created;

	private String tokenHead = "Bearer ";

	private Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	/**
	 * 生成token
	 * @return
	 */
	public String generateToken(Map<String, Object> claims) {
		claims.put(created, new Date());		// 授权签发时间
		String jwt = Jwts.builder()
						.setClaims(claims)
						.setExpiration(generateExpirationDate())		// 过期时间
						.signWith(SignatureAlgorithm.HS512, SECRET)		// 加密算法，密钥
						.compact();
		// jwt前面一般都会加Bearer (注：Bearer后有空格)
		return tokenHead + jwt;
	}
	
	/**
	 *  根据设置的过期秒数，转化成毫秒数，加上当前时间，生成过期Date
	 * @return 过期Date
	 */
	public Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
	
	/**
	 * 解析token获取Claims
	 * @param token
	 * @return Claims
	 */
	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try{
			claims = Jwts.parser()
							.setSigningKey(SECRET)
							.parseClaimsJws(token)
							.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}


	/**
	 * 校验token是否有效
	 * @param token
	 * @return 抛异常，token无效；不抛异常，token有效。
	 */
	public void validateToken(String token) {
		// 解析token
		try{
			Map<String, Object> body = Jwts.parser()
											.setSigningKey(SECRET)
											.parseClaimsJws(token.replace(tokenHead, ""))
											.getBody();
			logger.info("token: " + token, "解析后的body: " + body.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IllegalStateException("无效Token, message: "+e.getMessage());
		}
	}


	/**
	 * 更新token
	 * @param token
	 * @return 更新后的token
	 */
	public String refreshToken(String token) {
		String refreshToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(created, new Date());
			refreshToken = generateToken(claims);
		} catch (Exception e) {
			logger.error(e.getMessage());
			refreshToken = null;
		}
		return refreshToken;
	}
}