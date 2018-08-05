package com.jishitoutiao.service;

import com.jishitoutiao.dao.InformationOutputListRepository;
import com.jishitoutiao.domain.CrawlerCategory;
import com.jishitoutiao.domain.CrawlerManagement;
import com.jishitoutiao.domain.CrawlerSource;
import com.jishitoutiao.domain.InformationOutputList;
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
public class InformationOutputListService {
    // ehcache的name
    private final String CACHE_NAME = "entityCache";

    @Resource
    private InformationOutputListRepository informationOutputListRepository;

    // 返回的list数据
    private List<InformationOutputList> list = new ArrayList<InformationOutputList>();

    // 保存数据:cache key里的单引号不能少，否则会报错，被识别是一个对象,下同
    /**
     *新增/更新数据
     * @param informationOutputList 数据实体
     */
    @Transactional
    @CacheEvict(key="'informationOutputList'+#informationOutputList.outputListId", value=CACHE_NAME)
    public void save(InformationOutputList informationOutputList) {
        informationOutputListRepository.save(informationOutputList);
    }

    /**
     *删除数据1：通过实体
     * @param informationOutputList 数据实体
     */
    @Transactional
    @CacheEvict(key="'informationOutputList'+#informationOutputList.outputListId", value=CACHE_NAME)
    public void delete(InformationOutputList informationOutputList) {
        informationOutputListRepository.delete(informationOutputList);
    }

    /**
     *删除数据2：通过id
     * @param outputListId id主键
     */
    @Transactional
    @CacheEvict(key="'informationOutputList'+#outputListId", value=CACHE_NAME)
    public void deleteById(String outputListId) {
        informationOutputListRepository.deleteById(outputListId);
    }

    /**
     *查询单条数据
     * @param outputListId id主键
     */
    @Cacheable(key = "'informationOutputList'+#outputListId",value=CACHE_NAME)
    public InformationOutputList find(String outputListId) {
        return informationOutputListRepository.findById(outputListId).get();
    }

    /**
     *查询记录数
     * @param keyword 搜索关键字
     */
    public Long getCount(String keyword,String bkey,String ckey) {
        return informationOutputListRepository.count(getWhereClause(keyword, bkey, ckey));
    }

    /**
     *查询全部数据(包含有搜索条件的)
     * @param keyword 搜索关键字
     * @param bkey 源key
     * @param ckey 类别key
     */
    public Iterable<InformationOutputList> getAll(String keyword,String bkey,String ckey) {
        // 根据最后更新时间倒叙排列
        Sort sort = new Sort(Sort.Direction.DESC, "lastUpdate");

        // 动态查询
        list = informationOutputListRepository.findAll(getWhereClause(keyword, bkey, ckey), sort);

        return list;
    }

    /**
     *查询指定页码的全部数据(包含有搜索条件的)
     * @param keyword 搜索关键字
     * @param bkey 源key
     * @param ckey 类别key
     * @param pageNum 页码
     */
    public PageObj<InformationOutputList> getPageData(String keyword,String bkey,String ckey, String pageNum) {
        // 1.获取总记录数
        int totalRecord = Math.toIntExact(getCount(keyword, bkey, ckey));

        // 2.创建需返回给客户端的页面数据对象
        PageObj<InformationOutputList> pageObj = null;

        if (pageNum == null || "undefined".equals(pageNum) || "".equals(pageNum)) {
            //如果页码是空的，返回第一页数据
            pageObj = new PageObj<InformationOutputList>(1, totalRecord);
        } else {
            //如果页码不为空，则返回指定页数据
            pageObj = new PageObj<InformationOutputList>(Integer.parseInt(pageNum), totalRecord);
        }

        // 3.创建查询所需的PageRequest对象
        Pageable pageable = PageRequest.of(pageObj.getStartIndex(), pageObj.getPageSize(), new Sort(Sort.Direction.DESC, "lastUpdate"));

        // 4.查询数据并将list赋值给页面数据对象
        Page<InformationOutputList> allInformationOutputList = informationOutputListRepository.findAll(getWhereClause(keyword, bkey, ckey), pageable);
        pageObj.setList(allInformationOutputList.getContent());

        return pageObj;
    }

    /**
     * 清空输出列表
     */
    public void cleanOutputList(){
        informationOutputListRepository.deleteAllInBatch();
    }

    /**
     * 动态生成where语句
     * @param keyword 查询关键字
     * @param bkey 源key
     * @param ckey 类别key
     * @return
     */
    private Specification<InformationOutputList> getWhereClause(String keyword, String bkey, String ckey) {
        return new Specification<InformationOutputList>() {
            @Override
            public Predicate toPredicate(Root<InformationOutputList> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                + "FROM "
//                + "InformationOutputList iol "
//                + "JOIN "
//                + "ioa.crawlerSource cs "
//                + "JOIN "
//                + "ioa.crawlerCategory cc "
//                + "JOIN "
//                + "ioa.crawlerManagement cm "
                Join<InformationOutputList, CrawlerSource> joinListSource = root.join("crawlerSource", JoinType.INNER);
                Join<InformationOutputList, CrawlerCategory> joinListCategory = root.join("crawlerCategory", JoinType.INNER);
                Join<InformationOutputList, CrawlerManagement> joinListManagement = root.join("crawlerManagement", JoinType.INNER);

                List<Predicate> predicates = null;
                Predicate keywordPredicate = null;

                if (keyword != null && !"".equals(keyword) && !"undefined".equals(keyword)) {
                    predicates = new ArrayList<Predicate>();

                    // WHERE iol.informationId=:informationId OR cm.crawlerName LIKE :crawlerName OR iol.title LIKE :title
                    predicates.add(criteriaBuilder.like(root.get("informationId"), "%" + keyword + "%"));
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + keyword + "%"));

                    Path<String> crawlerNameExp = joinListManagement.get("crawlerName");
                    predicates.add(criteriaBuilder.like(crawlerNameExp, "%" + keyword + "%"));

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
                    Path<String> bkeyExp = joinListSource.get("bkey");
                    predicates.add(criteriaBuilder.equal(bkeyExp, bkey));
                }

                if (ckey != null && ckey != "") {
                    // "AND cc.ckey=:ckey "
                    Path<String> ckeyExp = joinListCategory.get("ckey");
                    predicates.add(criteriaBuilder.equal(ckeyExp, ckey));
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