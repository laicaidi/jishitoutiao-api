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
import com.jishitoutiao.domain.CrawlerCategory;
import com.jishitoutiao.domain.CrawlerManagement;
import com.jishitoutiao.domain.CrawlerSource;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferCrawlerManagement;
import com.jishitoutiao.rely.CrawlerStatus;
import com.jishitoutiao.rely.CrawlerSwitch;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.service.CrawlerCategoryService;
import com.jishitoutiao.service.CrawlerManagementService;
import com.jishitoutiao.service.CrawlerSourceService;

@RestController
@RequestMapping(value="/crawlermanagement")
public class CrawlerManagementController {
	@Autowired
	private CrawlerManagementService crawlerManagementService;		//爬虫服务
	
	@Autowired
	private CrawlerCategoryService crawlerCategoryService;		//源服务
	
	@Autowired
	private CrawlerSourceService crawlerSourceService;		//类别服务
	
	private Logger logger = LoggerFactory.getLogger(CrawlerManagementController.class);

	/**
	 * 获取所有CrawlerManagement
	 * @param keyword 查询关键字
	 * @param bkey 源key
	 * @param ckey 类别key
	 * @param crawlerStatus 爬虫状态
	 * @param crawlerSwitch 爬虫开关
	 * @param pageNum 当前页码
	 * @return Json数据
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ResponseEntity<DataResponse> getAll(@RequestParam(value="keyword",required=false) String keyword, 
											@RequestParam(value="bkey",required=false) String bkey, 
											@RequestParam(value="ckey",required=false) String ckey, 
											@RequestParam(value="crawler_status",required=false) String crawlerStatus, 
											@RequestParam(value="crawler_switch",required=false) String crawlerSwitch, 
											@RequestParam(value="page_num",defaultValue="1") String pageNum) {		
		//记录日志
		logger.info("value=/,method=RequestMethod.GET: "
				+ "keyword: " + keyword 
				+ " ,bkey: " + bkey 
				+ " ,ckey: " + ckey  
				+ " ,crawler_status: " + crawlerStatus
				+ " ,crawler_switch: " + crawlerSwitch
				+ " ,page_num: " + pageNum);
		
		//用于和前端交互的DTO对象
		TransferCrawlerManagement transferCrawlerManagement = null;
		List<TransferCrawlerManagement> tlist = new ArrayList<TransferCrawlerManagement>();
		List<TableColumn> columnList = new ArrayList<TableColumn>();
		
		//用于接受后端数据的Page对象
		PageObj<CrawlerManagement> dpage = null;
		
		//1.获取指定搜索条件，页码的全部数据
		try {
			dpage = crawlerManagementService.getPageData(keyword, bkey, ckey, crawlerStatus, crawlerSwitch, pageNum);
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
		PageObj<TransferCrawlerManagement> tpage = new PageObj<TransferCrawlerManagement>(dpage.getPageNum(), dpage.getTotalRecord());
		
		//2.2将获取的Dmain对象添加到DTO 的Page对象的List中
		for (CrawlerManagement cm : dpage.getList()) {
			transferCrawlerManagement = new TransferCrawlerManagement(cm);
			tlist.add(transferCrawlerManagement);
		}
		tpage.setList(tlist);
		
		// 2.3将table需要的column标题封装至tpage
		// columnList.add(new TableColumn("爬虫id", "crawler_id", "crawler_id"));
		columnList.add(new TableColumn("源名称", "bname", "bname"));
		columnList.add(new TableColumn("二级源名", "sld", "sld"));
		columnList.add(new TableColumn("所属类别", "cname", "cname"));
		columnList.add(new TableColumn("爬虫名", "crawler_name", "crawler_name"));
		columnList.add(new TableColumn("爬取base_url", "base_url", "base_url"));
		columnList.add(new TableColumn("爬虫文件所在目录", "crawler_directory", "crawler_directory"));
		columnList.add(new TableColumn("爬虫权重系数", "crawler_weight_factor", "crawler_weight_factor"));
		columnList.add(new TableColumn("爬虫权重分", "crawler_weight_score", "crawler_weight_score"));
		columnList.add(new TableColumn("状态", "crawler_status", "crawler_status"));
		columnList.add(new TableColumn("备注", "remark", "remark"));
		columnList.add(new TableColumn("开关", "crawler_switch", "crawler_switch"));
		tpage.setColumns(columnList);
		
		//3.返回DTO对象(通过DataResponse封装)
		DataResponse response = new DataResponse().success(tpage);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 通过id获取CrawlerManagement
	 * @param id 爬虫id
	 * @return Json数据
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> find(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.GET: "
				+ "id: " + id);
		
		//1.获取原始domain对象
		CrawlerManagement crawlerManagement = null;
		try {
			crawlerManagement = crawlerManagementService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.将domain对象转化为DTO对象
		TransferCrawlerManagement transferCrawlerManagement = new TransferCrawlerManagement(crawlerManagement);
		
		//3.返回DTO对象	
		DataResponse response = new DataResponse().success(transferCrawlerManagement);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}

	/**
	 * 创建一个CrawlerManagement
	 * @param param body数据
	 * @return
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
				+ "bid: " + jsObject.getString("bid") 
				+ " ,sld: " + jsObject.getString("sld") 
				+ " ,cid: " + jsObject.getString("cid")  
				+ " ,crawler_name: " + jsObject.getString("crawler_name")
				+ " ,base_url: " + jsObject.getString("base_url")
				+ " ,crawler_directory: " + jsObject.getString("crawler_directory")
				+ " ,crawler_weight_factor: " + jsObject.getString("crawler_weight_factor") 
				+ " ,crawler_weight_score: " + jsObject.getString("crawler_weight_score")  
				+ " ,crawler_status: " + jsObject.getString("crawler_status")
				+ " ,remark: " + jsObject.getString("remark")
				+ " ,crawler_switch: " + jsObject.getString("crawler_switch"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许新增
		// 规则1，判断crawler_name、base_url、crawler_directory、crawler_weight_factor、crawler_weight_score、crawler_status、crawler_switch是否为空
		if (jsObject.getString("bid") == null ||
				jsObject.getString("cid") == null ||
				jsObject.getString("crawler_name") == null || 
				jsObject.getString("base_url") == null || 
				jsObject.getString("crawler_directory") == null ||
				jsObject.getString("crawler_weight_factor") == null ||
				jsObject.getString("crawler_weight_score") == null || 
				jsObject.getString("crawler_status") == null || 
				jsObject.getString("crawler_switch") == null) {
			DataResponse response = new DataResponse().failure("bid、cid、crawler_name、base_url、crawler_directory、crawler_weight_factor、crawler_weight_score、crawler_status、crawler_switch不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.crawler_name是否为英文+数字
		boolean crawlerNameRex = jsObject.getString("crawler_name").matches("^[a-zA-Z_0-9]+$");
		logger.info("crawler_name: " + jsObject.getString("crawler_name") + ", Rex: " + crawlerNameRex);
		if (!crawlerNameRex) {		// 不满足
			DataResponse response = new DataResponse().failure("crawler_name必须为纯英文或数字");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3. base_url是否是url
		boolean baseUrlRex = jsObject.getString("base_url").matches("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
		logger.info("base_url: " + jsObject.getString("base_url") + ", Rex: " + baseUrlRex);
		if (!baseUrlRex) {
			DataResponse response = new DataResponse().failure("base_url必须为url链接");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		// 规则4. crawler_weight_factor是否是数字(包含小数)
		boolean crawlerWeightFactorlRex = jsObject.getString("crawler_weight_factor").matches("^([0-9]{1,}[.]?[0-9]*)$");
		logger.info("crawler_weight_factor: " + jsObject.getString("crawler_weight_factor") + ", Rex: " + crawlerWeightFactorlRex);
		if (!crawlerWeightFactorlRex) {
			DataResponse response = new DataResponse().failure("crawler_weight_factor必须为数字");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则5. crawler_weight_score是否是整数
		boolean crawlerWeightScorelRex = jsObject.getString("crawler_weight_score").matches("^([0-9]{1,})$");
		logger.info("crawler_weight_score: " + jsObject.getString("crawler_weight_score") + ", Rex: " + crawlerWeightScorelRex);
		if (!crawlerWeightScorelRex) {
			DataResponse response = new DataResponse().failure("crawler_weight_score必须为整数");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则6. crawler_status是否是ABNORMAL/NORMAL
		boolean crawlerStatuslRex = jsObject.getString("crawler_status").matches("ABNORMAL|NORMAL");
		logger.info("crawler_status: " + jsObject.getString("crawler_status") + ", Rex: " + crawlerStatuslRex);
		if (!crawlerStatuslRex) {
			DataResponse response = new DataResponse().failure("crawler_status不符合规则");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则7. crawler_switch是否是OFF/ON
		boolean crawlerSwitchlRex = jsObject.getString("crawler_switch").matches("OFF|ON");
		logger.info("crawler_switch: " + jsObject.getString("crawler_switch") + ", Rex: " + crawlerSwitchlRex);
		if (!crawlerSwitchlRex) {
			DataResponse response = new DataResponse().failure("crawler_switch不符合规则");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//获取源
		CrawlerSource crawlerSource = crawlerSourceService.find(jsObject.getString("bid"));
		if (crawlerSource == null) {
			DataResponse response = new DataResponse().failure("无此爬虫源");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//获取类别
		CrawlerCategory crawlerCategory = crawlerCategoryService.find(jsObject.getString("cid"));
		if (crawlerCategory == null) {
			DataResponse response = new DataResponse().failure("无此分类");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
			
		//封装domain对象
		CrawlerManagement crawlerManagement = new CrawlerManagement();
		crawlerManagement.setBaseUrl(jsObject.getString("base_url"));
		crawlerManagement.setCrawlerCategory(crawlerCategory);
		crawlerManagement.setCrawlerDirectory(jsObject.getString("crawler_directory"));
		crawlerManagement.setCrawlerName(jsObject.getString("crawler_name"));
		crawlerManagement.setCrawlerSource(crawlerSource);
		crawlerManagement.setCrawlerWeightFactor(Double.parseDouble(jsObject.getString("crawler_weight_factor")));
		crawlerManagement.setCrawlerWeightScore(Integer.parseInt(jsObject.getString("crawler_weight_score")));
		crawlerManagement.setRemark(jsObject.getString("remark"));
		crawlerManagement.setSld(jsObject.getString("sld"));
		crawlerManagement.setCrawlerStatus(CrawlerStatus.valueOf(jsObject.getString("crawler_status")));
		crawlerManagement.setCrawlerSwitch(CrawlerSwitch.valueOf(jsObject.getString("crawler_switch")));
		
		try {
			crawlerManagementService.save(crawlerManagement);
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
	 * 更新CrawlerManagement
	 * @param id 爬虫id
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
				+ " ,bid: " + jsObject.getString("bid") 
				+ " ,sld: " + jsObject.getString("sld") 
				+ " ,cid: " + jsObject.getString("cid")  
				+ " ,crawler_name: " + jsObject.getString("crawler_name")
				+ " ,base_url: " + jsObject.getString("base_url")
				+ " ,crawler_directory: " + jsObject.getString("crawler_directory")
				+ " ,crawler_weight_factor: " + jsObject.getString("crawler_weight_factor") 
				+ " ,crawler_weight_score: " + jsObject.getString("crawler_weight_score")  
				+ " ,crawler_status: " + jsObject.getString("crawler_status")
				+ " ,remark: " + jsObject.getString("remark")
				+ " ,crawler_switch: " + jsObject.getString("crawler_switch"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许更新
		// 规则1，判断crawler_name、base_url、crawler_directory、crawler_weight_factor、crawler_weight_score、crawler_status、crawler_switch是否为空
		if (jsObject.getString("bid") == null ||
				jsObject.getString("cid") == null ||
				jsObject.getString("crawler_name") == null || 
				jsObject.getString("base_url") == null || 
				jsObject.getString("crawler_directory") == null ||
				jsObject.getString("crawler_weight_factor") == null ||
				jsObject.getString("crawler_weight_score") == null || 
				jsObject.getString("crawler_status") == null || 
				jsObject.getString("crawler_switch") == null) {
			DataResponse response = new DataResponse().failure("bid、cid、crawler_name、base_url、crawler_directory、crawler_weight_factor、crawler_weight_score、crawler_status、crawler_switch不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.crawler_name是否为英文+数字
		boolean crawlerNameRex = jsObject.getString("crawler_name").matches("^[a-zA-Z_0-9]+$");
		logger.info("crawler_name: " + jsObject.getString("crawler_name") + ", Rex: " + crawlerNameRex);
		if (!crawlerNameRex) {		// 不满足
			DataResponse response = new DataResponse().failure("crawler_name必须为纯英文及数字，且必须以英文开头");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3. base_url是否是url
		boolean baseUrlRex = jsObject.getString("base_url").matches("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
		logger.info("base_url: " + jsObject.getString("base_url") + ", Rex: " + baseUrlRex);
		if (!baseUrlRex) {
			DataResponse response = new DataResponse().failure("base_url必须为url链接");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		// 规则4. crawler_weight_factor是否是数字(包含小数)
		boolean crawlerWeightFactorlRex = jsObject.getString("crawler_weight_factor").matches("^([0-9]{1,}[.]?[0-9]*)$");
		logger.info("crawler_weight_factor: " + jsObject.getString("crawler_weight_factor") + ", Rex: " + crawlerWeightFactorlRex);
		if (!crawlerWeightFactorlRex) {
			DataResponse response = new DataResponse().failure("crawler_weight_factor必须为数字");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则5. crawler_weight_score是否是整数
		boolean crawlerWeightScorelRex = jsObject.getString("crawler_weight_score").matches("^([0-9]{1,})$");
		logger.info("crawler_weight_score: " + jsObject.getString("crawler_weight_score") + ", Rex: " + crawlerWeightScorelRex);
		if (!crawlerWeightScorelRex) {
			DataResponse response = new DataResponse().failure("crawler_weight_score必须为整数");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则6. crawler_status是否是ABNORMAL/NORMAL
		boolean crawlerStatuslRex = jsObject.getString("crawler_status").matches("ABNORMAL|NORMAL");
		logger.info("crawler_status: " + jsObject.getString("crawler_status") + ", Rex: " + crawlerStatuslRex);
		if (!crawlerStatuslRex) {
			DataResponse response = new DataResponse().failure("crawler_status不符合规则");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则7. crawler_switch是否是OFF/ON
		boolean crawlerSwitchlRex = jsObject.getString("crawler_switch").matches("OFF|ON");
		logger.info("crawler_switch: " + jsObject.getString("crawler_switch") + ", Rex: " + crawlerSwitchlRex);
		if (!crawlerSwitchlRex) {
			DataResponse response = new DataResponse().failure("crawler_switch不符合规则");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//1.查到该对象
		CrawlerManagement crawlerManagement = null;
		try {
			crawlerManagement = crawlerManagementService.find(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//获取源
		CrawlerSource crawlerSource = crawlerSourceService.find(jsObject.getString("bid"));
		if (crawlerSource == null) {
			DataResponse response = new DataResponse().failure("无此爬虫源");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		//获取类别
		CrawlerCategory crawlerCategory = crawlerCategoryService.find(jsObject.getString("cid"));
		if (crawlerCategory == null) {
			DataResponse response = new DataResponse().failure("无此分类");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
			
		//2.封装domain对象
		crawlerManagement.setBaseUrl(jsObject.getString("base_url"));
		crawlerManagement.setCrawlerCategory(crawlerCategory);
		crawlerManagement.setCrawlerDirectory(jsObject.getString("crawler_directory"));
		crawlerManagement.setCrawlerName(jsObject.getString("crawler_name"));
		crawlerManagement.setCrawlerSource(crawlerSource);
		crawlerManagement.setCrawlerWeightFactor(Double.parseDouble(jsObject.getString("crawler_weight_factor")));
		crawlerManagement.setCrawlerWeightScore(Integer.parseInt(jsObject.getString("crawler_weight_score")));
		crawlerManagement.setRemark(jsObject.getString("remark"));
		crawlerManagement.setSld(jsObject.getString("sld"));
		crawlerManagement.setCrawlerStatus(CrawlerStatus.valueOf(jsObject.getString("crawler_status")));
		crawlerManagement.setCrawlerSwitch(CrawlerSwitch.valueOf(jsObject.getString("crawler_switch")));
				
		//3.修改
		try {
			crawlerManagementService.save(crawlerManagement);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("更新失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("更新成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 删除CrawlerManagement
	 * @param id 爬虫id
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<DataResponse> delete(@PathVariable("id") String id) {
		//记录日志
		logger.info("value=/{id},method=RequestMethod.DELETE: "
				+ "id: " + id);
		try {
			crawlerManagementService.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("删除失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("删除成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
}