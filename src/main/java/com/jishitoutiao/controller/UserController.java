package com.jishitoutiao.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jishitoutiao.domain.Role;
import com.jishitoutiao.rely.PageObj;
import com.jishitoutiao.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishitoutiao.domain.User;
import com.jishitoutiao.dto.AuthenticationResult;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferUser;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.rely.Sex;
import com.jishitoutiao.service.UserService;
import com.jishitoutiao.util.JwtTokenUtil;
/**
 * 方法权限控制:
 * 在 @PreAuthorize 中我们可以利用内建的 SPEL 表达式：比如 'hasRole()' 来决定哪些用户有权访问。
 * 需注意的一点是 hasRole 表达式认为每个角色名字前都有一个前缀 'ROLE_'。所以下面的 'ADMIN' 其实在
 * 数据库中存储的是 'ROLE_ADMIN' 。 @PreAuthorize 可以修饰Controller也可修饰Controller中的方法。
 **/
@RestController
@RequestMapping(value="/user")
// @PreAuthorize("hasRole('ADMIN')")
public class UserController {
	@Autowired
	private UserService userService;		//服务

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	 /**
	  * 获取所有User
	  * @param keyword 查询关键字
	  * @param pageNum 当前页码
	  * @return Json数据
	  */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ResponseEntity<DataResponse> getAll(@RequestParam(value="keyword",required=false) String keyword,
											@RequestParam(value="page_num",defaultValue="1") String pageNum) {		
		//记录日志
		logger.info("value=/,method=RequestMethod.GET: "
				+ "keyword: " + keyword
				+ " ,page_num: " + pageNum);
		
		//用于和前端交互的DTO对象
		TransferUser transferUser = null;
		List<TransferUser> tlist = new ArrayList<TransferUser>();
		List<TableColumn> columnList = new ArrayList<TableColumn>();
		
		//用于接受后端数据的Page对象
		PageObj<User> dpage = null;
		
		//1.获取指定搜索条件，页码的全部数据
		try {
			dpage = userService.getPageData(keyword, pageNum);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("获取数据失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//如果没有数据
		if (dpage.getList().isEmpty()) {
			//消息体封装message提示，并返回HttpStatus状态，下同
			DataResponse response = new DataResponse().success("无数据");
			//200 OK
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//2.将获取的Dmain对象数据转为与前端交互的DTO数据
		//2.1初始化DTO 的Page数据
		PageObj<TransferUser> tpage = new PageObj<TransferUser>(dpage.getPageNum(), dpage.getTotalRecord());
		
		//2.2将获取的Dmain对象添加到DTO 的Page对象的List中
		for (User us : dpage.getList()) {
			transferUser = new TransferUser(us);
			tlist.add(transferUser);
		}
		tpage.setList(tlist);
		
		//2.3将table需要的column标题封装至tpage
		columnList.add(new TableColumn("用户id", "user_id", "user_id"));
		columnList.add(new TableColumn("用户名", "username", "username"));
		columnList.add(new TableColumn("关联手机号", "phone", "phone"));
		columnList.add(new TableColumn("性别", "sex", "sex"));
		columnList.add(new TableColumn("头像", "head_portrait", "head_portrait"));
		columnList.add(new TableColumn("注册时间", "registration_time", "registration_time"));
		columnList.add(new TableColumn("角色", "roles", "roles"));
		tpage.setColumns(columnList);
		
		//3.返回DTO对象(通过DataResponse封装)
		DataResponse response = new DataResponse().success(tpage);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 通过id获取User
	 * @param id 用户uuid
	 * @return Json数据
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> find(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.GET: "
				+ "id: " + id);
		
		//1.获取原始domain对象
		User user = null;
		try {
			user = userService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.将domain对象转化为DTO对象
		TransferUser transferUser = new TransferUser(user);
		
		//3.返回DTO对象	
		DataResponse response = new DataResponse().success(transferUser);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 创建一个User(注册)
	 * @param param body数据
	 * @return 成功/失败信息
	 */
	@PostMapping(value="/signup")
	public ResponseEntity<DataResponse> signUp(@RequestBody String param) {
		logger.info("value=/,method=RequestMethod.POST: param: " + param);

		if (!param.startsWith("{") && !param.endsWith("}")) {
			// 以键值对方式传递的字符串，则需在两侧添加{}后，转换为JSONObject
			param = "{" + param + "}";
			logger.info("value=/login,method=RequestMethod.POST: handleParam: " + param);
		}

		JSONObject jsObject = JSON.parseObject(param);
		
		//记录日志
		logger.info("value=/,method=RequestMethod.POST: "
				+ "username: " + jsObject.getString("username")
				+ " ,phone: " + jsObject.getString("phone")
				+ " ,password: " + jsObject.getString("password")
				+ " ,sex: " + jsObject.getString("sex")
				+ " ,head_portrait: " + jsObject.getString("head_portrait"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许新增
		// 规则1，判断username、phone是否为空
		if (jsObject.getString("username") == null
				|| "".equals(jsObject.getString("username"))
				|| jsObject.getString("phone") == null
				|| "".equals(jsObject.getString("phone"))) {
			DataResponse response = new DataResponse().failure("username、phone不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.phone是否有效
		boolean phoneCountRex = jsObject.getString("phone").matches("^1d{10}$");
		logger.info("phone: " + jsObject.getString("phone") + ", Rex: " + phoneCountRex);
		if (!phoneCountRex) {		// 不满足
			DataResponse response = new DataResponse().failure("phone非法");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		// 规则3.username为8位汉字字母数字下划线
		boolean usernameRex = jsObject.getString("username").matches("^[\u4e00-\u9fa5A-Za-z0-9_]{1,8}$");
		logger.info("username: " + jsObject.getString("username") + ", Rex: " + usernameRex);
		if (!usernameRex) {
			DataResponse response = new DataResponse().failure("用户名非法，需为1-8位汉字字母数字下划线");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		// 封装domain对象
		User user = new User();
		
		// 用户注册，统一均为普通用户
		Role role = roleService.findRoleByName("GUEST");
		Set<Role> roleSet = new HashSet<Role>();
		roleSet.add(role);
		user.setRoles(roleSet);
		
		user.setHeadPortrait(jsObject.getString("head_portrait"));
		user.setUsername(jsObject.getString("username") );

		// 密码通过BCryptPasswordEncoder编译后存储至数据库
		BCryptPasswordEncoder BCryptencoder = new BCryptPasswordEncoder();
		String password = BCryptencoder.encode(jsObject.getString("password"));
		user.setPassword(password);

		user.setPhone(jsObject.getString("phone"));
		user.setSex(Sex.valueOf(jsObject.getString("sex")));
		
		// 获取当前时间，作为用户注册时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date registrationTime = null;
		try {
			registrationTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
			user.setRegistrationTime(registrationTime);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("日期格式化错误，请重新尝试");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		try {
			userService.save(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("注册失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("注册成功");
		//200 OK
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 更新User
	 * @param id 用户uuid
	 * @param param body数据
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<DataResponse> update(@PathVariable("id") String id, @RequestBody String param) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.PUT: "
		+ "id: " + id + ", param: " + param);

		if (!param.startsWith("{") && !param.endsWith("}")) {
			// 以键值对方式传递的字符串，则需在两侧添加{}后，转换为JSONObject
			param = "{" + param + "}";
			logger.info("value=/login,method=RequestMethod.POST: handleParam: " + param);
		}

		JSONObject jsObject = JSON.parseObject(param);
		
		//记录日志
		logger.info("value=/{id},method=RequestMethod.PUT: "
				+ "id: " + id
				+ " ,username: " + jsObject.getString("username")
				+ " ,password: " + jsObject.getString("password")
				+ " ,phone: " + jsObject.getString("phone")
				+ " ,sex: " + jsObject.getString("sex")
				+ " ,head_portrait: " + jsObject.getString("head_portrait"));
		
		//1.查到该对象
		User user = null;
		try {
			user = userService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的用户");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//2.修改
		if (jsObject.getString("username") != null
				&& !"".equals(jsObject.getString("username"))) {
			user.setUsername(jsObject.getString("username"));
		}
		if (jsObject.getString("password") != null
				&& !"".equals(jsObject.getString("password"))) {
			// 密码通过BCryptPasswordEncoder编译后存储至数据库
			BCryptPasswordEncoder BCryptencoder = new BCryptPasswordEncoder();
			String password = BCryptencoder.encode(jsObject.getString("password"));
			user.setPassword(password);
		}
		if (jsObject.getString("phone") != null
				&& !"".equals(jsObject.getString("phone"))) {
			user.setPhone(jsObject.getString("phone"));
		}
		if (jsObject.getString("sex") != null
				&& !"".equals(jsObject.getString("sex"))) {
			user.setSex(Sex.valueOf(jsObject.getString("sex")));
		}
		if (jsObject.getString("head_portrait") != null
				&& !"".equals(jsObject.getString("head_portrait"))) {
			user.setHeadPortrait(jsObject.getString("head_portrait"));
		}

		try {
			userService.save(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("更新失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("更新成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 删除User
	 * @param id 用户uuid
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<DataResponse> delete(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.DELETE: "
				+ " id: " + id);
		try {
			userService.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("删除失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("删除成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 用户登录接口(通过用户名密码)
	 * @param param body数据
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<DataResponse> login(@RequestBody String param) {
		logger.info("value=/login,method=RequestMethod.POST: param: " + param);

		if (!param.startsWith("{") && !param.endsWith("}")) {
			// 以键值对方式传递的字符串，则需在两侧添加{}后，转换为JSONObject
			param = "{" + param + "}";
			logger.info("value=/login,method=RequestMethod.POST: handleParam: " + param);
		}

		JSONObject jsObject = JSON.parseObject(param);
		//记录日志
		logger.info("value=/login,method=RequestMethod.POST: "
				+ "username: " + jsObject.getString("username")
				+ " ,password: " + jsObject.getString("password"));
		
		// 对提交的数据进行匹配，判断username、password是否为空，如为空，则不允许登录
		if (jsObject.getString("username") == null
				|| "".equals(jsObject.getString("username"))
				|| jsObject.getString("password") == null
				|| "".equals(jsObject.getString("password"))) {
			DataResponse response = new DataResponse().failure("用户名或密码不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		// 用于和前端交互的DTO对象
		TransferUser transferUser = null;
		// 用户接受后端数据的数组
		User loginUser = null;
		try {
			loginUser = userService.login(jsObject.getString("username"),
											jsObject.getString("username"),
											jsObject.getString("password"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("用户名或密码错误");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		if (loginUser == null) {
			DataResponse response = new DataResponse().failure("用户名或密码错误");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		// 转换为dto对象
		transferUser = new TransferUser(loginUser);
		
		DataResponse response = null;

		boolean haveAdminRole = false;
		for (Role role : loginUser.getRoles()) {
			if ("ADMIN".equals(role.getRoleName())) {
				haveAdminRole = true;
			}
		}

		if (haveAdminRole) {		// 管理员登录，生成token
			// 生成token
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("user_id", transferUser.getUserId());
			claims.put("username", transferUser.getUsername());
			String token = jwtTokenUtil.generateToken(claims);
			// 封装授权对象
			AuthenticationResult auth = new AuthenticationResult();
			auth.setPassport(true);
			auth.setAccessToken(token);
			
			response = new DataResponse().grant(auth, transferUser);		// 授权服务
		} else {
			response = new DataResponse().success(transferUser);		// 登录服务
		}

		//返回DTO对象	
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}

	/**
	 * 用户登录接口(通过手机号)
	 * @param param body数据
	 * @return
	 */
	@RequestMapping(value="/loginwithphone",method=RequestMethod.POST)
	public ResponseEntity<DataResponse> loginWithPhone(@RequestBody String param) {
		logger.info("value=/loginwithphone,method=RequestMethod.POST: param: " + param);

		if (!param.startsWith("{") && !param.endsWith("}")) {
			// 以键值对方式传递的字符串，则需在两侧添加{}后，转换为JSONObject
			param = "{" + param + "}";
			logger.info("value=/login,method=RequestMethod.POST: handleParam: " + param);
		}

		JSONObject jsObject = JSON.parseObject(param);

		//记录日志
		logger.info("value=/loginwithphone,method=RequestMethod.POST: "
				+ "phone: " + jsObject.getString("phone"));

		// 对提交的数据进行匹配，判断phone是否为空，如为空，则不允许登录
		if (jsObject.getString("phone") == null || "".equals(jsObject.getString("phone"))) {
			DataResponse response = new DataResponse().failure("手机号不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		// 用于和前端交互的DTO对象
		TransferUser transferUser = null;
		// 用户接受后端数据的数组
		User loginUser = null;
		try {
			loginUser = userService.loginWithPhone(jsObject.getString("phone"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("登录失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		if (loginUser == null) {
			DataResponse response = new DataResponse().failure("登录失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		// 转换为dto对象
		transferUser = new TransferUser(loginUser);

		DataResponse response = new DataResponse().success(transferUser);;

		//返回DTO对象
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
}