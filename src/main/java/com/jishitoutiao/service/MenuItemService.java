package com.jishitoutiao.service;

import com.jishitoutiao.dao.MenuItemRepository;
import com.jishitoutiao.domain.MenuItem;
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
public class MenuItemService {
    // ehcache的name
    private final String CACHE_NAME = "entityCache";

    @Resource
    private MenuItemRepository menuItemRepository;

    // 返回的list数据
    private List<MenuItem> list = new ArrayList<MenuItem>();

    // 保存数据:cache key里的单引号不能少，否则会报错，被识别是一个对象,下同
    /**
     *新增/更新数据
     * @param menuItem 数据实体
     */
    @Transactional
    @CacheEvict(key="'menuItem_'+#menuItem.itemId", value=CACHE_NAME)
    public void save(MenuItem menuItem) {
        menuItemRepository.save(menuItem);
    }

    /**
     *删除数据1：通过实体
     * @param menuItem 数据实体
     */
    @Transactional
    @CacheEvict(key="'menuItem_'+#menuItem.itemId", value=CACHE_NAME)
    public void delete(MenuItem menuItem) {
        menuItemRepository.delete(menuItem);
    }

    /**
     *删除数据2：通过id
     * @param itemId id主键
     */
    @Transactional
    @CacheEvict(key="'menuItem_'+#itemId", value=CACHE_NAME)
    public void deleteById(String itemId) {
        menuItemRepository.deleteById(itemId);
    }

    /**
     *查询单条数据
     * @param itemId id主键
     */
    @Cacheable(key = "'menuItem_'+#itemId",value=CACHE_NAME)
    public MenuItem find(String itemId) {
        return menuItemRepository.findById(itemId).get();
    }

    /**
     *查询记录数
     * @param keyword 搜索关键字
     */
    public Long getCount(String keyword) {
        return menuItemRepository.count(getWhereClause(keyword));
    }

    /**
     *查询全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     */
    public Iterable<MenuItem> getAll(String keyword) {
        // 根据最后更新时间倒叙排列
        Sort sort = new Sort(Sort.Direction.DESC, "itemIndex");

        // 动态查询
        list = menuItemRepository.findAll(getWhereClause(keyword), sort);

        return list;
    }

    /**
     * 动态生成where语句
     * @param keyword 查询关键字
     * @return
     */
    private Specification<MenuItem> getWhereClause(String keyword) {
        return new Specification<MenuItem>() {
            @Override
            public Predicate toPredicate(Root<MenuItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                + "FROM "
//                + "MenuItem ""
                if (keyword != null && !"".equals(keyword) && !"undefined".equals(keyword)) {
                    // WHERE itemKey LIKE :itemKey OR itemName LIKE :itemName
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    predicates.add(criteriaBuilder.like(root.get("itemKey"), "%" + keyword + "%"));
                    predicates.add(criteriaBuilder.like(root.get("itemName"), "%" + keyword + "%"));

                    // 两个查询条件用or连接
                   return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                }
                return null;
            }
        };
    }
}