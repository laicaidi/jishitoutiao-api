package com.jishitoutiao.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 元数据-菜单组
 * @author leitianxiang
 *
 */
public class TransferMenuGroup implements Serializable {
	private int groupIndex;		// 菜单组
	private String groupKey;		// 菜单组key，纯英文，不可为汉字
	private String groupName;		// 菜单组显示名称，一般为中文
	private List<TransferMenuItem> menuList;		// 该组下的菜单列表
	
	public int getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}
	public String getGroupKey() {
		return groupKey;
	}
	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<TransferMenuItem> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<TransferMenuItem> menuList) {
		this.menuList = menuList;
	}
}
