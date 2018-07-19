package com.dsep.service.survey;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.dsepmeta.SurveyUser;
import com.dsep.vm.PageVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface SurveyUserService {
	public abstract PageVM<SurveyUser> retriveUsers(String unitId,
			String discId, int pageIndex, int pageSize, Boolean desc,
			String orderProperName);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void saveOrUpdate(SurveyUser surveyUser);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void deleteSurveyUser(String userId);

	public abstract SurveyUser getSurveyUser(String userId);

	/**
	 * 
	 * @param excelPath
	 * @param unitId
	 * @param discId
	 * 学校用户discId必须传null,学科用户必须传入当前学科
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void extractSurveyUsersFromExcel(String excelPath,
			String unitId, String discId);
}
