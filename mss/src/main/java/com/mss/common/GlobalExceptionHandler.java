package com.mss.common;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

/**
 * 全局异常捕获类
 * 
 * @author David Lei
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	private static String ERR_MSG = "errmsg";

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleMissingServletRequestParameterException(MissingServletRequestParameterException e,
			HttpServletRequest request) {
		logger.error("缺少请求参数", e);
		request.setAttribute(ERR_MSG, "缺少请求参数:" + e.getMessage());
		return "error";
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public String handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
		logger.error("参数解析失败", e);
		request.setAttribute(ERR_MSG, "参数解析失败:" + e.getMessage());
		return "error";
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		logger.error("参数验证失败", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String field = error.getField();
		String code = error.getDefaultMessage();
		String message = String.format("%s:%s", field, code);
		request.setAttribute(ERR_MSG, "参数验证失败:" + message);
		return "error";
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public String handleBindException(BindException e, HttpServletRequest request) {
		logger.error("参数绑定失败", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String field = error.getField();
		String code = error.getDefaultMessage();
		String message = String.format("%s:%s", field, code);
		request.setAttribute(ERR_MSG, "参数绑定失败:" + message);
		return "error";
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public String handleServiceException(ConstraintViolationException e, HttpServletRequest request) {
		logger.error("参数验证失败", e);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String message = violation.getMessage();
		request.setAttribute(ERR_MSG, "参数验证失败:" + message);
		return "error";
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public String handleValidationException(ValidationException e, HttpServletRequest request) {
		logger.error("参数验证失败", e);
		request.setAttribute(ERR_MSG, "参数验证失败:" + e.getMessage());
		return "error";
	}

	/**
	 * 404 - Not Found
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceAccessException.class)
	public String noHandlerFoundException(ResourceAccessException e, HttpServletRequest request) {
		logger.error("Not Found", e);
		request.setAttribute(ERR_MSG, "通用异常:" + e.getMessage());
		return "error";
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
			HttpServletRequest request) {
		logger.error("不支持当前请求方法", e);
		request.setAttribute(ERR_MSG, "不支持当前请求方法:" + e.getMessage());
		return "error";
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public String handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e,
			HttpServletRequest request) {
		logger.error("不支持当前媒体类型", e);
		request.setAttribute(ERR_MSG, "不支持当前媒体类型:" + e.getMessage());
		return "error";
	}

	/**
	 * 操作数据或库出现异常
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(SQLException.class)
	public String handleException(SQLException e, HttpServletRequest request) {
		logger.error("操作数据库出现异常:", e);
		request.setAttribute(ERR_MSG, "操作数据库出现异常:" + e.getMessage());
		return "error";
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, HttpServletRequest request) {
		logger.error("通用异常", e);
		request.setAttribute(ERR_MSG, "500通用异常:" + e);
		return "error";
	}

}
