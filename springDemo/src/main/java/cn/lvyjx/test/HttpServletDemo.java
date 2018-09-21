package cn.lvyjx.test;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/HttpServletDemo",initParams={@WebInitParam(name="test", value="ztest")})
public class HttpServletDemo extends HttpServlet {
  
	public ServletConfig config;
  
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		System.out.println("configName:"+config.getServletName());
		System.out.println("name:"+config.getInitParameter("test"));
		System.out.println("servletContext:"+config.getServletContext().hashCode());
		this.init();
	    }

	
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		        throws ServletException, IOException{
		     System.out.println("doGet method");
		     ServletContext context = req.getServletContext();
		     System.out.println("doGet servletContext:"+context.hashCode());
		     System.out.println("name value:"+context.getClassLoader());
		     System.out.println("name value init:"+context.getInitParameter("test"));
	 }
}
