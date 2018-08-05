package com.jishitoutiao.service;

import com.jishitoutiao.dao.InformationRepetitionResultRepository;
import com.jishitoutiao.domain.CrawlerCategory;
import com.jishitoutiao.domain.CrawlerManagement;
import com.jishitoutiao.domain.CrawlerSource;
import com.jishitoutiao.domain.InformationRepetitionResult;
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
public class InformationRepetitionResultService {
    // ehcache的name
    private final String CACHE_NAME = "entityCache";

    @Resource
    private InformationRepetitionResultRepository informationRepetitionResultRepository;

    // 返回的list数据
    private List<InformationRepetitionResult> list = new ArrayList<InformationRepetitionResult>();

    // 保存数据:cache key里的单引号不能少，否则会报错，被识别是一个对象,下同
    /**
     *新增/更新数据
     * @param informationRepetitionResult 数据实体
     */
    @Transactional
    @CacheEvict(key="'informationRepetitionResult'+#informationRepetitionResult.repetitionResultId", value=CACHE_NAME)
    public void save(InformationRepetitionResult informationRepetitionResult) {
        informationRepetitionResultRepository.save(informationRepetitionResult);
    }

    /**
     *删除数据1：通过实体
     * @param informationRepetitionResult 数据实体
     */
    @Transactional
    @CacheEvict(key="'informationRepetitionResult'+#informationRepetitionResult.repetitionResultId", value=CACHE_NAME)
    public void delete(InformationRepetitionResult informationRepetitionResult) {
        informationRepetitionResultRepository.delete(informationRepetitionResult);
    }

    /**
     *删除数据2：通过id
     * @param repetitionResultId id主键
     */
    @Transactional
    @CacheEvict(key="'informationRepetitionResult'+#repetitionResultId", value=CACHE_NAME)
    public void deleteById(String repetitionResultId) {
        informationRepetitionResultRepository.deleteById(repetitionResultId);
    }

    /**
     *查询单条数据
     * @param repetitionResultId id主键
     */
    @Cacheable(key = "'informationRepetitionResult'+#repetitionResultId",value=CACHE_NAME)
    public InformationRepetitionResult find(String repetitionResultId) {
        return informationRepetitionResultRepository.findById(repetitionResultId).get();
    }

    /**
     *查询记录数
     * @param keyword 搜索关键字
     */
    public Long getCount(String keyword,String bkey,String ckey) {
        return informationRepetitionResultRepository.count(getWhereClause(keyword, bkey, ckey));
    }

    /**
     *查询全部数据(包含有搜索条件的)
     * @param keyword 搜索关键字
     * @param bkey 源key
     * @param ckey 类别key
     */
    public Iterable<InformationRepetitionResult> getAll(String keyword,String bkey,String ckey) {
        // 根据最后更新时间倒叙排列
        Sort sort = new Sort(Sort.Direction.DESC, "lastUpdate");

        // 动态查询
        list = informationRepetitionResultRepository.findAll(getWhereClause(keyword, bkey, ckey), sort);

        return list;
    }

    /**
     *查询指定页码的全部数据(包含有搜索条件的)
     * @param keyword 搜索关键字
     * @param bkey 源key
     * @param ckey 类别key
     * @param pageNum 页码
     */
    public PageObj<InformationRepetitionResult> getPageData(String keyword,String bkey,String ckey, String pageNum) {
        // 1.获取总记录数
        int totalRecord = Math.toIntExact(getCount(keyword, bkey, ckey));

        // 2.创建需返回给客户端的页面数据对象
        PageObj<InformationRepetitionResult> pageObj = null;

        if (pageNum == null || "undefined".equals(pageNum) || "".equals(pageNum)) {
            //如果页码是空的，返回第一页数据
            pageObj = new PageObj<InformationRepetitionResult>(1, totalRecord);
        } else {
            //如果页码不为空，则返回指定页数据
            pageObj = new PageObj<InformationRepetitionResult>(Integer.parseInt(pageNum), totalRecord);
        }

        // 3.创建查询所需的PageRequest对象
        Pageable pageable = PageRequest.of(pageObj.getStartIndex(), pageObj.getPageSize(), new Sort(Sort.Direction.DESC, "lastUpdate"));

        // 4.查询数据并将list赋值给页面数据对象
        Page<InformationRepetitionResult> allInformationRepetitionResult = informationRepetitionResultRepository.findAll(getWhereClause(keyword, bkey, ckey), pageable);
        pageObj.setList(allInformationRepetitionResult.getContent());

        return pageObj;
    }

    /**
     * 清空滤重结果
     */
    public void cleanRepetitionResult(){
        informationRepetitionResultRepository.deleteAllInBatch();
    }

    /**
     * 动态生成where语句
     * @param keyword 查询关键字
     * @param bkey 源key
     * @param ckey 类别key
     * @return
     */
    private Specification<InformationRepetitionResult> getWhereClause(String keyword, String bkey, String ckey) {
        return new Specification<InformationRepetitionResult>() {
            @Override
            public Predicate toPredicate(Root<InformationRepetitionResult> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                + "FROM "
//                + "InformationRepetitionResult irr "
//                + "JOIN "
//                + "iip.crawlerSource cs "
//                + "JOIN "
//                + "iip.crawlerCategory cc "
//                + "JOIN "
//                + "iip.crawlerManagement cm "
                Join<InformationRepetitionResult, CrawlerSource> joinResultSource = root.join("crawlerSource", JoinType.INNER);
                Join<InformationRepetitionResult, CrawlerCategory> joinResultCategory = root.join("crawlerCategory", JoinType.INNER);
                Join<InformationRepetitionResult, CrawlerManagement> joinResultManagement = root.join("crawlerManagement", JoinType.INNER);

                List<Predicate> predicates = null;
                Predicate keywordPredicate = null;

                if (keyword != null && !"".equals(keyword) && !"undefined".equals(keyword)) {
                    predicates = new ArrayList<Predicate>();

                    // WHERE irr.informationId=:informationId OR irr.repetitionNum=:repetitionNum OR cm.crawlerName LIKE :crawlerName OR irr.title LIKE :title
                    predicates.add(criteriaBuilder.like(root.get("informationId"), "%" + keyword + "%"));
                    predicates.add(criteriaBuilder.like(root.get("repetitionNum"), "%" + keyword + "%"));
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + keyword + "%"));

                    Path<String> crawlerNameExp = joinResultManagement.get("crawlerName");
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
                    Path<String> bkeyExp = joinResultSource.get("bkey");
                    predicates.add(criteriaBuilder.equal(bkeyExp, bkey));
                }

                if (ckey != null && ckey != "") {
                    // "AND cc.ckey=:ckey "
                    Path<String> ckeyExp = joinResultCategory.get("ckey");
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