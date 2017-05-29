package com.ticket;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

public class monitorTicket extends HttpServlet{
	


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Client user=(Client)req.getSession().getAttribute("User");
		String []s=null;
		int keyNum=-1;
		for(int i=0;i<15;i++){
			if(req.getParameterValues(Integer.toString(i))!=null){
				keyNum=i;

			}
		}		
		
		doBook(user,keyNum,req,resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	
	public void doBook(Client user,int keyNum,HttpServletRequest req,HttpServletResponse resp){
		try {
			String s1=user.checkUser();
			//System.out.println(s1);
			if(CheckJson.checkUser2(s1)){
				String s2=user.submitOrderRequest(keyNum);
				//System.out.println(s2);
				if(CheckJson.checkSubmit(s2)){
					System.out.println("true");
					
					Timer timer=new Timer();
					timer.scheduleAtFixedRate(new TimerTask(){
						public void run(){
								try {
									user.PassengerInf();
									user.getPassengerDTOs();
									user.checkOrderInfo();
									user.getQueueCount();
									System.out.println(user.confirmSingleForQueue());
									if(CheckJson.checkBuy(user.confirmSingleForQueue())){
										System.out.println("成功");	
										timer.cancel();
									}else{
										System.out.println("正在购票中，请稍等......");
									}
								} catch (ParseException e) {
									e.printStackTrace();
								} catch (ClientProtocolException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} 
						}
					}, 1000,2000);						
				}
			}else{
				System.out.println("登录信息过期，请重新登录");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}




