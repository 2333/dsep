package com.dsep.service.expert.batch;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.expert.PaperAndQuestionsFromFront;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalBatchVM;
import com.dsep.vm.expert.ExpertSelectionRuleVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface EvalBatchService {
	public abstract List<EvalBatch> getEvalBatchByExpertLoginId(
			String expertLoginId);
	
	public abstract PageVM<EvalBatch> getBatchesByPageForCenter(
			int pageIndex, int pageSize, Boolean desc, String orderProperName);

	/*@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void createBatchAndPapers(EvalBatch evalBatch,
			List<PaperAndQuestionsFromFront> pAndQs);*/

	public List<EvalBatch> getAll();

	public EvalBatch getEvalBatchById(String id);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void createBatchAndPapers(String batchChName,
			List<String> industrialItems, List<String> academicItemsString,
			String beginDate, String endDate);

	public void delEvalBatch(String id);

	public void saveOrUpdateEvalBatch(String batchId, String batchNum,
			String batchChName, List<String> industrialItems,List<String> academicItems);
	
	public PageVM<EvalBatchVM> showEvalBatches(String expertLoginId);
}
