package Preprocess;

import java.io.*;
import java.util.HashSet;
import java.util.Set;


public class SelectSentences {

	public static void main(String[] args) {
		String FileIn="src\\data\\data_pos.txt";
		String FileOut="src\\data\\data_pos_ReDuplication.txt";
		Select(FileIn, FileOut, 1,2);
		
	}
	/*
	 * @param FileIn 
	 * @param FileOut
	 * @throws Exception
	 */
	public static void Select(String FileIn,String FileOut,int a, int b) {
		try {
				if (a<=b &&b<=getTotalLines(FileIn)&&b>0){
					System.out.println("整个文本的行数为："+getTotalLines(FileIn));
					System.out.println("ok");
		
					BufferedReader br=new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File(FileIn))), "gbk"));
					OutputStreamWriter wr =new OutputStreamWriter(new FileOutputStream(new File(FileOut)), "utf-8");
			
					Set<String> set = new HashSet<String>();		
					
					String temp=new String();
					int i=1;
					while ((temp=br.readLine()) !=null){
							if(temp !="\r\n" ){
								if (i>=a&&i<=b){
										set.add(temp);
									
								}
								i++;
								
								}
							temp=br.readLine();
					}
					br.close();
					
					
					for(String sentences:set){
						System.out.println(sentences);
						wr.write(sentences+"\r\n");
					}
					wr.flush();
					wr.close();
		
				}
				else if (b==0) {
					System.out.println("第二个数应该大于0");
				}
				else if (a>b){
					System.out.println("第一个数应该小于第二个数");
				}
				else if (a>b){
					System.out.println("第一个数应该小于第二个数");
				}
				else if (b>getTotalLines(FileIn)){
					System.out.println("整个文本的行数为"+getTotalLines(FileIn)+"\r\n"+"b应该小于"+getTotalLines(FileIn));		//实际大小为去重后的行数
				}
		} catch (Exception e) {
			System.out.println("找不到文件");
		}
	}
	 static int getTotalLines(String fileName) throws IOException { 
         BufferedReader in = new BufferedReader(new InputStreamReader( 
                         new FileInputStream(fileName))); 
         LineNumberReader reader = new LineNumberReader(in); 
         String s = reader.readLine(); 
         int lines = 0; 
         while (s != null) { 
                 lines++; 
                 s = reader.readLine(); 
         } 
         reader.close(); 
         in.close(); 
         return lines; 
	 } 

}