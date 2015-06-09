package javabean;



/**
 * 某个概念具体对应的词
 * @author hx
 * 
 */
public class Keyword {
	private int keywordId;    //如果是概念是从数据库中读取的，则keywordId是数据库中相应的id

	private String keywordName;//如果是本体定义则直接是概念名，如果是数据库定义，name = 表名.字段名及其别名
	
	private String tags;
	private String tableName;              //本体定义是数据库，就存数据库的表名，为了后期方便回写数据库

	
	public Keyword(int keywordId, String keywordName, String tableName) {
		super();
		this.keywordId = keywordId;
		this.keywordName = keywordName;
		this.tableName = tableName;
	}
	
	
	public int getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(int keywordId) {
		this.keywordId = keywordId;
	}
	public String getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	

	public void setTags(String tags) {
		this.tags = tags;
	}


	public String getTags() {
		return tags;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


/*	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return keywordName;
	}*/



	
	

}
