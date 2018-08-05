package com.jishitoutiao.service;

import com.jishitoutiao.dao.MenuGroupRepository;
import com.jishitoutiao.domain.MenuGroup;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
public class MenuGroupService {
    // ehcache的name
    private final String CACHE_NAME = "entityCache";

    @Resource
    private MenuGroupRepository menuGroupRepository;

    // 返回的list数据
    private List<MenuGroup> list = new ArrayList<MenuGroup>();

    // 保存数据:cache key里的单引号不能少，否则会报错，被识别是一个对象,下同
    /**
     *新增/更新数据
     * @param menuGroup 数据实体
     */
    @Transactional
    @CacheEvict(key="'menuGroup_'+#menuGroup.groupId", value=CACHE_NAME)
    public void save(MenuGroup menuGroup) {
        menuGroupRepository.save(menuGroup);
    }

    /**
     *删除数据1：通过实体
     * @param menuGroup 数据实体
     */
    @Transactional
    @CacheEvict(key="'menuGroup_'+#menuGroup.groupId", value=CACHE_NAME)
    public void delete(MenuGroup menuGroup) {
        menuGroupRepository.delete(menuGroup);
    }

    /**
     *删除数据2：通过id
     * @param groupId id主键
     */
    @Transactional
    @CacheEvict(key="'menuGroup_'+#groupId", value=CACHE_NAME)
    public void deleteById(String groupId) {
        menuGroupRepository.deleteById(groupId);
    }

    /**
     *查询单条数据
     * @param groupId id主键
     */
    @Cacheable(key = "'menuGroup_'+#groupId",value=CACHE_NAME)
    public MenuGroup find(String groupId) {
        return menuGroupRepository.findById(groupId).get();
    }

    /**
     *查询记录数
     * @param keyword 搜索关键字
     */
    public Long getCount(String keyword) {
        return menuGroupRepository.count(getWhereClause(keyword));
    }

    /**
     *查询全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     */
    public Iterable<MenuGroup> getAll(String keyword) {
        // 根据最后更新时间倒叙排列
        Sort sort = new Sort(Sort.Direction.DESC, "groupIndex");

        // 动态查询
        list = menuGroupRepository.findAll(getWhereClause(keyword), sort);

        return list;
    }

    /**
     * 动态生成where语句
     * @param keyword 查询关键字
     * @return
     */
    private Specification<MenuGroup> getWhereClause(String keyword) {
        return new Specification<MenuGroup>() {
            @Override
            public Predicate toPredicate(Root<MenuGroup> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                + "FROM "
//                + "MenuGroup ""
                if (keyword != null && !"".equals(keyword) && !"undefined".equals(keyword)) {
                    // WHERE groupKey LIKE :groupKey OR groupName LIKE :groupName
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    predicates.add(criteriaBuilder.like(root.get("groupKey"), "%" + keyword + "%"));
                    predicates.add(criteriaBuilder.like(root.get("groupName"), "%" + keyword + "%"));

                    // 两个查询条件用or连接
                   return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                }
                return null;
            }
        };
    }
}