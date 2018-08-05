package com.jishitoutiao.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.jishitoutiao.domain.InformationComment;
import com.jishitoutiao.domain.InformationOutputArticle;
import com.jishitoutiao.domain.User;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferInformationComment;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.service.InformationCommentService;
import com.jishitoutiao.service.InformationOutputArticleService;
import com.jishitoutiao.service.UserService;

@RestController
@RequestMapping(value="/informationcomment")
public class InformationCommentController {
	@Autowired
	private UserService userService;		//用户服务
	
	@Autowired
	private InformationOutputArticleService informationOutputArticleService;		//用户服务
	
	@Autowired
	private InformationCommentService informationCommentService;		//服务

	private Logger logger = LoggerFactory.getLogger(InformationCommentController.class);
	
	 /**
	  * 获取所有InformationComment
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
		TransferInformationComment transferInformationComment = null;
		List<TransferInformationComment> tlist = new ArrayList<TransferInformationComment>();
		List<TableColumn> columnList = new ArrayList<TableColumn>();
		
		//用于接受后端数据的Page对象
		PageObj<InformationComment> dpage = null;
		
		//1.获取指定搜索条件，页码的全部数据
		try {
			dpage = informationCommentService.getPageData(keyword, pageNum);
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
		PageObj<TransferInformationComment> tpage = new PageObj<TransferInformationComment>(dpage.getPageNum(), dpage.getTotalRecord());
		
		//2.2将获取的Dmain对象添加到DTO 的Page对象的List中
		for (InformationComment ic : dpage.getList()) {
			transferInformationComment = new TransferInformationComment(ic);
			tlist.add(transferInformationComment);
		}
		tpage.setList(tlist);
		
		//2.3将table需要的column标题封装至tpage
		columnList.add(new TableColumn("用户id", "user_id", "user_id"));
		columnList.add(new TableColumn("用户名", "username", "username"));
		columnList.add(new TableColumn("评论内容", "content", "content"));
		columnList.add(new TableColumn("资讯id", "information_id", "information_id"));
		columnList.add(new TableColumn("评论时间", "comment_time", "comment_time"));
		tpage.setColumns(columnList);
		
		//3.返回DTO对象(通过DataResponse封装)
		DataResponse response = new DataResponse().success(tpage);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 通过id获取InformationComment
	 * @param id 评论id
	 * @return Json数据
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> find(@PathVariable("id") String id) {		
		//记录日志
		logger.info("value=/{id},method=RequestMethod.GET: "
				+ "id: " + id);
		
		//1.获取原始domain对象
		InformationComment informationComment = null;
		try {
			informationComment = informationCommentService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.将domain对象转化为DTO对象
		TransferInformationComment transferInformationComment = new TransferInformationComment(informationComment);
		
		//3.返回DTO对象	
		DataResponse response = new DataResponse().success(transferInformationComment);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}

	/**
	 * 创建一个InformationComment
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
				+ "user_id: " + jsObject.getString("user_id")
				+ " ,content: " + jsObject.getString("content")
				+ " ,output_article_id: " + jsObject.getString("output_article_id"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许新增
		// 规则：判断user_id、content、output_article_id是否为空
		if (jsObject.getString("user_id") == null || jsObject.getString("content") == null || jsObject.getString("output_article_id") == null) {
			DataResponse response = new DataResponse().failure("user_id、content、output_article_id不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//获取用户
		User user = userService.find(jsObject.getString("user_id"));
		if (user == null) {
			DataResponse response = new DataResponse().failure("无此用户");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//获取资讯
		InformationOutputArticle informationOutputArticle = informationOutputArticleService.find(jsObject.getString("output_article_id"));
		if (informationOutputArticle == null) {
			DataResponse response = new DataResponse().failure("无此资讯");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//封装domain实体
		InformationComment informationComment = new InformationComment();
		informationComment.setContent(jsObject.getString("content"));
		informationComment.setInformationOutputArticle(informationOutputArticle);
		informationComment.setUser(user);
		
		//获取当前时间，作为评论时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date commentTime = null;
		try {
			commentTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
			informationComment.setCommentTime(commentTime);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("日期格式化错误，请重新尝试");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		try {
			informationCommentService.save(informationComment);
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
	 * 删除InformationComment
	 * @param id 评论id
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<DataResponse> delete(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.DELETE: "
				+ "id: " + id);

		try {
			informationCommentService.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("删除失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("删除成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
}