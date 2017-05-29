package Preprocess;
import java.io.*;
import java.util.HashSet;


public class RemoveDuplication {

	public static void main(String[] args) {
		String FileIn="src\\data\\luyou_out.txt";
		String FileOut="src\\data\\data_pos_ReDuplication.txt";
		clear(FileIn, FileOut);

	}
	/*
	 * 
	 * @param FileIn 
	 * @param FileOut
	 * @throws Exception
	 */
	public  static void clear(String FileIn,String FileOut){
		
			
		
		try {
			File in = new File(FileIn);
			BufferedInputStream fin=new BufferedInputStream(new FileInputStream(in));
			BufferedReader  br=new BufferedReader(new InputStreamReader(fin, "gbk"));
			OutputStreamWriter wr=new OutputStreamWriter(new FileOutputStream( new File(FileOut)), "gbk");
			
			HashSet<String> set =new HashSet<String>();	//HashSet 存放没有顺序  TreeSet存放有顺序
			String temp;
			while ((temp=br.readLine()) !=null){
				if(temp !="\r\n" ){
					set.add(temp);				
				}
			}
			
			fin.close();
			br.close();
			
		/*			倒序遍历
		for(String sentences:set){
					System.out.println(sentences);
					wr.write(sentences+"\r\n");
				}
				
			}
			*/
			String[] arr =(String[])set.toArray(new String[0]);
			//String laString=((String) set).pollLast();	//TreeSet 可以直接找First 和 Last节点。不在for循环判断，原因是放在for循环里面pollFirst 每次都会变
			for(String str:arr){
				
				
				System.out.println(str);
					wr.write(str+"\r\n");
			
			}
			/*
			for(Iterator iterator=set.iterator();iterator.hasNext();){
				
				System.out.println(iterator.next());
			}*/
			System.out.println("一共有"+set.size()+"行句子");
			wr.flush();
			wr.close();
		} catch (Exception e) {
			System.out.print("File Not Found");
		}
		}
	
}
