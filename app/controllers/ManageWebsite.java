package controllers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import models.Article;
import models.ArticleType;
import models.OperationLog;
import models.UploadPicture;
import models.User;
import play.Play;
import play.data.validation.Required;
import play.libs.Files;
import play.mvc.Before;
import play.mvc.Controller;
import scau.hnql.bean.ImagesBean;
import scau.hnql.utils.CompressPicUtils;
import scau.hnql.utils.MyTools;

/**针对后台管理
 * @author Administrator
 *
 */
public class ManageWebsite extends Controller{
	
	
	/**想进入管理页面，必须要登陆后，否则不能接受请求
	 * 
	 */
	@Before(unless={"toLogin","loginPage","readOneArticle"})
	static void checkUser(){
		System.out.println("认证中……");
		if(session.get("acountName")==null){
			System.out.println("还没有登陆！！");
			loginPage();
			return;
		}
	}
	
	/**返回登陆界面
	 * 
	 */
	public static void loginPage(){
		render();
	}
	
	
	/**注册页面
     * 
     */
    public static void registPage(){
    	String nickName=session.get("nickName");
    	render("/ManageWebsite/registPage.html",nickName);
    }
    
    /**提交注册表单
     * @param acountName  帐号名
     * @param password	  密码
     * @param nickName   昵称
     */
    public static void toRegist(@Required String acountName,@Required String password,@Required String nickName){
    	User user = new User(acountName, password, nickName);
    	user.save();
    	render("/ManageWebsite/loginPage.html");
    }
    
    /**处理登陆请求
     * @param acountName
     * @param password
     */
    public static void toLogin(@Required String acountName,@Required String password){
    	User user = User.find("acountName = ?", acountName).first();
    	System.out.println(acountName+"   "+password);
    	if(user!=null&&user.getPassword().equals(password)){
    		System.out.println(user.getAcountName()+"   "+user.getPassword());
    		
    		session.put("acountName", acountName);//登陆成功后，要把登陆信息保存起来
    		session.put("nickName",user.getNickName());
    		
    		OperationLog operationLog = new OperationLog(user.getNickName(), "登陆系统后台");  //记录登陆行为
    		operationLog.save();
    		
    		managerPage(user.getNickName());
    		return;
    	}
    	render("/ManageWebsite/loginPage.html");
    }
    
    /**进入管理页面
     * @param nickName
     */
    public static void managerPage(String nickName){    	   	
    	render(nickName);
    }
	
	/**响应请求，并返回写文章的页面
	 * 
	 */
	public static void writeArticle(){
		String nickName=session.get("nickName");
		render(nickName);
	}
	
	/**写好文章后，把文章存到数据库
	 * @param title  标题
	 * @param articleType 类型
	 * @param articleFrom  文章来源
	 * @param content  文章内容
	 */
	public static void publishArticle(Article art){
		String author=session.get("acountName");
		if(art!=null){
			art.save();
			OperationLog operationLog = new OperationLog(author, "发表了一篇文章，标题是： "+art.getTitle());  //记录登陆行为
			operationLog.save();
		}
		
		
		
		showArticles(0);
	}
	
	/**显示所有的文章，暂时没做分页，以后要加分页
	 * 
	 */
	public static void showArticles(int page){
		List<Article> articles=Article.getArticleRecently(page, 30);
		Article.changeToNicknameList(articles);
		String nickName=session.get("nickName");
		render(nickName,articles);
	}
	
	/**删除文章，做了权限
	 * @param id 文章的id
	 */
	public static void deleteOneArticle(@Required Long id){
		String nickName=session.get("acountName");
		Article ar = Article.findById(id);	//进行权限认证，除了超级管理员，其它管理员只有自己写的文章才能删除
		if(ar!=null&&(nickName.equals("admin")||ar.getAuthor().equals(nickName))){
			Article.deleteOneArticle(id);
			OperationLog operationLog = new OperationLog(nickName, "删除了一篇文章，标题是： "+ar.getTitle());  //记录登陆行为
			operationLog.save();
		}
		showArticles(0);
	}
	
	/**批量删除文章，做了权限
	 * @param id 文章的id 格式如  2144-53242-53252
	 */
	public static void deleteArticles(@Required String arIds){
		String nickName=session.get("acountName");
		if(nickName.equals("admin")){
			Article.deleteArticles(arIds);
			OperationLog operationLog = new OperationLog(nickName, "批量删除文章，id是： "+arIds);  //记录登陆行为
			operationLog.save();
		}
		showArticles(0);
	}
	
	/**进入指定的文章，显示文章全部内容
	 * @param id  文章的ID
	 */
	public static void  readOneArticle(@Required Long id){
		Article article = Article.findById(id);
		article.changeToNickname(article);
		render(article);
	}
	
	/**通过作者来过虑，显示指定作者的文章
	 * @param author
	 */
	public static void findArticleByAuthor(String author,int page){
		List<Article> articles =Article.findArticleByAuthor(author,page,20);
		Article.changeToNicknameList(articles);
		render("/ManageWebsite/showArticles.html",articles);
	}
	
	/**找出指定时间区间的文章
	 * @param left  时间的左边
	 * @param right  时间的右边
	 */
	public static void findArticleByTime(String left,String right,int page){
		List<Article> articles =Article.findArticleByTime(left,right,page,10);
		render("/ManageWebsite/showArticles.html",articles);
	}
	
	/**初始化数据库，往里面写入一些数据
	 * 
	 */
	public static void writeDataToDB(){
		String author=session.get("acountName");
		String[] af ={"广州日报","羊城晚报","湛江日报","中国日报","南方周末","华农侨联"};
		Random rd = new Random();
		for(int i=0;i<100;i++){
			Article article=new Article(author+"  文章标题"+i, rd.nextInt(16)+1, author, af[i%6], "这里是内容啊","专题"+(rd.nextInt(5)+1));
			article.save();
		}
		showArticles(0);
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
	    	String author=session.get("acountName");
	    	List<String> imPathList = MyTools.dealUploadImages(ib, author);
	    	
	    	OperationLog operationLog = new OperationLog(author, "上传了 "+imPathList.size()+"张图片！");  //记录登陆行为
			operationLog.save();
	    	
	        render("/ManageWebsite/showUploadImages.html",imPathList);  
	    }  
	}
	
	
	/**显示最近上传的图片
	 * @param page  页数
	 */
	public static void showImages(int page){
		List<UploadPicture> images = UploadPicture.getImageRecently(page, 20);
		UploadPicture.changeToNicknameList(images);
		render(images);
		
	}
	
	/**删除一张照片，有权限
	 * @param id  图片id
	 */
	public static void deleteOneImage(Long id){
		String author=session.get("acountName");
		UploadPicture image = UploadPicture.findById(id);	//进行权限认证，除了超级管理员，其它管理员只有自己写的文章才能删除
		if(image!=null&&(author.equals("admin")||image.getAuthor().equals(author))){
			UploadPicture.deleteOneImage(id);
			OperationLog operationLog = new OperationLog(author, "删除了一张图片！图片名为："+image.getNewName());  //记录登陆行为
			operationLog.save();
		}
		showImages(0);
	}
	
	/**删除照片，有权限,只有超级管理员才能批量删除
	 * @param id  图片id
	 */
	public static void deleteImages(String imIds){
		String author=session.get("acountName");
		if(author.equals("admin")){
			UploadPicture.deleteImages(imIds);
			OperationLog operationLog = new OperationLog(author, "批量删除图片！图片id为："+imIds);  //记录登陆行为
			operationLog.save();
		}
		showImages(0);
	}
	
	/**显示后台操作记录
	 * @param page
	 */
	public static void showOperationLogs(int page){
		List<OperationLog> olList=OperationLog.getOperationLogRecently(page, 20);
		OperationLog.changeToNicknameList(olList);
		render(olList);
	}
	
	/**返回创建文章类型的页面
	 * 
	 */
	public static void  createArticleType(){
		render();
	}
	
	/**处理新建文章类型请求
	 * @param articleType  文章类型
	 */
	public static void toCreateArticleType(String articleType){
		if(articleType!=null&&!articleType.equals("")){
			ArticleType at = new ArticleType(articleType);
			at.save();
			
		}
		createArticleType();
	}
		

}
