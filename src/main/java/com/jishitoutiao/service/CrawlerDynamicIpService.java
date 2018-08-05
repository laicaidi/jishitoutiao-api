package com.jishitoutiao.service;

import com.jishitoutiao.dao.CrawlerDynamicIpRepository;
import com.jishitoutiao.domain.CrawlerDynamicIp;
import com.jishitoutiao.rely.PageObj;
import com.jishitoutiao.rely.Protocol;
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
public class CrawlerDynamicIpService {
    // ehcache的name
    private final String CACHE_NAME = "entityCache";

    @Resource
    private CrawlerDynamicIpRepository crawlerDynamicIpRepository;

    // 返回的list数据
    private List<CrawlerDynamicIp> list = new ArrayList<CrawlerDynamicIp>();

    // 保存数据:cache key里的单引号不能少，否则会报错，被识别是一个对象,下同
    /**
     *新增/更新数据
     * @param crawlerDynamicIp 数据实体
     */
    @Transactional
    @CacheEvict(key="'crawlerDynamicIp'+#crawlerDynamicIp.dynamicId", value=CACHE_NAME)
    public void save(CrawlerDynamicIp crawlerDynamicIp) {
        crawlerDynamicIpRepository.save(crawlerDynamicIp);
    }

    /**
     *删除数据1：通过实体
     * @param crawlerDynamicIp 数据实体
     */
    @Transactional
    @CacheEvict(key="'crawlerDynamicIp'+#crawlerDynamicIp.dynamicId", value=CACHE_NAME)
    public void delete(CrawlerDynamicIp crawlerDynamicIp) {
        crawlerDynamicIpRepository.delete(crawlerDynamicIp);
    }

    /**
     *删除数据2：通过id
     * @param id id主键
     */
    @Transactional
    @CacheEvict(key="'crawlerDynamicIp'+#id", value=CACHE_NAME)
    public void deleteById(String id) {
        crawlerDynamicIpRepository.deleteById(id);
    }

    /**
     *查询单条数据
     * @param id id主键
     */
    @Cacheable(key = "'crawlerDynamicIp'+#id",value=CACHE_NAME)
    public CrawlerDynamicIp find(String id) {
        return crawlerDynamicIpRepository.findById(id).get();
    }

    /**
     *查询记录数
     * @param keyword 搜索关键字
     */
    public Long getCount(String keyword, String protocol) {
        return crawlerDynamicIpRepository.count(getWhereClause(keyword, protocol));
    }

    /**
     *查询全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     */
    public Iterable<CrawlerDynamicIp> getAll(String keyword, String protocol) {
        // 根据最后更新时间倒叙排列
        Sort sort = new Sort(Sort.Direction.DESC, "lastUpdate");

        // 动态查询
        list = crawlerDynamicIpRepository.findAll(getWhereClause(keyword, protocol), sort);

        return list;
    }

    /**
     *查询指定页码的全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     * @param pageNum 页码
     */
    public PageObj<CrawlerDynamicIp> getPageData(String keyword, String protocol, String pageNum) {
        // 1.获取总记录数
        int totalRecord = Math.toIntExact(getCount(keyword, protocol));

        // 2.创建需返回给客户端的页面数据对象
        PageObj<CrawlerDynamicIp> pageObj = null;

        if (pageNum == null || "undefined".equals(pageNum) || "".equals(pageNum)) {
            //如果页码是空的，返回第一页数据
            pageObj = new PageObj<CrawlerDynamicIp>(1, totalRecord);
        } else {
            //如果页码不为空，则返回指定页数据
            pageObj = new PageObj<CrawlerDynamicIp>(Integer.parseInt(pageNum), totalRecord);
        }

        // 3.创建查询所需的PageRequest对象
        Pageable pageable = PageRequest.of(pageObj.getStartIndex(), pageObj.getPageSize(), new Sort(Sort.Direction.DESC, "lastUpdate"));

        // 4.查询数据并将list赋值给页面数据对象
        Page<CrawlerDynamicIp> allCrawlerCategory = crawlerDynamicIpRepository.findAll(getWhereClause(keyword, protocol), pageable);
        pageObj.setList(allCrawlerCategory.getContent());

        return pageObj;
    }

    /**
     * 动态生成where语句
     * @param keyword 查询关键字
     * @return
     */
    private Specification<CrawlerDynamicIp> getWhereClause(String keyword, String protocol) {
        return new Specification<CrawlerDynamicIp>() {
            @Override
            public Predicate toPredicate(Root<CrawlerDynamicIp> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (keyword != null && !"".equals(keyword) && !"undefined".equals(keyword)) {
                    // 1.搜索条件
                    predicates.add(criteriaBuilder.like(root.get("serverAddress"), "%" + keyword + "%"));
                }
                if (protocol != null && protocol != "") {     // 2.协议条件
                    predicates.add(criteriaBuilder.equal(root.get("protocol"), Protocol.valueOf(protocol)));
                }
                // 2.两个查询条件用and连接
                if (predicates.size() != 0) {
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
                return null;
            }
        };
    }
}