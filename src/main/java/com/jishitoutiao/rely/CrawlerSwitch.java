package com.jishitoutiao.rely;
/**
 * 爬虫开关，所属于domain层CrawlerManagement
 * @author leitianxiang
 *
 */
public enum CrawlerSwitch {
	OFF("OFF"),ON("ON");
		
	private String name;
	
	private CrawlerSwitch(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
