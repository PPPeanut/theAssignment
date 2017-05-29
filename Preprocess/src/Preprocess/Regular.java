package Preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Regular {


	
	public static void wordSeg(String inFile,String outFile) throws IOException{
		
		String news=null;
        String regEx = "<content.*?/content>";
        Pattern pattern = Pattern.compile(regEx);
        StringBuffer content=new StringBuffer();		
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)),"GBk"));
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFile)),"GBk"));
		StringBuffer strb=new StringBuffer();
		String str;
		while((str=br.readLine())!=null){	
	        	strb.append(str);
	        }
		
		br.close();
		news=strb.toString();
		
		Matcher matcher = pattern.matcher(news);
		
		
	    while(matcher.find()){
		       // System.out.println(matcher.group());
	    	content.append(matcher.group()+"\r\n");
	    	
		   } 
		bw.write(content.toString());
		bw.flush();
		bw.close();
	}
		
	public static void main(String[] args) throws IOException {
		
		  new Regular().wordSeg("news.txt","content.txt");
/*		        // 要验证的字符串
		        String str = "22bb23";
		        // 正则表达式规则
		        String regEx = "<content>*</content>";
		        // 编译正则表达式
		        Pattern pattern = Pattern.compile(regEx);
		        Matcher matcher = pattern.matcher(str);
		        // 查找字符串中是否有匹配正则表达式的字符/字符串,并打印出来
		       if(matcher.find()){
		        System.out.println(matcher.group());
		       } 
		    */
		     
	}

}
