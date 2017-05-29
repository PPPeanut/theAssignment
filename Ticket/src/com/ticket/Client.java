package com.ticket;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Client {
		
	private static String cookies;
	private HttpResponse response = null;
	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private static ArrayList<String> secretStr=null;
	private String startPlace=null;
	private String endPlace=null;
	private String startTime=null;
	private int keyNum=-1;
	private String passengerTicketStr=null;
	private String oldPassengerStr=null;
	private String token=null;
	private String leftTicketStr=null;
	private String key_check_isChange=null;
	private JSONArray ja=null;
	
	private String train_no =null;		
	private String stationTrainCode =null;
	private String fromStationTelecode =null;
	private String toStationTelecode = null;
	private String train_location = null;
	
	public void getCookie() {
		String s = null;
		String cookie1 = null, cookie2 = null;	
		HttpGet getcookies = new HttpGet("https://kyfw.12306.cn/otn/login/init");
			try {
				response = httpclient.execute(getcookies);
			} catch (IOException e) {
				e.printStackTrace();
			}
			s = response.getFirstHeader("Set-Cookie").toString();

			if (s != null) {
				cookie1 = s.substring(s.indexOf("JSESSIONID"));
			}
			s = response.getLastHeader("Set-Cookie").toString();
			if (s != null) {
				cookie2 = s.substring(s.indexOf("BIGipServerotn"));
			}
			cookies = cookie1 + cookie2;
		
	}

	public InputStream imageDownload() {
		HttpGet getvCode = new HttpGet(
				"https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&0.8355952971810705");
		try {
			response = httpclient.execute(getvCode);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return entity.getContent();
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("下载出错");
		}
		return null;
	}

	
	//生成POST请求消息体,验证码
	public String postIcode(String vcode) {
		HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn");
		post.addHeader("Accept", "*/*");
		post.addHeader("Accept-Encoding", "gzip, deflate, br");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Cookie", cookies);
		post.addHeader("Host", "kyfw.12306.cn");
		post.addHeader("Origin", "https://kyfw.12306.cn");
		post.addHeader("Referer", "https://kyfw.12306.cn/otn/login/init");
		post.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		List<BasicNameValuePair> postparams = new ArrayList<BasicNameValuePair>();
		postparams.add(new BasicNameValuePair("randCode", vcode));
		postparams.add(new BasicNameValuePair("rand", "sjrand"));

		try {
			post.setEntity(new UrlEncodedFormEntity(postparams));   //设置请求参数
			response = httpclient.execute(post);    //发送请求，返回一个HttpResponse
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	
	//生成POST请求消息体，账号密码和验证码
	public String postAccount(String Acc , String Pwd , String vcode) throws Exception {
		HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/login/loginAysnSuggest");
		post.addHeader("Accept", "*/*");
		post.addHeader("Accept-Encoding", "gzip, deflate, br");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.81");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Cookie", cookies);
		post.addHeader("Host", "kyfw.12306.cn");
		post.addHeader("Origin", "https://kyfw.12306.cn");
		post.addHeader("Referer", "https://kyfw.12306.cn/otn/login/init");
		post.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		List<BasicNameValuePair> form = new ArrayList<BasicNameValuePair>();
		form.add(new BasicNameValuePair("loginUserDTO.user_name", Acc));
		form.add(new BasicNameValuePair("userDTO.password", Pwd));
		form.add(new BasicNameValuePair("randCode", vcode));

		post.setEntity(new UrlEncodedFormEntity(form));

		response = httpclient.execute(post);

		return EntityUtils.toString(response.getEntity(), "UTF-8");
	}
	
	

	//查询余票请求
	public String inquiryTicket(String startPlace,String endPlace,String startTime){
		this.startPlace=startPlace;
		this.endPlace=endPlace;
		this.startTime=startTime;
		
		HttpGet getTickets = new HttpGet("https://kyfw.12306.cn/otn/leftTicket/query?"+"leftTicketDTO.train_date="+startTime+"&leftTicketDTO.from_station="+startPlace+"&leftTicketDTO.to_station="+endPlace+"&purpose_codes=ADULT");
		try {
			response = httpclient.execute(getTickets);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//查询车票信息
	public ArrayList<List<String>> showStation(String json){
		secretStr=new ArrayList<String>();
		JSONObject jo=JSONObject.fromString(json).getJSONObject("data");
		ja=JSONArray.fromObject(jo.getString("result"));
		ArrayList<List<String>> list=new ArrayList<List<String>>();  //存放查询信息
		for(int i=0;i<ja.length();i++){
			List<String> al=new ArrayList<String>();//存放一行查询信息
			String line = ja.get(i).toString();
			String[] tlist = line.split("\\|");
			if(tlist[31].equals("")){
				tlist[31] = "无";
			}
			//System.out.println(tlist[0]);
			secretStr.add(tlist[0]);
			al.add(tlist[3]);			//列车代号
			al.add(tlist[6]);			//始站
			al.add(tlist[7]);			//终站
			al.add(tlist[8]);			//出发时间
			al.add(tlist[9]);   		//到站时间
			al.add(tlist[31]);			//一等座余票
			list.add(al);
			
		}
		return list;
	}
	
	
	//预定时确认是否登录
	public String checkUser() throws Exception{
		HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/login/checkUser");
		post.addHeader("Accept", "*/*");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Cookie",cookies);
		post.addHeader("Host", "kyfw.12306.cn");
		post.addHeader("Origin", "https://kyfw.12306.cn");
		post.addHeader("Referer", "https://kyfw.12306.cn/otn/login/init");
		post.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		response = httpclient.execute(post);
		return EntityUtils.toString(response.getEntity(),"UTF-8");
	}
	
	
	//提交预定申请
	public String submitOrderRequest(int keyNum) throws ClientProtocolException, IOException{
		this.keyNum=keyNum;
		HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest");
		post.addHeader("Accept", "*/*");
		post.addHeader("Accept-Encoding", "gzip, deflate, br");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Cookie", cookies);
		post.addHeader("Host", "kyfw.12306.cn");
		post.addHeader("Origin", "https://kyfw.12306.cn");
		post.addHeader("Referer", "https://kyfw.12306.cn/otn/leftTicket/init");
		post.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		List<BasicNameValuePair> form=new ArrayList<BasicNameValuePair>();
		//System.out.println(URLDecoder.decode(secreStr.get(keyNum)));
		form.add(new BasicNameValuePair("secretStr", URLDecoder.decode(secretStr.get(keyNum))));
		form.add(new BasicNameValuePair("train_date",startTime));
		form.add(new BasicNameValuePair("back_train_date",startTime));
		form.add(new BasicNameValuePair("tour_flag", "dc"));
		form.add(new BasicNameValuePair("purpose_codes", "ADULT"));
		form.add(new BasicNameValuePair("query_from_station_name", "广州"));
		form.add(new BasicNameValuePair("query_to_station_name", "上海"));
		post.setEntity(new UrlEncodedFormEntity(form,"UTF-8"));
		response = httpclient.execute(post);
		return EntityUtils.toString(response.getEntity(),"UTF-8");

	}
	
	//返回的是一个HTML字符串，从中提取SubmitToken、leftTicketStr、key_check_isChange
	public void PassengerInf() throws ParseException, IOException {
		HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/initDc");
		post.addHeader("Accept", "*/*");
		post.addHeader("Accept-Encoding", "gzip, deflate, br");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Cookie",cookies);
		post.addHeader("Host", "kyfw.12306.cn");
		post.addHeader("Origin", "https://kyfw.12306.cn");
		post.addHeader("Referer", "https://kyfw.12306.cn/otn/leftTicket/init");
		post.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		response = httpclient.execute(post);
		String s = EntityUtils.toString(response.getEntity(), "UTF-8");
		extractKey(s);//更新SubmitToken、leftTicketStr、key_check_isChange
	}
	
	public void extractKey(String s){
		Matcher m = Pattern.compile("globalRepeatSubmitToken\\s=\\s'(\\w+)'").matcher(s);
		while (m.find()) {
			token = m.group(1);
		//	System.out.println(token);
		}
		m = Pattern.compile("ticketInfoForPassengerForm=(.+?);").matcher(s);
		while (m.find()) {
			String str = m.group(1);
			str = str.replaceAll("\'", "\"");
			JSONObject jsonobj = JSONObject.fromString(str);
			leftTicketStr = jsonobj.getString("leftTicketStr");
			System.out.println(leftTicketStr);
			key_check_isChange = jsonobj.getString("key_check_isChange");
			System.out.println(key_check_isChange);
		}
	}
	
	
	//获取关联乘客信息
	public void getPassengerDTOs() throws ClientProtocolException, IOException{
		HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs");
		post.addHeader("Accept", "*/*");
		post.addHeader("Accept-Encoding", "gzip, deflate, br");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Cookie",cookies);
		post.addHeader("Host", "kyfw.12306.cn");
		post.addHeader("Origin", "https://kyfw.12306.cn");
		post.addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc");
		post.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		List<BasicNameValuePair> form = new ArrayList<BasicNameValuePair>();
		form.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", token));//
		post.setEntity(new UrlEncodedFormEntity(form));
		response = httpclient.execute(post);
		getPassengerTicketStr(EntityUtils.toString(response.getEntity(), "UTF-8"));
	}
	
	//处理json
	public void getPassengerTicketStr(String json){
		passengerTicketStr = CheckJson.parsepassengerTicketStr(json);
	//	System.out.println(passengerTicketStr);
		oldPassengerStr = CheckJson.parseoldPassengerStr(json);
	//	System.out.println(oldPassengerStr);
	}
	
	
	
	
	
	
	//确认车票信息
	public String checkOrderInfo() throws ClientProtocolException, IOException{
		HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo");
		post.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Cookie", cookies);
		post.addHeader("Host", "kyfw.12306.cn");
		post.addHeader("Origin", "https://kyfw.12306.cn");
		post.addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc");
		post.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		List<BasicNameValuePair> form=new ArrayList<BasicNameValuePair>();
		form.add(new BasicNameValuePair("cancel_flag", "2"));
		form.add(new BasicNameValuePair("bed_level_order_num", "000000000000000000000000000000"));
		form.add(new BasicNameValuePair("passengerTicketStr", passengerTicketStr));//
		form.add(new BasicNameValuePair("oldPassengerStr", oldPassengerStr));//
		form.add(new BasicNameValuePair("tour_flag", "dc"));
		form.add(new BasicNameValuePair("randCode", ""));
		form.add(new BasicNameValuePair("_json_att", ""));
		form.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN",token));//
		post.setEntity(new UrlEncodedFormEntity(form,"utf-8"));
		response = httpclient.execute(post);
		return EntityUtils.toString(response.getEntity(),"utf-8");
	}
	
	
	public String getQueueCount() throws ParseException, IOException {
		LockthatTrain(); // 将数据报所要的参数包装好
		HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount");
		post.addHeader("Accept", "*/*");
		post.addHeader("Accept-Encoding", "gzip, deflate, br");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Cookie",cookies);
		post.addHeader("Host", "kyfw.12306.cn");
		post.addHeader("Origin", "https://kyfw.12306.cn");
		post.addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc");
		post.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		List<BasicNameValuePair> form = new ArrayList<BasicNameValuePair>();
		// 以下参数都需动态修改
		// seatType = M 支持一等座
		form.add(new BasicNameValuePair("train_date",getDateInfo()));
		form.add(new BasicNameValuePair("train_no", train_no));
		form.add(new BasicNameValuePair("stationTrainCode", stationTrainCode));
		form.add(new BasicNameValuePair("seatType", "M"));
		form.add(new BasicNameValuePair("fromStationTelecode", fromStationTelecode));
		form.add(new BasicNameValuePair("toStationTelecode", toStationTelecode));
		form.add(new BasicNameValuePair("leftTicket", leftTicketStr));
		form.add(new BasicNameValuePair("purpose_codes", "00"));
		form.add(new BasicNameValuePair("train_location", train_location));
		form.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", token));
		post.setEntity(new UrlEncodedFormEntity(form, "UTF-8"));
		response = httpclient.execute(post);
		return EntityUtils.toString(response.getEntity(), "UTF-8");
	}
	
	public String getDateInfo(){
		String s1=null;
		String s2=" 00:00:00 GMT+0800 (中国标准时间)";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("EEE MMM dd yyyy",Locale.ENGLISH);
		s1=sdf.format(date);
		//System.out.println(s1+s2);
		return s1+s2;
	}
	
	//更新车票信息
	public void LockthatTrain(){
		for (int i = 0; i < ja.length(); i++) {
			String line = ja.get(i).toString();
			String[] tlist = line.split("\\|");
			if (tlist[0].equals(secretStr.get(keyNum))) {
				train_no = tlist[2];				
				stationTrainCode = tlist[3];			
				fromStationTelecode = tlist[4];				
				toStationTelecode = tlist[5];
				train_location = tlist[15];
		/*		System.out.println(train_no);
				System.out.println(stationTrainCode);
				System.out.println(fromStationTelecode);
				System.out.println(toStationTelecode);
				System.out.println(train_location);*/
				break;
			}
		}
	}
	
	
	public String confirmSingleForQueue() throws ParseException, IOException{
		HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue");
		post.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Accept-Language", "z4h-CN,zh;q=0.8");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Cookie", cookies);
		post.addHeader("Host", "kyfw.12306.cn");
		post.addHeader("Origin", "https://kyfw.12306.cn");
		post.addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc");
		post.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		List<BasicNameValuePair> form = new ArrayList<BasicNameValuePair>();
		form.add(new BasicNameValuePair("passengerTicketStr", passengerTicketStr));
		form.add(new BasicNameValuePair("oldPassengerStr", oldPassengerStr));
		form.add(new BasicNameValuePair("randCode", ""));
		form.add(new BasicNameValuePair("purpose_codes", "00"));
		form.add(new BasicNameValuePair("key_check_isChange", key_check_isChange));
		form.add(new BasicNameValuePair("leftTicketStr", leftTicketStr));
		form.add(new BasicNameValuePair("train_location", train_location));
		form.add(new BasicNameValuePair("choose_seats", ""));
		form.add(new BasicNameValuePair("seatDetailType", "000"));
		form.add(new BasicNameValuePair("roomType", "00"));
		form.add(new BasicNameValuePair("dwAll", "N"));
		form.add(new BasicNameValuePair("_json_att", ""));
		form.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", token));
		post.setEntity(new UrlEncodedFormEntity(form,"UTF-8"));
		response = httpclient.execute(post);
		return EntityUtils.toString(response.getEntity(),"UTF-8");
	}
	

	
	
	
	
}
