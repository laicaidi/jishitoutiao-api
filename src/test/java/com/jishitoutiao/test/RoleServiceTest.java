package com.jishitoutiao.test;

import com.jishitoutiao.domain.Role;
import com.jishitoutiao.rely.Sex;
import com.jishitoutiao.service.RoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoleServiceTest extends BaseJunit4Test {
	@Autowired
	private RoleService roleService;
	
	Role role = new Role();
	
	List<Role> list = new ArrayList<Role>();
	long count = 0L;
	
	/**
	 * 添加Role
	 * @throws ParseException 
	 */
	@Test
	public void testAdd() throws ParseException {


		Role role = new Role();
		role.setRoleName("GUEST");

		try {
			roleService.save(role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 删除Role
	 */
	@Test
	public void testDelete() {
		role = roleService.find("9e9d20386096df87016096df90a80000");
		try {
			roleService.delete(role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新Role
	 */
	@Test
	public void testUpdate() {
		role = roleService.find("9e9d20386096df87016096df90a80000");
//		role.setHeadPortrait("http");
		try {
			roleService.save(role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查找指定Role
	 */
	@Test
	public void testFind() {
		role = roleService.find("9e9d20386096df87016096df90a80000");
		sop(role);
	}
	
	/**
	 * 获取所有Role(带搜索条件)
	 */
//	@Test
//	public void testGetAll() {
//		try {
//			list = roleService.getAll("ADMIN");
//		} catch (SqlExecuteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (Role role : list) {
//			sop(role);
//		}
//	}
//
	/**
	 * 获取分页数据
//	 */
//	@Test
//	public void testGetPageData() {
//		Page<Role> page = null;
//		try {
//			page = roleService.getPageData("GUEST", "1");
//		} catch (SqlExecuteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (Role role : page.getList()) {
//			sop(role);
//		}
//	}
	
	/**
	 * 获取总记录数(带搜索条件)
	 */
	@Test
	public void testGetCount() {
		try {
			count = roleService.getCount("GUEST");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sop(count);
	}
	
	public void sop(Object obj) {
		System.out.println(obj);
	}
}