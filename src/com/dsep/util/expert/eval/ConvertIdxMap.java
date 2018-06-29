package com.dsep.util.expert.eval;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.vm.expert.L3rdIdxMapVM;

public class ConvertIdxMap {
	public static List<L3rdIdxMapVM> getL3rdIdxMap(List<IndexMap> indexItems) {
		List<L1stIdx> tree = new ArrayList<L1stIdx>();
		for (IndexMap idx : indexItems) {
			// 一级指标体系
			if (1 == idx.getIndexLevel()) {
				L1stIdx l1stIdx = new L1stIdx();
				l1stIdx.setIdx(idx);
				tree.add(l1stIdx);
			}
			// 二级指标体系
			else if (2 == idx.getIndexLevel()) {
				L2ndIdx l2ndIdx = new L2ndIdx();
				l2ndIdx.setIdx(idx);
				for (L1stIdx l1stIdx : tree) {
					// 寻找这个二级的指标体系的父指标体系
					if (idx.getParentId().equals(l1stIdx.getIdx().getId())) {
						l1stIdx.getL2ndList().add(l2ndIdx);
						break;
					}
				}
			}
			// 三级指标体系
			else if (3 == idx.getIndexLevel()) {
				// 遍历树中的一级指标体系
				for (L1stIdx l1stIdx : tree) {
					// 拿到某个一级指标体系的所有二级指标体系
					List<L2ndIdx> l2ndIdxList = l1stIdx.getL2ndList();
					{
						// 循环遍历该二级指标体系，直到该idx的父ID匹配该二级指标体系
						for (L2ndIdx l2ndIdx : l2ndIdxList) {
							System.out.println(idx.getParentId() + "$" + idx.getName());
							System.out.println(l2ndIdx.getIdx().getId() + "$" + l2ndIdx.getIdx().getName());
							
							if (idx.getParentId().equals(l2ndIdx.getIdx().getId())) {
								l2ndIdx.getL3rdList().add(idx);
								break;
							}
						}
					}
				}
			}
		}
		ArrayList<L3rdIdxMapVM> sequenceIndexMap = new ArrayList<L3rdIdxMapVM>();
		for (L1stIdx l1stIdx : tree) {
			String grandPId = l1stIdx.getIdx().getId();
			String grandPName = l1stIdx.getIdx().getName();
			String grandPWeight = convertToFloatFormat(l1stIdx.getIdx().getWeight());

			List<L2ndIdx> l2ndList = l1stIdx.getL2ndList();
			for (L2ndIdx l2nIdx : l2ndList) {
				String pId = l2nIdx.getIdx().getId();
				String pName = l2nIdx.getIdx().getName();
				String pWeight = convertToFloatFormat(l2nIdx.getIdx().getWeight());

				List<IndexMap> l3rdList = l2nIdx.getL3rdList();
				for (IndexMap idx : l3rdList) {
					String id = idx.getId();
					String name = idx.getName();
					String weight = convertToFloatFormat(idx.getWeight());

					L3rdIdxMapVM vm = new L3rdIdxMapVM(id, pId, grandPId, name,
							pName, grandPName, weight, pWeight, grandPWeight);
					sequenceIndexMap.add(vm);
				}
			}
		}
		return sequenceIndexMap;
	}
	
	private static String convertToFloatFormat(String num) {
		if (num.contains(".")) {
			 float scale = Float.valueOf(num);   
			 DecimalFormat fnum = new DecimalFormat("##0.00");    
			 return fnum.format(scale);  
		} else {
			return num + ".00";
		}
	}
}

class L1stIdx {
	IndexMap idx;
	List<L2ndIdx> l2ndList = new ArrayList<L2ndIdx>();

	public IndexMap getIdx() {
		return idx;
	}

	public void setIdx(IndexMap idx) {
		this.idx = idx;
	}

	public List<L2ndIdx> getL2ndList() {
		return l2ndList;
	}

	public void setL2ndList(List<L2ndIdx> l2ndList) {
		this.l2ndList = l2ndList;
	}
}

class L2ndIdx {
	IndexMap idx;
	List<IndexMap> l3rdList = new ArrayList<IndexMap>();

	public IndexMap getIdx() {
		return idx;
	}

	public void setIdx(IndexMap idx) {
		this.idx = idx;
	}

	public List<IndexMap> getL3rdList() {
		return l3rdList;
	}

	public void setL3rdList(List<IndexMap> l3rdList) {
		this.l3rdList = l3rdList;
	}
}