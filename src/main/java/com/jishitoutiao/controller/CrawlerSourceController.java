package com.jishitoutiao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishitoutiao.domain.CrawlerSource;
import com.jishitoutiao.dto.TableColumn;
import com.jishitoutiao.dto.TransferCrawlerSource;
import com.jishitoutiao.rely.DataResponse;
import com.jishitoutiao.rely.PageObj;
import com.jishitoutiao.service.CrawlerSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/crawlersource")
public class CrawlerSourceController {

    private Logger logger = LoggerFactory.getLogger(CrawlerSourceController.class);

    @Autowired
    private CrawlerSourceService crawlerSourceService;

    /**
     * 获取所有CrawlerSource
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
                + " , page_num: " + pageNum);

        //用于和前端交互的DTO对象
        TransferCrawlerSource transferCrawlerSource = null;
        List<TransferCrawlerSource> tlist = new ArrayList<TransferCrawlerSource>();
        List<TableColumn> columnList = new ArrayList<TableColumn>();

        //用于接受后端数据的Page对象
        PageObj<CrawlerSource> dpage = null;

        //1.获取指定搜索条件，页码的全部数据
        try {
            dpage = crawlerSourceService.getPageData(keyword, pageNum);
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
        PageObj<TransferCrawlerSource> tpage = new PageObj<TransferCrawlerSource>(dpage.getPageNum(), dpage.getTotalRecord());

        //2.2将获取的Dmain对象添加到DTO 的Page对象的List中
        for (CrawlerSource cs : dpage.getList()) {
            transferCrawlerSource = new TransferCrawlerSource(cs);
            tlist.add(transferCrawlerSource);
        }
        tpage.setList(tlist);

        //2.3将table需要的column标题封装至tpage
        columnList.add(new TableColumn("源key", "bkey", "bkey"));
        columnList.add(new TableColumn("源name", "bname", "bname"));
        columnList.add(new TableColumn("源网站首页", "homepage", "homepage"));
        columnList.add(new TableColumn("品牌logo", "logo", "logo"));
        columnList.add(new TableColumn("介绍", "remark", "remark"));
        tpage.setColumns(columnList);

        //3.返回DTO对象(通过DataResponse封装)
        DataResponse response = new DataResponse().success(tpage);
        return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
    }

    /**
     * 通过id获取CrawlerSource
     * @param bid 源id
     * @return Json数据
     */
    @RequestMapping(value="/{bid}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> find(@PathVariable("bid") String bid) {
        //记录日志
        logger.info("value=/{bid},method=RequestMethod.GET: "
                + "bid: " + bid);

        //1.获取原始domain对象
        CrawlerSource crawlerSource = null;
        try {
            crawlerSource = crawlerSourceService.find(bid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            DataResponse response = new DataResponse().failure("无符合条件的数据");
            return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
        }

        //2.将domain对象转化为DTO对象
        TransferCrawlerSource transferCrawlerSource = new TransferCrawlerSource(crawlerSource);

        //3.返回DTO对象
        DataResponse response = new DataResponse().success(transferCrawlerSource);
        return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
    }

    /**
     * 创建一个CrawlerSource
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
                + "bkey: " + jsObject.getString("bkey")
                + " , bname: " + jsObject.getString("bname")
                + " , homepage: " + jsObject.getString("homepage")
                + " , logo: " + jsObject.getString("logo")
                + " , remark: " + jsObject.getString("remark"));

        // 对提交的数据进行匹配，如不符合规则，则不允许新增
        // 规则1，判断bkey或bname是否为空
        if (jsObject.getString("bkey") == null || jsObject.getString("bname") == null) {
            DataResponse response = new DataResponse().failure("bkey或bname不可为空");
            return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
        }
        // 规则2.bey是否为英文+数字，并以英文开头
        boolean bkeyRex = jsObject.getString("bkey").matches("[a-zA-Z_0-9]+");
        logger.info("bkey: " + jsObject.getString("bkey") + ", Rex: " + bkeyRex);
        if (!bkeyRex) {		// 不满足
            DataResponse response = new DataResponse().failure("bkey必须为纯英文或数字");
            return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
        }
        // 规则3. homepage是否是url
        if (jsObject.getString("homepage") != null && jsObject.getString("homepage").trim().equals("") != true) {
            String homepage = jsObject.getString("homepage");

            boolean homepageRex = homepage.matches("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
            logger.info("homepage: " + jsObject.getString("homepage") + ", Rex: " + homepageRex);
            if (!homepageRex) {
                DataResponse response = new DataResponse().failure("homepage必须为url链接");
                return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
            }
        }

        //封装domain对象
        CrawlerSource crawlerSource = new CrawlerSource();
        crawlerSource.setBkey(jsObject.getString("bkey"));
        crawlerSource.setBname(jsObject.getString("bname"));
        crawlerSource.setHomepage(jsObject.getString("homepage"));
        crawlerSource.setLogo(jsObject.getString("logo"));
        crawlerSource.setRemark(jsObject.getString("remark"));

        try {
            crawlerSourceService.save(crawlerSource);
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
     * 更新CrawlerSource
     * @param bid 源id
     * @param param body数据
     * @return 成功/失败信息
     */
    @RequestMapping(value="/{bid}",method=RequestMethod.PUT)
    public ResponseEntity<DataResponse> update(@PathVariable("bid") String bid, @RequestBody String param) {
        //记录日志
        logger.info("value=/{bid},method=RequestMethod.PUT: "
                + "bid: " + bid + ", param: " + param);

        if (!param.startsWith("{") && !param.endsWith("}")) {
            // 以键值对方式传递的字符串，则需在两侧添加{}后，转换为JSONObject
            param = "{" + param + "}";
            logger.info("value=/login,method=RequestMethod.POST: handleParam: " + param);
        }

        JSONObject jsObject = JSON.parseObject(param);

        //记录日志
        logger.info("value=/{bid},method=RequestMethod.PUT: "
                + "bid: " + bid
                + " , bkey: " + jsObject.getString("bkey")
                + " , bname: " + jsObject.getString("bname")
                + " , homepage: " + jsObject.getString("homepage")
                + " , logo: " + jsObject.getString("logo")
                + " , remark: " + jsObject.getString("remark"));

        // 对提交的数据进行匹配，如不符合规则，则不允许更新
        // 规则1，判断bkey或bname是否为空
        if (jsObject.getString("bkey") == null || jsObject.getString("bname") == null) {
            DataResponse response = new DataResponse().failure("bkey或bname不可为空");
            return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
        }
        // 规则2.bey是否为英文+数字，并以英文开头
        boolean bkeyRex = jsObject.getString("bkey").matches("[a-zA-Z_0-9]+");
        logger.info("bkey: " + jsObject.getString("bkey") + ", Rex: " + bkeyRex);
        if (!bkeyRex) {		// 不满足
            DataResponse response = new DataResponse().failure("bkey必须为纯英文或数字");
            return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
        }

        if (jsObject.getString("homepage") != null && jsObject.getString("homepage").trim().equals("") != true) {
            // 规则3. homepage是否是url
            boolean homepageRex = jsObject.getString("homepage").matches("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
            logger.info("homepage: " + jsObject.getString("homepage") + ", Rex: " + homepageRex);
            if (!homepageRex) {
                DataResponse response = new DataResponse().failure("homepage必须为url链接");
                return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
            }
        }

        //1.查到该对象
        CrawlerSource crawlerSource = null;
        try {
            crawlerSource = crawlerSourceService.find(bid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            DataResponse response = new DataResponse().failure("无符合条件的数据");
            return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
        }

        //2.修改
        crawlerSource.setBkey(jsObject.getString("bkey"));
        crawlerSource.setBname(jsObject.getString("bname"));
        crawlerSource.setHomepage(jsObject.getString("homepage"));
        crawlerSource.setLogo(jsObject.getString("logo"));
        crawlerSource.setRemark(jsObject.getString("remark"));

        try {
            crawlerSourceService.save(crawlerSource);
        } catch (Exception e) {
            logger.error(e.getMessage());
            DataResponse response = new DataResponse().failure("更新失败");
            return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
        }

        DataResponse response = new DataResponse().success("更新成功");
        return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
    }

    /**
     * 删除CrawlerSource
     * @param bid 源id
     * @return 成功/失败信息
     */
    @RequestMapping(value="/{bid}",method=RequestMethod.DELETE)
    public ResponseEntity<DataResponse> delete(@PathVariable("bid") String bid) {
        //记录日志
        logger.info("value=/{bid},method=RequestMethod.DELETE: "
                + "bid: " + bid);

        try {
            crawlerSourceService.deleteById(bid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            DataResponse response = new DataResponse().failure("删除失败");
            return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
        }

        DataResponse response = new DataResponse().success("删除成功");
        return new ResponseEntity<DataResponse>(response, HttpStatus.OK);
    }
}