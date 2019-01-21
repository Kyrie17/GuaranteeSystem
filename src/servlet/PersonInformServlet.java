package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ShowPerInformDao;
import dao.ShowPerInformDaoImpl;
import dao.StudentDao;
import dao.StudentDaoImpl;
import tools.CommonUtils;

/**
 * Servlet implementation class PersonInformServlet
 */
@WebServlet("/PersonInformServlet")
public class PersonInformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDao studentDao = new StudentDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonInformServlet() {
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
		System.out.println("进来了");
		/*
		 * 设置编码
		 */
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		/*
		 * 获取填写的信息
		 */
		String stu_id=request.getParameter("stu_id");
		String room=request.getParameter("room");
		String sex=request.getParameter("sex");
		String area=request.getParameter("area");
		String build=request.getParameter("build");
		if(room==null||sex==null||area==null||build==null||stu_id==null) {
			response.getWriter().print("{\"message\":\"信息填写不完整\"}");
			return;
		}
		
		/*
		 *
		 */
		System.out.println("学号"+stu_id);
		int n=studentDao.improveInform(stu_id, room, sex, area, build);
		System.out.println(n+"可以123");
		if(n==1) {
			response.getWriter().print("{\"message\":\"添加成功\"}");
		}else {
			response.getWriter().print("{\"message\":\"添加失败\"}");
		}
	}

}
