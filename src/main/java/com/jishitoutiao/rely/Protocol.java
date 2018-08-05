package com.jishitoutiao.rely;
/**
 * 动态ip连接协议，所属于domain层CrawlerDynamicIp
 * @author leitianxiang
 *
 */
public enum Protocol {
	HTTP("HTTP"),HTTPS("HTTPS");
	
	private String name;
	
	private Protocol(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
