package com.dsep.domain.dsepmeta.expert;

import java.util.ArrayList;
import java.util.List;

import com.dsep.entity.expert.Expert;

/**
 * 当前登录的专家、该专家在某个批次中被第几学科选中以及该选中的学科的大类封装在一起
 * 在专家选择某个批次之后就应该统一输出管理
 */
public class CurrentBatchExpertInfo {
	private String currentBatchId;
	private String expertId;

	private List<String> routes = new ArrayList<String>();


	public String getCurrentBatchId() {
		return currentBatchId;
	}

	public void setCurrentBatchId(String currentBatchId) {
		this.currentBatchId = currentBatchId;
	}

	public List<String> getRoutes() {
		return routes;
	}

	public void setRoutes(List<String> routes) {
		this.routes = routes;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

}
