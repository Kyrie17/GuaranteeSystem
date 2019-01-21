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
import model.Cart;
import model.Result;
import tools.CommonUtils;

/**
 * Servlet implementation class PurchaseCommidityServlet
 */
@WebServlet("/PurchaseCommidityServlet")
public class PurchaseCommidityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CommidityDao commidityDao=new CommidityDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseCommidityServlet() {
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
		//获得用户传过来的id
		String id=request.getParameter("commidity_id");
		Double price=Double.parseDouble(request.getParameter("commidity_price"));
		String name=request.getParameter("commidity_name");
		int n=0;
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
			
			//将物品添加至购物车
			int judge=commidityDao.judgeIfExist(stu_id, id);
			if(judge>0) {
				int answer1=commidityDao.addCart(stu_id, judge, id);
				System.out.println(answer1+"777");
				if(answer1>0) {
					response.getWriter().print(JSONObject.toJSON(new Result(1, null, "添加购物车成功")));
				}else {
					response.getWriter().print(JSONObject.toJSON(new Result(1, null, "添加购物车失败")));
				}
			}else {
				int answer2=commidityDao.insertCart(stu_id,id, judge,name,price);
				System.out.println(answer2+"888");
				if(answer2>0) {
					response.getWriter().print(JSONObject.toJSON(new Result(1, null, "添加购物车成功")));
				}else {
					response.getWriter().print(JSONObject.toJSON(new Result(1, null, "添加购物车失败")));
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
