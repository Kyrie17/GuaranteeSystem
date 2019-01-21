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
 * 根据条件分页显示报修表
 */
@WebServlet("/SublistServlet")
public class SublistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private RepairFormDao repairFormDao = new RepairFormDaoImpl();
    
    public SublistServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		/**
		 * 获取request参数
		 */
		//用户名
		String username = "";
		username = request.getParameter("username").trim();	
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		
		//报修类型
		int serType = Constant.SERTYPE_ALL;
		String serTypeStr = request.getParameter("serType");
		if(serTypeStr != null && !"".equals(serTypeStr.trim())){
			serType = Integer.parseInt(serTypeStr);
		}
		
		//报修状态
		int judgeState = Constant.JUDGESTATE_ALL;
		String judgeStateStr = request.getParameter("judgeState");
		if(judgeStateStr != null && !"".equals(judgeStateStr.trim())){
			judgeState = Integer.parseInt(judgeStateStr);
		}
		
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
		/*String pageSizeStr = request.getParameter("pageSize");
		if(pageSizeStr!=null && !"".equals(pageSizeStr.trim())){
			pageSize = Integer.parseInt(pageSizeStr);
		}*/
		
		
		// 组装查询条件
		RepairForm searchModel = new RepairForm(); 
		searchModel.setUsername(username);
		searchModel.setSerType(serType);
		searchModel.setJudgeState(judgeState);
		
		
		//获取查询结果
		Pager<RepairForm> result = repairFormDao.findRepairForm(searchModel,pageNum, pageSize);
		response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
	}
}
