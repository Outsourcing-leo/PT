package org.tnt.pt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/** 
 * 
 * @author:maochunlei
 * @time:2013-03-26
 */

public class FileUtil {

	/** 
     * 复制整个文件夹内容    复制查找到的文件夹
     * @param oldPath String 原文件路径 如：c:/fqf 
     * @param newPath String 复制后路径 如：f:/fqf/ff 
     * @return boolean 
     */ 
   public static void copyFolder(String oldPath, String newPath) { 
       try { 
           (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
           File a=new File(oldPath); 
           String[] file=a.list(); 
           File temp=null; 
           for (int i = 0; i < file.length; i++) { 
               if(oldPath.endsWith(File.separator)){ 
                   temp=new File(oldPath+file[i]); 
               } 
               else{ 
                   temp=new File(oldPath+File.separator+file[i]); 
               } 

               if(temp.isFile()){ 
                   FileInputStream input = new FileInputStream(temp); 
                   FileOutputStream output = new FileOutputStream(newPath + "/" + 
                           (temp.getName()).toString()); 
                   byte[] b = new byte[1024 * 5]; 
                   int len; 
                   while ( (len = input.read(b)) != -1) { 
                       output.write(b, 0, len); 
                   } 
                   output.flush(); 
                   output.close(); 
                   input.close(); 
               } 
               if(temp.isDirectory()){//如果是子文件夹 
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
               } 
           } 
       } 
       catch (Exception e) { 
           System.out.println("复制整个文件夹内容操作出错"); 
           e.printStackTrace(); 

       } 

   }   

   /** 
    * 复制单个文件 
    * @param oldPath String 原文件路径 如：c:/fqf.txt 
    * @param newPath String 复制后路径 如：f:/fqf.txt 
    * @return boolean 
    */ 
	  public static void copyFile(String oldPath, String newPath) { 
	      try { 
	          int byteread = 0; 
	          File oldfile = new File(oldPath); 
	          if (oldfile.exists()) { //文件存在时 
	              InputStream inStream = new FileInputStream(oldPath); //读入原文件 
	              FileOutputStream fs = new FileOutputStream(newPath); 
	              byte[] buffer = new byte[1444]; 
	              while ( (byteread = inStream.read(buffer)) != -1) { 
	                  fs.write(buffer, 0, byteread); 
	              } 
	              inStream.close(); 
	          } 
	      } 
	      catch (Exception e) { 
	          System.out.println("复制单个文件操作出错"); 
	          e.printStackTrace();
	
	      } 
	  }
      /** 
       * 删除文件夹里面的所有文件 
       * @param path String 文件夹路径 如 c:/fqf 
       */ 
     public static void delAllFile(String path){ 
         File file = new File(path); 
         if (!file.exists()) { 
             return; 
         } 
         if (!file.isDirectory()) { 
             return; 
         } 
         String[] tempList = file.list(); 
         File temp = null; 
         for (int i = 0; i < tempList.length; i++) { 
             if (path.endsWith(File.separator)) { 
                 temp = new File(path + tempList[i]); 
             } 
             else { 
                 temp = new File(path + File.separator + tempList[i]); 
             } 
             if (temp.isFile()) { 
                 temp.delete(); 
             } 
             if (temp.isDirectory()) { 
                 delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件 
                 delFolder(path+"/"+ tempList[i]);//再删除空文件夹 
             } 
         } 
     } 
  
  /** 
   * 删除文件夹 
   * @param filePathAndName String 文件夹路径及名称 如c:/fqf 
   * @param fileContent String 
   * @return boolean 
   */ 
	 public static void delFolder(String folderPath) { 
	     try { 
	         delAllFile(folderPath); //删除完里面所有内容 
	         String filePath = folderPath; 
	         filePath = filePath.toString(); 
	         java.io.File myFilePath = new java.io.File(filePath); 
	         myFilePath.delete(); //删除空文件夹 
	     } 
	     catch (Exception e) { 
	         System.out.println("删除文件夹操作出错"); 
	         e.printStackTrace(); 
	
	     } 
	
	 } 
	 /** 
	  * 删除文件 
	  * @param filePathAndName String 文件路径及名称 如c:/fqf.txt 
	  * @param fileContent String 
	  * @return boolean 
	  */ 
	public static void delFile(String filePathAndName) { 
	    try {
	    	if(filePathAndName==null||"".equals(filePathAndName)){
	    		return;
	    	}
	        File myDelFile = new File(filePathAndName);
	        if(!myDelFile.exists()){
	        	
	        }else{
	        	myDelFile.delete();
	        }
	    } 
	    catch (Exception e) { 
	        System.out.println("删除文件操作出错"); 
	        e.printStackTrace(); 
	
	    } 
	
	} 
	
	 /** 
	  * 删除rar文件 
	  * @param filePathAndName String 文件路径及名称 如c:/fqf.txt 
	  * @param fileContent String 
	  * @return boolean 
	  */ 
	public static void delRarFile(String filePath) { 
		try {
		    File dir = new File(filePath);//文件目录		
		    File [] files = dir.listFiles();		
		    String fileName = "";		
		    for(File f: files){			
		    	fileName = f.getName();			
		    	if(fileName.endsWith(".rar")){				
		    		if(f.delete()){					
		    			System.out.println("已删除文件："+fileName);				
		    			}			
		    		}		
		    	}
		}catch (Exception e) { 
	        System.out.println("删除rar文件操作出错"); 
	        e.printStackTrace(); 
	
	    } 
	
	} 
	/**
     * @Description: 电子签章图片上传服务器<br/>
     * @param localhostPath
     * @param ftpPath
     * @param FileName
     * @author gaojunlong
	 * @throws Exception 
     * @since JDK 1.6
     * @serial 2013-10-10 下午4:13:44
     */
    public static void uploadImgToServer(File srcFile, String serverPath)
            throws Exception {
        // 开始上传
        InputStream is = null;
        OutputStream os = null;
        is = new FileInputStream(srcFile);
        os = new FileOutputStream(serverPath);
        byte[] buffer = new byte[1024];
        int length=0;
        while (-1!=(length=(is.read(buffer)))) {
            os.write(buffer, 0, length);
        }
        is.close();
        os.close();
    }
}
