package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.ManageDaoImpl;
import dao.StudentDaoImpl;
import model.Result;
import tools.CommonUtils;

/**
 * Servlet implementation class ManageLoginServlet
 */
@WebServlet("/ManageLoginServlet")
public class ManageLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageLoginServlet() {
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
		
		PrintWriter writer = response.getWriter();
		try {
			// 获取参数
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			ManageDaoImpl db = new ManageDaoImpl();
			int n = db.ManageloginJudge(username,password);
			if (username != null && password != null) {
				if (n == 1) {
					// 创建用户令牌
					String token = CommonUtils.createJWT(username, 30 * 60 * 1000);
					writer.print(JSONObject.toJSONString(new Result<String>(1, token, "登录成功")));
					writer.flush();
					writer.close();
				} else {
					writer.print(JSONObject.toJSONString(new Result<String>(-1, null, "信息填写不正确")));
				}
			} else {
				writer.print(JSONObject.toJSONString(new Result<Object>(-1, null, "信息填写不正确")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
		}
	}

}
