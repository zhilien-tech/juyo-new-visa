/**
 * TestAnsj.java
 * com.juyo.visa
 * Copyright (c) 2019, 北京科技有限公司版权所有.
*/

package com.juyo.visa;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2019年3月7日 	 
 */
public class TestAnsj {
	public static void test() {

		String aaa = "2019/04/02";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		try {
			System.out.println(sdf.parse(sdf.format(sdf2.parse(aaa))));
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		/*String aaa = "";
		String str = null;
		Result result = ToAnalysis.parse(str);
		List<Term> terms = result.getTerms();
		for (Term term : terms) {
			String name = term.getName();
			if (!Util.isEmpty(name)) {
				aaa += name + " ";
			}
			//System.out.println(name);
		}
		System.out.println(aaa);*/
		//System.out.println(result);
		//System.out.println(result.getTerms());

		/*//只关注这些词性的词
		Set<String> expectedNature = new HashSet<String>() {
			{
				add("n");
				add("ag");
				add("m");
				add("nr");
				add("v");
				add("p");
				add("d");
				add("a");
				add("l");
				add("w");
				add("q");
				add("h");
				add("j");
				add("vd");
				add("ns");
				add("tg");
				add("vn");
				add("vf");
				add("vx");
				add("vi");
				add("vl");
				add("vg");
				add("nt");
				add("nz");
				add("nw");
				add("nl");
				add("ng");
				add("wh");
			}
		};
		String str = "农业农村部国际交流服务中心";
		Result result = ToAnalysis.parse(str); //分词结果的一个封装，主要是一个List<Term>的terms
		System.out.println(result.getTerms());

		List<Term> terms = result.getTerms(); //拿到terms
		System.out.println(terms.size());

		for (int i = 0; i < terms.size(); i++) {
			String word = terms.get(i).getName(); //拿到词
			String natureStr = terms.get(i).getNatureStr(); //拿到词性
			if (expectedNature.contains(natureStr)) {
				System.out.println(word + ":" + natureStr);
			}
		}*/
	}

	public static void main(String[] args) {
		test();
	}

}
