package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import model.Result;
import tools.DButil;

/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		String username=request.getParameter("username");
		String roomid=request.getParameter("roomid");
		
		
		Connection conn = null;
		PreparedStatement ptmt = null;
		int m = 0;
		try {
			conn = DButil.getInstance().getConnection();
			String sql = " insert into chat (username,roomid) values( ?,? ) ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, username);
			ptmt.setString(2, roomid);
			m = ptmt.executeUpdate();
			response.getWriter().print(JSONObject.toJSONString(new Result<Object>(-1, null, "正确")));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("添加注册信息失败");
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
