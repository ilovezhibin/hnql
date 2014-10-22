package scau.hnql.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagesBean {
	public File image0=null;
	public File image1=null;
	public File image2=null;
	public File image3=null;
	public File image4=null;
	public File image5=null;
	public File image6=null;
	public File image7=null;
	public File image8=null;
	public File image9=null;
	
	public List<File> getFiles(){
		List<File> flist = new ArrayList<File>();
		if(image0!=null){
			flist.add(image0);
		}
		if(image1!=null){
			flist.add(image1);
		}
		if(image2!=null){
			flist.add(image2);
		}
		if(image3!=null){
			flist.add(image3);
		}
		if(image4!=null){
			flist.add(image4);
		}
		if(image5!=null){
			flist.add(image5);
		}
		if(image6!=null){
			flist.add(image6);
		}
		if(image7!=null){
			flist.add(image7);
		}
		if(image8!=null){
			flist.add(image8);
		}
		if(image9!=null){
			flist.add(image9);
		}
		return flist;
	}
	
}
