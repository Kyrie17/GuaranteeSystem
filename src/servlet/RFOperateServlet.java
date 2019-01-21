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
import model.RepairForm;
import model.Result;

/**
 * 对报修单进行修改
 */
@WebServlet("/RFOperateServlet")
public class RFOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public RFOperateServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		
		int m = 0;
		
		//获取表单的各个属性
		String orderNumber = request.getParameter("orderNumber");
		String serAdd = request.getParameter("serAdd");
		String serInform = request.getParameter("serInform");
		String serTime = request.getParameter("serTime");
		int judgeState = Integer.valueOf(request.getParameter("judgeState"));
		String repairMan = request.getParameter("repairMan");
		String choice = request.getParameter("choice");
		
		System.out.println("choice = " + choice);
		
		int r = Integer.parseInt(orderNumber);	//将orderNumber转为int类型
		System.out.println( "orderNumber = " + r);
		// 组装查询条件
		RepairForm searchModel = new RepairForm();
		searchModel.setOrderNumber(r);
		searchModel.setUsername(serAdd);
		searchModel.setSerInform(serInform);
		searchModel.setSerTime(serTime);
		searchModel.setJudgeState(judgeState);
		searchModel.setRepairMan(repairMan);
		
		//执行对报修单的操作
		if(choice.equals("update")) {	//更新
			m = repairFormDao.updateRFInfor(searchModel);
		}else if(choice.equals("delete")) {		//删除记录
			m = repairFormDao.removeRFInfor(orderNumber);
		}else {		//查看详情
			m = repairFormDao.getDetailRFInfor(orderNumber);
		}
		
//		response.getWriter().print(JSONObject.toJSON(new Result(1, m, "")));
		response.sendRedirect("http://localhost:8080/GuaranteeSystem/html/RepairFormOperate.html");	//请求转发
	}
}
