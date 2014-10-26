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

import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity
public class ArticleType extends GenericModel{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
     Integer id;
	
	@Column(nullable=false)
	String typeName;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    Date createDate;
	
	public ArticleType(String typeName){
		this.typeName=typeName;
		this.createDate=new Date();
	}
	
	public List<String> getArticleType(){
		List<String> typeList = new ArrayList<String>();
		List<ArticleType> aList=ArticleType.findAll();
		for(ArticleType item:aList){
			typeList.add(item.getTypeName());
		}
		return typeList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
