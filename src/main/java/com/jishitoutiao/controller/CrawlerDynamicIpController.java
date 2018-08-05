package com.jishitoutiao.controller;

import java.util.ArrayList;
import java.util.List;

import com.jishitoutiao.rely.PageObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishitoutiao.domain.CrawlerDynamicIp;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferCrawlerDynamicIp;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.rely.Protocol;
import com.jishitoutiao.service.CrawlerDynamicIpService;

@RestController
@RequestMapping(value="/crawlerdynamicip")
public class CrawlerDynamicIpController {
	@Autowired
	private CrawlerDynamicIpService crawlerDynamicIpService;		//服务
	
	private Logger logger = LoggerFactory.getLogger(CrawlerDynamicIpController.class);
	
	/**
	 * 获取所有CrawlerDynamicIp
	 * @param keyword 查询关键字
	 * @param protocol 类型
	 * @param pageNum 当前页码
	 * @return Json数据
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ResponseEntity<DataResponse> getAll(@RequestParam(value="keyword",required=false) String keyword, 
											@RequestParam(value="protocol",required=false) String protocol, 
											@RequestParam(value="page_num",defaultValue="1") String pageNum) {		
		//记录日志
		logger.info("value=/,method=RequestMethod.GET: "
				+ "keyword: " + keyword
				+ " ,protocol: " + protocol 
				+ " ,page_num: " + pageNum);
		
		//用于和前端交互的DTO对象
		TransferCrawlerDynamicIp transferCrawlerDynamicIp = null;
		List<TransferCrawlerDynamicIp> tlist = new ArrayList<TransferCrawlerDynamicIp>();
		List<TableColumn> columnList = new ArrayList<TableColumn>();
		
		//用于接受后端数据的Page对象
		PageObj<CrawlerDynamicIp> dpage = null;
		
		//1.获取指定搜索条件，页码的全部数据
		try {
			dpage = crawlerDynamicIpService.getPageData(keyword, protocol, pageNum);
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
		PageObj<TransferCrawlerDynamicIp> tpage = new PageObj<TransferCrawlerDynamicIp>(dpage.getPageNum(), dpage.getTotalRecord());
		
		//2.2将获取的Dmain对象添加到DTO 的Page对象的List中
		for (CrawlerDynamicIp cdi : dpage.getList()) {
			transferCrawlerDynamicIp = new TransferCrawlerDynamicIp(cdi);
			tlist.add(transferCrawlerDynamicIp);
		}
		tpage.setList(tlist);
		
		//2.3将table需要的column标题封装至tpage
		columnList.add(new TableColumn("IP地址", "ip_address", "ip_address"));
		columnList.add(new TableColumn("端口", "port", "port"));
		columnList.add(new TableColumn("服务器地址", "server_address", "server_address"));
		columnList.add(new TableColumn("是否匿名", "anonymity", "anonymity"));
		columnList.add(new TableColumn("类型", "protocol", "protocol"));
		columnList.add(new TableColumn("速度", "speed", "speed"));
		columnList.add(new TableColumn("连接时间", "connect_time", "connect_time"));
		columnList.add(new TableColumn("存活时间", "alive_duration", "alive_duration"));
		columnList.add(new TableColumn("验证时间", "verify_time", "verify_time"));
		tpage.setColumns(columnList);
		
		//3.返回DTO对象(通过DataResponse封装)
		DataResponse response = new DataResponse().success(tpage);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 通过id获取CrawlerDynamicIp
	 * @param id dynamic_id
	 * @return Json数据
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> find(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.GET: "
				+ "id: " + id);
		
		//1.获取原始domain对象
		CrawlerDynamicIp crawlerDynamicIp = null;
		try {
			crawlerDynamicIp = crawlerDynamicIpService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.将domain对象转化为DTO对象
		TransferCrawlerDynamicIp transferCrawlerDynamicIp = new TransferCrawlerDynamicIp(crawlerDynamicIp);
		
		//3.返回DTO对象	
		DataResponse response = new DataResponse().success(transferCrawlerDynamicIp);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}

	/**
	 * 创建一个CrawlerDynamicIp
	 * @param param body数据
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public ResponseEntity<DataResponse> create(@RequestBody String param) {
		logger.info("value=/,method=RequestMethod.POST: param: " + param);

		if (!param.startsWith("{") && !param.endsWith("}")) {
			// 以键值对方式传递的字符串，则需在两侧添加{}后，转换为JSONObject
			param = "{" + param + "}";
			logger.info("value=/login,method=RequestMethod.POST: handleParam: " + param);
		}

		JSONObject jsObject = JSON.parseObject(param);
		
		//记录日志
		logger.info("value=/,method=RequestMethod.POST: "
				+ "ip_address: " + jsObject.getString("ip_address")
				+ " ,port: " + jsObject.getString("port")
				+ " ,server_address: " + jsObject.getString("server_address")
				+ " ,anonymity: " + jsObject.getString("anonymity")
				+ " ,protocol: " + jsObject.getString("protocol")
				+ " ,speed: " + jsObject.getString("speed")
				+ " ,connect_time: " + jsObject.getString("connect_time")
				+ " ,alive_duration: " + jsObject.getString("alive_duration")
				+ " ,verify_time: " + jsObject.getString("verify_time"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许新增
		// 规则1，判断ip_address、port、protocol是否为空
		if (jsObject.getString("ip_address") == null || jsObject.getString("port") == null || jsObject.getString("protocol") == null) {
			DataResponse response = new DataResponse().failure("ip_address、port、protocol不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.ip_address是否为ip地址
		boolean ipAddresskeyRex = jsObject.getString("ip_address").matches("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
																+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
																+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
																+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");
		logger.info("ip_address: " + jsObject.getString("ip_address") + ", Rex: " + ipAddresskeyRex);
		if (!ipAddresskeyRex) {		// 不满足
			DataResponse response = new DataResponse().failure("ip地址非法");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3. prot端口号是否为纯数字
		boolean portRex = jsObject.getString("port").matches("^([0-9]{1,6})$");
		logger.info("port: " + jsObject.getString("port")+ ", Rex: " + portRex);
		if (!portRex) {
			DataResponse response = new DataResponse().failure("port端口号仅支持数字");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则4. protocol是否是HTTP/HTTPS
		boolean protocolRex = jsObject.getString("protocol").matches("^[A-Za-z]{4,6}$");
		logger.info("protocol: " + jsObject.getString("protocol")+ ", Rex: " + protocolRex);
		if (!protocolRex) {
			DataResponse response = new DataResponse().failure("protocol仅支持HTTP/HTTPS");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//封装domain对象
		CrawlerDynamicIp crawlerDynamicIp = new CrawlerDynamicIp();
		crawlerDynamicIp.setAliveDuration(jsObject.getString("alive_duration"));
		crawlerDynamicIp.setAnonymity(jsObject.getString("anonymity"));
		crawlerDynamicIp.setConnectTime(jsObject.getString("connect_time"));
		crawlerDynamicIp.setIpAddress(jsObject.getString("ip_address"));
		crawlerDynamicIp.setPort(Integer.parseInt(jsObject.getString("port")));
		crawlerDynamicIp.setProtocol(Protocol.valueOf(jsObject.getString("protocol").toUpperCase()));
		crawlerDynamicIp.setServerAddress(jsObject.getString("server_address"));
		crawlerDynamicIp.setSpeed(jsObject.getString("speed"));
		crawlerDynamicIp.setVerifyTime(jsObject.getString("verify_time"));
		
		try {
			crawlerDynamicIpService.save(crawlerDynamicIp);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("新增失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		DataResponse response = new DataResponse().success("新增成功");
		//200 OK
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 更新CrawlerDynamicIp
	 * @param id dynamic_id主键
	 * @param param body数据
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<DataResponse> update(@PathVariable("id") String id, @RequestBody String param) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.PUT: "
		+ "bid: " + id + ", param: " + param);

		if (!param.startsWith("{") && !param.endsWith("}")) {
			// 以键值对方式传递的字符串，则需在两侧添加{}后，转换为JSONObject
			param = "{" + param + "}";
			logger.info("value=/login,method=RequestMethod.POST: handleParam: " + param);
		}

		JSONObject jsObject = JSON.parseObject(param);
		
		//记录日志
		logger.info("value=/{id},method=RequestMethod.PUT: "
				+ " id: " + id
				+ " ,ip_address: " + jsObject.getString("ip_address")
				+ " ,port: " + jsObject.getString("port")
				+ " ,server_address: " + jsObject.getString("server_address")
				+ " ,anonymity: " + jsObject.getString("anonymity")
				+ " ,protocol: " + jsObject.getString("protocol")
				+ " ,speed: " + jsObject.getString("speed")
				+ " ,connect_time: " + jsObject.getString("connect_time")
				+ " ,alive_duration: " + jsObject.getString("alive_duration")
				+ " ,verify_time: " + jsObject.getString("verify_time"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许更新
		// 规则1，判断ip_address、port、protocol是否为空
		if (jsObject.getString("ip_address") == null || jsObject.getString("port") == null || jsObject.getString("protocol") == null) {
			DataResponse response = new DataResponse().failure("ip_address、port、protocol不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.ip_address是否为ip地址
		boolean ipAddresskeyRex = jsObject.getString("ip_address").matches("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
																+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
																+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
																+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");
		logger.info("ip_address: " + jsObject.getString("ip_address") + ", Rex: " + ipAddresskeyRex);
		if (!ipAddresskeyRex) {		// 不满足
			DataResponse response = new DataResponse().failure("ip地址非法");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3. prot端口号是否为纯数字
		boolean portRex = jsObject.getString("port").matches("^([0-9]{1,6})$");
		logger.info("port: " + jsObject.getString("port")+ ", Rex: " + portRex);
		if (!portRex) {
			DataResponse response = new DataResponse().failure("port端口号仅支持数字");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则4. protocol是否是HTTP/HTTPS
		boolean protocolRex = jsObject.getString("protocol").matches("^[A-Za-z]{4,6}$");
		logger.info("protocol: " + jsObject.getString("protocol")+ ", Rex: " + protocolRex);
		if (!protocolRex) {
			DataResponse response = new DataResponse().failure("protocol仅支持HTTP/HTTPS");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//1.查到该对象
		CrawlerDynamicIp crawlerDynamicIp = null;
		try {
			crawlerDynamicIp = crawlerDynamicIpService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
			
		//2.封装domain对象
		crawlerDynamicIp.setAliveDuration(jsObject.getString("alive_duration"));
		crawlerDynamicIp.setAnonymity(jsObject.getString("anonymity"));
		crawlerDynamicIp.setConnectTime(jsObject.getString("connect_time"));
		crawlerDynamicIp.setIpAddress(jsObject.getString("ip_address"));
		crawlerDynamicIp.setPort(Integer.parseInt(jsObject.getString("port")));
		crawlerDynamicIp.setProtocol(Protocol.valueOf(jsObject.getString("protocol").toUpperCase()));
		crawlerDynamicIp.setServerAddress(jsObject.getString("server_address"));
		crawlerDynamicIp.setSpeed(jsObject.getString("speed"));
		crawlerDynamicIp.setVerifyTime(jsObject.getString("verify_time"));
				
		//3.修改
		try {
			crawlerDynamicIpService.save(crawlerDynamicIp);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("更新失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("更新成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 删除CrawlerDynamicIp
	 * @param id dynamic_id主键
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<DataResponse> delete(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.DELETE: "
				+ "id: " + id);

		try {
			crawlerDynamicIpService.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("删除失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("删除成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
}