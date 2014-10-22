package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import org.h2.engine.User;

import models.*;

public class Application extends Controller {

    public static void index() {
    	render("/Application/loginPage.html");
    }
    
    
    /**注册页面
     * 
     */
    public static void registPage(){
    	render("/Application/registPage.html");
    }
    /**提交注册表单
     * @param acountName  帐号名
     * @param password	  密码
     * @param nickName   昵称
     */
    public static void toRegist(@Required String acountName,@Required String password,@Required String nickName){
    	Users user = new Users(acountName, password, nickName);
    	user.save();
    	render("/Application/loginPage.html");
    }
    
    /**处理登陆请求
     * @param acountName
     * @param password
     */
    public static void toLogin(@Required String acountName,@Required String password){
    	Users user = Users.find("acountName = ?", acountName).first();
    	System.out.println(acountName+"   "+password);
    	if(user!=null&&user.getPassword().equals(password)){
    		System.out.println(user.getAcountName()+"   "+user.getPassword());
    		
    		session.put("acountName", acountName);
    		session.put("nickName",user.getNickName());
    		
    		managerPage(user.getNickName());
    		return;
    	}
    	render("/Application/loginPage.html");
    }
    
    /**进入管理页面
     * @param nickName
     */
    public static void managerPage(String nickName){
    	
    	
    	render(nickName);
    }
    
    

}