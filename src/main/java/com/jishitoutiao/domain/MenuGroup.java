package com.jishitoutiao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 菜单组表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="menu_group")
public class MenuGroup implements Serializable {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="group_id",length=40)
	private String groupId;
	
	@Column(name="group_index",nullable=false)
	private int groupIndex;
	
	@Column(name="group_key",length=20,nullable=false)
	private String groupKey;
	
	@Column(name="group_name",length=20,nullable=false)
	private String groupName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
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
	
	public MenuGroup(String groupId, int groupIndex, String groupKey, String groupName, Date lastUpdate) {
		super();
		this.groupId = groupId;
		this.groupIndex = groupIndex;
		this.groupKey = groupKey;
		this.groupName = groupName;
		this.lastUpdate = lastUpdate;
	}
	public MenuGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "MenuGroup [groupId=" + groupId + ", groupIndex=" + groupIndex + ", groupKey=" + groupKey
				+ ", groupName=" + groupName + ", lastUpdate=" + lastUpdate + "]";
	}
}