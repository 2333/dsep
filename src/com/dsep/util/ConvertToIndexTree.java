package com.dsep.util;

import java.util.ArrayList;
import java.util.List;

import com.dsep.entity.dsepmeta.IndexMap;

public class ConvertToIndexTree {
	
	public static final List<IndexMap> convertToTree(List<IndexMap> list){
		List<IndexMap> Index1List = new ArrayList<IndexMap>();
		List<IndexMap> Index2List = new ArrayList<IndexMap>();
		List<IndexMap> Index3List = new ArrayList<IndexMap>();
		for(IndexMap o:list){
			if(o.getId().length()==9){
				Index1List.add(o);
			}
			if(o.getId().length()==11){
				Index2List.add(o);
			}
			if(o.getId().length()==13){
				Index3List.add(o);
			}
		}
		List<IndexMap> result = new ArrayList<IndexMap>();
		for(IndexMap o:Index1List){
			result.add(o);
			for(IndexMap e:Index2List){
				if(e.getId().substring(0, 9).equals(o.getId())){
					result.add(e);
					for(IndexMap m:Index3List){
						if(m.getId().substring(0, 11).equals(e.getId())){
							result.add(m);
						}
					}
				}
			}
		}
		return result;
	}
}
