package models;


import java.util.Date;
import java.util.List;

import javax.management.Query;
import javax.persistence.*;

import org.hibernate.ejb.EntityManagerImpl;

import net.sf.oval.constraint.MaxSize;
import play.data.validation.Required;
import play.db.jpa.*;

@Entity
public class Articles extends GenericModel{
	
	@Id
    @Required
    @GeneratedValue(strategy=GenerationType.AUTO)
	 Long id;
	
    @Required
    @Column(length=100,nullable=false)
	String title;
	
	@Required
	@Column(nullable = false)
	Integer articleType ;
	
	@Required
	@Column(nullable = false)
	String author;
	
	String articleFrom;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	Date publishTime ;
	
	@Column(length=60000)
	String content;

	public Articles(String title,Integer articleType,String author,String articleFrom,String content){
		this.title=title;
		this.articleType=articleType;
		this.author=author;
		this.articleFrom=articleFrom;
		this.content=content;
		this.publishTime=new Date();
		
	}
	
	/**得到数据库里所有的文章
	 * @return
	 */
	public static List<Articles> getAllArticle(){
		
		JPAQuery jq2=  find("select count(*) as id    from Articles as ar order by publishTime desc");
		Long ar =Articles.count();
		System.out.println("###############  "+ ar);
		
		JPAQuery jq=  find("select a1   from Articles a1 order by publishTime desc");
		//JPAQuery jq=  find("select a1   from Articles a1 where a1.publishTime>'2014-10-19 12:09:01'");
		
		List<Articles> as=jq.fetch();
		
		return as;
		
		//return Articles.findAll();
	}
	
	
	/**  列出某作者所写的的文章
	 * @param author
	 * @return
	 */
	public static List<Articles> findArticleByAuthor(String author){
		JPAQuery jq=  find("select a from Articles a where author=?", author);
		 
		List<Articles> as=jq.fetch();
		return as;
	}
	
	public static List<Articles> findArticleByTime(String left,String right){
		String sql = "select t from Articles t where t.publishTime>='"+left+"' and t.publishTime<='"+right+"'";
		System.out.println(sql);
		JPAQuery jq=  find(sql);
		 
		List<Articles> as=jq.fetch();
		return as;
	}
	
	
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getArticleType() {
		return articleType;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getArticleFrom() {
		return articleFrom;
	}

	public void setArticleFrom(String articleFrom) {
		this.articleFrom = articleFrom;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
