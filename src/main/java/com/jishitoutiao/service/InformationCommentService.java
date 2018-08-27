package com.jishitoutiao.service;

import com.jishitoutiao.dao.InformationCommentRepository;
import com.jishitoutiao.domain.*;
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
public class InformationCommentService {
    // ehcache的name
    private final String CACHE_NAME = "entityCache";

    @Resource
    private InformationCommentRepository informationCommentRepository;

    // 返回的list数据
    private List<InformationComment> list = new ArrayList<InformationComment>();

    // 保存数据:cache key里的单引号不能少，否则会报错，被识别是一个对象,下同
    /**
     *新增/更新数据
     * @param informationComment 数据实体
     */
    @Transactional
    @CacheEvict(key="'informationComment_'+#informationComment.commentId", value=CACHE_NAME)
    public void save(InformationComment informationComment) {
        informationCommentRepository.save(informationComment);
    }

    /**
     *删除数据1：通过实体
     * @param informationComment 数据实体
     */
    @Transactional
    @CacheEvict(key="'informationComment_'+#informationComment.commentId", value=CACHE_NAME)
    public void delete(InformationComment informationComment) {
        informationCommentRepository.delete(informationComment);
    }

    /**
     *删除数据2：通过id
     * @param commentId id主键
     */
    @Transactional
    @CacheEvict(key="'informationComment_'+#commentId", value=CACHE_NAME)
    public void deleteById(String commentId) {
        informationCommentRepository.deleteById(commentId);
    }

    /**
     *查询单条数据
     * @param commentId id主键
     */
    @Cacheable(key = "'informationComment_'+#commentId",value=CACHE_NAME)
    public InformationComment find(String commentId) {
        return informationCommentRepository.findById(commentId).get();
    }

    /**
     *查询记录数
     * @param keyword 搜索关键字
     */
    public Long getCount(String keyword) {
        return informationCommentRepository.count(getWhereClause(keyword));
    }

    /**
     *查询全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     */
    public Iterable<InformationComment> getAll(String keyword) {
        // 根据最后更新时间倒叙排列
        Sort sort = new Sort(Sort.Direction.DESC, "lastUpdate");

        // 动态查询
        list = informationCommentRepository.findAll(getWhereClause(keyword), sort);

        return list;
    }

    /**
     *查询指定页码的全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     * @param pageNum 页码
     */
    public PageObj<InformationComment> getPageData(String keyword, String pageNum) {
        // 1.获取总记录数
        int totalRecord = Math.toIntExact(getCount(keyword));

        // 2.创建需返回给客户端的页面数据对象
        PageObj<InformationComment> pageObj = null;

        if (pageNum == null || "undefined".equals(pageNum) || "".equals(pageNum)) {
            //如果页码是空的，返回第一页数据
            pageObj = new PageObj<InformationComment>(1, totalRecord);
        } else {
            //如果页码不为空，则返回指定页数据
            pageObj = new PageObj<InformationComment>(Integer.parseInt(pageNum), totalRecord);
        }

        // 3.创建查询所需的PageRequest对象,PageRequest的page从0开始算,所以需要-1
        Pageable pageable = PageRequest.of(pageObj.getPageNum() - 1, pageObj.getPageSize(), new Sort(Sort.Direction.DESC, "lastUpdate"));

        // 4.查询数据并将list赋值给页面数据对象
        Page<InformationComment> allInformationComment = informationCommentRepository.findAll(getWhereClause(keyword), pageable);
        pageObj.setList(allInformationComment.getContent());

        return pageObj;
    }

    /**
     * 清空评论表内容
     */
    public void cleanComment(){
        informationCommentRepository.deleteAllInBatch();
    }

    /**
     * 动态生成where语句
     * @param keyword 查询关键字
     * @return
     */
    private Specification<InformationComment> getWhereClause(String keyword) {
        return new Specification<InformationComment>() {
            @Override
            public Predicate toPredicate(Root<InformationComment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                + "FROM "
//                + "InformationComment ic "
//                + "JOIN "
//                + "ic.user us "
//                + "JOIN "
//                + "ic.informationOutputArticle ioa "
                Join<InformationComment, User> joinCommentUser = root.join("user", JoinType.INNER);
                Join<InformationComment, InformationOutputArticle> joinCommentArticle = root.join("informationOutputArticle", JoinType.INNER);
                if (keyword != null && !"".equals(keyword) && !"undefined".equals(keyword)) {
                    // WHERE us.userId LIKE :userId OR us.nickName LIKE :nickName OR ioa.informationId LIKE :informationId or ic.content LIKE :content "
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    Path<String> userIdExp = joinCommentUser.get("userId");
                    predicates.add(criteriaBuilder.like(userIdExp, "%" + keyword + "%"));

                    Path<String> nicknameExp = joinCommentUser.get("nickname");
                    predicates.add(criteriaBuilder.like(nicknameExp, "%" + keyword + "%"));

                    Path<String> informationIdExp = joinCommentArticle.get("informationId");
                    predicates.add(criteriaBuilder.like(informationIdExp, "%" + keyword + "%"));

                    predicates.add(criteriaBuilder.like(root.get("content"), "%" + keyword + "%"));

                    // 两个查询条件用or连接
                   return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                }
                return null;
            }
        };
    }
}