package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RepairFormDao;
import dao.RepairFormDaoImpl;
import model.RepairForm;

/**
 * 更新报修表单信息
 */
@WebServlet("/RFUpdateServlet")
public class RFUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    
    public RFUpdateServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		
		//获取表单的各个属性
		String r = request.getParameter("orderNumber");
		int judgeState = Integer.valueOf(request.getParameter("judgeState"));
		String repairMan = request.getParameter("repairMan");

		int orderNumber = Integer.parseInt(r);	//将orderNumber转为int类型
		
		// 组装查询条件
		RepairForm searchModel = new RepairForm();
		searchModel.setOrderNumber(orderNumber);
		searchModel.setJudgeState(judgeState);
		searchModel.setRepairMan(repairMan);
		
		repairFormDao.updateRFInfor(searchModel);
		
		response.sendRedirect("http://localhost:8080/GuaranteeSystem/html/ManageOperate.html");	//请求转发
	}

}
