package dataAnalysis;

import java.util.Date;

import snooway.dao.DataDao;
import util.DupNewsCache;

public class DupNewsDemo {

	/**
	 * @param args
	 */
	public static DataDao da = DataDao.createDao();
	public static void main(String[] args) {
		
		
		DupNewsCache.addCacheTest(new Date());
		System.out.println();

	}

}
