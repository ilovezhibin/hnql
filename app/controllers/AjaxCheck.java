package controllers;

import java.util.List;

import models.Article;
import models.User;
import play.data.validation.Required;
import play.mvc.Controller;

public class AjaxCheck extends Controller {
	public static void  checkUser(@Required String acountName1){
		System.out.println(acountName1);
		List<User> userList= User.findAll();
		for(int i =0 ;i<userList.size();i++){
			if(acountName1.equals(userList.get(i).getNickName())){
				//renderJSON(acountName1);
				renderJSON("str");
				return;
			}
		}
		renderJSON(acountName1+"123");
		return;
	}
}
