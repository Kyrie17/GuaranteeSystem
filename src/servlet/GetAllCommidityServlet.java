package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.CommidityDao;
import dao.CommidityDaoImpl;
import model.Commidity;
import model.RepairForm;
import model.Result;
import tools.CommonUtils;

/**
 * Servlet implementation class GetAllCommidityServlet
 */
@WebServlet("/GetAllCommidityServlet")
public class GetAllCommidityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CommidityDao commidityDao=new CommidityDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllCommidityServlet() {
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
		
		try {
			//初始化所有商品
			List<Commidity> result = commidityDao.findAllCommidity();
			response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("错误");	
		}finally {
		}
	}

}
