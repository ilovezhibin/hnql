package models;

import java.util.List;

import javax.persistence.*;

import net.sf.oval.constraint.MaxSize;
import net.sf.oval.constraint.MinSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity
public class Users extends GenericModel {
	
    @Id
    @Required
    @GeneratedValue(strategy=GenerationType.AUTO)
     Integer id;
    
    @Column(length=20,nullable=false)
    @MaxSize(20)
    @Required
     String password;
    
    
    @Column(unique=true,length=20,nullable=false)
    @MaxSize(20)
     String acountName;
    
    @Column(unique=true,length=20,nullable=false)
    @MaxSize(20)
     String nickName;
    
    public Users(String acountName,String password,String nickName){
    	this.acountName=acountName;
    	this.password=password;
    	this.nickName=nickName;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAcountName() {
		return acountName;
	}

	public void setAcountName(String acountName) {
		this.acountName = acountName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
    
    
    

    
}