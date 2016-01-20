package com.engineer.sssp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 日志
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2016年1月20日 下午9:37:38
 */
@Table(name = "SSSP_LOG")
@Entity
@XmlRootElement
public class Log implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// 日志类型（1：接入日志；2：错误日志）
	public static final String TYPE_ACCESS = "1";
	public static final String TYPE_EXCEPTION = "2";
	private Long id;
	private String type; // 日志类型（1：接入日志；2：错误日志）
	private String title; // 日志标题
	private String remoteAddr; // 操作用户的IP地址
	private String requestUri; // 操作的URI
	private String method; // 操作的方式
	private String params; // 操作提交的数据
	private String userAgent; // 操作用户代理信息
	private String exception; // 异常信息
	private Date createTime;
	private Date updateTime;
	private Date deleteTime;
	
	@GeneratedValue
	@Id
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getRemoteAddr() {
		return remoteAddr;
	}
	
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public String getRequestUri() {
		return requestUri;
	}
	
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
	public String getException() {
		return exception;
	}
	
	public void setException(String exception) {
		this.exception = exception;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDeleteTime() {
		return deleteTime;
	}
	
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	
	/**
	 * 设置请求参数
	 * @param paramMap
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void setParams(Map paramMap) {
		if (paramMap == null) {
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()) {
			params.append(("".equals(params.toString())?"":"&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0?param.getValue()[0]:"");
			params.append(paramValue);
		}
		this.params = params.toString();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
