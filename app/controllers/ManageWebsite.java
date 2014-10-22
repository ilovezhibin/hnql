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
	
	/**响应请求，并返回写文章的页面
	 * 
	 */
	public static void writeArticle(){
		render();
	}
	
	/**写好文章后，把文章存到数据库
	 * @param title  标题
	 * @param articleType 类型
	 * @param articleFrom  文章来源
	 * @param content  文章内容
	 */
	public static void publishArticle(String title,Integer articleType,String articleFrom,String content){
		String author=session.get("nickName");
		Articles article=new Articles(title, articleType, author, articleFrom, content);
		article.save();
		
		showArticles();
	}
	
	/**显示所有的文章，暂时没做分页，以后要加分页
	 * 
	 */
	public static void showArticles(){
		List<Articles> articles=Articles.getAllArticle();
		//List<Articles> articles=Articles.findArticle("牵手无奈");
		
		render(articles);
	}
	
	/**删除文章，还没做权限，以后要做删除权限
	 * @param id 文章的id
	 */
	public static void deleteArticle(@Required Long id){
		Articles.delete("id=?", id);
		showArticles();
	}
	
	/**进入指定的文章，显示文章全部内容
	 * @param id  文章的ID
	 */
	public static void  readOneArticle(@Required Long id){
		Articles article = Articles.findById(id);
		render(article);
	}
	
	/**通过作者来过虑，显示指定作者的文章
	 * @param author
	 */
	public static void findArticleByAuthor(String author){
		List<Articles> articles =Articles.findArticleByAuthor(author);
		render("/ManageWebsite/showArticles.html",articles);
	}
	
	/**找出指定时间区间的文章
	 * @param left  时间的左边
	 * @param right  时间的右边
	 */
	public static void findArticleByTime(String left,String right){
		List<Articles> articles =Articles.findArticleByTime(left,right);
		render("/ManageWebsite/showArticles.html",articles);
	}
	
	/**初始化数据库，往里面写入一些数据
	 * 
	 */
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
	
	/**上传图片的页面
	 * 
	 */
	public static void uploadImagesPage(){
		render();
	}
	
	/**提交图片表单后进行处理
	 * @param ib
	 */
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
