package servlet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
 * 做出验证码图片，并保存当前验证码信息以供比较
 */
@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  StringBuffer sb=new StringBuffer();
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		/*
		 * 获取表单内的name用来接收对应数据
		 */
		String check=request.getParameter("check");
		if(check==null) {
			check="";
		}
		if(check.equals("1")) {
			String login_mess=request.getParameter("mess");
			if(login_mess==null) {
				login_mess="";
			}
			login_mess=login_mess.toLowerCase();
			if(!login_mess.equals(sb.substring(sb.length()-4,sb.length()))){
				response.getWriter().print("{\"msg\":\"验证码错误\"}");
			}
			else
			{
				response.getWriter().print("{\"msg\":\"\"}");
			}
		}
		else
		{
		request.getParameter("login_mess");
		/*
		 * 建立图象缓冲区
		 * 建立绘制图片的对象 Graphics
		 * 获取颜色
		 * 设置图片位置及大小
		 */
		BufferedImage bi=new BufferedImage(70,40,BufferedImage.TYPE_INT_BGR);
		Graphics g=bi.getGraphics();
		Random r=new Random();
		Color c=new Color(248,246,231);
		g.setColor(c);
		g.fillRect(0, 0, 70, 40);
		/*
		 * 生成随机产生字符范围
		 * 新建随机数对象，在所给字符串长度内生成随机数，通过对应位置读取对应字符
		 * 建立验证码字符串对象，并添加至4个（设置验证码为4个字符）
		 */
		char ch[]="abcdefghijklmn123456789".toCharArray();
		
		
		int len=ch.length;
		for(int i=0;i<4;i++) {
			int index=r.nextInt(len);
			//随机设置当前字符的颜色
			g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
			g.setFont(new Font(ch[index]+"", 10, 18));
			g.drawString(ch[index]+"", i*15+9, 20);
			sb.append(ch[index]);
		}
		/*
		 * 保存当前验证码字符串
		 * 绘制对应验证码的图象
		 */
		ImageIO.write(bi, "JPG", response.getOutputStream());
		
	}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
