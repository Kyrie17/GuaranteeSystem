package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.RepairFormDao;
import dao.RepairFormDaoImpl;
import model.RepairForm;
import model.Result;

/**
 * 获取最近几条报修信息
 */
@WebServlet("/GetLastestRFServlet")
public class GetLastestRFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RepairFormDao repairFormDao = new RepairFormDaoImpl();
    
    public GetLastestRFServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		
		//获取查询结果
		List<RepairForm> result =  repairFormDao.getLastestRF();
		response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
