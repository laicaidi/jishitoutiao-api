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
import com.jishitoutiao.domain.InformationIllegalitySet;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferInformationIllegalitySet;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.service.InformationIllegalitySetService;

@RestController
@RequestMapping(value="/informationillegalityset")
public class InformationIllegalitySetController {
	@Autowired
	private InformationIllegalitySetService informationIllegalitySetService;		//服务

	private Logger logger = LoggerFactory.getLogger(InformationIllegalitySetController.class);
	
	/**
	 * 获取所有InformationIllegalitySet
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
		TransferInformationIllegalitySet transferInformationIllegalitySet = null;
		List<TransferInformationIllegalitySet> tlist = new ArrayList<TransferInformationIllegalitySet>();
		List<TableColumn> columnList = new ArrayList<TableColumn>();
		
		//用于接受后端数据的Page对象
		PageObj<InformationIllegalitySet> dpage = null;
		
		//1.获取指定搜索条件，页码的全部数据
		try {
			dpage = informationIllegalitySetService.getPageData(keyword, pageNum);
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
		PageObj<TransferInformationIllegalitySet> tpage = new PageObj<TransferInformationIllegalitySet>(dpage.getPageNum(), dpage.getTotalRecord());
		
		//2.2将获取的Dmain对象添加到DTO 的Page对象的List中
		for (InformationIllegalitySet iis : dpage.getList()) {
			transferInformationIllegalitySet = new TransferInformationIllegalitySet(iis);
			tlist.add(transferInformationIllegalitySet);
		}
		tpage.setList(tlist);
		
		//2.3将table需要的column标题封装至tpage
		columnList.add(new TableColumn("键", "ikey", "ikey"));
		columnList.add(new TableColumn("值", "ivalue", "ivalue"));
		columnList.add(new TableColumn("备注", "remark", "remark"));
		tpage.setColumns(columnList);
		
		//3.返回DTO对象(通过DataResponse封装)
		DataResponse response = new DataResponse().success(tpage);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 通过sid获取InformationIllegalitySet
	 * @param sid
	 * @return Json数据
	 */
	@RequestMapping(value="/{sid}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> find(@PathVariable("sid") String sid) {
		//记录日志
		logger.info("value=/{sid},method=RequestMethod.GET: "
				+ "sid: " + sid);
		
		//1.获取原始domain对象
		InformationIllegalitySet informationIllegalitySet = null;
		try {
			informationIllegalitySet = informationIllegalitySetService.find(sid);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.将domain对象转化为DTO对象
		TransferInformationIllegalitySet transferInformationIllegalitySet = new TransferInformationIllegalitySet(informationIllegalitySet);
		
		//3.返回DTO对象
		DataResponse response = new DataResponse().success(transferInformationIllegalitySet);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}

	/**
	 * 创建一个InformationIllegalitySet
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
				+ "ikey: " + jsObject.getString("ikey")
				+ " ,ivalue: " + jsObject.getString("ivalue")
				+ " ,remark: " + jsObject.getString("remark"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许新增
		// 规则1，判断ikey、ivalue是否为空
		if (jsObject.getString("ikey") == null || jsObject.getString("ivalue") == null) {
			DataResponse response = new DataResponse().failure("ikey、ivalue不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.ikey是否为英文或数字
		boolean ikeyRex = jsObject.getString("ikey").matches("^[a-zA-Z_0-9]+$");
		logger.info("ikey: " + jsObject.getString("ikey") + ", Rex: " + ikeyRex);
		if (!ikeyRex) {		// 不满足
			DataResponse response = new DataResponse().failure("ikey必须为英文或数字");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//封装domain对象
		InformationIllegalitySet informationIllegalitySet = new InformationIllegalitySet();
		informationIllegalitySet.setIkey(jsObject.getString("ikey"));
		informationIllegalitySet.setIvalue(jsObject.getString("ivalue"));
		informationIllegalitySet.setRemark(jsObject.getString("remark"));
		
		try {
			informationIllegalitySetService.save(informationIllegalitySet);
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
	 * 更新informationIllegalitySet
	 * @param sid sid主键
	 * @param param body数据
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{sid}",method=RequestMethod.PUT)
	public ResponseEntity<DataResponse> update(@PathVariable("sid") String sid, @RequestBody String param) {
		//记录日志
		logger.info("value=/{sid},method=RequestMethod.PUT: "
		+ "sid: " + sid + ", param: " + param);

		if (!param.startsWith("{") && !param.endsWith("}")) {
			// 以键值对方式传递的字符串，则需在两侧添加{}后，转换为JSONObject
			param = "{" + param + "}";
			logger.info("value=/login,method=RequestMethod.POST: handleParam: " + param);
		}

		JSONObject jsObject = JSON.parseObject(param);
		
		//记录日志
		logger.info("value=/{sid},method=RequestMethod.PUT: "
				+ "sid: " + sid
				+ " ,ikey: " + jsObject.getString("ikey")
				+ " ,ivalue: " + jsObject.getString("ivalue")
				+ " ,remark: " + jsObject.getString("remark"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许更新
		// 规则1，判断ikey、ivalue是否为空
		if (jsObject.getString("ikey") == null || jsObject.getString("ivalue") == null) {
			DataResponse response = new DataResponse().failure("ikey、ivalue不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.ikey是否为英文或数字
		boolean ikeyRex = jsObject.getString("ikey").matches("^[a-zA-Z_0-9]+$");
		logger.info("ikey: " + jsObject.getString("ikey") + ", Rex: " + ikeyRex);
		if (!ikeyRex) {		// 不满足
			DataResponse response = new DataResponse().failure("ikey必须为英文或数字");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//1.查到该对象
		InformationIllegalitySet informationIllegalitySet = null;
		try {
			informationIllegalitySet = informationIllegalitySetService.find(sid);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.封装domain对象
		informationIllegalitySet.setIkey(jsObject.getString("ikey"));
		informationIllegalitySet.setIvalue(jsObject.getString("ivalue"));
		informationIllegalitySet.setRemark(jsObject.getString("remark"));
				
		//3.修改
		try {
			informationIllegalitySetService.save(informationIllegalitySet);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("更新失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("更新成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 删除InformationIllegalitySet
	 * @param sid 设置id主键
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{sid}",method=RequestMethod.DELETE)
	public ResponseEntity<DataResponse> delete(@PathVariable("sid") String sid) {
		//记录日志
		logger.info("value=/{sid},method=RequestMethod.DELETE: "
				+ "sid: " + sid);

		try {
			informationIllegalitySetService.deleteById(sid);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("删除失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("删除成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
}