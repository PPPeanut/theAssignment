package Preprocess;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class SelectHotWord {
	public static void main(String[] args) {
		String fileIn="src\\data\\wordTag.txt";				//这个数据集需要分词之后的，最好是去重去停的
		String fileOut="src\\data\\HotWord.txt";
		HotWord(fileIn, fileOut);

	}
	public static void HotWord(String fielIn,String fileOut){
		
		try {
			BufferedInputStream fin=new BufferedInputStream(new FileInputStream(new File(fielIn)));
			BufferedReader  br=new BufferedReader(new InputStreamReader(fin, "GBK"));
			OutputStreamWriter wr=new OutputStreamWriter(new FileOutputStream(new File(fileOut)), "GBK");
			String temp;
			
			//Map<String,String>map=new HashMap<String,String>();			
			//选好数据结构很重要,不用Map原因，不可重复
			Separate separate=new Separate();
			while ((temp=br.readLine())!=null) {
				String[] strings=temp.split(", ");
				for(int i=0;i<strings.length;i++){
					String[] strings2=strings[i].split("/");
					for(int w=0;w<strings2.length-1;){
						//map.put(strings2[w], strings2[w+1]);
						separate.putWord(strings2[w].toString());
						separate.putTag(strings2[w+1].toString());			//注意逻辑！w不能到length,因为这里还需要length+1；不然空指针错误
						break;
					}
					
				}
				
			}
			br.close();
			fin.close();
			//System.out.println(separate.toString());
			//System.out.println(separate.getSimilarWord("n"));
			
			
			wr.write(separate.getSimilarWord("n"));
			wr.flush();
			wr.close();
		} catch (Exception e) {
			System.out.println("File Not Found");
		}
	}
	static class Separate{
		private  StringBuffer word=new StringBuffer();
		private  StringBuffer tag=new StringBuffer();
		
		public Separate() {
		}
		public void putWord(String word) {
			this.word.append(word+" ");
		}
		public void putTag(String tag) {
			this.tag.append(tag+" ");
		}
		public String  getAllWord() {
			StringBuffer stringBuffer =new StringBuffer();
			String[] strings=word.toString().split(" ");//莫名bug
			for(int i=0;i<strings.length;i++){
				stringBuffer.append(strings[i]+"\r\n");
			}
			return stringBuffer.toString();
		}
		
		 
		 
		public String getWord(String tag){
			StringBuffer stringBuffer =new StringBuffer();
			String[] strings=word.toString().split(" ");
			String[] strings2=this.tag.toString().split(" ");
			for(int i=0;i<strings2.length;i++){
				if (strings2[i].equals(tag)) {
					stringBuffer.append(strings[i]+"\r\n");
				}
			}
			if(stringBuffer.length()==0){
				return "Not Such Word";
			}else{
				return stringBuffer.toString();
			}
		}
		
		
		 
		public String getSimilarWord(String tag){
			StringBuffer stringBuffer =new StringBuffer();
			String[] strings=word.toString().split(" ");
			String[] strings2=this.tag.toString().split(" ");
			for(int i=0;i<strings2.length;i++){
				if (strings2[i].contains(tag)) {
					if (strings[i].length()>=2) {
						stringBuffer.append(strings[i]+"\r\n");
					}
					
				}
			}
			if(stringBuffer.length()==0){
				return "Not Such Similar Word";
			}else{
				return stringBuffer.toString();
			}
		}
		
		
		
		public String  getAllTag() {
			StringBuffer stringBuffer =new StringBuffer();
			String[] strings=tag.toString().split(" ");
			for(int i=0;i<strings.length;i++){
				stringBuffer.append(strings[i]+"\r\n");
			}
			return stringBuffer.toString();
		}
		
		
		 
		public String getTag(String word){
			StringBuffer stringBuffer =new StringBuffer();
			String[] strings=this.word.toString().split(" ");
			String[] strings2=tag.toString().split(" ");
			for(int i=0;i<strings.length;i++){
						//System.out.println(strings[i]);
				if (strings[i].equals(word)) {
					stringBuffer.append(strings2[i]+"\r\n");
				}
			}
			if(stringBuffer.length()==0){
				return "Not Such Tag";
			}else{
				return stringBuffer.toString();
			}
		}
		
		 
		 
		public String getSimilarTag(String word){
			StringBuffer stringBuffer =new StringBuffer();
			String[] strings=this.word.toString().split(" ");
			String[] strings2=tag.toString().split(" ");
			for(int i=0;i<strings.length;i++){
						//System.out.println(strings[i]);
				if (strings[i].contains(word)) {
					stringBuffer.append(strings2[i]+"\r\n");
				}
			}
			if(stringBuffer.length()==0){
				return "Not Such Similar Tag";
			}else{
				return stringBuffer.toString();
			}
		}
		
		public String toString(){
			StringBuffer stringBuffer =new StringBuffer();
			String[] strings=word.toString().split(", ");
			String[] strings2=tag.toString().split(", ");
			for(int i=0;i<strings.length;i++){
				stringBuffer.append(strings[i]+"  -->  "+strings2[i]+"\r\n");
			}
			return stringBuffer.toString();
		}
	}
}
