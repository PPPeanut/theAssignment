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
			
			HashSet<String> set =new HashSet<String>();	//HashSet ���û��˳��  TreeSet�����˳��
			String temp;
			while ((temp=br.readLine()) !=null){
				if(temp !="\r\n" ){
					set.add(temp);				
				}
			}
			
			fin.close();
			br.close();
			
		/*			�������
		for(String sentences:set){
					System.out.println(sentences);
					wr.write(sentences+"\r\n");
				}
				
			}
			*/
			String[] arr =(String[])set.toArray(new String[0]);
			//String laString=((String) set).pollLast();	//TreeSet ����ֱ����First �� Last�ڵ㡣����forѭ���жϣ�ԭ���Ƿ���forѭ������pollFirst ÿ�ζ����
			for(String str:arr){
				
				
				System.out.println(str);
					wr.write(str+"\r\n");
			
			}
			/*
			for(Iterator iterator=set.iterator();iterator.hasNext();){
				
				System.out.println(iterator.next());
			}*/
			System.out.println("һ����"+set.size()+"�о���");
			wr.flush();
			wr.close();
		} catch (Exception e) {
			System.out.print("File Not Found");
		}
		}
	
}
