package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import tools.DateMinusUtil;
import tools.StringUtil;

/**
 * 个人中心显示报修单（分类）
 */
@WebServlet("/PersonalCenterServlet")
public class PersonalCenterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
   
    public PersonalCenterServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String username = request.getParameter("username");
		int judgeState = Integer.parseInt(request.getParameter("judgeState"));
		int userConfirm = Integer.parseInt(request.getParameter("userConfirm"));
		
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
				
		
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		Pager<RepairForm> result = new Pager<RepairForm>();
		
		if(judgeState != 2) {	//查询未完成的报修单
			
			result = repairFormDao.findUnfinishedRF(username, pageNum, pageSize);

			if(result.getDataList() == null) {	//返回结果为空
				response.getWriter().print(JSONObject.toJSON(new Result(-1, null, "未查询到结果")));
			}else {
				response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
			}
			
		}else {
			
			if(userConfirm == 1) {	//报修单审核通过，用户已确定
				result = repairFormDao.findFinishedRF(username, userConfirm, pageNum, pageSize);
				response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
			}else {	//报修单审核通过，用户未确定
				
				result = repairFormDao.findFinishedRF(username, userConfirm, pageNum, pageSize);
				
				//当前时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = sdf.format(new Date());
				long endT = DateMinusUtil.fromDateStringToLong(now);
		
				
				if(result.getDataList() == null) {	//返回结果为空
					response.getWriter().print(JSONObject.toJSON(new Result(-1, null, "未查询到结果")));
				}else {
				
					
					//计算如果当前时间与维修工确定完成时间超过15天
					for(int i = 0; i < result.getDataList().size(); i++) {
						
						long startT = DateMinusUtil.fromDateStringToLong(result.getDataList().get(i).getSubmitTime());
						
						long ss = (endT - startT) / 1000; // 共计秒数
						
						if(ss >= 1296000) {//如果超过15天,学生端自动确认报修单完成
							
							int orderNumber = result.getDataList().get(i).getOrderNumber();
							repairFormDao.ChangeUserConfirm(orderNumber);
							
						}
						
					}
					
					result = repairFormDao.findFinishedRF(username, userConfirm, pageNum, pageSize);
					response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
					
				}
				
			}
			
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
