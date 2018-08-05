package com.jishitoutiao.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jishitoutiao.domain.CrawlerCategory;
import com.jishitoutiao.domain.CrawlerSource;
import com.jishitoutiao.domain.MenuGroup;
import com.jishitoutiao.domain.MenuItem;
import com.jishitoutiao.dto.PlatformMeta;
import com.jishitoutiao.dto.TransferCrawlerCategoryList;
import com.jishitoutiao.dto.TransferCrawlerSourceOption;
import com.jishitoutiao.dto.TransferMenuGroup;
import com.jishitoutiao.dto.TransferMenuItem;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.service.CrawlerCategoryService;
import com.jishitoutiao.service.CrawlerSourceService;
import com.jishitoutiao.service.MenuGroupService;
import com.jishitoutiao.service.MenuItemService;

@RestController
@RequestMapping(value="/meta")
public class MetaController {
	@Autowired
	private MenuGroupService menuGroupService;		//菜单-组服务
	@Autowired
	private MenuItemService menuItemService;		//菜单-项服务
	
	@Autowired
	private CrawlerSourceService crawlerSourceService;		//爬虫源服务
	@Autowired
	private CrawlerCategoryService crawlerCategoryService;		//类别服务

	private Logger logger = LoggerFactory.getLogger(MetaController.class);
	
	 /**
	  * 获取所有元数据
	  * @return Json数据
	  */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ResponseEntity<DataResponse> getAll() {		
		//记录日志
		logger.info("value=/,method=RequestMethod.GET:");
		
		//用于和前端交互的DTO对象
		PlatformMeta meta = new PlatformMeta();
		
		//用于接受数据库数据的list
		List<MenuGroup> menuGroupList = new ArrayList<MenuGroup>();		//菜单组
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();		//菜单项
		List<CrawlerSource> sourceList = new ArrayList<CrawlerSource>();		//爬虫源
		List<CrawlerCategory> categoryList = new ArrayList<CrawlerCategory>();		//爬虫类别
		
		//用于构建转换对象
		List<TransferMenuGroup> transMenuGroupList = new ArrayList<TransferMenuGroup>();
		List<TransferCrawlerSourceOption> transCrawlerSourceOptionList = new ArrayList<TransferCrawlerSourceOption>();
		List<TransferCrawlerCategoryList> transCrawlerCategoryListList = new ArrayList<TransferCrawlerCategoryList>();
		
		// 1.从数据库获取所有menu(组+项)数据，转换为TransferMenuGroup，并加到tmenuList
		try {
			Iterable<MenuGroup> menuGroupIterable = menuGroupService.getAll(null);
			for (MenuGroup menuGroup : menuGroupIterable) {
				menuGroupList.add(menuGroup);		// 将遍历的menuGroup加到list结合中
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("获取组数据失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		} 
		
		// 1.1获取数据库所有菜单组
		for (MenuGroup mg : menuGroupList) {
			TransferMenuGroup transMenuGroup = new TransferMenuGroup();
			transMenuGroup.setGroupIndex(mg.getGroupIndex());
			transMenuGroup.setGroupKey(mg.getGroupKey());
			transMenuGroup.setGroupName(mg.getGroupName());

			List<TransferMenuItem> transmenuItemList = new ArrayList<TransferMenuItem>();
				
			// 1.2获取所有菜单项，如果跟当前组相同，就加到菜单组的list中
			try {
				Iterable<MenuItem> menuItemIterable = menuItemService.getAll(null);
				for (MenuItem menuItem : menuItemIterable) {
					menuItemList.add(menuItem);		// 将遍历的menuItem加到list结合中
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				DataResponse response = new DataResponse().failure("获取菜单项数据失败");
				return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
			}
			
			for (MenuItem mi : menuItemList) {
				String key = mi.getMenuGroup().getGroupKey();
				if (key.equals(transMenuGroup.getGroupKey())) {
					TransferMenuItem transMenuItem = new TransferMenuItem();
					transMenuItem.setItemIndex(mi.getItemIndex());
					transMenuItem.setItemKey(mi.getItemKey());
					transMenuItem.setItemName(mi.getItemName());
					
					transmenuItemList.add(transMenuItem);
				}
			}
			transMenuGroup.setMenuList(transmenuItemList);
			
			// 1.3将菜单组+项的数据添加至platform中
			transMenuGroupList.add(transMenuGroup);
		}
		meta.setMenu(transMenuGroupList);
		
		// 2.获取所有源数据，加到list
		try {
			Iterable<CrawlerSource> crawlerSourceIterable = crawlerSourceService.getAll(null);
			for (CrawlerSource crawlerSource : crawlerSourceIterable) {
				sourceList.add(crawlerSource);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("获取源数据失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		for (CrawlerSource cs : sourceList) {
			TransferCrawlerSourceOption transCrawlerSourceOption = new TransferCrawlerSourceOption();
			transCrawlerSourceOption.setBid(cs.getBid());
			transCrawlerSourceOption.setBkey(cs.getBkey());
			transCrawlerSourceOption.setBname(cs.getBname());
			
			transCrawlerSourceOptionList.add(transCrawlerSourceOption);
		}
		meta.setSourceOption(transCrawlerSourceOptionList);
		
		// 3.获取所有类别数据，加到list
		try {
			Iterable<CrawlerCategory> crawlerCategoryIterable = crawlerCategoryService.getAll(null);
			for (CrawlerCategory crawlerCategory : crawlerCategoryIterable) {
				categoryList.add(crawlerCategory);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			DataResponse response = new DataResponse().failure("获取类别数据失败");
			return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
		}
		
		for (CrawlerCategory cc : categoryList) {
			TransferCrawlerCategoryList transCrawlerCategoryList = new TransferCrawlerCategoryList();
			transCrawlerCategoryList.setCid(cc.getCid());
			transCrawlerCategoryList.setCkey(cc.getCkey());
			transCrawlerCategoryList.setCname(cc.getCname());
			
			transCrawlerCategoryListList.add(transCrawlerCategoryList);
		}
		meta.setCategoryList(transCrawlerCategoryListList);

		// 4.将meta元数据加到与前端交互的DataResponse对象，返回
		DataResponse response = new DataResponse().success(meta, null);
		return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
	}
}