package Preprocess;
/*
 * @param ·Ö´Ê
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.hankcs.hanlp.HanLP;



public class WordSegmente {

	public static void wordSeg(String inFile,String outFile) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)),"GBk"));
		StringBuffer strb=new StringBuffer();
		String str;
		String s;
		
		while((str=br.readLine())!=null){
			String[] starr=HanLP.segment(str).toString().split(",");		
	        for(String st:starr){
	        	String[] sr=st.split("/");
	        	//strb.append(sr[0]+"  ");
	        	s=sr[0]+"  ";
	        	s=s.substring(1,s.length());
	        	strb.append(s);
	        }strb.append("\r\n");
		}
		br.close();
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFile)),"GBk"));
		bw.write(strb.toString());
		bw.flush();
		bw.close();
	}
	
	public static void main(String[] args) throws IOException {

		wordSeg("src\\data\\content.txt", "src\\data\\fenci.txt");
	}
}

