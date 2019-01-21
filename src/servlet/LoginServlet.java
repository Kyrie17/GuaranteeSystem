package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.StudentDaoImpl;
import model.Message;
import model.Result;

import tools.CommonUtils;
import tools.DButil;
import tools.Md5Utils;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Md5Utils md5=new Md5Utils();
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置字符编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			// 获取参数
			String stu_id = request.getParameter("student_id");
			String password = request.getParameter("login_password");
			StudentDaoImpl db = new StudentDaoImpl();
			String time = db.getTime(stu_id);
			if (!time.equals("0")) {
				password=md5.getMd5(password+time);
				int n = db.loginJudge(stu_id, password);
				if (stu_id != null && password != null) {
					if (n == 1) {
						// 创建用户令牌
						String token = CommonUtils.createJWT(stu_id, 30 * 60 * 1000);
						writer.print(JSONObject.toJSONString(new Result<String>(1, token, "登录成功")));
						writer.flush();
						writer.close();
					} else {
						writer.print(JSONObject.toJSONString(new Result<String>(-1, null, "信息填写不正确")));
					}
				} else {
					writer.print(JSONObject.toJSONString(new Result<Object>(-1, null, "信息填写不正确")));

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
