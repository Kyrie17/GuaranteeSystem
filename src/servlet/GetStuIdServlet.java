package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import model.Result;
import tools.CommonUtils;

/**
 * Servlet implementation class GetStuIdServlet
 */
@WebServlet("/GetStuIdServlet")
public class GetStuIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStuIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		Cookie[] cookies = request.getCookies();
		String token = "";
		if(cookies!=null)
		{
		
		for (Cookie cookie : cookies) {
		    switch(cookie.getName()){
		        case "cookie":
		            token = cookie.getValue();
		            break;
		        default:
		            break;
		    }
		}
		}
		String stu_id = "";
		try {
			
			stu_id = CommonUtils.parseJWT(token).getSubject();
			response.getWriter().print(JSONObject.toJSON(new Result<String>(1, stu_id, "获取学号成功")));
		} catch (Exception e) {
			response.getWriter().print(JSONObject.toJSON(new Result<String>(-1,null , "获取学号失败")));
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
