package com.fzb.sssp.web.support;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import com.fzb.sssp.exception.EngineerException;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月2日 下午3:42:32
 */
public class CustomExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {
	
	private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandlerExceptionResolver.class);
	private String defaultErrorView;
	
	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}
	
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex) {
		if (handlerMethod == null) {
			return null;
		}
		Method method = handlerMethod.getMethod();
		if (method == null) {
			return null;
		}
		ModelAndView returnValue = super.doResolveHandlerMethodException(request, response, handlerMethod, ex);
		if (returnValue == null) {
			returnValue = new ModelAndView();
		}
		ErrMsg errMsg = new ErrMsg();
		if (ex instanceof EngineerException) {
			EngineerException engineerException = (EngineerException)ex;
			errMsg.setCode(engineerException.getCode());
			errMsg.setMsg(engineerException.getMessage());
		} else if (ex instanceof Throwable) {
			errMsg.setCode("-1");
			errMsg.setMsg("系统未知异常[" + ex.getClass().getName() + ":" + ex.getMessage() + "]");
			log.error("在{}.{}位置，出现系统未知异常", (handlerMethod.getBean() != null?handlerMethod.getBean().getClass().getName():""), method.getName(), ex);
		}
		returnValue.getModel().put("errMsg", errMsg);
		ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);
		if (null != responseBody) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			try {
				ResponseStatus responseStatusAnn = AnnotationUtils.findAnnotation(method, ResponseStatus.class);
				if (responseStatusAnn != null) {
					HttpStatus responseStatus = responseStatusAnn.value();
					String reason = responseStatusAnn.reason();
					if (StringUtils.isBlank(reason)) {
						response.setStatus(responseStatus.value());
					} else {
						try {
							response.sendError(responseStatus.value(), reason);
						} catch (IOException e) {
						}
					}
				}
				return handleResponseBody(returnValue, request, response);
			} catch (Exception e) {
				return null;
			}
		}
		request.getSession().setAttribute("errMsg", errMsg);
		returnValue.setViewName(defaultErrorView);
		return returnValue;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes", "resource"})
	private ModelAndView handleResponseBody(ModelAndView returnValue, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map value = returnValue.getModelMap();
		HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
		List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
		if (acceptedMediaTypes.isEmpty()) {
			acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
		}
		MediaType.sortByQualityValue(acceptedMediaTypes);
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
		Class<?> returnValueType = value.getClass();
		List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
		if (messageConverters != null) {
			for (MediaType acceptedMediaType : acceptedMediaTypes) {
				for (HttpMessageConverter messageConverter : messageConverters) {
					if (messageConverter.canWrite(returnValueType, acceptedMediaType)) {
						messageConverter.write(value, acceptedMediaType, outputMessage);
						return new ModelAndView();
					}
				}
			}
		}
		if (logger.isWarnEnabled()) {
			logger.warn("Could not find HttpMessageConverter that supports return type [" + returnValueType + "] and " + acceptedMediaTypes);
		}
		return null;
	}
	
	public static class ErrMsg {
		
		private String code;
		private String msg;
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getMsg() {
			return msg;
		}
		
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}
