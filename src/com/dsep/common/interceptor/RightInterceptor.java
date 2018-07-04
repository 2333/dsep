package com.dsep.common.interceptor;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import com.dsep.entity.Right;
import com.dsep.entity.User;
import com.dsep.service.rbac.UserService;
import com.dsep.util.Configurations;
import com.dsep.util.MySessionContext;
import com.dsep.util.UserSession;

public class RightInterceptor extends HandlerInterceptorAdapter{

	@Resource(name = "userService")
	private UserService userService;
	
	private List<String> excludedUrls;
	
	
	public List<String> getExcludedUrls() {
		return excludedUrls;
	}
	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	@Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  

		System.out.println("============This is request:" + request.getRequestURI() + " preHandle.");  
		
		//排除未登录的路由
		String requestUri = request.getRequestURI();
        for (String url : excludedUrls) {
            if (requestUri.matches(url)) {
                return true;
            }
        }
        
        //如果没有用户Session直接跳转登录页面
        String sessionid = request.getParameter("jsessionid");
        HttpSession session = request.getSession();
     
        MySessionContext myc= MySessionContext.getInstance();
        
        if(StringUtils.isNotBlank(sessionid)){
        	session = myc.getSession(sessionid);
        }
        else{
        	session = request.getSession();
        }
        
  		/*User user = (User)session.getAttribute("userSession");
  		
  	    if (user == null) {
  	    	if(isAjax(request, response)){
  	    		response.getWriter().print("登录超时，请重新登录！");
  	    	}else{
  	    		if(Configurations.getUrlContextPath().equals("DSEP_ZYPG"))
  	    			response.getWriter().print("<script language=\"javascript\"> alert(\"登录超时，请重新登录！\");  window.location.href=\'${ContextPath}/rbac/logoutZ.jsp\' </script> ");
  	    		else
  	    			response.getWriter().print("<script language=\"javascript\"> alert(\"登录超时，请重新登录！\");  window.location.href=\'${ContextPath}/rbac/logout.jsp\' </script> ");
  	    	}
  	    	return false;
  	    }*/
  	    
		//判断是否有某权限
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		List<Right> menuRights = us.getMenuRights();
		List<Right> forbidRights = us.getForbidRights();
		
		for(Right right:forbidRights){
			if(requestUri.equals("/"+Configurations.getUrlContextPath()+"/" + right.getUrl())) 
			{
				response.setCharacterEncoding("utf-8");
				response.sendRedirect("/"+Configurations.getUrlContextPath()+"/rbac/error.jsp");
				return false;
			}
		}
		
		for(Right right:menuRights){
			if(requestUri.startsWith("/"+Configurations.getUrlContextPath()+"/" + right.getUrl())){
				//如果权限匹配菜单权限前缀，则默认拥有该页面下所有权限
				return true;
			}
		}
		
        return false;
    }  
	
    @Override  
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {  
        //System.out.println("============This is request:" + request.getRequestURI() + " postHandle.");  
    }  
    
    @Override  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {  
        //System.out.println("============This is request:" + request.getRequestURI() + " afterCompletion.");  
    }  
    
    /**
     * 判断是否为ajax请求
     * @param request
     * @param response
     * @return 是返回true 不是返回false
     */
}
