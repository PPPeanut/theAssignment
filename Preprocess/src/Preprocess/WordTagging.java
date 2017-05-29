package Preprocess;
/*
 * @param ДЪад
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

public class WordTagging {
	 
	public static void main(String[] args) throws IOException {
		wordTag("src\\data\\fenci.txt", "src\\data\\postag.txt");
	}
	
	public static void wordTag(String inFile,String outFile) throws IOException{
		// TODO Auto-generated method stub
				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)),"GB2312"));
				StringBuilder sb=new StringBuilder();
				String s;
				String str;
				while((str=br.readLine())!=null){
					sb.append(str+"\n");
				}		
				List<Term> list=HanLP.segment(sb.toString());
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFile)),"GB2312"));
				bw.write(list.toString());
				br.close();
				bw.flush();
				bw.close();
	
   }
}
	
