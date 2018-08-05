package com.jishitoutiao.rely;

/**
 * 爬虫状态，所属于domain层CrawlerManagement
 * @author leitianxiang
 *
 */
public enum CrawlerStatus {
	ABNORMAL("ABNORMAL"),NORMAL("NORMAL");
	
	private String name;
	
	CrawlerStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
