package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.NoticeInformDao;
import dao.NoticeInformDaoImpl;
import model.NoticeInform;
import model.Pager;
import model.Result;
import tools.Constant;
import tools.StringUtil;

/**
 * 分页显示公告信息
 */
@WebServlet("/ShowNoticeServlet")
public class ShowNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeInformDao noticeInformDao = new NoticeInformDaoImpl();

	public ShowNoticeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=UTF-8");

			// 校验pageNum参数输入合法性
			String pageNumStr = request.getParameter("pageNum");
			if (pageNumStr != null && !StringUtil.isNum(pageNumStr)) {
				response.getWriter().print(JSONObject.toJSON(new Result(-1, null, "参数错误")));
			}

			// 显示第几页数据
			int pageNum = Constant.DEFAULT_PAGE_NUM;
			if (pageNumStr != null && !"".equals(pageNumStr.trim())) {
				pageNum = Integer.parseInt(pageNumStr);
			}
			// 每页显示多少条记录
			int pageSize = Constant.DEFAULT_PAGE_SIZE;
			String pageSizeStr = request.getParameter("pageSize");
			if (pageSizeStr != null && !"".equals(pageSizeStr.trim())) {
				pageSize = Integer.parseInt(pageSizeStr);
			}
			// 调用service 获取查询结果
			Pager<NoticeInform> result = noticeInformDao.findNoticeForm(pageNum, pageSize);
			response.getWriter().print(JSONObject.toJSON(new Result(1, result, "")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
