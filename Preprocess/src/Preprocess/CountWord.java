package Preprocess;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;


public class CountWord {

	public static void main(String[] args) {
		String fileIn="src\\data\\HotWord.txt";				//这个数据集需要分词之后的，最好是去重去停的
		String fileOut="src\\data\\CountWord.txt";
		count(fileIn, fileOut);

	}
	
	public static void count(String fileIn,String fileOut){
		
		
		try {
			BufferedInputStream fin=new BufferedInputStream(new FileInputStream(new File(fileIn)));
			BufferedReader  br=new BufferedReader(new InputStreamReader(fin, "GB2312"));
			OutputStreamWriter wr=new OutputStreamWriter(new FileOutputStream(new File(fileOut)), "GB2312");
			 //不使用一个类，而使用HahMap的原因：HashMap能很快地判断新加入的词是否重复
			HashMap<String, Integer> map=new HashMap<String,Integer>( );
			String temp;
			while((temp=br.readLine())!=null){
				if (temp=="\r\n") {
					continue;
				}
				String[] word =temp.split(" ");
				for(int i=0;i<word.length;i++){
				
					if (!map.containsKey(word[i])){
						map.put(word[i], 1);
					}
					else{
						Integer time=map.get(word[i]);
						time++;
						map.put(word[i], time);
					}
					
				}
			}
			br.close();
			fin.close();
			
			//排序方法第一种
			ByValueComparator bvc = new ByValueComparator(map);
	         
	        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc); //TreeMap基于红黑树（Red-Black tree）实现。其键的自然顺序进行排序
	        sorted_map.putAll(map);
	         int i=0;
	         System.out.println("输出词频最高的前20个词：");
	        for(String word : sorted_map.keySet()){
	        	if (i<20) {
	        		System.out.printf("%s -> %d\n", word, map.get(word));
	        		i++;
				}
	            //System.out.printf("%s -> %d\n", word, map.get(word));
	            wr.write(word+"\t"+map.get(word)+"\r\n");
	        }
	        /*可以考虑的第二种排序算法
	         * 
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys, bvc);
        for(String key : keys) {
            System.out.printf("%s -> %d\n", key, datas.get(key));
        }
	         */
	        
	      
	        /*
	         * 如果不排序的话，直接写入
			for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {

			    Map.Entry entry = (Map.Entry) iter.next();    

			    Object key = entry.getKey();

			    Object val = entry.getValue();
			    wr.write(key.toString()+"\t"+val.toString()+"\r\n");
			    //System.out.println(key.toString()+val.toString());
			}
			*/
			
			
			wr.flush();
			wr.close();
				}catch(Exception e){
					System.out.println("File not found");
			}
	}
		
	 static class ByValueComparator implements Comparator<String> {
	        HashMap<String, Integer> base_map;
	 
	        public ByValueComparator(HashMap<String, Integer> base_map) {
	            this.base_map = base_map;
	        }
	 
	        public int compare(String arg0, String arg1) {
	            if (!base_map.containsKey(arg0) || !base_map.containsKey(arg1)) {
	                return 0;
	            }
	 
	            if (base_map.get(arg0) <= base_map.get(arg1)) {
	                return 1;
	            //} else if (base_map.get(arg0) == base_map.get(arg1)) {
	              //  return 0;
	            } else {
	                return -1;
	            }
	        }
	    }
	


}