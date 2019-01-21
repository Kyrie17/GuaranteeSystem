package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.StudentDao;
import dao.StudentDaoImpl;
import model.Result;
import tools.CommonUtils;
import tools.Md5Utils;

/**
 * Servlet implementation class ModifyPasswordServlet
 */
@WebServlet("/ModifyPasswordServlet")
public class ModifyPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDao studentDao=new StudentDaoImpl();
	private Md5Utils md5=new Md5Utils();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyPasswordServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置字符编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		/*
		 * 获取学号
		 */
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

		/*
		 * 修改密码功能
		 */
		try {
			String stu_id = CommonUtils.parseJWT(token).getSubject();
			String old_password=request.getParameter("old_password");
			String new_password=request.getParameter("new_password");
			System.out.println(old_password+"  "+new_password);
			if(old_password==null) {
				old_password="";
			}
			String time = studentDao.getTime(stu_id);
			if (!time.equals("0")) {
			old_password=md5.getMd5(old_password+time);
			int n=studentDao.judgePassword(old_password,stu_id);
			if(n==0) {
				response.getWriter().print(JSONObject.toJSON(new Result<String>(-1,null , "修改密码失败")));
				return;
			}else {
				String timestamp = md5.getTimestamp();
				String encoded = md5.getMd5(new_password + time);
				int k=studentDao.modifyPassword(stu_id, encoded);
				if(k==1) {
					response.getWriter().print(JSONObject.toJSON(new Result<String>(-1,null , "密码更新成功")));
				}else {
					response.getWriter().print(JSONObject.toJSON(new Result<String>(-1,null , "密码更新失败")));
				}
			}
			}else {
				response.getWriter().print(JSONObject.toJSON(new Result<String>(-1,null , "密码更新失败")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(JSONObject.toJSON(new Result<String>(-1,null , "修改密码失败")));
		}

	}

}
