package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import net.sf.oval.constraint.MaxSize;
import net.sf.oval.constraint.MinSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity
public class User extends GenericModel {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
     Integer id;
    
    @Column(length=20,nullable=false)
    @MaxSize(20)
     String password;
    
    
    @Column(unique=true,length=20,nullable=false)
    @MaxSize(20)
     String acountName;
    
    @Column(unique=true,length=20,nullable=false)
    @MaxSize(20)
     String nickName;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    Date registDate;
    
    public User(String acountName,String password,String nickName){
    	this.acountName=acountName;
    	this.password=password;
    	this.nickName=nickName;
    	this.registDate=new Date();
    }
    
    /**通过acountName来获取user
     * @param acountName
     * @return
     */
    public static User getUserByAcountName(String acountName){
    	return find("acountName=?", acountName).first();
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