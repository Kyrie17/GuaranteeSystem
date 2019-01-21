package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;

import dao.NoticeInformDao;
import dao.NoticeInformDaoImpl;
import model.NoticeInform;
import model.Result;
import tools.CommonUtils;

/**
 * 上传公告文件
 */
@WebServlet("/UpLoadServlet")
public class UpLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 // 上传文件存储目录
   private static final String UPLOAD_DIRECTORY = "upload";

   // 上传配置
   private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
   private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
   private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
   
   public UpLoadServlet() {
       super();
   }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		
		// 检测是否为多媒体上传
       if (!ServletFileUpload.isMultipartContent(request)) {
           // 如果不是则停止ֹ
           PrintWriter writer = response.getWriter();
           writer.println("Error：表单必须包含  enctype=multipart/form-data");
           writer.flush();
           return;
       }

       // 配置上传参数
       DiskFileItemFactory factory = new DiskFileItemFactory();
       // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
       factory.setSizeThreshold(MEMORY_THRESHOLD);
       // 设置临时存储目录
       factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

       ServletFileUpload upload = new ServletFileUpload(factory);
        
       // 设置最大文件上传值ֵ
       upload.setFileSizeMax(MAX_FILE_SIZE);
        
       // 设置最大请求值 (包含文件和表单数据)
       upload.setSizeMax(MAX_REQUEST_SIZE);
       
       // 中文处理
       upload.setHeaderEncoding("UTF-8"); 

       // 构造临时路径来存储上传的文件
       // 这个路径相对当前应用的目录
       String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;
      
       
       // 如果目录不存在则创建
       File uploadDir = new File(uploadPath);
       if (!uploadDir.exists()) {
           uploadDir.mkdir();
       }

       int m = 0;
       try {
           // 解析请求的内容提取文件数据
           @SuppressWarnings("unchecked")
           List<FileItem> formItems = upload.parseRequest(request);
  
           
           if (formItems != null && formItems.size() > 0) {
               // 迭代表单数据

        	  
               for (FileItem item : formItems) {
                   // 处理不在表单中的字段
            	   
                   if (!item.isFormField()) {
                       String fileName = new File(item.getName()).getName();
                       String filePath = uploadPath + File.separator + fileName;
                       File storeFile = new File(filePath);
                       // 在控制台输出文件的上传路径
                       System.out.println(filePath);
                       // 保存文件到硬盘
                       item.write(storeFile);
                       
                       response.getWriter().print(JSONObject.toJSON(new Result(1, m, "文件上传成功")));
                       
                       
                       String upLoader = "1";
                   /* Cookie[] cookies = request.getCookies();
               		String token = "";
               		for (Cookie cookie : cookies) {
               		    switch(cookie.getName()){
               		        case "cookie":
               		            token = cookie.getValue();
               		            break;
               		        default:
               		            break;
               		    }
               		}
               		//获取token
               		String upLoader = "";
               		
               		try {
               			upLoader = CommonUtils.parseJWT(token).getSubject();
               			
               		} catch (Exception e) {
               			e.printStackTrace();
               		}
                       */
                       
                       
                       
                    Cookie[] cookies = request.getCookies();
               		String token = "";
               		if(cookies!=null)
               		{
               		
               		for (Cookie cookie : cookies) {
               		    switch(cookie.getName()){
               		        case "manage_cookie":
               		            token = cookie.getValue();
               		            break;
               		        default:
               		            break;
               		    }
               		}
               		}

               	
               		
               		try {
               			upLoader = CommonUtils.parseJWT(token).getSubject();
               		} catch (Exception ex) {
               		 response.getWriter().print(JSONObject.toJSON(new Result(1, "错误信息: " + ex.getMessage(), "")));
               		}
                       
                       
                      
                       Date date = new Date();
                       DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                       String upLoadDate = df.format(date);
                       int downLoadNum = 0;
                       NoticeInformDao noticeInformDao = new NoticeInformDaoImpl();
                       
                       //封装公告信息
                       NoticeInform model  = new NoticeInform();
                       model.setUpLoader(upLoader);
                       model.setFileName(fileName);
                       model.setFilePath(filePath);
                       model.setDownLoadNum(downLoadNum);
                       model.setUpLoadDate(upLoadDate);
                       m = noticeInformDao.upLoadNoticeInform(model);
                       
                   }
               }
           }
       } catch (Exception ex) {
       response.getWriter().print(JSONObject.toJSON(new Result(1, "错误信息: " + ex.getMessage(), "")));
       }
       
      	response.getWriter().print(JSONObject.toJSON(new Result(1, m, "")));
//       	response.sendRedirect("http://localhost:8080/GuaranteeSystem/html/ManageOperate.html");	//请求转发
	}

}
