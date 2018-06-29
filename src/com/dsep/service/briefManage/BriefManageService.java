package com.dsep.service.briefManage;

import com.dsep.entity.User;
import com.dsep.vm.brief.DiscBriefVM;

public interface BriefManageService {
	
	/**
	 * 通过学校、学科获取简况表VM
	 * @param unitId
	 * @param discId
	 * @return
	 */
	public DiscBriefVM getDiscBriefVM(String unitId,String discId);
    /**
     * 通过教师ID获取简况表vm
     * @param userId
     * @return
     */
	public DiscBriefVM getTeacherBriefVM(String userId);
	/**
	 * 通过学校Id，学科Id生成简况表
	 */
	public String produceBrief(String unitId,String discId);
	/**
	 * 生成教师简况表
	 * @param userId
	 * @return
	 */
	public String produceBrief(String userId);
	/**
	 * 通过简况表Id下载简况表
	 */
	public String downLoadBrief(String briefId);
	/**
	 *@author qyx
	 *2015年1月22日 下午3:37:31
	 * @param briefId TODO
	 *@return
	 */
	public String generateBrief(User user, String briefId);
}
