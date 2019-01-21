package servlet;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.HomePageShowDaoImpl;
import dao.NoticeInformDao;
import dao.NoticeInformDaoImpl;
import model.NoticeInform;
import model.Result;

/**
 * 获取最近几条报修信息
 */
@WebServlet("/GetLastestNIServlet")
public class GetLastestNIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private NoticeInformDao repairFormDao = new NoticeInformDaoImpl();
    
    public GetLastestNIServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		//获取查询结果
		List<Object> result=null;
		try {
			result = new HomePageShowDaoImpl().show();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
