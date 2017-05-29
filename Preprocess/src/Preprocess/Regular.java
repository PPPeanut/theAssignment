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
/*		        // Ҫ��֤���ַ���
		        String str = "22bb23";
		        // ������ʽ����
		        String regEx = "<content>*</content>";
		        // ����������ʽ
		        Pattern pattern = Pattern.compile(regEx);
		        Matcher matcher = pattern.matcher(str);
		        // �����ַ������Ƿ���ƥ��������ʽ���ַ�/�ַ���,����ӡ����
		       if(matcher.find()){
		        System.out.println(matcher.group());
		       } 
		    */
		     
	}

}
