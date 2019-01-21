package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.CommonUtils;

/**
 * Servlet Filter implementation class WorkerUserFilter
 */
@WebFilter("/WorkerUserFilter")
public class WorkerUserFilter implements Filter {

    /**
     * Default constructor. 
     */
    public WorkerUserFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println(666);
		HttpServletResponse resp=(HttpServletResponse)response;
		/*
		 * 设置响应编码
		 */
		request.setCharacterEncoding("utf-8");
	//	response.setContentType("text/json charset=utf-8");
		/*
		 * 获取请求头token
		 */
		HttpServletRequest req=(HttpServletRequest)request;
		
		Cookie[] cookies = req.getCookies();
		String token = "";
		if(cookies!=null)
		{
		
		for (Cookie cookie : cookies) {
		    switch(cookie.getName()){
		        case "worker_cookie":
		            token = cookie.getValue();
		            break;
		        default:
		            break;
		    }
		}
		}

	
		
		try {
			 CommonUtils.parseJWT(token);
			chain.doFilter(request, response);
			
		} catch (Exception e) {
			resp.sendRedirect("/GuaranteeSystem/html/Manage_login.html");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
