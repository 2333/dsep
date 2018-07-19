package com.dsep.util.crawler.spider;

import java.util.ArrayList;
import java.util.List;

public class IdOrderStrSequRecorder {
	List<String> list = new ArrayList<String>();
	
	public void add(String idOrderStr) {
		list.add(idOrderStr);
	}
	
	// count from 0
	public String getIdOrderStrBySequ(int sequ) {
		return list.get(sequ);
	}
}
