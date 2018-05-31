package com.dsep.service.base;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.dsepmeta.TeachDisc;
import com.dsep.vm.PageVM;
import com.dsep.vm.teacher.SelectedTeachVM;
import com.dsep.vm.teacher.ViewTeachVM;

public interface TeachDiscService {
	
	/**
	 * 获取基础库教师信息，并封装成VM
	 * @param unitId
	 * @param discId
	 * @param pageIndex
	 * @param pageSize
	 * @param orderProperName
	 * @param dsec
	 * @return
	 */
	public abstract PageVM<ViewTeachVM> getViewTeachDiscPageVM(String teachLoginId,String teachName,String unitId,
			String discId1,String discId2,int pageIndex,int pageSize,String orderProperName,
			Boolean dsec);
	/**
	 * 获取教师学科信息，并封装成VM
	 * @param unitId
	 * @param discId
	 * @param pageIndex
	 * @param pageSize
	 * @param orderProperName
	 * @param desc
	 * @return
	 */
	public abstract PageVM<SelectedTeachVM> getTeachDiscPageVM(String teachLoginId,String teachName,String unitId,String discId,int pageIndex,
			int pageSize,String orderProperName,Boolean desc);
	/**
	 * 用户信息从基础用户信息表导入到教师学科表
	 * @param unitId
	 * @param discId
	 * @param pkValue 用户信息表中的主键
	 * @return 新插入记录的序号
	 */
	public abstract String import2FromUser(String unitId,String discId,String pkValue);
	/**
	 * 删除一条教师学科记录
	 * @param unitId
	 * @param discId
	 * @param pkValue
	 * @param seqNo
	 * @return 返回删除记录的主键
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String delTeachDisc(String unitId,String discId,String pkValue,String seqNo);
	/**
	 * 获取某个学校、学科的已选教师ID
	 * @param unitId
	 * @param discId
	 * @return 教师ID列表
	 */
	public abstract List<String> getTeachIds(String unitId,String discId);
	
	/**
	 * 该教师是否已经导入学科教师表中
	 * @param teacherId
	 * @return
	 */
	public abstract boolean isTeacherExist(String unitId,String discId,String teacherId);
}
