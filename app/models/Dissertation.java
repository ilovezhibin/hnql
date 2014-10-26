package models;

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

import play.db.jpa.GenericModel;
import play.db.jpa.GenericModel.JPAQuery;

/**这是文章的专题
 * @author Administrator
 *
 */
@Entity
public class Dissertation extends GenericModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id;
	@Column(nullable=false)
	String name;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	Date createTime;
	
	@Column(nullable=false)
	String author;
	
	public Dissertation(String name,String author){
		this.name=name;
		this.createTime=new Date();
		this.author=author;
	}
	
	
	/**获取所有的专题名
	 * @return
	 */
	public List<String> getDissertation(){
		List<String> nameList = new ArrayList<String>();
		JPAQuery jq=  find("select a1   from Dissertation a1 order by createTime desc");
		List<Dissertation> dList=jq.fetch(10);
		for(Dissertation item:dList){	
			nameList.add(item.getName());
		}
		return nameList;
	}
	
	
	/**把user里的acountName换成nickName
	 * @param d
	 * @return
	 */
	private Dissertation changeToNickname(Dissertation d){
		User u=Dissertation.find("acountName=?", d.getAuthor()).first();
		d.setAuthor(u.getNickName());
		return d;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
	

}
