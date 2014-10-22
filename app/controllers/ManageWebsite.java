package controllers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import models.Articles;
import play.Play;
import play.data.validation.Required;
import play.libs.Files;
import play.mvc.Controller;
import play.mvc.Http.Request;
import scau.hnql.bean.ImagesBean;
import scau.hnql.utils.CompressPicUtils;
import scau.hnql.utils.MyTools;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class ManageWebsite extends Controller{
	
	public static void writeArticle(){
		render();
	}
	
	public static void publishArticle(String title,Integer articleType,String articleFrom,String content){
		String author=session.get("nickName");
		Articles article=new Articles(title, articleType, author, articleFrom, content);
		article.save();
		
		showArticles();
	}
	
	public static void showArticles(){
		List<Articles> articles=Articles.getAllArticle();
		//List<Articles> articles=Articles.findArticle("牵手无奈");
		
		render(articles);
	}
	
	public static void deleteArticle(@Required Long id){
		Articles.delete("id=?", id);
		showArticles();
	}
	
	public static void  readOneArticle(@Required Long id){
		Articles article = Articles.findById(id);
		render(article);
	}
	
	public static void findArticleByAuthor(String author){
		List<Articles> articles =Articles.findArticleByAuthor(author);
		render("/ManageWebsite/showArticles.html",articles);
	}
	
	public static void findArticleByTime(String left,String right){
		List<Articles> articles =Articles.findArticleByTime(left,right);
		render("/ManageWebsite/showArticles.html",articles);
	}
	
	public static void writeDataToDB(){
		String author=session.get("nickName");
		String[] af ={"广州日报","羊城晚报","湛江日报","中国日报","南方周末","华农侨联"};
		Random rd = new Random();
		for(int i=0;i<50;i++){
			Articles article=new Articles(author+"  文章标题"+i, rd.nextInt(10)+1, author, af[i%6], "这里是内容啊");
			article.save();
		}
		
		showArticles();
		
	}
	
	public static void uploadImagesPage(){
		render();
	}
	
	public static void imageUpload(ImagesBean ib){
		System.out.println("有文件上传！！");
		if(request.method.equalsIgnoreCase("GET")){  
	        render();
	    }else{  
	    	String author=session.get("nickName");
	    	List<String> imPathList = MyTools.dealUploadImages(ib, author);
	        render("/ManageWebsite/showUploadImages.html",imPathList);  
	    }  
	}
	
	

}
