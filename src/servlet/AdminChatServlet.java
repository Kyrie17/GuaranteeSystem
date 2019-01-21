package servlet;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import model.Chat;
import model.Result;
import tools.DButil;

/**
 * Servlet implementation class AdminChatServlet
 */
@WebServlet("/AdminChatServlet")
public class AdminChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminChatServlet() {
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
		String delete=request.getParameter("delete");
		if(delete==null) {
			delete="";
		}
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		DButil dbutil = DButil.getInstance();
		try {
		if(delete.equals("delete")) {
			String roomid=request.getParameter("roomid");
			System.out.println(roomid+"房间");
			if(roomid==null) {
				roomid="";
			}
			String sql = "delete from student_chat where roomid=? ";
				conn = dbutil.getConnection();
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1, roomid);
				boolean m = ptmt.execute();
		}
		List<Chat> result = new ArrayList<Chat>(); 
		String sql2 = "SELECT * FROM student_chat ";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql2);
			resultSet = ptmt.executeQuery(); 
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
					
				}

				Chat s = new Chat(map);
				result.add(s);
				
			}
			response.getWriter().print(JSONObject.toJSONString(new Result<Object>(-1, result, "��ȷ")));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
