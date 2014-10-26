package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.GenericModel;
import play.db.jpa.GenericModel.JPAQuery;

/**用来记录管理员的操作
 * @author Administrator
 *
 */
@Entity
public class OperationLog extends GenericModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	@Column(nullable=false)
	String author;
	
	@Column(nullable=false)
	String description;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	Date operationTime;
	
	
	public OperationLog(String author,String description){
		this.author=author;
		this.description=description;
		this.operationTime=new Date();
	}
	
	
	/**得到数据库里最近的操作记录
	 * @return
	 */
	public static List<OperationLog> getOperationLogRecently(int page,int pageSize){
			
		JPAQuery jq=  find("select a1   from OperationLog a1 order by operationTime desc");
		
		return jq.from(page*pageSize).fetch(pageSize);
	}
	
	
	/**把user里的acountName换成nickName
	 * @param d
	 * @return
	 */
	private OperationLog changeToNickname(OperationLog d){
		User u=User.find("acountName=?", d.getAuthor()).first();
		d.setAuthor(u.getNickName());
		return d;
	}
	
	/**批量把user里的acountName换成nickName
	 * @param d
	 * @return
	 */
	public static  List<OperationLog> changeToNicknameList(List<OperationLog> aList){
		for(OperationLog d:aList){
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	
	

}
