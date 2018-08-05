package com.jishitoutiao.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jishitoutiao.domain.InformationComment;
import com.jishitoutiao.domain.InformationOutputArticle;
import com.jishitoutiao.domain.User;
import com.jishitoutiao.service.InformationCommentService;
import com.jishitoutiao.service.InformationOutputArticleService;
import com.jishitoutiao.service.UserService;

public class InformationCommentServiceTest extends BaseJunit4Test {
	@Autowired
	private InformationCommentService informationCommentService;

	@Autowired
	private InformationOutputArticleService informationOutputArticleService;

	@Autowired
	private UserService userService;

	//实体类
	InformationComment informationComment = new InformationComment();

	//关联类
	InformationOutputArticle informationOutputArticle = new InformationOutputArticle();
	User user = new User();

	List<InformationComment> list = new ArrayList<InformationComment>();
	long count = 0L;

	/**
	 * 添加InformationComment
	 *
	 * @throws ParseException
	 */
	@Test
	public void testAdd() throws ParseException {

		for (int i = 0; i < 1; i++) {
			informationOutputArticle = informationOutputArticleService.find("4028298163548b680163548b77db0000");
			informationComment.setInformationOutputArticle(informationOutputArticle);

			user = userService.find("4028298163f816a80163f816ca7a0000");
			informationComment.setUser(user);

			String time = "2017-09-09 09:09:09";
			Date commentTime = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				commentTime = df.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
				throw e;
			}
			informationComment.setCommentTime(commentTime);
			;

			informationComment.setContent("content" + i);
			informationComment.setUser(user);

			try {
				informationCommentService.save(informationComment);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除InformationComment
	 */
	@Test
	public void testDelete() {
		try {
			informationCommentService.deleteById("402829816097234a0160972355830001");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 更新InformationComment
	 */
	@Test
	public void testUpdate() {
		informationComment = informationCommentService.find("402829816097234a0160972355830001");
		informationComment.setContent("ssssssss");
		try {
			informationCommentService.save(informationComment);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查找指定InformationComment
	 */
	@Test
	public void testFind() {
		informationComment = informationCommentService.find("402829816097234a0160972355830001");
//		sop(informationComment);
	}

	/**
	 * 获取所有InformationComment(带搜索条件)
	 */
//	@Test
//	public void testGetAll() {
//		try {
//			list = informationCommentService.getAll("4028298");
//		} catch (SqlExecuteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (InformationComment ic : list) {
//			sop(ic);
//		}
//	}

	/**
	 * 获取分页数据
	 */
//	@Test
//	public void testGetPageData() {
//		Page<InformationComment> page = null;
//
//		try {
//			page = informationCommentService.getPageData(null, "1");
//		} catch (SqlExecuteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (InformationComment ic : page.getList()) {
//			sop(ic);
//		}
//	}

	/**
	 * 获取总记录数(带搜索条件)
	 */
//	@Test
//	public void testGetCount() {
//		try {
//			count = informationCommentService.getCount(null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		sop(count);
//	}
//
	/**
	 * 清空评论表数据
	 */
//	@Test
//	public void testClean() {
//		try {
//			informationCommentService.cleanComment();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	public void sop(Object obj) {
//		System.out.println(obj);
//	}
}