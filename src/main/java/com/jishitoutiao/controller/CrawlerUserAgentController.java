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
import com.jishitoutiao.domain.CrawlerUserAgent;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferCrawlerUserAgent;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.service.CrawlerUserAgentService;

@RestController
@RequestMapping(value="/crawleruseragent")
public class CrawlerUserAgentController {
	@Autowired
	private CrawlerUserAgentService crawlerUserAgentService;		//服务

	private Logger logger = LoggerFactory.getLogger(CrawlerUserAgentController.class);
	
	/**
	 * 获取所有CrawlerUserAgent
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
		TransferCrawlerUserAgent transferCrawlerUserAgent = null;
		List<TransferCrawlerUserAgent> tlist = new ArrayList<TransferCrawlerUserAgent>();
		List<TableColumn> columnList = new ArrayList<TableColumn>();
		
		//用于接受后端数据的Page对象
		PageObj<CrawlerUserAgent> dpage = null;
		
		//1.获取指定搜索条件，页码的全部数据
		try {
			dpage = crawlerUserAgentService.getPageData(keyword, pageNum);
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
		PageObj<TransferCrawlerUserAgent> tpage = new PageObj<TransferCrawlerUserAgent>(dpage.getPageNum(), dpage.getTotalRecord());
		
		//2.2将获取的Dmain对象添加到DTO 的Page对象的List中
		for (CrawlerUserAgent cua : dpage.getList()) {
			transferCrawlerUserAgent = new TransferCrawlerUserAgent(cua);
			tlist.add(transferCrawlerUserAgent);
		}
		tpage.setList(tlist);
		
		//2.3将table需要的column标题封装至tpage
		columnList.add(new TableColumn("Accept", "accept", "accept"));
		columnList.add(new TableColumn("Accept-Encoding", "accept_encoding", "accept_encoding"));
		columnList.add(new TableColumn("Accept-Language", "accept_language", "accept_language"));
		columnList.add(new TableColumn("Connection", "connection", "connection"));
		columnList.add(new TableColumn("Host", "host", "host"));
		columnList.add(new TableColumn("User-Agent", "user_agent", "user_agent"));
		columnList.add(new TableColumn("备注", "remark", "remark"));
		tpage.setColumns(columnList);
		
		//3.返回DTO对象(通过DataResponse封装)
		DataResponse response = new DataResponse().success(tpage);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 通过id获取CrawlerUserAgent
	 * @param id UserAgentid
	 * @return Json数据
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> find(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.GET: "
				+ "id: " + id);
		
		//1.获取原始domain对象
		CrawlerUserAgent crawlerUserAgent = null;
		try {
			crawlerUserAgent = crawlerUserAgentService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.将domain对象转化为DTO对象
		TransferCrawlerUserAgent transferCrawlerUserAgent = new TransferCrawlerUserAgent(crawlerUserAgent);
		
		//3.返回DTO对象	
		DataResponse response = new DataResponse().success(transferCrawlerUserAgent);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}

	/**
	 * 创建一个CrawlerUserAgent
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
				+ "accept: " + jsObject.getString("accept")
				+ " ,accept_encoding: " + jsObject.getString("accept_encoding")
				+ " ,accept_language: " + jsObject.getString("accept_language")
				+ " ,connection: " + jsObject.getString("connection")
				+ " ,host: " + jsObject.getString("host")
				+ " ,user_agent: " + jsObject.getString("user_agent")
				+ " ,remark: " + jsObject.getString("remark"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许新增
		// 规则1，判断user_agent是否为空
		if (jsObject.getString("user_agent") == null) {
			DataResponse response = new DataResponse().failure("user_agent不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//封装domain对象
		CrawlerUserAgent crawlerUserAgent = new CrawlerUserAgent();
		crawlerUserAgent.setAccept(jsObject.getString("accept"));
		crawlerUserAgent.setAcceptEncoding(jsObject.getString("accept_encoding"));
		crawlerUserAgent.setAcceptLanguage(jsObject.getString("accept_language"));
		crawlerUserAgent.setConnection(jsObject.getString("connection"));
		crawlerUserAgent.setHost(jsObject.getString("host"));
		crawlerUserAgent.setRemark(jsObject.getString("remark"));
		crawlerUserAgent.setUserAgent(jsObject.getString("user_agent"));
		
		try {
			crawlerUserAgentService.save(crawlerUserAgent);
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
	 * 更新CrawlerUserAgent
	 * @param id UserAgentid
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
				+ " ,accept: " + jsObject.getString("accept")
				+ " ,accept_encoding: " + jsObject.getString("accept_encoding")
				+ " ,accept_language: " + jsObject.getString("accept_language")
				+ " ,connection: " + jsObject.getString("connection")
				+ " ,host: " + jsObject.getString("host")
				+ " ,user_agent: " + jsObject.getString("user_agent")
				+ " ,remark: " + jsObject.getString("remark"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许更新
		// 规则:判断user_agent是否为空
		if (jsObject.getString("user_agent") == null) {
			DataResponse response = new DataResponse().failure("user_agent不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//1.查到该对象
		CrawlerUserAgent crawlerUserAgent = null;
		try {
			crawlerUserAgent = crawlerUserAgentService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
			
		//2.封装domain对象
		crawlerUserAgent.setAccept(jsObject.getString("accept"));
		crawlerUserAgent.setAcceptEncoding(jsObject.getString("accept_encoding"));
		crawlerUserAgent.setAcceptLanguage(jsObject.getString("accept_language"));
		crawlerUserAgent.setConnection(jsObject.getString("connection"));
		crawlerUserAgent.setHost(jsObject.getString("host"));
		crawlerUserAgent.setRemark(jsObject.getString("remark"));
		crawlerUserAgent.setUserAgent(jsObject.getString("user_agent"));
				
		//3.修改
		try {
			crawlerUserAgentService.save(crawlerUserAgent);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("更新失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("更新成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 删除CrawlerUserAgent
	 * @param id UserAgentid
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<DataResponse> delete(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.DELETE: "
				+ "id: " + id);

		try {
			crawlerUserAgentService.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("删除失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("删除成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
}