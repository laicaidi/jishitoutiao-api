package com.jishitoutiao.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jishitoutiao.domain.Role;
import com.jishitoutiao.service.RoleService;
import com.jishitoutiao.util.CryptUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jishitoutiao.domain.User;
import com.jishitoutiao.rely.Sex;
import com.jishitoutiao.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sun.misc.BASE64Encoder;

public class UserServiceTest extends BaseJunit4Test {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	User user = new User();
	
	List<User> list = new ArrayList<User>();
	long count = 0L;
	
	/**
	 * 添加User
	 * @throws ParseException 
	 */
	@Test
	public void testAdd() throws ParseException {

		String time = "2018-08-26 14:14:14";
		Date registrationTime = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		registrationTime = df.parse(time);
		user.setRegistrationTime(registrationTime);

		Role role = roleService.findRoleByName("ADMIN");
		Set<Role> roleSet = new HashSet<Role>();
		roleSet.add(role);
		user.setRoles(roleSet);

		user.setUsername("UTadmin");
		user.setPassword("$2a$10$vIqEAWnoQvDGM.vXemOisOAQJH0hCsDOMpUnYhM19TehWbE6yRUpe");
		user.setPhone("13683365443");
		user.setSex(Sex.MALE);

		try {
			userService.save(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除User
	 */
	@Test
	public void testDelete() {
		user = userService.find("9e9d20386096df87016096df90a80000");
		try {
			userService.delete(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新User
	 */
	@Test
	public void testUpdate() {
		user = userService.find("9e9d20386096df87016096df90a80000");
		user.setHeadPortrait("http");
		try {
			userService.save(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查找指定User
	 */
	@Test
	public void testFind() {
		user = userService.find("9e9d20386096df87016096df90a80000");
		sop(user);
	}
	
	/**
	 * 获取所有User(带搜索条件)
	 */
//	@Test
//	public void testGetAll() {
//		try {
//			list = userService.getAll("ADMIN");
//		} catch (SqlExecuteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (User user : list) {
//			sop(user);
//		}
//	}
//
	/**
	 * 获取分页数据
//	 */
//	@Test
//	public void testGetPageData() {
//		Page<User> page = null;
//		try {
//			page = userService.getPageData("GUEST", "1");
//		} catch (SqlExecuteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (User user : page.getList()) {
//			sop(user);
//		}
//	}
	
	/**
	 * 获取总记录数(带搜索条件)
	 */
	@Test
	public void testGetCount() {
		try {
			count = userService.getCount("GUEST");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sop(count);
	}

	@Test
	public void testPass(){
		String pass = "OTlhM2JlZDRjMDE0YzU5MTFjOTg3MDZkNjEzYTMxMWI=";
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		String hashPass = encode.encode(pass);
		System.out.println("OTlhM2JlZDRjMDE0YzU5MTFjOTg3MDZkNjEzYTMxMWI=: BCrypt Encode: " + hashPass);
	}

	@Test
	public void testBCryptPassSame() {
		String oldPassword = "OTlhM2JlZDRjMDE0YzU5MTFjOTg3MDZkNjEzYTMxMWI=";
		String newPassword = "$2a$10$vIqEAWnoQvDGM.vXemOisOAQJH0hCsDOMpUnYhM19TehWbE6yRUpe";
		boolean same = BCrypt.checkpw(oldPassword, newPassword);
		System.out.println("same: " + same);
	}

	@Test
	public void testMdtPassword() throws Exception {
		String originPassword = "1986120812";
		// 确定计算方法

		String md5Password = CryptUtils.GetMD5Code(originPassword);
		System.out.println("md5Password: " + md5Password);

		BASE64Encoder base64Encoder = new BASE64Encoder();
		// 加密后的字符串
		String base64Password = base64Encoder.encode(md5Password.getBytes("utf-8"));
		System.out.println("base64Password: " + base64Password);
	}

	public void sop(Object obj) {
		System.out.println(obj);
	}
}