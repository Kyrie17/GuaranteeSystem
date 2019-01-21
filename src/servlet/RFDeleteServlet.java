package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RepairFormDao;
import dao.RepairFormDaoImpl;

/**
 * 删除报修表单信息
 */
@WebServlet("/RFDeleteServlet")
public class RFDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public RFDeleteServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		
		//获取表单的各个属性
		String orderNumber = request.getParameter("orderNumber");
		
		repairFormDao.removeRFInfor(orderNumber);
		response.sendRedirect("http://localhost:8080/GuaranteeSystem/html/ManageOperate.html");	//请求转发
	}

}
