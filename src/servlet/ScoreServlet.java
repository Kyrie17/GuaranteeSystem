package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.SystemOutLogger;

import com.alibaba.fastjson.JSONObject;

import dao.ScoreDaoImpl;
import dao.StudentDaoImpl;
import model.Result;

/**
 * Servlet implementation class ScoreServlet
 */
@WebServlet("/ScoreServlet")
public class ScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String judge="";
		if(request.getParameter("judge")!=null) {
			judge=request.getParameter("judge");
		}
		if(judge.equals("1")) {
			int r_ordernumber=Integer.parseInt(request.getParameter("r_ordernumber"));
			ScoreDaoImpl score=new ScoreDaoImpl();
			int n=score.judge(r_ordernumber);
			PrintWriter writer=response.getWriter();
			writer.print(JSONObject.toJSONString(new Result<String>(n, null, "")));
			writer.flush();
			writer.close();
			return;
		}
		String addressID=request.getParameter("addressID");
		String suggest=request.getParameter("suggest");
		String r_ordernumber=request.getParameter("r_ordernumber");
		String index=request.getParameter("index");
		String s_id=request.getParameter("s_id");
		String a_id=request.getParameter("a_id");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		ScoreDaoImpl db = new ScoreDaoImpl();
		try {
			int n = db.insert(addressID, suggest, r_ordernumber, index,s_id,a_id,now);
			PrintWriter writer=response.getWriter();
			if(n==1) {
			writer.print(JSONObject.toJSONString(new Result<String>(n, null, "添加成功")));
			}else {
				writer.print(JSONObject.toJSONString(new Result<String>(n, null, "添加失败")));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
