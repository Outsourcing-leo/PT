package org.tnt.pt.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			FileReader fr=new FileReader("D:\\tt.txt");
	        //可以换成工程目录下的其他文本文件
	        BufferedReader br=new BufferedReader(fr);
	        String str = "";int row = 1;
	        while((str=br.readLine())!=null){
		        System.out.println("update pt_country set depotcode='"+str+"' where id="+row+";");
	            row++;
	        }
	        br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
