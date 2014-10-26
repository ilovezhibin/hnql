package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import org.h2.engine.User;

import models.*;

/**这个类是用于接受游客的请求
 * @author Administrator
 *
 */
public class Application extends Controller {

    public static void index() {
    	List<Article> ra=Article.getArticleRecently(0, 10);//最近发表的文章
    	List<Article> sqla=Article.getArticleByType(1, 0, 8);
    	List<Article> jcql=Article.getArticleByType(2, 0, 21);
    	List<Article> hwqq=Article.getArticleByType(3, 0, 5);
    	List<Article> qxdb=Article.getArticleByType(4, 0, 8);
    	List<Article> rdzx=Article.getArticleByType(5, 0, 5);
    	List<Article> zcfg=Article.getArticleByType(6, 0, 5);
    	List<Article> tszs=Article.getArticleByType(7, 0, 5);
    	List<Article> qjjy=Article.getArticleByType(8, 0, 5);
    	List<Article> wqfw=Article.getArticleByType(9, 0, 5);
    	List<Article> qszl=Article.getArticleByType(10, 0, 5);
    	List<Article> lnqx=Article.getArticleByType(11, 0, 5);
    	List<Article> llyj=Article.getArticleByType(12, 0, 5);
    	List<Article> spdb=Article.getArticleByType(13, 0, 7);
    	List<Article> qjra=Article.getArticleByType(14, 0, 5);
    	render(ra,sqla,jcql,hwqq,qxdb,rdzx,zcfg,tszs,qjjy,wqfw,qszl,lnqx,llyj,spdb,qjra);
    }
    
    
    
    
    

}