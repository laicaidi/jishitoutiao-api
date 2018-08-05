package com.jishitoutiao.dto;

import java.io.Serializable;

/**
 * 元数据-菜单项
 * @author leitianxiang
 *
 */
public class TransferMenuItem implements Serializable {
	private int itemIndex;		// 菜单项排序编号
	private String itemKey;		// 菜单项key，纯英文，不可为数字
	private String itemName;	// 菜单项名称，一般为中文
	
	public int getItemIndex() {
		return itemIndex;
	}
	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}
	public String getItemKey() {
		return itemKey;
	}
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}