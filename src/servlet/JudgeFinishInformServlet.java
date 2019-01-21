package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.StudentDao;
import dao.StudentDaoImpl;
import model.Result;
import tools.CommonUtils;

/**
 * Servlet implementation class JudgeFinishInform
 */
@WebServlet("/JudgeFinishInformServlet")
public class JudgeFinishInformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     StudentDao studentDao=new StudentDaoImpl();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JudgeFinishInformServlet() {
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
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=UTF-8");
			Cookie[] cookies = request.getCookies();
			String token = "";
			if(cookies!=null)
			{
			
			for (Cookie cookie : cookies) {
			    switch(cookie.getName()){
			        case "cookie":
			            token = cookie.getValue();
			            break;
			        default:
			            break;
			    }
			}
			}
			String stu_id = "";
			try {
				
				stu_id = CommonUtils.parseJWT(token).getSubject();
				int n=studentDao.judgeIfFinish(stu_id);
				if(n==0) {
				response.getWriter().print(JSONObject.toJSON(new Result<String>(1, stu_id, "未完善个人信息")));
				}else {
					response.getWriter().print(JSONObject.toJSON(new Result<String>(1, stu_id, "已完善个人信息")));
				}
			} catch (Exception e) {
				response.getWriter().print(JSONObject.toJSON(new Result<String>(-1,null , "获取学号失败")));
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}

}
