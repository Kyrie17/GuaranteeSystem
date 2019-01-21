package filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import model.Result;
import tools.CommonUtils;


@WebFilter(urlPatterns = {"/html/ManageOperate.html","/html/manage_index.html","/html/manage_data_all.html","/html/analyse_data.html"})
public class ManageUserFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
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
		        case "manage_cookie":
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

	@Override 
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
