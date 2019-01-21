package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@WebServlet("/StudentChatServlet")
public class StudentChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentChatServlet() {
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
		Connection conn = null;
		PreparedStatement ptmt = null;
		PreparedStatement ptmt2 = null;
		int m = 0;
		try {
			conn = DButil.getInstance().getConnection();
			String sql1=" select max(num) from student_chat ";
			ptmt=conn.prepareStatement(sql1);
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				String roomid="room"+(rs.getInt(1)+1);
				String sql = "insert into student_chat (username,roomid) values(?,?) ";
				ptmt2 = conn.prepareStatement(sql);
				ptmt2.setString(1, username);
				ptmt2.setString(2, roomid);
				m = ptmt2.executeUpdate();
				if(m==1) {
				response.getWriter().print(JSONObject.toJSONString(new Result<Object>(1, roomid, "添加房间成功")));
				}else {
					response.getWriter().print(JSONObject.toJSONString(new Result<Object>(1, null, "添加房间失败")));
				}
			}else {
				response.getWriter().print(JSONObject.toJSONString(new Result<Object>(1, null, "添加房间失败")));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("添加房间信息失败");
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
