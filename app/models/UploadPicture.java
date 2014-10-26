package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.Play;
import play.db.jpa.GenericModel;
import play.db.jpa.GenericModel.JPAQuery;

@Entity
public class UploadPicture extends GenericModel{

	@Id
	Long id;
	
	@Column(nullable=false)
	String oldName ;
	
	@Column(nullable=false)
	String newName;
	
	String description ;
	
	@Column(nullable=false)
	String fileType ;
	
	@Column(nullable=false)
	String bigPath;
	
	@Column(nullable=false)
	String smallPath;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	Date uploadTime ;
	
	@Column(nullable=false)
	String author;
	
	public UploadPicture(Long id,String oldName,String newName,String fileType,String bigPath,String smallPath,Date uploaDate,String author){
		this.id=id;
		this.oldName=oldName;
		this.newName=newName;
		this.fileType=fileType;
		this.bigPath=bigPath;
		this.smallPath=smallPath;
		this.uploadTime=uploaDate;
		this.author=author;
	}
	
	
	/**得到数据库里最近的图片
	 * @return
	 */
	public static List<UploadPicture> getImageRecently(int page,int pageSize){
		
		JPAQuery jq2=  find("select count(*) as id    from UploadPicture as ar ");
		Long ar =UploadPicture.count();
		System.out.println("###############  "+ ar);
		
		JPAQuery jq=  find("select a1   from UploadPicture a1 order by uploadTime desc");
		List<UploadPicture> upList=jq.from(page*pageSize).fetch(pageSize);
		//List<String> sList = new ArrayList<String>(); 
		//for(UploadPicture up:upList){
		//	sList.add(up.getSmallPath());
		//}
		return upList;
	}
	
	
	/**  列出某作者所写的的图片
	 * @param author
	 * @return
	 */
	public static List<UploadPicture> findImageByAuthor(String author,int page,int pageSize){
		JPAQuery jq=  find("select a from UploadPicture a where author=? order by uploadTime desc", author);
		 
		return jq.from(page*pageSize).fetch(pageSize);
	}
	
	/**返回某时间区间的图片
	 * @param left  时间的左边
	 * @param right  时间 的右边
	 * @return  图片列表
	 */
	public static List<UploadPicture> findImageByTime(String left,String right,int page,int pageSize){
		String sql = "select t from UploadPicture t where t.uploadTime>='"+left+"' and t.uploadTime<='"+right+"'";
		System.out.println(sql);
		JPAQuery jq=  find(sql);
		 
		return jq.from(page*pageSize).fetch(pageSize);
	}
	
	

	/**删除图片，可以删除多张图片
	 * @param arIds  多个图片ID连起来 如 342323-748239-478928
	 */
	public static void deleteImages(String imIds){
		String[] imageIds=imIds.split("-");
		int length=imageIds.length;
		long  id = 0;
		int result=0;
		for(int i=0;i<length;i++){
			id=Long.parseLong(imageIds[i]);//把字符串转化
			result=deleteOneImage(id);
			System.out.println("删除了图片，id="+id+"  返回的信息："+result);
		}
	}
	
	/**删除单张图片
	 * @param id  图片的id
	 * @return
	 */
	public static int deleteOneImage(Long id){
		UploadPicture up = findById(id);
		String rootPath = Play.applicationPath.getAbsolutePath();
		File bigFile= new File(rootPath+up.getBigPath());
		if(bigFile.exists()){
			bigFile.delete();
		}
		File smallFile= new File(rootPath+up.getSmallPath());
		if(smallFile.exists()){
			smallFile.delete();
		}
		int result=delete("id=?", id);
		
		return result;
	}
	
	
	/**把user里的acountName换成nickName
	 * @param d
	 * @return
	 */
	private UploadPicture changeToNickname(UploadPicture d){
		User u=User.find("acountName=?", d.getAuthor()).first();
		d.setAuthor(u.getNickName());
		return d;
	}
	
	/**批量把user里的acountName换成nickName
	 * @param d
	 * @return
	 */
	public static  List<UploadPicture> changeToNicknameList(List<UploadPicture> aList){
		for(UploadPicture d:aList){
			User u=User.find("acountName=?", d.getAuthor()).first();
			d.setAuthor(u.getNickName());
		}
		
		return aList;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOldName() {
		return oldName;
	}


	public void setOldName(String oldName) {
		this.oldName = oldName;
	}


	public String getNewName() {
		return newName;
	}


	public void setNewName(String newName) {
		this.newName = newName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getBigPath() {
		return bigPath;
	}


	public void setBigPath(String bigPath) {
		this.bigPath = bigPath;
	}


	public String getSmallPath() {
		return smallPath;
	}


	public void setSmallPath(String smallPath) {
		this.smallPath = smallPath;
	}


	public Date getUploadTime() {
		return uploadTime;
	}


	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
	
}
