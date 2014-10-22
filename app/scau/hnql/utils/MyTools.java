package scau.hnql.utils;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.UploadPictures;
import play.Play;
import play.libs.Files;
import scau.hnql.bean.ImagesBean;

public class MyTools {

	public static List<String> dealUploadImages(ImagesBean ib,String author){
		Random rd = new Random();
		long id =0;
		File file ;
		List<String> imPaths = new ArrayList<String>();
		List<File> flist=ib.getFiles();
    	int length=flist.size();
    	System.out.println("有文件上传！！共有"+length+"个！！");
    	for(int i=0;i<length;i++){
    	file = flist.get(i);
    	String oldName = file.getName();	
    	String fileType=getType(oldName);
    	oldName=getName(oldName, fileType);
    	id = System.currentTimeMillis();
    	id=id*100+rd.nextInt(100);
    	String newName = author+oldName+rd.nextInt(10000)+"."+fileType;
    	Date uploaDate = new Date(id);
    	String bigPath="public/uploadBigImages/"+newName;
    	String smallPath="public/uploadSmallImages/"+newName;
    		
    	File bigFile =  Play.getFile(bigPath);
    	File smallFile =  Play.getFile(smallPath);
        Files.copy(flist.get(i),bigFile); 
        CompressPicUtils cpu = new CompressPicUtils();
        boolean b=cpu.compressPic(bigFile, smallFile, 800, 600, true);
        if(b){
        	System.out.println("压缩成功！");
        }else {
        	System.out.println("压缩失败！");
		}
        
        UploadPictures up = new UploadPictures(new Long(id), oldName, newName, fileType, "/"+bigPath, "/"+smallPath, uploaDate,author);
        up.save();
        imPaths.add("/"+smallPath);
        System.out.println("保存数据成功！");
        
    	}
    	return imPaths;
	}
	
	/**获取文件的类型
	 * @param fileName
	 * @return
	 */
	public static String getType(String fileName){
		String[] strs=fileName.split("\\.");
		return strs[strs.length-1];
	}
	
	public static String getName(String fileName,String type){
		return fileName.substring(0, fileName.length()-type.length()-1);
	}
	
}
