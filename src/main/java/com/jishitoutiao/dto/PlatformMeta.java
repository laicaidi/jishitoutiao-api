package com.jishitoutiao.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 爬虫管理系统-元数据
 * @author leitianxiang
 *
 */
public class PlatformMeta implements Serializable {
	private List<TransferMenuGroup> menu;		// 元数据-菜单
	private List<TransferCrawlerSourceOption> sourceOption;		// 元数据-爬虫源下拉列表
	private List<TransferCrawlerCategoryList> categoryList;		// 元数据-类别菜单
	
	public List<TransferMenuGroup> getMenu() {
		return menu;
	}
	public void setMenu(List<TransferMenuGroup> menu) {
		this.menu = menu;
	}
	public List<TransferCrawlerSourceOption> getSourceOption() {
		return sourceOption;
	}
	public void setSourceOption(List<TransferCrawlerSourceOption> sourceOption) {
		this.sourceOption = sourceOption;
	}
	public List<TransferCrawlerCategoryList> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<TransferCrawlerCategoryList> categoryList) {
		this.categoryList = categoryList;
	}
}