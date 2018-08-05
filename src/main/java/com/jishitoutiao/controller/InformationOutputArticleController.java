package com.jishitoutiao.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jishitoutiao.domain.CrawlerCategory;
import com.jishitoutiao.domain.CrawlerManagement;
import com.jishitoutiao.domain.CrawlerSource;
import com.jishitoutiao.domain.InformationOutputArticle;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferInformationOutputArticle;
import com.jishitoutiao.redis.service.RedisService;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.rely.Type;
import com.jishitoutiao.service.CrawlerManagementService;
import com.jishitoutiao.service.InformationOutputArticleService;

@RestController
@RequestMapping(value="/informationoutputarticle")
public class InformationOutputArticleController {
	@Autowired
	private CrawlerManagementService crawlerManagementService;		//爬虫服务
	
	@Autowired
	private InformationOutputArticleService informationOutputArticleService;
	
	@Autowired
	private RedisService redisService;		// redis服务

	private Logger logger = LoggerFactory.getLogger(InformationOutputArticleController.class);

	/**
	 * 获取所有InformationOutputArticle
	 * @param keyword 查询关键字
	 * @param bkey 源key
	 * @param ckey 类别key
	 * @param pageNum 当前页码
	 * @return Json数据
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ResponseEntity<DataResponse> getAll(@RequestParam(value="keyword",required=false) String keyword, 
											@RequestParam(value="bkey",required=false) String bkey, 
											@RequestParam(value="ckey",required=false) String ckey, 
											@RequestParam(value="page_num",defaultValue="1") String pageNum) {		
		//记录日志
		logger.info("value=/,method=RequestMethod.GET: "
				+ "keyword: " + keyword
				+ " ,bkey: " + bkey
				+ " ,ckey: " + ckey
				+ " ,page_num: " + pageNum);
		
		//用于接受后端数据的Page对象
		PageObj<InformationOutputArticle> dpage = null;
		
		//1.获取指定搜索条件，页码的全部数据
		try {
			dpage = informationOutputArticleService.getPageData(keyword, bkey, ckey, pageNum);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("获取数据失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//用于和前端交互的DTO对象
		TransferInformationOutputArticle transferInformationOutputArticle = null;
		List<TransferInformationOutputArticle> tlist = new ArrayList<TransferInformationOutputArticle>();
		List<TableColumn> columnList = new ArrayList<TableColumn>();
		
		// 从redis缓存中读取状态(status)和批次号(batch)数据
		String statusKey = "string:information_output_article:status";
		String batchKey = "string:information_output_article:batch";
		
		String statusValue = redisService.getStr(statusKey);
		String batchValue = redisService.getStr(batchKey);
		Map<String, String> redisData = new HashMap<String, String>();
		if (statusValue != null && statusValue.trim().length() != 0) {
			redisData.put(statusKey, statusValue);
		}
		if (batchValue != null && batchValue.trim().length() != 0) {
			redisData.put(batchKey, batchValue);
		}
		
		//如果没有数据
		if (dpage.getList().isEmpty()) {
			if (redisData.size() == 0) {		// 从redis中读取的数据也为空
				// 消息体封装message提示，并返回HttpStatus状态，下同
				DataResponse response = new DataResponse().success("无数据");
				// 200 OK
				return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
			} else {		// page中没有数据，但redis中有
				DataResponse response = new DataResponse().success(redisData);
				return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
			}
		}
		
		//2.将获取的Dmain对象数据转为与前端交互的DTO数据
		//2.1初始化DTO 的Page数据
		PageObj<TransferInformationOutputArticle> tpage = new PageObj<TransferInformationOutputArticle>(dpage.getPageNum(), dpage.getTotalRecord());
		
		//2.2将获取的Dmain对象添加到DTO 的Page对象的List中
		for (InformationOutputArticle ioa : dpage.getList()) {
			transferInformationOutputArticle = new TransferInformationOutputArticle(ioa);
			tlist.add(transferInformationOutputArticle);
		}
		tpage.setList(tlist);
		
		//2.3将table需要的column标题封装至tpage
		columnList.add(new TableColumn("源名称", "bname", "bname"));
		columnList.add(new TableColumn("爬虫名", "crawler_name", "crawler_name"));
		columnList.add(new TableColumn("资讯id", "information_id", "information_id"));
		columnList.add(new TableColumn("标题", "title", "title"));
		columnList.add(new TableColumn("作者", "author", "author"));
		columnList.add(new TableColumn("内容", "content", "content"));
		columnList.add(new TableColumn("阅读数", "read_count", "read_count"));
		columnList.add(new TableColumn("来源", "origin", "origin"));
		columnList.add(new TableColumn("类型", "type", "type"));
		columnList.add(new TableColumn("picurl", "picurl", "picurl"));
		columnList.add(new TableColumn("评论数", "comment_count", "comment_count"));
		columnList.add(new TableColumn("点赞数", "like_count", "like_count"));
		columnList.add(new TableColumn("资讯URL", "url", "url"));
		columnList.add(new TableColumn("资讯时间", "created_time", "created_time"));
		tpage.setColumns(columnList);
		
		//3.返回DTO对象(通过DataResponse封装)
		if (redisData.size() == 0) {		// redis中无数据
			DataResponse response = new DataResponse().success(tpage);
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		} else {
			DataResponse response = new DataResponse().success(redisData, tpage);
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);		// 将redis和data数据一并返回
		}
	}
	
	/**
	 * 通过id获取InformationOutputArticle
	 * @param id 内容输出表id
	 * @return Json数据
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> find(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.GET: "
				+ "id: " + id);
		
		//1.获取原始domain对象
		InformationOutputArticle informationOutputArticle = null;
		try {
			informationOutputArticle = informationOutputArticleService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.将domain对象转化为DTO对象
		TransferInformationOutputArticle transferInformationOutputArticle = new TransferInformationOutputArticle(informationOutputArticle);
		
		//3.返回DTO对象	
		DataResponse response = new DataResponse().success(transferInformationOutputArticle);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 创建一个InformationOutputArticle
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
				+ " ,crawler_id: " + jsObject.getString("crawler_id")
				+ " ,information_id: " + jsObject.getString("information_id")
				+ " ,title: " + jsObject.getString("title")
				+ " ,author: " + jsObject.getString("author")
				+ " ,content: " + jsObject.getString("content")
				+ " ,read_count: " + jsObject.getString("read_count")
				+ " ,origin: " + jsObject.getString("origin")
				+ " ,type: " + jsObject.getString("type")
				+ " ,picurl: " + jsObject.getString("picurl")
				+ " ,comment_count: " + jsObject.getString("comment_count")
				+ " ,like_count: " + jsObject.getString("like_count")
				+ " ,url: " + jsObject.getString("url")
				+ " ,created_time: " + jsObject.getString("created_time"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许新增
		// 规则1，判断information_id、title、url、content、type是否为空
		if (jsObject.getString("information_id") == null || 
			jsObject.getString("title") == null || 
			jsObject.getString("url") == null || 
			jsObject.getString("content") == null || 
			jsObject.getString("type") == null) {
			DataResponse response = new DataResponse().failure("information_id、title、url、content、type不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2. url是否是合法
		boolean urlRex = jsObject.getString("url").matches("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
		logger.info("url: " + jsObject.getString("url") + ", Rex: " + urlRex);
		if (!urlRex) {
			DataResponse response = new DataResponse().failure("url链接不合法");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3. type是否是PICTURE/TEXT/VIDEO
		boolean typeRex = jsObject.getString("type").matches("PICTURE|TEXT|VIDEO");
		logger.info("type: " + jsObject.getString("type") + ", Rex: " + typeRex);
		if (!typeRex) {
			DataResponse response = new DataResponse().failure("type必须为PICTURE|TEXT|VIDEO之一");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//获取爬虫
		CrawlerManagement crawlerManagement = crawlerManagementService.find(jsObject.getString("crawler_id"));
		
		//获取源
		CrawlerSource crawlerSource = crawlerManagement.getCrawlerSource();
		
		//获取类别
		CrawlerCategory crawlerCategory = crawlerManagement.getCrawlerCategory();
			
		//封装domain对象
		InformationOutputArticle informationOutputArticle = new InformationOutputArticle();
		
		informationOutputArticle.setInformationId(jsObject.getString("information_id"));
		informationOutputArticle.setAuthor(jsObject.getString("author"));
		informationOutputArticle.setCommentCount(Integer.parseInt(jsObject.getString("comment_count")));
		informationOutputArticle.setContent(jsObject.getString("content"));
		informationOutputArticle.setCrawlerCategory(crawlerCategory);
		informationOutputArticle.setCrawlerManagement(crawlerManagement);
		informationOutputArticle.setCrawlerSource(crawlerSource);
		informationOutputArticle.setLikeCount(Integer.parseInt(jsObject.getString("like_count")));
		informationOutputArticle.setOrigin(jsObject.getString("origin"));
		informationOutputArticle.setPicurl(jsObject.getString("picurl"));
		informationOutputArticle.setReadCount(Integer.parseInt(jsObject.getString("read_count")));
		informationOutputArticle.setTitle(jsObject.getString("title"));
		informationOutputArticle.setType(Type.valueOf(jsObject.getString("type")));
		informationOutputArticle.setUrl(jsObject.getString("url"));
					
		//将字符串转成时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date time = simpleDateFormat.parse(jsObject.getString("created_time"));
			informationOutputArticle.setCreatedTime(time);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("日期不满足yyyy-MM-dd HH:mm:ss格式");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		try {
			informationOutputArticleService.save(informationOutputArticle);
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
	 * 更新InformationOutputArticle
	 * @param id 内容输出表id
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
				+ "id:" + id
				+ " ,crawler_id: " + jsObject.getString("crawler_id")
				+ " ,information_id: " + jsObject.getString("information_id")
				+ " ,title: " + jsObject.getString("title")
				+ " ,author: " + jsObject.getString("author")
				+ " ,content: " + jsObject.getString("content")
				+ " ,read_count: " + jsObject.getString("read_count")
				+ " ,origin: " + jsObject.getString("origin")
				+ " ,picurl: " + jsObject.getString("picurl")
				+ " ,type: " + jsObject.getString("type")
				+ " ,comment_count: " + jsObject.getString("comment_count")
				+ " ,like_count: " + jsObject.getString("like_count")
				+ " ,url: " + jsObject.getString("url")
				+ " ,created_time: " + jsObject.getString("created_time"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许更新
		// 规则1，判断information_id、title、url、content、type是否为空
		if (jsObject.getString("information_id") == null || 
			jsObject.getString("title") == null || 
			jsObject.getString("url") == null || 
			jsObject.getString("content") == null || 
			jsObject.getString("type") == null) {
			DataResponse response = new DataResponse().failure("information_id、title、url、content、type不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2. url是否是合法
		boolean urlRex = jsObject.getString("url").matches("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
		logger.info("url: " + jsObject.getString("url") + ", Rex: " + urlRex);
		if (!urlRex) {
			DataResponse response = new DataResponse().failure("url链接不合法");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3. type是否是PICTURE/TEXT/VIDEO
		boolean typeRex = jsObject.getString("type").matches("PICTURE|TEXT|VIDEO");
		logger.info("type: " + jsObject.getString("type") + ", Rex: " + typeRex);
		if (!typeRex) {
			DataResponse response = new DataResponse().failure("type必须为PICTURE|TEXT|VIDEO之一");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//1.查到该对象
		InformationOutputArticle informationOutputArticle = null;
		try {
			informationOutputArticle = informationOutputArticleService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//获取爬虫
		CrawlerManagement crawlerManagement = crawlerManagementService.find(jsObject.getString("crawler_id"));
		
		//获取源
		CrawlerSource crawlerSource = crawlerManagement.getCrawlerSource();
		
		//获取类别
		CrawlerCategory crawlerCategory = crawlerManagement.getCrawlerCategory();
			
		//封装domain对象
		informationOutputArticle.setInformationId(jsObject.getString("information_id"));
		informationOutputArticle.setAuthor(jsObject.getString("author"));
		informationOutputArticle.setCommentCount(Integer.parseInt(jsObject.getString("comment_count")));
		informationOutputArticle.setContent(jsObject.getString("content"));
		informationOutputArticle.setCrawlerCategory(crawlerCategory);
		informationOutputArticle.setCrawlerManagement(crawlerManagement);
		informationOutputArticle.setCrawlerSource(crawlerSource);
		informationOutputArticle.setLikeCount(Integer.parseInt(jsObject.getString("like_count")));
		informationOutputArticle.setOrigin(jsObject.getString("origin"));
		informationOutputArticle.setPicurl(jsObject.getString("picurl"));
		informationOutputArticle.setReadCount(Integer.parseInt(jsObject.getString("read_count")));
		informationOutputArticle.setTitle(jsObject.getString("title"));
		informationOutputArticle.setType(Type.valueOf(jsObject.getString("type")));
		informationOutputArticle.setUrl(jsObject.getString("url"));
			
		//将字符串转成时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date time = simpleDateFormat.parse(jsObject.getString("created_time"));
			informationOutputArticle.setCreatedTime(time);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("日期不满足yyyy-MM-dd HH:mm:ss格式");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
				
		//3.修改
		try {
			informationOutputArticleService.save(informationOutputArticle);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("更新失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("更新成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 删除InformationOutputArticle
	 * @param id 内容输出表id
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<DataResponse> delete(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.DELETE: "
				+ "id: " + id);

		try {
			informationOutputArticleService.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("删除失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("删除成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 清空InformationOutputArticle,不提供对外接口
	 * @return 成功/失败信息
	 */
//	@RequestMapping(value="/",method=RequestMethod.DELETE)
//	public ResponseEntity<DataResponse> clean() {
//		informationOutputArticleService.cleanOutputArticle();
//		
//		DataResponse response = new DataResponse().success("清空InformationOutputArticle表成功");
//		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
//	}
}