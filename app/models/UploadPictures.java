package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.GenericModel;

@Entity
public class UploadPictures extends GenericModel{

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
	
	public UploadPictures(Long id,String oldName,String newName,String fileType,String bigPath,String smallPath,Date uploaDate,String author){
		this.id=id;
		this.oldName=oldName;
		this.newName=newName;
		this.fileType=fileType;
		this.bigPath=bigPath;
		this.smallPath=smallPath;
		this.uploadTime=uploaDate;
		this.author=author;
	}
	
	
}
