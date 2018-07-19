package com.dsep.controller.advice.exception.handle;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.JDBCException;
import org.hibernate.QueryException;
import org.hibernate.SessionException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.exception.BusinessException;
import com.dsep.common.exception.CollectBusinessException;
import com.dsep.common.exception.SessionNullException;
import com.dsep.common.exception.TeachManageException;
import com.dsep.common.logger.LoggerTool;
import com.dsep.util.Configurations;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
/**
 * 
 * @author OPCUser
 *
 */
@ControllerAdvice
@RequestMapping("/ExceptionHandle")
public class ExceptionHandleController {
	@Resource(name="loggerTool")
	private LoggerTool logger;
	private String[]exceptionSet={"未知异常","业务异常","数据库异常","登录超时"};
	/**
	 * 捕获和处理业务异常
	 * @param request 请求类
	 * @param ex 异常类
	 * @return 返回相关的页面和再次跳转
	 */
	@ExceptionHandler(BusinessException.class)
	public String handlerBusinessException(HttpServletRequest request,BusinessException ex){
		logger.error("业务错误 ："+ex.getMessage());
		String redirectUrl=ajaxException(request,1);
		if(redirectUrl!=null){
			return redirectUrl;
		}
		else{
			request.setAttribute("message", exceptionSet[1]);
			return "/error/business_error";
		}
		
	}
	/**
	 * 对于spring的数据库错误进行捕获
	 * @param request 请求类
	 * @param ex 异常类
	 * @return 返回错误页面或再次跳转
	 */
	@ExceptionHandler(DataAccessException.class)
	public String handleDataAccessException(HttpServletRequest request,DataAccessException ex)
	{
		logger.error("spring类型的数据库错误 ："+ex.getMessage());
		String redirectUrl=ajaxException(request,2);
		if(redirectUrl!=null){
			return redirectUrl;
		}else {
			request.setAttribute("message", exceptionSet[2]);
			return "/error/sql_error";
		}
		
	}
	/**
	 * 对于hibernate的数据库资源异常的捕获和处理，例如建立数据库连接失败等
	 * @param request 请求类
	 * @param ex 异常类
	 * @return 返回错误页面或再次进行跳转
	 */
	@ExceptionHandler(JDBCException.class)
	public String handleJDBCException(HttpServletRequest request,JDBCException ex)
	{
		logger.error("hibernate数据库资源错误 ："+ex.getMessage());
		String redirectUrl=ajaxException(request,2);
		if(redirectUrl!=null){
			return redirectUrl;
		}
		else {
			request.setAttribute("message", exceptionSet[2]);
			return "/error/sql_error";
		}
		
	}
	/**
	 * 对于hibernate数据查询语句的异常进行捕获和处理，例如查询参数错误等
	 * @param request 请求类
	 * @param ex 异常类
	 * @return 返回错误页面或再次进行跳转
	 */
	@ExceptionHandler(QueryException.class)
	public String handleQueryException(HttpServletRequest request,QueryException ex)
	{
		logger.error("hibernate数据库查询语句错误 ："+ex.getMessage());
		String redirectUrl=ajaxException(request,2);
		if(redirectUrl!=null){
			return redirectUrl;
		}
		else{
			request.setAttribute("message",exceptionSet[2]);
			return "/error/sql_error";
		}
		
		
	}
	/**
	 * 对于hibernate的session异常进行捕获和相关处理，例如session的打开和关闭
	 * @param request 请求类
	 * @param ex 异常类
	 * @return 返回相关页面或再次跳转
	 */
	@ExceptionHandler(SessionException.class)
	public String handleSessionException(HttpServletRequest request,SessionException ex)
	{  
		logger.error("hibernate数据库session错误 ："+ex.getMessage());
		String redirectUrl=ajaxException(request,2);
		if(redirectUrl!=null){
			return redirectUrl;
		}else {
			request.setAttribute("message",exceptionSet[2]);
			return "/error/sql_error";
		}
		
	}
	/**
	 * 对于系统未知的异常进行捕获和处理
	 * @param request 请求
	 * @param response 响应
	 * @param ex 捕捉的异常类
	 * @return 返回错误页面或者跳转
	 */
	@ExceptionHandler(RuntimeException.class)
    public String handlerRuntimeException(HttpServletRequest request,HttpServletResponse response,RuntimeException ex) {
		logger.error("运行时错误  "+ex.getMessage());
		String redirectUrl=ajaxException(request,0);
		if(redirectUrl!=null){
			return redirectUrl;
		}	
		else{
			request.setAttribute("message", exceptionSet[0]);
			return "/error/unexcept_error";
		}   
    }
	/**
	 * 对于ajax的局部请求异常进行统一处理
	 * @param exceptionType 异常类型
	 * @param request 请求类
	 * @param response 响应类
	 * @param ex 异常类
	 */
	@RequestMapping("/ajaxException/{exceptionType}")
    public void handleAjaxException(@PathVariable("exceptionType")int exceptionType,HttpServletRequest request,
    		HttpServletResponse response,RuntimeException ex) {
		/**
		 * 判断是否请求数据为json格式
		 */
		if(exceptionType==3){
			response.setHeader("sessionstatus", "timeout");//在响应头设置session状态   
			response.setHeader("contextPath",Configurations.getUrlContextPath());
		}
		if(request.getHeader("accept").indexOf("application/json")>-1)
		{
			/*josn格式的string*/
			String str="{\"message\":\""+exceptionSet[exceptionType]+"\"}";
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().print(str);
			} catch (IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			/*返回text类型结果*/
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(exceptionSet[exceptionType]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}    
    }
	/**
	 * 判断是否是ajax请求如果是返回跳转结果，否则返回null
	 * @param request 请求类
	 * @param exceptionType 错误信息类型
	 * @return 返回跳转的路径
	 */
	private  String ajaxException(HttpServletRequest request,int exceptionType)
	{
		String requestType=request.getHeader("X-Requested-With");
		if(requestType!=null&&requestType.indexOf("XMLHttpRequest")>-1)
		{
			return "redirect:/ExceptionHandle/ajaxException/"+exceptionType;
		}
		return null;
	}
	
	/**
	 * Session为空的异常
	 * @param request 请求类
	 * @param ex 异常类
	 * @return 返回相关页面或再次跳转
	 * @throws IOException 
	 */
	@ExceptionHandler(SessionNullException.class)
	public String handlerUserNullException(HttpServletRequest request,HttpServletResponse response
			,SessionNullException ex) throws IOException{
		logger.error("Session错误 ："+ex.getMessage());
		if(isAjax(request,response)){
			return "redirect:/ExceptionHandle/ajaxException/"+3;
		}else{
			return "/rbac/logout";
		}
		
		
	}
	@ExceptionHandler(CollectBusinessException.class)
	@ResponseBody
	public String handleCollectBusinessException(CollectBusinessException collectException,HttpServletRequest request)
	{
		if(request.getHeader("accept").indexOf("application/json")>-1){
			String message=null;
			if("json".equals(collectException.getMessageType())){
				message = collectException.getMessage();
			}else {
				message = "{\"message\":\""+collectException.getMessage()+"\"}";
			}
			
			return message;
		}
		return collectException.getMessage();
	}
	@ExceptionHandler(TeachManageException.class)
	@ResponseBody
	public String handleTeachManageException(TeachManageException teachManageException,HttpServletRequest request)
	{
		if(request.getHeader("accept").indexOf("application/json")>-1){//json请求
			String message=null;
			if("json".equals(teachManageException.getMessageType())){
				message = teachManageException.getMessage();
			}else {
				message = "{\"message\":\""+teachManageException.getMessage()+"\"}";
			}
			
			return message;
		}
		return teachManageException.getMessage();
	}
	private boolean isAjax(HttpServletRequest request,HttpServletResponse response){
    	if(request.getHeader("x-requested-with")!=null&&
    			request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
    		return true;  
    	}
    	return false;
    }
}
