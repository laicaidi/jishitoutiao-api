package com.jishitoutiao.rely;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 数据响应类
 * @author leitianxiang
 *
 *
 1.每个REST请求将返回相同结构的JSON响应结构。
 不妨定义一个相对通用的JSON响应结构，其中包含两部分：元数据与返回值，
 其中，元数据表示操作是否成功与返回值消息等，返回值对应服务端方法所返回的数据。
 该JSON响应结构如下：
 {
	"status": {
		"success": true,
		"message": "ok",
		"time": yyyy-mm-dd HH:mm:ss
	},
	"meta": ...,		// 代表初始化元数据，如菜单、下拉列表选项等数据
	"data": ...,	// 代表数据，真实的数据
	"redis_data":....		// redis缓存中的表状态(status)和批次号(batch)数据
	"auth": ...		// 登录鉴权结果
 }
 
 2.Response类包括两类通用返回值消息：ok与error，
 还包括两个常用的操作方法：success( )与failure( )，
 通过一个内部类来展现元数据结构
 */
public class DataResponse implements Serializable {
	private static final String OK = "ok";
	private static final String ERROR = "error";

	private Status status;
	private Object meta;
	private Object data;
	private Map<String, String> redisData;		// redis缓存中的表状态(status)和批次号(batch)数据
	private Object auth;		// 授权信息
	
	/**
	 * 成功，不附带任何消息
	 * @return
	 */
	public DataResponse success() {
		this.status = new Status(true, OK);
		return this;
	}
	/**
	 * 返回仅带status状态的DataResponse
	 * @param message 成功消息
	 * @return
	 */
	public DataResponse success(String message) {
		this.status = new Status(true, message);
		return this;
	}
	/**
	 * 返回status状态和redis数据的DataResponse
	 * @param redisData redis对象
	 * @return
	 */
	public DataResponse success(Map<String, String> redisData) {
		this.status = new Status(true, OK);
		this.redisData = redisData;
		return this;
	}
	/**
	 * 返回带status及数据的DataResponse
	 * @param data 数据对象
	 * @return
	 */
	public DataResponse success(Object data) {
		this.status = new Status(true, OK);
		this.data = data;
		return this;
	}
	/**
	 * 返回带status,meta的DataResponse,data可为空(为避免方法重复)
	 * @param meta 封装的元数据对象
	 * @param data 数据对象
	 * @return
	 */
	public DataResponse success(Object meta, Object data) {
		this.status = new Status(true, OK);
		this.meta = meta;
		this.data = data;
		return this;
	}
	/**
	 *  返回带status,redis数据和data数据的DataResponse
	 * @param redisData redis对象
	 * @param data 数据对象
	 * @return
	 */
	public DataResponse success(Map<String, String> redisData, Object data) {
		this.status = new Status(true, OK);
		this.redisData = redisData;
		this.data = data;
		return this;
	}
	/**
	 *  返回鉴权结果
	 * @param auth 封装的鉴权结果对象
	 * @return
	 */
	public DataResponse grant(Object auth, Object data) {
		this.status = new Status(true, OK);
		this.auth = auth;
		this.data = data;
		return this;
	}
	/**
	 * 失败，不封装消息提示
	 * @return
	 */
	public DataResponse failure() {
		this.status = new Status(false, ERROR);
		return this;
	}
	/**
	 * 带失败消息的返回
	 * @param message
	 * @return
	 */
	public DataResponse failure(String message) {
		this.status = new Status(false, message);
		return this;
	}
	
	public Status getStatus() {
		return status;
	}
	public Object getMeta() {
		return meta;
	}
	public Object getData() {
		return data;
	}	
	public Map<String, String> getRedisData() {
		return redisData;
	}
	public Object getAuth() {
		return auth;
	}

	// 消息内部类
	public class Status {
		private boolean success;
		private String message;
		private Date time;
		
		public Status(boolean success, String message) {
			this.success = success;
			this.message = message;
			this.time = new Date();		// 每次返回的数据，都是使用当前时间
		}
		
		public boolean isSuccess() {
			return success;
		}
		
		public String getMessage() {
			return message;
		}
		
		public Date getTime() {
			return time;
		}
	}
}