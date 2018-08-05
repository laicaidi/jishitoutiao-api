package com.jishitoutiao.service;

import com.jishitoutiao.dao.CrawlerUserAgentRepository;
import com.jishitoutiao.domain.CrawlerUserAgent;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * save,update,delete操作需要绑定事务
 * 使用@Transactional进行事物绑定
 */
@Service
public class CrawlerUserAgentService {
    // ehcache的name
    private final String CACHE_NAME = "entityCache";

    @Resource
    private CrawlerUserAgentRepository crawlerUserAgentRepository;

    // 返回的list数据
    private List<CrawlerUserAgent> list = new ArrayList<CrawlerUserAgent>();

    // 保存数据:cache key里的单引号不能少，否则会报错，被识别是一个对象,下同
    /**
     *新增/更新数据
     * @param crawlerUserAgent 数据实体
     */
    @Transactional
    @CacheEvict(key="'crawlerUserAgent_'+#crawlerUserAgent.userAgentId", value=CACHE_NAME)
    public void save(CrawlerUserAgent crawlerUserAgent) {
        crawlerUserAgentRepository.save(crawlerUserAgent);
    }

    /**
     *删除数据1：通过实体
     * @param crawlerUserAgent 数据实体
     */
    @Transactional
    @CacheEvict(key="'crawlerUserAgent_'+#crawlerUserAgent.userAgentId", value=CACHE_NAME)
    public void delete(CrawlerUserAgent crawlerUserAgent) {
        crawlerUserAgentRepository.delete(crawlerUserAgent);
    }

    /**
     *删除数据2：通过id
     * @param userAgentId id主键
     */
    @Transactional
    @CacheEvict(key="'crawlerUserAgent_'+#userAgentId", value=CACHE_NAME)
    public void deleteById(String userAgentId) {
        crawlerUserAgentRepository.deleteById(userAgentId);
    }

    /**
     *查询单条数据
     * @param userAgentId id主键
     */
    @Cacheable(key = "'crawlerUserAgent_'+#userAgentId",value=CACHE_NAME)
    public CrawlerUserAgent find(String userAgentId) {
        return crawlerUserAgentRepository.findById(userAgentId).get();
    }

    /**
     *查询记录数
     * @param keyword 搜索关键字
     */
    public Long getCount(String keyword) {
        return crawlerUserAgentRepository.count(getWhereClause(keyword));
    }

    /**
     *查询全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     */
    public Iterable<CrawlerUserAgent> getAll(String keyword) {
        // 根据最后更新时间倒叙排列
        Sort sort = new Sort(Sort.Direction.DESC, "lastUpdate");

        // 动态查询
        list = crawlerUserAgentRepository.findAll(getWhereClause(keyword), sort);

        return list;
    }

    /**
     *查询指定页码的全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     * @param pageNum 页码
     */
    public PageObj<CrawlerUserAgent> getPageData(String keyword, String pageNum) {
        // 1.获取总记录数
        int totalRecord = Math.toIntExact(getCount(keyword));

        // 2.创建需返回给客户端的页面数据对象
        PageObj<CrawlerUserAgent> pageObj = null;

        if (pageNum == null || "undefined".equals(pageNum) || "".equals(pageNum)) {
            //如果页码是空的，返回第一页数据
            pageObj = new PageObj<CrawlerUserAgent>(1, totalRecord);
        } else {
            //如果页码不为空，则返回指定页数据
            pageObj = new PageObj<CrawlerUserAgent>(Integer.parseInt(pageNum), totalRecord);
        }

        // 3.创建查询所需的PageRequest对象
        Pageable pageable = PageRequest.of(pageObj.getStartIndex(), pageObj.getPageSize(), new Sort(Sort.Direction.DESC, "lastUpdate"));

        // 4.查询数据并将list赋值给页面数据对象
        Page<CrawlerUserAgent> allCrawlerUserAgent = crawlerUserAgentRepository.findAll(getWhereClause(keyword), pageable);
        pageObj.setList(allCrawlerUserAgent.getContent());

        return pageObj;
    }

    /**
     * 动态生成where语句
     * @param keyword 查询关键字
     * @return
     */
    private Specification<CrawlerUserAgent> getWhereClause(String keyword) {
        return new Specification<CrawlerUserAgent>() {
            @Override
            public Predicate toPredicate(Root<CrawlerUserAgent> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if (keyword != null && !"".equals(keyword) && !"undefined".equals(keyword)) {
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    // 1.创造查询条件对象并添加至条件集合
                    predicates.add(criteriaBuilder.like(root.get("userAgent"), "%" + keyword + "%"));
                    predicates.add(criteriaBuilder.like(root.get("remark"), "%" + keyword + "%"));

                    // 2.两个查询条件用or连接
                   return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                }
                return null;
            }
        };
    }
}