package scau.hnql.utils;

import java.io.File;
import java.io.IOException;

import models.ArticleType;
import models.Dissertation;
import models.User;
import play.Play;
import play.jobs.*;

@OnApplicationStart
public class MyJob extends Job{

	@Override
	public void doJob() throws Exception {
		// TODO Auto-generated method stub
		super.doJob();
		initDB();
		initFolder();
		initArticleType();
		initDissertation();
		
	}
	
	/**初始化上传图片的路径
	 * @throws IOException
	 */
	private void initFolder() throws IOException{
		String imagePath =Play.applicationPath.getPath()+"/public";
		File bigImage = new File(imagePath+"/uploadBigImages");
		File smallImage = new File(imagePath+"/uploadSmallImages");
		if(!bigImage.exists()){
			bigImage.mkdirs();
		}
		if(!smallImage.exists()){
			smallImage.mkdirs();
		}
		
		System.out.println("初始化文件夹！");
	}
	
	/**初始化数据库的用户，注册个超级管理员
	 * 
	 */
	private void initDB(){
		User admin=User.getUserByAcountName("admin");
		if(admin==null){
			admin=new User("admin", "admin", "超级管理员");
			admin.save();
			System.out.println("初始化了超级管理员！");
		}
	}
	
	/**初始化文章类型
	 * 
	 */
	private void initArticleType(){
		long sum =ArticleType.count();
		if(sum<=0){
			String[] types={"省侨联","基层侨联","海外侨情","侨讯短波","热点资讯","政策法规",
					"他山之石","侨界精英","为侨服务","侨史资料","岭南侨乡","理论研究","视频点播","侨界仁爱基金会",
					"图片中心","电子刊物"};
			for(String item :types){
				new ArticleType(item).save();
			}
			System.out.println("初始化文章类型！");
		}
	}
	
	/**初始化专题
	 * 
	 */
	private void initDissertation(){
		long sum =Dissertation.count();
		if(sum<=0){
			String[] types={"专题1","专题2","专题3","专题4","专题5"};
			for(String item :types){
				new Dissertation(item,"admin").save();
			}
			System.out.println("初始化专题类型！");
		}
	}

	
	
}
