/**
 * TestAnsj.java
 * com.juyo.visa
 * Copyright (c) 2019, 北京科技有限公司版权所有.
*/

package com.juyo.visa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.itextpdf.xmp.impl.Base64;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2019年3月7日 	 
 */
public class TestAnsj {

	private Lock lock = new ReentrantLock();

	public Object test() {

		lock.lock();
		try {
			String encode = Base64.encode("20124");

			System.out.println(encode);

			String decode = Base64.decode(encode);
			System.out.println(decode);

		} catch (Exception e) {

		} finally {
			lock.unlock();
		}

		return null;
		/*String aaa = "2019-04-03";
		String bbb = "2019-04-03";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			long time = sdf.parse(aaa).getTime();
			long time2 = sdf.parse(bbb).getTime();
			System.out.println(time);
			System.out.println(time2);
			System.out.println(time = time2);
		} catch (ParseException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}*/

		/*SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		try {
			System.out.println(sdf.parse(sdf.format(sdf2.parse(aaa))));
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}*/

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
		/*TestAnsj test = new TestAnsj();
		for (int i = 0; i < 100; i++) {
			System.out.println("i:" + i + "=============");
			Thread t = new Thread(new Runnable() {
				public void run() {
					// run方法调用自动填表
					test.test();
				}
			});
			t.start();
		}*/

		/*String encode = URLEncoder
				.encode("SQtwB9EqkTmNF+hS/fV2DCG3cY1xx+IYAFj7zgzG9SATW94tOYb9JuoHUJY/t+VCn9FMFyMYbI13MPBqZtGWQXjoJqL1AN9SJ3fYGx2Ux24OuESgBEdsu9Ml9vvPcKp6HrlLIPZyose4jIVeujMmj0HGu7PZgvfAkFwhg66vcrPIo7mRBza+a09AnKug4ujwu5zjsm+JXcmU7mHgH+APVQW+iUenCYUd1+JtyC/smmoAg/7EWE3rnf3yyN8sYlA8JJjGlcbaKPOiyEfZpiHD9w3ThWDus7lif4ctmC9E6jayjKMX1DjFDkuMQaMKBQG8NOM7BhFeQto2VqZDMBKLDZj+56CYKW2Akj1wZMqUIqNQkMMdmCT5cjxPqjdPDDEmYgapIxUtmhu5lSBXVNdQiwV5HDIdINRNL4WMUUHT1SlgAeY6odFIfY7pqgLL3tbHWNmqrJDPTCrpNWB1csIIGFqh2WWS8k7ZBefCrZMbrNgpqDyglME+NWQY1El297hNWRkpQcBB2spC/WFndx6VxTDNpfIk+W3kmh6L5J8Q8xnC7KC7lSkm7XpD1c4Xx8tKdVjsWZli3tN3DXoaSQeFmGkRKgOgsbMRc12gIGFhfPDlmEcfrmOLkME3IOdB8P+3dRuLqlEuUTejUn2vFjk4ZoHrekrFi/aGFS+vgbYoPSXgvGyJIdjK14KzMYSNhWkwzs3wh02G8qz0rOUhVByTb6ve04wq6qXQoVxXtGK+oX7qecROjI3tzRdXAc8OSk60/CmtVmCKSmOzt45RCpbKKI+CrcREgEDnx8YQ0XbAuGZVjaZy+k4oouXUBSTVtxvgqPe1VqDgUOKm1zX6h8FhOIRCNbtlwqrqQ8ZGBNvIFn2egW5T6sdtFcnELMowkoHW",
						"utf-8");
		System.out.println(encode);

		System.out.println(1554777083868.0 - 1554777051705.0);*/

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			Date parse = sdf.parse("2018-01-01:191010");
			System.out.println(parse);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
}
