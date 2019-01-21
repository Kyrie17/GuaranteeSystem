package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RepairFormDao;
import dao.RepairFormDaoImpl;
import dao.StudentDao;
import dao.StudentDaoImpl;
import model.Student;

/**
 * 计算你填写的报修单超过百分之几的同学
 */
@WebServlet("/CalculatePercent")
public class CalculatePercent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CalculatePercent() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StudentDao studentDao = new StudentDaoImpl();
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		
		String username = request.getParameter("username");
		int more = 0;	//该同学报修单超过的其他同学的数量
		int myCount = 0;	//该同学的报修单数量
		int percent = 0;	//分数
		//获取该学生的报修单数
		myCount = repairFormDao.getSelfRFNum(username);
		
		//获取所有学生, 并获取每个人的报修单数
		List<Student> student = studentDao.getAllStudent();
		for(int i = 0; i < student.size(); i++) {
			int count = repairFormDao.getSelfRFNum(student.get(i).getStu_id());
			if(myCount > count) {
				more++;
			}
		}
		
		if(student.size() != 0) {
			percent = more / student.size();
		}
		
		
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
