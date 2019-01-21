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
 * 维修端改变报修段审核状态
 */
@WebServlet("/ChangeJudgeStateServlet")
public class ChangeJudgeStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ChangeJudgeStateServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
response.setContentType("application/json;charset=UTF-8");
		
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		
		int orderNumber =  Integer.parseInt(request.getParameter("orderNumber"));
		int i = Integer.parseInt(request.getParameter("judgeState"));
		
		int m = 0;
		if(i == -1) {
			m = repairFormDao.changeJudgeState(i, orderNumber);
		}else if(i == 1){
			m = repairFormDao.changeJudgeState(i, orderNumber);
		}
		
		if(m == 0) {
			response.getWriter().print(JSONObject.toJSON(new Result(-1, null, "操作失败")));
		}else {
			response.getWriter().print(JSONObject.toJSON(new Result(1, null, "操作成功")));
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
