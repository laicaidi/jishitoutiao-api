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
import com.jishitoutiao.domain.InformationOutputList;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferInformationOutputList;
import com.jishitoutiao.redis.service.RedisService;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.service.CrawlerManagementService;
import com.jishitoutiao.service.InformationOutputListService;

@RestController
@RequestMapping(value="/informationoutputlist")
public class InformationOutputListController {
	@Autowired
	private CrawlerManagementService crawlerManagementService;		//爬虫服务
	
	@Autowired
	private InformationOutputListService informationOutputListService;
	
	@Autowired
	private RedisService redisService;		// redis服务

	private Logger logger = LoggerFactory.getLogger(InformationOutputListController.class);

	/**
	 * 获取所有InformationOutputList
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
				+ "keyword:" + keyword
				+ " ,bkey: " + bkey
				+ " ,ckey: " + ckey
				+ " ,page_num: " + pageNum);
		
		//用于接受后端数据的Page对象
		PageObj<InformationOutputList> dpage = null;
		
		//1.获取指定搜索条件，页码的全部数据
		try {
			dpage = informationOutputListService.getPageData(keyword, bkey, ckey, pageNum);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("获取数据失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//用于和前端交互的DTO对象
		TransferInformationOutputList transferInformationOutputList = null;
		List<TransferInformationOutputList> tlist = new ArrayList<TransferInformationOutputList>();
		List<TableColumn> columnList = new ArrayList<TableColumn>();
		
		// 从redis缓存中读取状态(status)和批次号(batch)数据
		String statusKey = "string:information_output_list:status";
		String batchKey = "string:information_output_list:batch";
		
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
		PageObj<TransferInformationOutputList> tpage = new PageObj<TransferInformationOutputList>(dpage.getPageNum(), dpage.getTotalRecord());
		
		//2.2将获取的Dmain对象添加到DTO 的Page对象的List中
		for (InformationOutputList iol : dpage.getList()) {
			transferInformationOutputList = new TransferInformationOutputList(iol);
			tlist.add(transferInformationOutputList);
		}
		tpage.setList(tlist);
		
		//2.3将table需要的column标题封装至tpage
		columnList.add(new TableColumn("源名称", "bname", "bname"));
		columnList.add(new TableColumn("爬虫名", "crawler_name", "crawler_name"));
		columnList.add(new TableColumn("爬虫权重系数", "crawler_weight_factor", "crawler_weight_factor"));
		columnList.add(new TableColumn("爬虫权重分", "crawler_weight_score", "crawler_weight_score"));
		columnList.add(new TableColumn("爬虫得分", "crawler_score", "crawler_score"));
		columnList.add(new TableColumn("资讯id", "information_id", "information_id"));
		columnList.add(new TableColumn("标题", "title", "title"));
		columnList.add(new TableColumn("阅读权重", "read_weight", "read_weight"));
		columnList.add(new TableColumn("阅读权重系数", "read_weight_factor", "read_weight_factor"));
		columnList.add(new TableColumn("阅读数", "read_count", "read_count"));
		columnList.add(new TableColumn("阅读得分", "read_score", "read_score"));
		columnList.add(new TableColumn("评论权重", "comment_weight", "comment_weight"));
		columnList.add(new TableColumn("评论权重系数", "comment_weight_factor", "comment_weight_factor"));
		columnList.add(new TableColumn("评论数", "comment_count", "comment_count"));
		columnList.add(new TableColumn("评论得分", "comment_score", "comment_score"));
		columnList.add(new TableColumn("点赞权重", "like_weight", "like_weight"));
		columnList.add(new TableColumn("点赞权重系数", "like_weight_factor", "like_weight_factor"));
		columnList.add(new TableColumn("点赞数", "like_count", "like_count"));
		columnList.add(new TableColumn("点赞得分", "like_score", "like_score"));
		columnList.add(new TableColumn("字数权重", "word_weight", "word_weight"));
		columnList.add(new TableColumn("字数权重系数", "word_weight_factor", "word_weight_factor"));
		columnList.add(new TableColumn("文章字数", "word_count", "word_count"));
		columnList.add(new TableColumn("字数得分", "word_score", "word_score"));
		columnList.add(new TableColumn("资讯URL", "url", "url"));
		columnList.add(new TableColumn("资讯时间", "created_time", "created_time"));
		columnList.add(new TableColumn("总得分", "information_score", "information_score"));
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
	 * 通过id获取InformationOutputList
	 * @param id 列表输出表id
	 * @return Json数据
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> find(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.GET: "
				+ "id: " + id);
		
		//1.获取原始domain对象
		InformationOutputList informationOutputList = null;
		try {
			informationOutputList = informationOutputListService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.将domain对象转化为DTO对象
		TransferInformationOutputList transferInformationOutputList = new TransferInformationOutputList(informationOutputList);
		
		//3.返回DTO对象	
		DataResponse response = new DataResponse().success(transferInformationOutputList);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 创建一个InformationOutputList
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
				+ " ,crawler_weight_score: " + jsObject.getString("crawler_weight_score")
				+ " ,crawler_weight_factor: " + jsObject.getString("crawler_weight_factor")
				+ " ,crawler_score: " + jsObject.getString("crawler_score")
				+ " ,title: " + jsObject.getString("title")
				+ " ,read_count: " + jsObject.getString("read_count")
				+ " ,read_weight: " + jsObject.getString("read_weight")
				+ " ,read_weight_factor: " + jsObject.getString("read_weight_factor")
				+ " ,read_score: " + jsObject.getString("read_score")
				+ " ,comment_count: " + jsObject.getString("comment_count")
				+ " ,comment_weight: " + jsObject.getString("comment_weight")
				+ " ,comment_weight_factor: " + jsObject.getString("comment_weight_factor")
				+ " ,comment_score: " + jsObject.getString("comment_score")
				+ " ,like_count: " + jsObject.getString("like_count")
				+ " ,like_weight: " + jsObject.getString("like_weight")
				+ " ,like_weight_factor: " + jsObject.getString("like_weight_factor")
				+ " ,like_score: " + jsObject.getString("like_score")
				+ " ,word_count: " + jsObject.getString("word_count")
				+ " ,word_weight: " + jsObject.getString("word_weight")
				+ " ,word_weight_factor: " + jsObject.getString("word_weight_factor")
				+ " ,word_score: " + jsObject.getString("word_score")
				+ " ,url: " + jsObject.getString("url")
				+ " ,information_score: " + jsObject.getString("information_score")
				+ " ,created_time: " + jsObject.getString("created_time"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许新增
		// 规则1，判断information_id、title、word_count、url是否为空
		if (jsObject.getString("information_id") == null || jsObject.getString("title") == null || jsObject.getString("word_count") == null || jsObject.getString("url") == null) {
			DataResponse response = new DataResponse().failure("information_id、title、word_count、url不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.word_count是否为正整数
		boolean wordCountRex = jsObject.getString("word_count").matches("^([0-9]{1,})$");
		logger.info("word_count: " + jsObject.getString("word_count") + ", Rex: " + wordCountRex);
		if (!wordCountRex) {		// 不满足
			DataResponse response = new DataResponse().failure("word_count必须为正整数");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3. url是否是合法
		boolean urlRex = jsObject.getString("url").matches("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
		logger.info("url: " + jsObject.getString("url") + ", Rex: " + urlRex);
		if (!urlRex) {
			DataResponse response = new DataResponse().failure("url链接不合法");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//获取爬虫
		CrawlerManagement crawlerManagement = crawlerManagementService.find(jsObject.getString("crawler_id"));
		
		//获取源
		CrawlerSource crawlerSource = crawlerManagement.getCrawlerSource();
		
		//获取类别
		CrawlerCategory crawlerCategory = crawlerManagement.getCrawlerCategory();
			
		//封装domain对象
		InformationOutputList informationOutputList = new InformationOutputList();
		
		informationOutputList.setCrawlerCategory(crawlerCategory);
		informationOutputList.setCrawlerManagement(crawlerManagement);
		informationOutputList.setCrawlerSource(crawlerSource);
		
		informationOutputList.setInformationId(jsObject.getString("information_id"));
		
		int crawlerWeightScore = Integer.parseInt(jsObject.getString("crawler_weight_score"));		//权重分
		informationOutputList.setCrawlerWeightScore(crawlerWeightScore);
		double crawlerWeightFactor = Double.parseDouble(jsObject.getString("crawler_weight_factor"));		//权重系数
		informationOutputList.setCrawlerWeightFactor(crawlerWeightFactor);
		// 爬虫分=爬虫权重分*爬虫权重系数
		double crawlerScore = crawlerWeightScore * crawlerWeightFactor;
		informationOutputList.setCrawlerScore(crawlerScore);
		
		informationOutputList.setReadWeight(jsObject.getString("read_weight"));
		double readWeightFactor = Double.parseDouble(jsObject.getString("read_weight_factor"));
		informationOutputList.setReadWeightFactor(readWeightFactor);
		int readCount = Integer.parseInt(jsObject.getString("read_count"));
		informationOutputList.setReadCount(readCount);	
		double readScore = readWeightFactor * readCount;
		informationOutputList.setReadScore(readScore);		//分=系数*数量，下同
		
		informationOutputList.setCommentWeight(jsObject.getString("comment_weight"));
		double commentWeightFactor = Double.parseDouble(jsObject.getString("comment_weight_factor"));
		informationOutputList.setCommentWeightFactor(commentWeightFactor);
		int commentCount = Integer.parseInt(jsObject.getString("comment_count"));
		informationOutputList.setCommentCount(commentCount);	
		double commentScore = commentWeightFactor * commentCount;
		informationOutputList.setCommentScore(commentScore);
		
		informationOutputList.setLikeWeight(jsObject.getString("like_weight"));
		double likeWeightFactor = Double.parseDouble(jsObject.getString("like_weight_factor"));
		informationOutputList.setLikeWeightFactor(likeWeightFactor);
		int likeCount = Integer.parseInt(jsObject.getString("like_count"));
		informationOutputList.setLikeCount(likeCount);
		double likeScore = likeWeightFactor * likeCount;
		informationOutputList.setLikeScore(likeScore);
		
		informationOutputList.setWordWeight(jsObject.getString("word_weight"));
		double wordWeightFactor = Double.parseDouble(jsObject.getString("word_weight_factor"));
		informationOutputList.setWordWeightFactor(wordWeightFactor);
		int wordCount = Integer.parseInt(jsObject.getString("word_count"));
		informationOutputList.setWordCount(wordCount);
		double wordScore = wordWeightFactor * wordCount;
		informationOutputList.setWordScore(wordScore);
		
		informationOutputList.setTitle(jsObject.getString("title"));
		informationOutputList.setUrl(jsObject.getString("url"));
		
		//总分=爬虫分+阅读分+评论分+点赞分+字数分
		informationOutputList.setInformationScore(crawlerScore + readScore + commentScore + likeScore + wordScore);
			
		//将字符串转成时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date time = simpleDateFormat.parse(jsObject.getString("created_time"));
			informationOutputList.setCreatedTime(time);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("日期不满足yyyy-MM-dd HH:mm:ss格式");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		try {
			informationOutputListService.save(informationOutputList);
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
	 * 更新InformationOutputList
	 * @param id 列表输出表id
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
				+ " ,crawler_id: " + jsObject.getString("crawler_id")
				+ " ,information_id: " + jsObject.getString("information_id")
				+ " ,crawler_weight_score: " + jsObject.getString("crawler_weight_score")
				+ " ,crawler_weight_factor: " + jsObject.getString("crawler_weight_factor")
				+ " ,crawler_score: " + jsObject.getString("crawler_score")
				+ " ,title: " + jsObject.getString("title")
				+ " ,read_count: " + jsObject.getString("read_count")
				+ " ,read_weight: " + jsObject.getString("read_weight")
				+ " ,read_weight_factor: " + jsObject.getString("read_weight_factor")
				+ " ,read_score: " + jsObject.getString("read_score")
				+ " ,comment_count: " + jsObject.getString("comment_count")
				+ " ,comment_weight: " + jsObject.getString("comment_weight")
				+ " ,comment_weight_factor: " + jsObject.getString("comment_weight_factor")
				+ " ,comment_score: " + jsObject.getString("comment_score")
				+ " ,like_count: " + jsObject.getString("like_count")
				+ " ,like_weight: " + jsObject.getString("like_weight")
				+ " ,like_weight_factor: " + jsObject.getString("like_weight_factor")
				+ " ,like_score: " + jsObject.getString("like_score")
				+ " ,word_count: " + jsObject.getString("word_count")
				+ " ,word_weight: " + jsObject.getString("word_weight")
				+ " ,word_weight_factor: " + jsObject.getString("word_weight_factor")
				+ " ,word_score: " + jsObject.getString("word_score")
				+ " ,url: " + jsObject.getString("url")
				+ " ,information_score: " + jsObject.getString("information_score")
				+ " ,created_time: " + jsObject.getString("created_time"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许更新
		// 规则1，判断information_id、title、word_count、url是否为空
		if (jsObject.getString("information_id") == null || jsObject.getString("title") == null || jsObject.getString("word_count") == null || jsObject.getString("url") == null) {
			DataResponse response = new DataResponse().failure("information_id、title、word_count、url不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.word_count是否为正整数
		boolean wordCountRex = jsObject.getString("word_count").matches("^([0-9]{1,})$");
		logger.info("word_count: " + jsObject.getString("word_count") + ", Rex: " + wordCountRex);
		if (!wordCountRex) {		// 不满足
			DataResponse response = new DataResponse().failure("word_count必须为正整数");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3. url是否是合法
		boolean urlRex = jsObject.getString("url").matches("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
		logger.info("url: " + jsObject.getString("url") + ", Rex: " + urlRex);
		if (!urlRex) {
			DataResponse response = new DataResponse().failure("url链接不合法");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//1.查到该对象
		InformationOutputList informationOutputList = null;
		try {
			informationOutputList = informationOutputListService.find(id);
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
		
		informationOutputList.setCrawlerCategory(crawlerCategory);
		informationOutputList.setCrawlerManagement(crawlerManagement);
		informationOutputList.setCrawlerSource(crawlerSource);
		
		informationOutputList.setInformationId(jsObject.getString("information_id"));
			
		int crawlerWeightScore = Integer.parseInt(jsObject.getString("crawler_weight_score"));		//权重分
		informationOutputList.setCrawlerWeightScore(crawlerWeightScore);
		double crawlerWeightFactor = Double.parseDouble(jsObject.getString("crawler_weight_factor"));		//权重系数
		informationOutputList.setCrawlerWeightFactor(crawlerWeightFactor);
		// 爬虫分=爬虫权重分*爬虫权重系数
		double crawlerScore = crawlerWeightScore * crawlerWeightFactor;
		informationOutputList.setCrawlerScore(crawlerScore);
		
		informationOutputList.setReadWeight(jsObject.getString("read_weight"));
		double readWeightFactor = Double.parseDouble(jsObject.getString("read_weight_factor"));
		informationOutputList.setReadWeightFactor(readWeightFactor);
		int readCount = Integer.parseInt(jsObject.getString("read_count"));
		informationOutputList.setReadCount(readCount);	
		double readScore = readWeightFactor * readCount;
		informationOutputList.setReadScore(readScore);		//分=系数*数量，下同
		
		informationOutputList.setCommentWeight(jsObject.getString("comment_weight"));
		double commentWeightFactor = Double.parseDouble(jsObject.getString("comment_weight_factor"));
		informationOutputList.setCommentWeightFactor(commentWeightFactor);
		int commentCount = Integer.parseInt(jsObject.getString("comment_count"));
		informationOutputList.setCommentCount(commentCount);	
		double commentScore = commentWeightFactor * commentCount;
		informationOutputList.setCommentScore(commentScore);
		
		informationOutputList.setLikeWeight(jsObject.getString("like_weight"));
		double likeWeightFactor = Double.parseDouble(jsObject.getString("like_weight_factor"));
		informationOutputList.setLikeWeightFactor(likeWeightFactor);
		int likeCount = Integer.parseInt(jsObject.getString("like_count"));
		informationOutputList.setLikeCount(likeCount);
		double likeScore = likeWeightFactor * likeCount;
		informationOutputList.setLikeScore(likeScore);
		
		informationOutputList.setWordWeight(jsObject.getString("word_weight"));
		double wordWeightFactor = Double.parseDouble(jsObject.getString("word_weight_factor"));
		informationOutputList.setWordWeightFactor(wordWeightFactor);
		int wordCount = Integer.parseInt(jsObject.getString("word_count"));
		informationOutputList.setWordCount(wordCount);
		double wordScore = wordWeightFactor * wordCount;
		informationOutputList.setWordScore(wordScore);
		
		informationOutputList.setTitle(jsObject.getString("title"));
		informationOutputList.setUrl(jsObject.getString("url"));
		
		//总分=爬虫分+阅读分+评论分+点赞分+字数分
		informationOutputList.setInformationScore(crawlerScore + readScore + commentScore + likeScore + wordScore);	
			
		//将字符串转成时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date time = simpleDateFormat.parse(jsObject.getString("created_time"));
			informationOutputList.setCreatedTime(time);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("日期不满足yyyy-MM-dd HH:mm:ss格式");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
				
		//3.修改
		try {
			informationOutputListService.save(informationOutputList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("更新失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("更新成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 删除InformationOutputList
	 * @param id 列表输出表id
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<DataResponse> delete(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.DELETE: "
				+ "id: " + id);

		try {
			informationOutputListService.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("删除失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("删除成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 清空InformationOutputList,不提供对外接口
	 * @return 成功/失败信息
	 */
//	@RequestMapping(value="/",method=RequestMethod.DELETE)
//	public ResponseEntity<DataResponse> clean() {
//		informationOutputListService.cleanOutputList();
//		
//		DataResponse response = new DataResponse().success("清空InformationOutputList表成功");
//		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
//	}
}