package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NoticeInformDao;
import dao.NoticeInformDaoImpl;

/**
 * Servlet implementation class LoadFileServlet
 */
@WebServlet("/LoadFileServlet")
public class LoadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeInformDao noticeInformDao = new NoticeInformDaoImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置获取表单的字符编码，防止乱码
				//获取文件路径和名字
				String fileName = new String(request.getParameter("fileName").getBytes("iso-8859-1"), "utf-8");
				String filePath="";
				filePath=noticeInformDao.findPath(fileName);
				// if (filePath == null )
				response.setHeader("Content-disposition", "attachment;filename=" + fileName);
				try {
					File f = new File(filePath);
					FileInputStream in = new FileInputStream(f);
					OutputStream out = response.getOutputStream();
					int n = 0;
					byte[] b = new byte[500];
					while ((n = in.read(b)) != -1) {
						out.write(b, 0, n);
					}
					out.close();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("发生错误");
				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
