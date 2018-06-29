package com.dsep.util.datacalculate;

import java.util.ArrayList;
import java.util.List;

public class Stack {
	public List<Object> objs = new ArrayList<Object>();
	public int topSQ = -1;
	public Object top;
	public Object pre;
	
	public void push(Object T){
		objs.add(T);
		topSQ ++;
		pre = top;
		top = T;
	}
	
	public void pop(){
		objs.remove(topSQ);
		topSQ --;
		top = pre;
		pre = (topSQ==0||topSQ==-1)?null:(objs.get(topSQ-1));
	}
}

