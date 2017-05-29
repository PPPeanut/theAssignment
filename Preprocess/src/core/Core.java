package core;

import Preprocess.CountWord;
import Preprocess.SelectHotWord;
import Preprocess.Regular;
import Preprocess.WordGuoLv;
import Preprocess.WordTagging;

public class Core {

	public static void main(String[] args) {
		String dataFile="src\\data\\dataFile.txt";
		String inFile ="src\\data\\data-news.txt";
		String outFile1="src\\data\\wordTag.txt";
		String outFile2="src\\data\\SelectHotWord.txt";
		String outFile3="src\\data\\SelectHotWordFilter.txt";
		String outFile4="src\\data\\HotWord.txt";
		try {
			Regular.wordSeg(dataFile,inFile);//正则
			System.out.println("Process 1...");	
			WordTagging.wordTag(inFile, outFile1);//分词词性标注
			System.out.println("Process 1 over"+"\r\n"+"Process 2...");
			SelectHotWord.HotWord(outFile1,outFile2);//选择可以作为候选的热词
			System.out.println("Process 2 over"+"\r\n"+"Process 3...");
			WordGuoLv.wordGuo(outFile2, outFile3);//候选词过滤
			System.out.println("Process 3 over"+"\r\n"+"Process 4...");
			CountWord.count(outFile3, outFile4);//过滤完的热词排序
			System.out.println("Process 4 over");
		} catch (Exception e) {
			System.out.println("File Not Found");
		}
		
	}

}
