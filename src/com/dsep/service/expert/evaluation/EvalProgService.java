package com.dsep.service.expert.evaluation;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;

@Transactional(propagation = Propagation.SUPPORTS)
public interface EvalProgService {
	/**
	 * 通过批次(batchId)获得某专家当前批次所有任务
	 * 显示专家的打分任务table
	 */
	public abstract String getQPreview(String batchId, String expertId);
	

	/**
	 * 专家打分导航路由
	 */
	public abstract List<String> getRoutes(CurrentBatchExpertInfo info);
}
