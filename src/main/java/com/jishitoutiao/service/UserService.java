package com.jishitoutiao.service;

import com.jishitoutiao.dao.UserRepository;
import com.jishitoutiao.domain.Role;
import com.jishitoutiao.domain.User;
import com.jishitoutiao.rely.PageObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
public class UserService{
    // ehcache的name
    private final String CACHE_NAME = "entityCache";

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserRepository userRepository;

    // 返回的list数据
    private List<User> list = new ArrayList<User>();

    // 保存数据:cache key里的单引号不能少，否则会报错，被识别是一个对象,下同
    /**
     *新增/更新数据
     * @param user 数据实体
     */
    @Transactional
    @CacheEvict(key="'user_'+#user.userId", value=CACHE_NAME)
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     *删除数据1：通过实体
     * @param user 数据实体
     */
    @Transactional
    @CacheEvict(key="'user_'+#user.userId", value=CACHE_NAME)
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     *删除数据2：通过id
     * @param userId id主键
     */
    @Transactional
    @CacheEvict(key="'user_'+#userId", value=CACHE_NAME)
    public void deleteById(String userId) {
        userRepository.deleteById(userId);
    }

    /**
     *查询单条数据
     * @param userId id主键
     */
    @Cacheable(key = "'user_'+#userId",value=CACHE_NAME)
    public User find(String userId) {
        return userRepository.findById(userId).get();
    }

    /**
     *查询记录数
     * @param keyword 搜索关键字
     */
    public Long getCount(String keyword) {
        return userRepository.count(getWhereClause(keyword));
    }

    /**
     *查询全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     */
    public Iterable<User> getAll(String keyword) {
        // 根据最后更新时间倒叙排列
        Sort sort = new Sort(Sort.Direction.DESC, "lastUpdate");

        // 动态查询
        list = userRepository.findAll(getWhereClause(keyword), sort);

        return list;
    }

    /**
     *查询指定页码的全部数据(包含有搜索关键字的)
     * @param keyword 搜索关键字
     * @param pageNum 页码
     */
    public PageObj<User> getPageData(String keyword, String pageNum) {
        // 1.获取总记录数
        int totalRecord = Math.toIntExact(getCount(keyword));

        // 2.创建需返回给客户端的页面数据对象
        PageObj<User> pageObj = null;

        if (pageNum == null || "undefined".equals(pageNum) || "".equals(pageNum)) {
            //如果页码是空的，返回第一页数据
            pageObj = new PageObj<User>(1, totalRecord);
        } else {
            //如果页码不为空，则返回指定页数据
            pageObj = new PageObj<User>(Integer.parseInt(pageNum), totalRecord);
        }

        // 3.创建查询所需的PageRequest对象,PageRequest的page从0开始算,所以需要-1
        Pageable pageable = PageRequest.of(pageObj.getPageNum() - 1, pageObj.getPageSize(), new Sort(Sort.Direction.DESC, "lastUpdate"));

        // 4.查询数据并将list赋值给页面数据对象
        Page<User> allUser = userRepository.findAll(getWhereClause(keyword), pageable);
        pageObj.setList(allUser.getContent());

        return pageObj;
    }

    /**
     * 登录服务(通过用户名密码)
     * @param username 用户名
     * @param phone 手机号
     * @param password 密码
     * @return
     */
    public User login(String username, String phone, String password) {
        User user = userRepository.findByUsernameOrPhone(username, phone);

        if (user == null) {     // 无此用户
            return null;
        }

        if (user.getPassword() == null || user.getPassword().length() == 0) {
            logger.warn("该用户: " +user.getUsername()  + ", 密码为空");
            return null;
        }

        boolean same = BCrypt.checkpw(password, user.getPassword());

        if (same) {     // 登录成功
            return user;
        } else {        // 密码不符
            return null;
        }
    }

    /**
     * 登录服务(通过手机号)
     * @param phone 手机号
     * @return
     */
    public User loginWithPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    /**
     * 动态生成where语句
     * @param keyword 查询关键字
     * @return
     */
    private Specification<User> getWhereClause(String keyword) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<User, Role> joinUserRole = root.join("roles", JoinType.INNER);

                if (keyword != null && !"".equals(keyword) && !"undefined".equals(keyword)) {
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    // 1.创造查询条件对象并添加至条件集合
                    Path<String> roleNameExp = joinUserRole.get("roleName");
                    predicates.add(criteriaBuilder.like(roleNameExp, "%" + keyword + "%"));

                    predicates.add(criteriaBuilder.like(root.get("username"), "%" + keyword + "%"));
                    predicates.add(criteriaBuilder.like(root.get("phone"), "%" + keyword + "%"));

                    // 2.两个查询条件用or连接
                   return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                }
                return null;
            }
        };
    }
}