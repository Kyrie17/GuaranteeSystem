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
import model.Pager;
import model.RepairForm;
import model.Result;
import tools.Constant;
import tools.StringUtil;

/**
 * 维修工获取自己的所有报修单
 */
@WebServlet("/GetRepairManRFServlet")
public class GetRepairManRFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public GetRepairManRFServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		Pager<RepairForm> result = new Pager<RepairForm>();
		
		String repairMan = new String(request.getParameter("username").getBytes("ISO-8859-1"), "UTF-8");
		// 校验pageNum参数输入合法性
		String pageNumStr = request.getParameter("pageNum"); 
		if(pageNumStr !=null && !StringUtil.isNum(pageNumStr)){
			response.getWriter().print(JSONObject.toJSON(new Result(-1, null, "参数错误")));
		}
		
		//显示第几页数据
		int pageNum = Constant.DEFAULT_PAGE_NUM; 
		if(pageNumStr!=null && !"".equals(pageNumStr.trim())){
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		// 每页显示多少条记录
		int pageSize = Constant.DEFAULT_PAGE_SIZE; 
				
				
		result = repairFormDao.findRFByRepairMan(repairMan, pageNum, pageSize);
		
		response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
