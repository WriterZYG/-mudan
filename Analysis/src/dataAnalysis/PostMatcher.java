package dataAnalysis;




import javabean.MatchedResult;
import javabean.db.Event;
import javabean.db.Page;



/**
 * 文章匹配器接口
 * @author Bys
 *
 */
public interface PostMatcher {

	
	/**
	 * @author hx
	 * 
	 * @param MatchedReslut
	 * 
	 * @param DownloadDoc
	 * 
	 * @param text
	 * 
	 * @return 1：如果抽取到的是一般事件，则返回1,即EventMatchStop 0：都没有抽到，继续抽，NoStop
	 */
	
	/**
	 * 1：如果抽取到的是一般事件，则返回1,即EventMatchStop 0：都没有抽到，继续抽，NoStop
	 * @param mr 事件的匹配结果
	 * @param page 被匹配的文档
	 * @param e
	 * @return
	 * @throws Exception
	 * * @author hx
	 */
	public int postMatcher(MatchedResult mr,Page page,Event e) throws Exception;
	
	//以下为测试
	public int postMatcher(MatchedResult mr,Page page,Event e,int test) throws Exception;

	
}
