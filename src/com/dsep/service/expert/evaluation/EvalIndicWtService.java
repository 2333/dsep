package com.dsep.service.expert.evaluation;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalIndicWtVM;

public interface EvalIndicWtService {
	
	public abstract PageVM<EvalIndicWtVM> showIndicWtQAndResultsTable(CurrentBatchExpertInfo info);
	
	public abstract String getIndicWtProg(String batchId, String expertId);
}
