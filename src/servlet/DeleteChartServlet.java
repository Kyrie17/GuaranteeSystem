package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.CommidityDao;
import dao.CommidityDaoImpl;
import model.Result;
import tools.CommonUtils;

/**
 * Servlet implementation class DeleteChartServlet
 */
@WebServlet("/DeleteChartServlet")
public class DeleteChartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CommidityDao commidityDao=new CommidityDaoImpl();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteChartServlet() {
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
		// 设置字符编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		try {
			//获得当前用户的学号
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
			String stu_id;
			stu_id =CommonUtils.parseJWT(token).getSubject(); //CommonUtils.parseJWT(token).getSubject();
			if(stu_id==null) {
				stu_id="";
			}
			//初始化所有商品
			int  n = commidityDao.deleteChart(stu_id);
			if(n>0) {
			response.getWriter().print(JSONObject.toJSON(new Result(1, null, "删除成功")));
			}else {
				response.getWriter().print(JSONObject.toJSON(new Result(-1, null, "删除失败")));	
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("错误");	
		}finally {
		}
	}

}
