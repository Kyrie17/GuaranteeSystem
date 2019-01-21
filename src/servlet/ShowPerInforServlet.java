package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.RepairFormDao;
import dao.RepairFormDaoImpl;
import model.RepairForm;
import model.Result;
import tools.CommonUtils;

/**
 * 展示个人信息
 */
@WebServlet("/ShowPerInforServlet")
public class ShowPerInforServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ShowPerInforServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		/*//获取token
		Cookie[] cookies = request.getCookies();
		String token = "";
		for (Cookie cookie : cookies) {
		    switch(cookie.getName()){
		        case "cookie":
		            token = cookie.getValue();
		            break;
		        default:
		            break;
		    }
		}
		*/
		int orderNumber = Integer.parseInt(request.getParameter("orderNumber"));
		
		/*try {
			username = CommonUtils.parseJWT(token).getSubject();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		//按学号查找报修单
		List<RepairForm> result = repairFormDao.getPerInform(orderNumber);
		
		//返回报修单
		response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
