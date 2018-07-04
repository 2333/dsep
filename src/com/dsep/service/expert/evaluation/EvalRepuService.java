package com.dsep.service.expert.evaluation;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalRepuVM;

public interface EvalRepuService {

	/**
	 * 通过专家的信息和具体成果子项id即subQuestion
	 * 展示专家为摸个具体成果子项打分的界面，包括显示已经打过的分数
	 */
	public abstract PageVM<EvalRepuVM> getRepuResults(CurrentBatchExpertInfo info);

	public String getRepuProg(String batchId, String expertId);
}
