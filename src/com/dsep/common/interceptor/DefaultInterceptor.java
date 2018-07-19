package com.dsep.common.interceptor;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.dsep.util.Configurations;
import com.dsep.util.TextConfiguration;


/**
 * @author OPCUser
 *
 */
public class DefaultInterceptor extends WebContentInterceptor{

	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = f.parse("1990-08-30");
		System.out.println(d.getTimezoneOffset());
		System.out.println(d + "------");
		System.out.println(d.toString() + "????????");
		System.out.println(d.toGMTString() + "????????");
		
		Date d2 = f.parse("1990-12-30");
		System.out.println(d2.getTimezoneOffset());
		System.out.println(d2 + "------");
		System.out.println(d2.toString() + "????????");
		System.out.println(d2.toGMTString() + "????????");
		
		Date d3 = f.parse("1992-12-30");
		System.out.println(d3.getTimezoneOffset());
		System.out.println(d3 + "------");
		System.out.println(d3.toString() + "????????");
		System.out.println(d3.toGMTString() + "????????");
		
		this.setContext(request);
		this.setDomainId(request);
		this.setTextConfiguration(request);
		this.setConfigurations(request);
		//super.postHandle(request, response, handler, modelAndView);
	}

	private void setConfigurations(HttpServletRequest request) {
		if(request.getContextPath()!=null){
			request.setAttribute("configurations", Configurations.getConfigurations());
		}
		
	}

	private void setTextConfiguration(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if(request.getContextPath()!=null){
			request.setAttribute("textConfiguration", TextConfiguration.getTextConfiguration());
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {
		return super.preHandle(request, response, handler);
	}

	
	/**
	 * @param request
	 */
	private void setContext(HttpServletRequest request)
	{
		if(request.getContextPath()!=null){
			request.setAttribute("ContextPath", request.getContextPath());
		}
	}
	
	public void setDomainId(HttpServletRequest request){
		if(request.getContextPath()!=null){
			request.setAttribute("DomainId", Configurations.getCurrentDomainId());
		}
	}
	
}
