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
 * 维修工获取报修评价
 */
@WebServlet("/GetScoreByRm")
public class GetScoreByRm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public GetScoreByRm() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		
		String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");
		
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
		
		
		ScoreDao scoreDao = new ScoreDaoImpl();
		Pager<Score> result = scoreDao.findScoreByRm(username, pageNum, pageSize);
		
		if(result.getDataList() == null) {
			response.getWriter().print(JSONObject.toJSON(new Result(-1, null, "")));
		}else {
			response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
