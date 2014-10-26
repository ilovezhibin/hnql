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
public class Article extends GenericModel{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	 Long id;
	
    @Column(length=100,nullable=false)
	String title;
	
	@Column(nullable = false)
	Integer articleType ;
	
	@Column(nullable = false)
	String author;
	
	String articleFrom;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	Date publishTime ;
	
	@Column(length=60000)
	String content;
	
	String dissertation;

	public Article(String title,Integer articleType,String author,String articleFrom,String content,String dissertation){
		this.title=title;
		this.articleType=articleType;
		this.author=author;
		this.articleFrom=articleFrom;
		this.content=content;
		this.publishTime=new Date();
		this.dissertation=dissertation;
		
	}
	
	/**得到数据库里所有的文章
	 * @return
	 */
	public static List<Article> getArticleRecently(int page,int pageSize){
		
		JPAQuery jq2=  find("select count(*) as id    from Article as ar ");
		Long ar =Article.count();
		System.out.println("###############  "+ ar);
		
		JPAQuery jq=  find("select a1   from Article a1 order by publishTime desc");
		//JPAQuery jq=  find("select a1   from Articles a1 where a1.publishTime>'2014-10-19 12:09:01'");
		
		return jq.from(page*pageSize).fetch(pageSize);
	}
	
	
	
	
	/**  列出某作者所写的的文章
	 * @param author
	 * @return
	 */
	public static List<Article> findArticleByAuthor(String author,int page,int pageSize){
		JPAQuery jq=  find("select a from Article a where author=?", author);
		 
		return jq.from(page*pageSize).fetch(pageSize);
	}
	
	/**返回某时间区间的文章
	 * @param left  时间的左边
	 * @param right  时间 的右边
	 * @return  文章列表
	 */
	public static List<Article> findArticleByTime(String left,String right,int page,int pageSize){
		String sql = "select t from Article t where t.publishTime>='"+left+"' and t.publishTime<='"+right+"' order by publishTime desc";
		System.out.println(sql);
		JPAQuery jq=  find(sql);
		 
		return jq.from(page*pageSize).fetch(pageSize);
	}
	
	/**从数据库找到指定类型的文章，并返回指定的数量，按时间返回
	 * @param type  类型
	 * @param sum  个数
	 * @return list<articles>
	 */
	public static List<Article> getArticleByType(int type,int page,int pageSize){
		String sql="select t from Article t where t.articleType=? order by publishTime desc";
		JPAQuery jq = find(sql, type);
		return jq.from(page*pageSize).fetch(pageSize);

	}
	
	/**从数据库找到指定专题类型的文章，并返回指定的数量，按时间返回
	 * @param dissertation 专题类型
	 * @param sum  个数
	 * @return
	 */
	public static List<Article> getArticleByDissertation(int dissertation,int page,int pageSize){
		String sql="select t from Article t where t.dissertation=? order by publishTime desc";
		JPAQuery jq = find(sql, dissertation);
		return jq.from(page*pageSize).fetch(pageSize);
	}
	
	/**删除文章，可以删除多篇文章
	 * @param arIds  多个文章ID连起来 如 342323-748239-478928
	 */
	public static void deleteArticles(String arIds){
		String[] articlesIds=arIds.split("-");
		int length=articlesIds.length;
		long id=0;
		int result=0;
		for(int i=0;i<length;i++){
			id=Long.parseLong(articlesIds[i]);//把字符串转化
			result=delete("id=?", id);
			System.out.println("删除了文章，id="+id+"  返回的信息："+result);
			
		}
	}
	
	/**删除一篇文章
	 * @param id
	 * @return
	 */
	public static int deleteOneArticle(Long id){
		return delete("id=?", id);
	}
	
	
	/**把user里的acountName换成nickName
	 * @param d
	 * @return
	 */
	public Article changeToNickname(Article d){
		User u=User.find("acountName=?", d.getAuthor()).first();
		d.setAuthor(u.getNickName());
		return d;
	}
	
	/**批量把user里的acountName换成nickName
	 * @param d
	 * @return
	 */
	public static List<Article> changeToNicknameList(List<Article> aList){
		for(Article d:aList){
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
