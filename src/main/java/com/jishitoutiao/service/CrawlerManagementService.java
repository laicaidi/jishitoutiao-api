package com.jishitoutiao.service;

import com.jishitoutiao.dao.CrawlerManagementRepository;
import com.jishitoutiao.domain.CrawlerCategory;
import com.jishitoutiao.domain.CrawlerManagement;
import com.jishitoutiao.domain.CrawlerSource;
import com.jishitoutiao.rely.CrawlerStatus;
import com.jishitoutiao.rely.CrawlerSwitch;
import com.jishitoutiao.rely.PageObj;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * save,update,delete操作需要绑定事务
 * 使用@Transactional进行事物绑定
 */
@Service
public class CrawlerManagementService {
    // ehcache的name
    private final String CACHE_NAME = "entityCache";

    @Resource
    private CrawlerManagementRepository crawlerManagementRepository;

    // 返回的list数据
    private List<CrawlerManagement> list = new ArrayList<CrawlerManagement>();

    // 保存数据:cache key里的单引号不能少，否则会报错，被识别是一个对象,下同
    /**
     *新增/更新数据
     * @param crawlerManagement 数据实体
     */
    @Transactional
    @CacheEvict(key="'crawlerManagement'+#crawlerManagement.crawlerId", value=CACHE_NAME)
    public void save(CrawlerManagement crawlerManagement) {
        crawlerManagementRepository.save(crawlerManagement);
    }

    /**
     *删除数据1：通过实体
     * @param crawlerManagement 数据实体
     */
    @Transactional
    @CacheEvict(key="'crawlerManagement'+#crawlerManagement.crawlerId", value=CACHE_NAME)
    public void delete(CrawlerManagement crawlerManagement) {
        crawlerManagementRepository.delete(crawlerManagement);
    }

    /**
     *删除数据2：通过id
     * @param crawlerId id主键
     */
    @Transactional
    @CacheEvict(key="'crawlerManagement'+#crawlerId", value=CACHE_NAME)
    public void deleteById(String crawlerId) {
        crawlerManagementRepository.deleteById(crawlerId);
    }

    /**
     *查询单条数据
     * @param crawlerId id主键
     */
    @Cacheable(key = "'crawlerManagement'+#crawlerId",value=CACHE_NAME)
    public CrawlerManagement find(String crawlerId) {
        return crawlerManagementRepository.findById(crawlerId).get();
    }

    /**
     *查询记录数
     * @param keyword 搜索关键字
     * @param bkey 源key
     * @param ckey 类别key
     * @param crawlerStatus 爬虫状态
     * @param crawlerSwitch 爬虫开关
     */
    public Long getCount(String keyword,String bkey,String ckey,String crawlerStatus, String crawlerSwitch) {
        return crawlerManagementRepository.count(getWhereClause(keyword, bkey, ckey, crawlerStatus, crawlerSwitch));
    }

    /**
     *查询全部数据(包含有搜索条件的)
     * @param keyword 搜索关键字
     * @param bkey 源key
     * @param ckey 类别key
     * @param crawlerStatus 爬虫状态
     * @param crawlerSwitch 爬虫开关
     */
    public Iterable<CrawlerManagement> getAll(String keyword,String bkey,String ckey,String crawlerStatus, String crawlerSwitch) {
        // 根据最后更新时间倒叙排列
        Sort sort = new Sort(Sort.Direction.DESC, "lastUpdate");

        // 动态查询
        list = crawlerManagementRepository.findAll(getWhereClause(keyword, bkey, ckey, crawlerStatus, crawlerSwitch), sort);

        return list;
    }

    /**
     *查询指定页码的全部数据(包含有搜索条件的)
     * @param keyword 搜索关键字
     * @param bkey 源key
     * @param ckey 类别key
     * @param crawlerStatus 爬虫状态
     * @param crawlerSwitch 爬虫开关
     * @param pageNum 页码
     */
    public PageObj<CrawlerManagement> getPageData(String keyword,String bkey,String ckey,String crawlerStatus, String crawlerSwitch, String pageNum) {
        // 1.获取总记录数
        int totalRecord = Math.toIntExact(getCount(keyword, bkey, ckey, crawlerStatus, crawlerSwitch));

        // 2.创建需返回给客户端的页面数据对象
        PageObj<CrawlerManagement> pageObj = null;

        if (pageNum == null || "undefined".equals(pageNum) || "".equals(pageNum)) {
            //如果页码是空的，返回第一页数据
            pageObj = new PageObj<CrawlerManagement>(1, totalRecord);
        } else {
            //如果页码不为空，则返回指定页数据
            pageObj = new PageObj<CrawlerManagement>(Integer.parseInt(pageNum), totalRecord);
        }

        // 3.创建查询所需的PageRequest对象
        Pageable pageable = PageRequest.of(pageObj.getStartIndex(), pageObj.getPageSize(), new Sort(Sort.Direction.DESC, "lastUpdate"));

        // 4.查询数据并将list赋值给页面数据对象
        Page<CrawlerManagement> allCrawlerManagement = crawlerManagementRepository.findAll(getWhereClause(keyword, bkey, ckey, crawlerStatus, crawlerSwitch), pageable);
        pageObj.setList(allCrawlerManagement.getContent());

        return pageObj;
    }

    /**
     * 动态生成where语句
     * @param keyword 查询关键字
     * @param bkey 源key
     * @param ckey 类别key
     * @param crawlerStatus 爬虫状态
     * @param crawlerSwitch 爬虫开关
     * @return
     */
    private Specification<CrawlerManagement> getWhereClause(String keyword, String bkey, String ckey, String crawlerStatus, String crawlerSwitch) {
        return new Specification<CrawlerManagement>() {
            @Override
            public Predicate toPredicate(Root<CrawlerManagement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // "FROM "
                // + "CrawlerManagement cm "
                // + "JOIN "
                // + "cm.crawlerSource cs "
                // + "JOIN "
                // + "cm.crawlerCategory cc "
                Join<CrawlerManagement, CrawlerSource> joinManageSource = root.join("crawlerSource", JoinType.INNER);
                Join<CrawlerManagement, CrawlerCategory> joinManageCategory = root.join("crawlerCategory", JoinType.INNER);

                List<Predicate> predicates = null;
                Predicate keywordPredicate = null;

                if (keyword != null && !"".equals(keyword) && !"undefined".equals(keyword)) {
                    predicates = new ArrayList<Predicate>();

                    // WHERE cs.bname LIKE :bname OR cm.sld LIKE :sld OR cc.cname LIKE :cname OR cm.crawlerName LIKE :crawlerName
                    Path<String> bnameExp = joinManageSource.get("bname");
                    predicates.add(criteriaBuilder.like(bnameExp, "%" + keyword + "%"));

                    Path<String> cnameExp = joinManageCategory.get("cname");
                    predicates.add(criteriaBuilder.like(cnameExp, "%" + keyword + "%"));

                    predicates.add(criteriaBuilder.like(root.get("sld"), "%" + keyword + "%"));
                    predicates.add(criteriaBuilder.like(root.get("crawlerName"), "%" + keyword + "%"));

                    // 查询条件用or连接
                    keywordPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                }

                // 重新初始化Predicate集合，存储其他所有条件
                predicates = new ArrayList<Predicate>();
                if (keywordPredicate != null) {     // 如果有搜索条件，默认先加到集合首位
                    predicates.add(keywordPredicate);
                }

                if (bkey != null && bkey != "") {
                    // "AND cs.bkey=:bkey "
                    Path<String> bkeyExp = joinManageSource.get("bkey");
                    predicates.add(criteriaBuilder.equal(bkeyExp, bkey));
                }

                if (ckey != null && ckey != "") {
                    // "AND cc.ckey=:ckey "
                    Path<String> ckeyExp = joinManageCategory.get("ckey");
                    predicates.add(criteriaBuilder.equal(ckeyExp, ckey));
                }

                if (crawlerStatus != null && crawlerStatus != "") {
                    // "AND crawler_status=:cawlerStatus "
                    predicates.add(criteriaBuilder.equal(root.get("crawlerStatus"), CrawlerStatus.valueOf(crawlerStatus)));
                }

                if (crawlerSwitch != null && crawlerSwitch != "") {
                    // "AND crawler_switch=:crawlerSwitch "
                    predicates.add(criteriaBuilder.equal(root.get("crawlerSwitch"), CrawlerSwitch.valueOf(crawlerSwitch)));
                }

                // 新的查询条件用and连接
                if (predicates.size() != 0) {
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
                return null;
            }
        };
    }
}