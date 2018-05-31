package com.dsep.vm.expert;

import com.dsep.entity.expert.EvalBatch;

public class EvalBatchVM {
	private String batchId;
	private String batchName;

	private String expertId;
	private String expertDiscId;

	public EvalBatchVM(EvalBatch batch, String expertId, String expertDiscId) {
		this.batchId = batch.getId();
		this.batchName = batch.getBatchChName();
		this.expertId = expertId;
		this.expertDiscId = expertDiscId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public String getExpertDiscId() {
		return expertDiscId;
	}

	public void setExpertDiscId(String expertDiscId) {
		this.expertDiscId = expertDiscId;
	}

}
