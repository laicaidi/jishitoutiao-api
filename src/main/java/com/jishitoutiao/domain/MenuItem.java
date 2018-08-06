package com.jishitoutiao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 菜单项表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="menu_item")
public class MenuItem implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="item_id",length=40)
	private String itemId;
	
	@ManyToOne
	@JoinColumn(name="group_id",nullable=false)
	private MenuGroup menuGroup;
	
	@Column(name="item_index",nullable=false)
	private int itemIndex;
	
	@Column(name="item_key",length=20,nullable=false)
	private String itemKey;
	
	@Column(name="item_name",length=20,nullable=false)
	private String itemName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

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

	public MenuItem(String itemId, MenuGroup menuGroup, int itemIndex, String itemKey, String itemName,
			Date lastUpdate) {
		super();
		this.itemId = itemId;
		this.menuGroup = menuGroup;
		this.itemIndex = itemIndex;
		this.itemKey = itemKey;
		this.itemName = itemName;
		this.lastUpdate = lastUpdate;
	}

	public MenuItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MenuItem [itemId=" + itemId + ", menuGroup=" + menuGroup + ", itemIndex=" + itemIndex + ", itemKey="
				+ itemKey + ", itemName=" + itemName + ", lastUpdate=" + lastUpdate + "]";
	}
}