package dataAnalysis;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 根据String或者WithReglExpr生成正则Pattern，并将结果存在一张hashMap里面
 * @author Bys
 *
 */
public class PatternAgent {
	 static HashMap<Object,Pattern> pmap =new HashMap<Object,Pattern>();
	
	public static Pattern getPattern(Object o) {
		if(pmap==null){
			pmap = new HashMap<Object,Pattern>();
		}
		if (pmap.containsKey(o))
			return pmap.get(o);
		else {
			Pattern p = loadPattern(o);
			pmap.put(o, p);
			return p;
		}
	}

	private static Pattern loadPattern(Object o) {
		// TODO Auto-generated method stub
		String exp = null;
		if (o instanceof String)
			exp = (String)o;
		else if (o instanceof WithReglExpr)
			exp = ((WithReglExpr)o).generateExp();
		Pattern p = exp==null?null:Pattern.compile(exp);
		if (p==null)
			System.out.println("[严重警告] loadPattern()的参数必须是String或WithReglExpr对象");
		return p;
	}
	
	
}
