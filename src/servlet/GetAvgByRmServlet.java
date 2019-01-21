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
import model.Result;

/**
 * 维修工获取自己平均评分
 */
@WebServlet("/GetAvgByRmServlet")
public class GetAvgByRmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public GetAvgByRmServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		
		String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");
		
		ScoreDao scoreDao = new ScoreDaoImpl();
		
		double m = scoreDao.getAvgByRm(username);
		response.getWriter().print(JSONObject.toJSON(new Result(m, null, "")));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
