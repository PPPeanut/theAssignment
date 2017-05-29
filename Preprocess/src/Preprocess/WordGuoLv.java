package Preprocess;
import java.io.BufferedInputStream;
/*
 * @param 停用词过滤
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class WordGuoLv {
	
	public static void wordGuo(String inFile,String outFile) throws IOException{
		 
		 String stopWordTable ="src\\stopwords.txt";
		 Set<String> stopWordSet = new HashSet<String>();
		 BufferedReader  br=new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File(inFile))), "GBK"));
	     BufferedReader br2 = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File(stopWordTable))), "GBK"));
	     BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFile))));
	                       
	     String stopWord ;
	     while((stopWord = br2.readLine()) != null){
	            stopWordSet.add(stopWord);        
	     }            
	            
	     String paragraph ;
	     StringBuffer finalStr = new StringBuffer();
	     /*
	      while((paragraph = br.readLine()) != null){   
	                String[] resultArray = paragraph.split(" ");
	                for(int i = 0; i< resultArray.length; i++){
	                    if(stopWordSet.contains(resultArray[i])){
	                        resultArray[i] = null;
	                    } 
	                }
	                int w=0;					//防止出现空行
	                
	                for(int i = 0; i< resultArray.length; i++){
	                    if(resultArray[i]!= null&&resultArray[i].length()>0){
	                    	resultArray[w]=resultArray[i].trim();
	                        finalStr = finalStr.append(resultArray[w]).append(" ");
	                        w++;
	                    }else{
		                	resultArray[w] = null ;
		                }
	                }
	               if(resultArray[0]!= null){
	            	   finalStr.append("\r\n");
	               }
	               
	            }
	      */
	     while((paragraph = br.readLine()) != null){   
             String[] resultArray = paragraph.split(" ");
             for(int i = 0; i< resultArray.length; i++){
                 if(!(stopWordSet.contains(resultArray[i]))){
                     finalStr.append(resultArray[i]);
                 } 
             }
             if (!(stopWordSet.contains(paragraph))) {
            	 finalStr.append("\r\n");
			}
             
	     }
	     		bw.write(finalStr.toString());
	     		
	     		br2.close();
	            br.close();  
	            
	            bw.flush();
	            bw.close();

	}
	
	public static void main(String[] args) throws IOException {
		
	        wordGuo("src\\data\\SelectHotWord.txt", "src\\data\\SelectHotWordFilter.txt");
	}
}
