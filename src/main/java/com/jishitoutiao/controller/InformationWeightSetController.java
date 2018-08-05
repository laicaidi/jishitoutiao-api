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
import com.jishitoutiao.domain.InformationWeightSet;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferInformationWeightSet;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.service.InformationWeightSetService;

@RestController
@RequestMapping(value="/informationweightset")
public class InformationWeightSetController {
	@Autowired
	private InformationWeightSetService informationWeightSetService;		//服务

	private Logger logger = LoggerFactory.getLogger(InformationWeightSetController.class);
	
	/**
	 * 获取所有InformationWeightSet
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
		TransferInformationWeightSet transferInformationWeightSet = null;
		List<TransferInformationWeightSet> tlist = new ArrayList<TransferInformationWeightSet>();
		List<TableColumn> columnList = new ArrayList<TableColumn>();
		
		//用于接受后端数据的Page对象
		PageObj<InformationWeightSet> dpage = null;
		
		//1.获取指定搜索条件，页码的全部数据
		try {
			dpage = informationWeightSetService.getPageData(keyword, pageNum);
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
		PageObj<TransferInformationWeightSet> tpage = new PageObj<TransferInformationWeightSet>(dpage.getPageNum(), dpage.getTotalRecord());
		
		//2.2将获取的Dmain对象添加到DTO 的Page对象的List中
		for (InformationWeightSet iws : dpage.getList()) {
			transferInformationWeightSet = new TransferInformationWeightSet(iws);
			tlist.add(transferInformationWeightSet);
		}
		tpage.setList(tlist);
		
		//2.3将table需要的column标题封装至tpage
		columnList.add(new TableColumn("键", "wkey", "wkey"));
		columnList.add(new TableColumn("值", "wvalue", "wvalue"));
		columnList.add(new TableColumn("备注", "remark", "remark"));
		tpage.setColumns(columnList);
		
		//3.返回DTO对象(通过DataResponse封装)
		DataResponse response = new DataResponse().success(tpage);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 通过sid获取InformationWeightSet
	 * @param sid
	 * @return Json数据
	 */
	@RequestMapping(value="/{sid}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataResponse> find(@PathVariable("sid") String sid) {
		//记录日志
		logger.info("value=/{sid},method=RequestMethod.GET: "
				+ "sid: " + sid);
		
		//1.获取原始domain对象
		InformationWeightSet informationWeightSet = null;
		try {
			informationWeightSet = informationWeightSetService.find(sid);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}

		//2.将domain对象转化为DTO对象
		TransferInformationWeightSet transferInformationWeightSet = new TransferInformationWeightSet(informationWeightSet);
		
		//3.返回DTO对象
		DataResponse response = new DataResponse().success(transferInformationWeightSet);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}

	/**
	 * 创建一个InformationWeightSet
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
				+ "wkey: " + jsObject.getString("wkey")
				+ " ,wvalue: " + jsObject.getString("wvalue")
				+ " ,remark: " + jsObject.getString("remark"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许新增
		// 规则1，判断wkey或wvalue是否为空
		if (jsObject.getString("wkey") == null || jsObject.getString("wvalue") == null) {
			DataResponse response = new DataResponse().failure("wkey或wvalue不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.wkey是否为英文或数字
		boolean wkeyRex = jsObject.getString("wkey").matches("^[a-zA-Z_0-9]+$");
		logger.info("wkey: " + jsObject.getString("wkey") + ", Rex: " + wkeyRex);
		if (!wkeyRex) {		// 不满足
			DataResponse response = new DataResponse().failure("wkey必须为英文或数字");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3.wvalue是否为大于0的整数或小数
		boolean wvalueRex = jsObject.getString("wvalue").matches("^[0-9]+([.]{1}[0-9]+){0,1}$");
		logger.info("wvalue: " + jsObject.getString("wvalue") + ", Rex: " + wvalueRex);
		if (!wvalueRex) {
			DataResponse response = new DataResponse().failure("wvalue必须为大于0的整数或小数");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//封装domain对象
		InformationWeightSet informationWeightSet = new InformationWeightSet();
		informationWeightSet.setWkey(jsObject.getString("wkey"));
		informationWeightSet.setWvalue(Double.parseDouble(jsObject.getString("wvalue")));
		informationWeightSet.setRemark(jsObject.getString("remark"));
		
		try {
			informationWeightSetService.save(informationWeightSet);
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
	 * 更新InformationWeightSet
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
				+ " ,wkey: " + jsObject.getString("wkey")
				+ " ,wvalue: " + jsObject.getString("wvalue")
				+ " ,remark: " + jsObject.getString("remark"));
		
		// 对提交的数据进行匹配，如不符合规则，则不允许更新
		// 规则1，判断wkey或wvalue是否为空
		if (jsObject.getString("wkey") == null || jsObject.getString("wvalue") == null) {
			DataResponse response = new DataResponse().failure("wkey或wvalue不可为空");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则2.wkey是否为英文或数字
		boolean wkeyRex = jsObject.getString("wkey").matches("^[a-zA-Z_0-9]+$");
		logger.info("wkey: " + jsObject.getString("wkey") + ", Rex: " + wkeyRex);
		if (!wkeyRex) {		// 不满足
			DataResponse response = new DataResponse().failure("wkey必须为英文或数字");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		// 规则3.wvalue是否为大于0的整数或小数
		boolean wvalueRex = jsObject.getString("wvalue").matches("^[0-9]+([.]{1}[0-9]+){0,1}$");
		logger.info("wvalue: " + jsObject.getString("wvalue") + ", Rex: " + wvalueRex);
		if (!wvalueRex) {
			DataResponse response = new DataResponse().failure("wvalue必须为大于0的整数或小数");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		//1.查到该对象
		InformationWeightSet informationWeightSet = null;
		try {
			informationWeightSet = informationWeightSetService.find(sid);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("无符合条件的数据");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
			
		//2.封装domain对象
		informationWeightSet.setWkey(jsObject.getString("wkey"));
		informationWeightSet.setWvalue(Double.parseDouble(jsObject.getString("wvalue")));
		informationWeightSet.setRemark(jsObject.getString("remark"));
				
		//3.修改
		try {
			informationWeightSetService.save(informationWeightSet);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("更新失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("更新成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 删除InformationWeightSet
	 * @param sid 设置id主键
	 * @return 成功/失败信息
	 */
	@RequestMapping(value="/{sid}",method=RequestMethod.DELETE)
	public ResponseEntity<DataResponse> delete(@PathVariable("sid") String sid) {
		//记录日志
		logger.info("value=/{sid},method=RequestMethod.DELETE: "
				+ "sid: " + sid);

		try {
			informationWeightSetService.deleteById(sid);
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("删除失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		DataResponse response = new DataResponse().success("删除成功");
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
}