package com.ticket;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class loginServlet extends HttpServlet {

	private String username;
	private String password;
	private String codeNum;
	private ArrayList<String>as;
	private Client user=null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		as=new ArrayList<>();
		username=(String)req.getParameter("username");
		password=(String)req.getParameter("password");
		codeNum=(String)req.getParameter("a");
		translateCode(codeNum);
		codeNum=as.toString().substring(1,(as.toString().length()-1));	
		try {
			checkLogin(username,password,codeNum,req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void checkLogin(String username,String password,String codeNum,HttpServletRequest req, HttpServletResponse resp) throws Exception{

		user=(Client)req.getSession().getAttribute("User");
		if(CheckJson.VCodeisTrue(user.postIcode(codeNum))){
			System.out.println("验证码正确");
			if(CheckJson.isLogined(user.postAccount(username, password, codeNum))){
				System.out.println("登录成功");			
				req.getRequestDispatcher("successLogin.jsp").forward(req, resp);
			}else{
				System.out.println("密码错误");
				resp.sendRedirect("/Ticket/index.jsp");
			}
		}else{
			System.out.println("验证码错误");
			resp.sendRedirect("/Ticket/index.jsp");
		}
	
	}
	
	public void translateCode(String codeNum){
		String[] s= codeNum.split(",");
		for(int i=0;i<s.length;i=i+2){
			convert(s[i],s[i+1]);
		}
	}

	public void convert(String s1,String s2){
		int x=Integer.parseInt(s1);
		int y=Integer.parseInt(s2);
		if (y >= 30 && y <= 88) {
			if (x >= 0 && x <= 75) {
				as.add(40 + "," + 43);
			} else if (x > 75 && x <= 150) {
				as.add(113 + "," + 43);
			} else if (x > 150 && x <= 225) {
				as.add(182 + "," + 43);
			} else if (x > 225 && x <= 300) {
				as.add(250 + "," + 43);
			}
		} else if (y > 88 && y <= 145) {
			if (x >= 0 && x <= 75) {
				as.add(40 + "," + 117);
			} else if (x > 75 && x <= 150) {
				as.add(113 + "," + 117);
			} else if (x > 150 && x <= 225) {
				as.add(182 + "," + 117);
			} else if (x > 225 && x <= 300) {
				as.add(250 + "," + 117);
			}
		}
		
	}


}
