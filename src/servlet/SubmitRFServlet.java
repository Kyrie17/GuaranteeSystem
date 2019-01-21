package servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;

import dao.RepairFormDao;
import dao.RepairFormDaoImpl;
import dao.RepairManDao;
import dao.RepairManDaoImpl;
import model.RepairForm;
import model.Result;
import tools.AutomaticAssignUtil;
import tools.DateMinusUtil;

/**
 * 提交报修表单
 */
@WebServlet("/SubmitRFServlet")
public class SubmitRFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 // 上传文件存储目录
   private static final String UPLOAD_DIRECTORY = "upload";

   // 上传配置
   private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
   private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
   private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
  
   public SubmitRFServlet() {
       super();
   }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
	
		
		//声明变量
		int orderNumber = 0;
		String username = "1";
		String phone = "1";
		int serType = 1;
		String serAdd = "";
		String serInform = "";
		String serTime = "";
		int judgeState = -1;
		String detailTime = "";
		String repairMan = "";
		

		//获取报修信息
		username = request.getParameter("username").trim();
		phone = request.getParameter("phone").trim();
		serType = Integer.parseInt(request.getParameter("serType").trim());
		serAdd = new String(request.getParameter("serAdd").getBytes("ISO-8859-1"), "UTF-8");
		serInform = new String(request.getParameter("serInform").getBytes("ISO-8859-1"), "UTF-8"); 
		serTime = request.getParameter("serTime").trim();
		detailTime = request.getParameter("detailTime").trim();

		//自动分配维修工人
		repairMan = repairMan = AutomaticAssignUtil.automaticAssign(serType);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		System.out.println("date = " + date);
		
		
		
		// 检测是否为多媒体上传
       if (!ServletFileUpload.isMultipartContent(request)) {
           // 如果不是则停止
           response.getWriter().print(JSONObject.toJSON(new Result<String>(-1, null, "Error: 表单必须包含 enctype=multipart/form-data")));
           return;
       }

       
       // 设置临时存储目录// 配置上传参数
       DiskFileItemFactory factory = new DiskFileItemFactory();
       // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
       factory.setSizeThreshold(MEMORY_THRESHOLD);
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
       String uploadPath = getServletContext().getRealPath("/") +File.separator + UPLOAD_DIRECTORY;
       // 如果目录不存在则创建
       File uploadDir = new File(uploadPath);
       if (!uploadDir.exists()) {
           uploadDir.mkdir();
       }
     
       
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
                       response.getWriter().print(JSONObject.toJSON(new Result<String>(1, null, "图片上传成功!")));
                       
                       
               		//获取Dao的实现
               		RepairFormDaoImpl db = new RepairFormDaoImpl();
               		//实例化RepairForm
               		RepairForm rf = new RepairForm();
               		rf.setOrderNumber(orderNumber);
               		rf.setUsername(username);
               		rf.setPhone(phone);
               		rf.setSerType(serType);
               		rf.setSerAdd(serAdd);
               		rf.setSerInform(serInform);
               		rf.setSerTime(serTime);
               		rf.setJudgeState(judgeState);
               		rf.setFile_path(fileName);
               		rf.setRepairMan(repairMan);
               		rf.setDetailTime(detailTime);
               		//将报修单信息保存到数据库
               		int m = db.submitRepairForm(rf);
               		if(m!=0) {//报修单信息提交成功
               			response.getWriter().print(JSONObject.toJSON(new Result<String>(1, null, "报修单提交成功")));
               			response.sendRedirect("/GuaranteeSystem/html/Person.html");
               		}
               		else
               			response.getWriter().print(JSONObject.toJSON(new Result<String>(-1, null, "报修单提交失败")));
                   }
               }
           }
       } catch (Exception ex) {
       	response.getWriter().print(JSONObject.toJSON(new Result<String>(-1, null, "错误：报修单提交失败！")));
       }
	}
}
