package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.ScoreDao;
import dao.ScoreDaoImpl;
import model.Pager;
import model.Result;
import model.Score;
import tools.Constant;
import tools.StringUtil;


/**
 * 管理端获取所有报修评价
 */
@WebServlet("/GetScoreServlet")
public class GetScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public GetScoreServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		ScoreDao scoreDao = new ScoreDaoImpl();
		
		//满意度选项
		String satisfied = "全部";
		String satisfiedStr = request.getParameter("satisfied");
		if(satisfiedStr != null && !"".equals(satisfiedStr.trim())){
			satisfied = satisfiedStr;
		}
		
		
		//星级评分
		int classState = Constant.CLASS_ALL;
		String classStateStr = request.getParameter("classState");
		if(classStateStr != null && !"".equals(classStateStr.trim())){
			classState = Integer.parseInt(classStateStr);
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
		
		
		//封装查询信息
		Score searchModel = new Score();
		searchModel.setSatisfied(satisfied);
		searchModel.setScore(classState);
		
		System.out.println("pageNum = " + pageNum);
		
		Pager<Score> pager = scoreDao.findScore(searchModel, pageNum, pageSize);
		
		response.getWriter().print(JSONObject.toJSON(new Result(1, pager, "查询成功")));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
