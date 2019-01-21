package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.RepairManDao;
import dao.RepairManDaoImpl;
import model.RepairMan;
import model.Result;

/**
 * 获取某个工种的工作人员
 */
@WebServlet("/GetRepairManServlet")
public class GetRepairManServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	RepairManDao repairMan = new RepairManDaoImpl();
   
    public GetRepairManServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		
		List<RepairMan> result;
		/**
		 * 获取request参数
		 */
		if(request.getParameter("serType") == null || request.getParameter("serType").equals("")) {
			
			result = repairMan.getAllRepairMan();
			
		
		} else {
			
			int major = Integer.parseInt(request.getParameter("serType"));
			
			result = repairMan.getRepairMan(major);
		}
		
		response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
