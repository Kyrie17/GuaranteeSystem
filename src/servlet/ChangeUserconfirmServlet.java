package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.RepairFormDao;
import dao.RepairFormDaoImpl;
import model.Result;

/**
 * 用户确认报修单已完成
 */
@WebServlet("/ChangeUserconfirmServlet")
public class ChangeUserconfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ChangeUserconfirmServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		
		int orderNumber =  Integer.parseInt(request.getParameter("orderNumber"));
		
		System.out.println("orderNumber = " + orderNumber);
		int m = repairFormDao.ChangeUserConfirm(orderNumber);
		
		if(m == 0)
			response.getWriter().print(JSONObject.toJSON(new Result(-1, null, "操作失败")));
		else
			response.getWriter().print(JSONObject.toJSON(new Result(1, null, "操作成功")));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
