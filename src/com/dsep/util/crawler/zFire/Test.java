package com.dsep.util.crawler.zFire;

import java.io.File;
import java.util.LinkedList;

public class Test {
	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();	
		list.add("1077597284870");
		list.add("1035587231150");
		list.add("1096567793054");
		list.add("1049577776789");
		list.add("1034597779423");
		list.add("1023537718101");
		list.add("1071537058970");
		list.add("1038537568548");
		list.add("1045597592509");
		list.add("1034547504982");
		list.add("1069567508616");
		list.add("1089597517617");
		list.add("1061577535246");
		list.add("1045587168194");
		list.add("1067507126099");
		list.add("1087567185546");
		list.add("1069567846986");
		list.add("1067597844562");
		list.add("1081577879387");
		list.add("1016567856234");
		list.add("1069567508616");
		list.add("1089597517617");
		list.add("1061577535246");
		list.add("1045587168194");
		list.add("1067507126099");

		File f = new File("D:/MYSOUQ/20171227/1downloadOrders");
		
		for (String ele :f.list()) {
			if (list.contains(ele)) {
				System.out.println(ele);
				int x = list.indexOf(ele);
				System.out.println(x);
				list.remove(x);
			}
		}
		System.out.println("=====================");
		for (String x :list) {
			System.out.println(x);
		}
	}
}
