package servlet;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.AnalyzeDao;
import dao.AnalyzeDaoImpl;
import dao.NoticeInformDao;
import dao.NoticeInformDaoImpl;
import model.Result;

/**
 * Servlet implementation class GetAnalyzeServlet
 */
@WebServlet("/GetAnalyzeServlet")
public class GetAnalyzeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AnalyzeDao analyzeDao = new AnalyzeDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAnalyzeServlet() {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		String analyze = request.getParameter("analyze");
		// 报修类别分析
		if (analyze.equals("1")) {
			try {
				int[] a = analyzeDao.AnalyzeSerType();
				response.getWriter().print(JSONObject.toJSON(new Result(1, a, "")));
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
		// 审核状态分析
		if (analyze.equals("2")) {
			try {
				int[] a = analyzeDao.AnalyzeJudgeState();
				response.getWriter().print(JSONObject.toJSON(new Result(1, a, "")));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//// 报修时间分析
		if (analyze.equals("3")) {
			try {
				int[] a = analyzeDao.AnalyzeSerTime();
				response.getWriter().print(JSONObject.toJSON(new Result(1, a, "")));
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

		// 维修工类别分析
		if (analyze.equals("4")) {
			try {
				int[] a = analyzeDao.AnalyzeAdmin();
				response.getWriter().print(JSONObject.toJSON(new Result(1, a, "")));
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
	}

}
