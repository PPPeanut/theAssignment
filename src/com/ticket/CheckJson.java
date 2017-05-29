package com.ticket;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CheckJson {
	public static boolean VCodeisTrue(String VCode) {
		JSONObject jsonObj  = JSONObject.fromString(VCode);		
		if(jsonObj.getJSONObject("data").getString("msg").equals("TRUE"))	return true;
		else{
			return  false;
		}
	}
	
	public static boolean isLogined(String Login){
		if (Login.indexOf("\"loginCheck\":\"Y\"") != -1) {
			return true;
		}else {
			return  false;
		}

	}		
	
	public static boolean checkUser2(String str){
		JSONObject jo=JSONObject.fromString(str);
		if(jo.getJSONObject("data").getString("flag").equals("true"))	return true;
		else{
			return  false;
		}
	}
	
	public static boolean checkSubmit(String str){
		JSONObject jo=JSONObject.fromString(str);
		if(jo.getString("status").equals("true")){return true;}
		return false;
	}
	
	
	public static boolean checkBook(String str){
		JSONObject jo=JSONObject.fromString(str);
		if(jo.getString("status").equals("true")){return true;}
		return false;
	}
	
	public static String parsepassengerTicketStr(String json) {
		JSONObject jsonObj = JSONObject.fromString(json);
		JSONArray jarr = JSONArray.fromObject(jsonObj.getJSONObject("data").getString("normal_passengers"));
		jsonObj = jarr.getJSONObject(0);
		/*****
		 * M ：座位类别，一等座、二等座、商务座等等 1 ：乘客类别，学生、成人等等 N ：暂视为定值
		 */
		return "M" + "," + jsonObj.getString("passenger_flag") + "," + "1" + "," + jsonObj.getString("passenger_name")
				+ "," + jsonObj.getString("passenger_id_type_code") + "," + jsonObj.getString("passenger_id_no") + ","
				+ jsonObj.getString("mobile_no") + "," + "N";
	}

	public static String parseoldPassengerStr(String json) {
		JSONObject jsonObj = JSONObject.fromString(json);
		JSONArray jarr = JSONArray.fromObject(jsonObj.getJSONObject("data").getString("normal_passengers"));
		jsonObj = jarr.getJSONObject(0);
		/***
		 * 以上例类似，”3_“暂视为定值
		 */
		return jsonObj.getString("passenger_name") + "," + jsonObj.getString("passenger_id_type_code") + ","
				+ jsonObj.getString("passenger_id_no") + "," + "3_";
	}
	
	public static boolean checkBuy(String s){
		JSONObject json=JSONObject.fromString(s);
		if(json.getJSONObject("data").getString("submitStatus").equals("true")){
			return true;
		}else{
			return false;
		}
}
	
}
