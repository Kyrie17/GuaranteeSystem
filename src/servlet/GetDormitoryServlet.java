package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.AddressDao;
import dao.AddressDaoImpl;
import model.Area;
import model.Build;
import model.Result;


@WebServlet("/GetDormitoryServlet")
public class GetDormitoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private AddressDao addressDao=new AddressDaoImpl();
	
    public GetDormitoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method=request.getParameter("method");
		if(null!=method&&!"".equals(method)) {
			if("initArea".equals(method)) {
				initArea(request,response);
			}else if("getBuild".equals(method)) {
				getBuild(request,response);
			}
		}
	}
	
	/*
	 * 查询对应区域
	 * @param request
	 * @param response
	 */
	public void initArea(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		try {
			PrintWriter out=response.getWriter();
			List<Area> areaList=addressDao.getAllArea();
			out.print(JSONObject.toJSON(new Result(1,areaList,"获得区域")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/*
	 * 查询对应的栋数
	 */
	private void getBuild(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		try {
			PrintWriter out=response.getWriter();
			int code=Integer.parseInt(request.getParameter("code"));
			List<Build> buildList=addressDao.getAllBuildByArea(code);
			out.print(JSONObject.toJSON(new Result(1,buildList,"获得区域")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
