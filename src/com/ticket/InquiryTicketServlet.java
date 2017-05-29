package com.ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InquiryTicketServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取起始地、目的地、当天时间
		String startPlace=req.getParameter("start");
		String endPlace=req.getParameter("end");
		String startTime=req.getParameter("time");
		//System.out.println(startPlace+endPlace+startTime);
		
		Client user=(Client)req.getSession().getAttribute("User");
		//查询余票
		String json=user.inquiryTicket(startPlace, endPlace, startTime);	
		//System.out.println(json);
		//user.updateSecreStr(json);//更新secreStr
		
		ArrayList<List<String>> all=user.showStation(json);
		req.getSession().setAttribute("stationMessage", all);//存放到session
		
/*		for (List<String> list : all) {
			for (String string : list) {
				System.out.println(string);
				System.out.println(1);
			}
		}*/
		
		


		resp.sendRedirect("../successLogin.jsp");

	}
	
	



}
