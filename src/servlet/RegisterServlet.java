package servlet;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDaoImpl;
import model.Message;
import model.Register;
import model.Result;
import tools.CommonUtils;
import tools.DButil;
import tools.Md5Utils;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;;
	private  int k = 1;
	private Md5Utils md5=new Md5Utils();

	public RegisterServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		String action = request.getParameter("action");
		String stu_id2 = request.getParameter("stu_id");

		if (action == null) {
			action = "";
		}
		if (action.equals("check")) {
			StudentDaoImpl db = new StudentDaoImpl();
			// 学号已经被注册
			try {
				int rs = db.judgeStudent(stu_id2);
				if (rs == 0) {
					String str = "{\"message\":\"该学号已被注册\"}";
					response.getWriter().print(str);
					k = 0;
					response.getWriter().flush();
					response.getWriter().close();
					return;
				} else {
					String str = "{\"message\":\"\"}";
					response.getWriter().print(str);
					k = 1;
					response.getWriter().flush();
					response.getWriter().close();
					return;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (k == 1) {
			PrintWriter writer = response.getWriter();
			// 获得用户注册信息
			String stu_id = "";
			String mess = "";
			String phone = "";
			String password = "";
			String repeatPassword = "";
			try {
				stu_id = request.getParameter("stu_id").trim();
				mess = request.getParameter("mess").trim();
				phone = request.getParameter("phone").trim();
				password = request.getParameter("password").trim();
				repeatPassword = request.getParameter("repeatPassword").trim();
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean boo = stu_id.length() > 0 && mess.length() > 0 && phone.length() > 0 && password.length() > 0
					&& password.equals(repeatPassword);
			try {
				String timestamp = md5.getTimestamp();
				String encoded = md5.getMd5(password + timestamp);
				StudentDaoImpl db = new StudentDaoImpl();
				if (boo == true) {
					int m = db.addStudent(stu_id, phone,  encoded, boo,timestamp);
					if (m != 0) {
						writer.print(JSONObject.toJSONString(new Result(1, null, "注册成功")));
					} else {
						writer.print(
								JSONObject.toJSON(new Result<String>(-1, null, "信息填写不完整或填写信息有误或学号已被注册")).toString());// 666信息填写不完整或填写信息有误huo学号已被注册
					}
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher("html/HomePage.html");
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				writer.print(JSONObject.toJSON(new Result<String>(-1, null, "该学号已经被使用，请您更换学号")).toString());// 该学号已经被使用，请您更换学号
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

}
