package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import model.RepairForm;
import model.Result;
import tools.CommonUtils;

/**
 * Servlet implementation class JudgeLoginState
 */
@WebServlet("/JudgeLoginState")
public class JudgeLoginState extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JudgeLoginState() {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		String token=request.getParameter("token");
		String stu_id=null;
		PrintWriter writer = response.getWriter();
		try {
			stu_id=CommonUtils.parseJWT(token).getSubject();
			writer.print(JSONObject.toJSONString(new Result<String>(1, stu_id, "已登录")));
		} catch (Exception e) {
			response.getWriter().print(JSONObject.toJSONString(new Result<String>(0, "", "未登录")));
		}
	}

}
