package com.jishitoutiao.rely;

import java.util.Map;

/**
 * 定义一个HQL类，Service层进行拼接HQL时，进行语句和参数类型返回
 * @author leitianxiang
 *
 */
public class QueryHqlObject {
	public String queryHql;		//查询语句
	public Map<String, Object> paramsMap;		//查询参数
}
