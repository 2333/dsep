package com.dsep.service.briefManage.impl;

import org.apache.commons.lang.StringUtils;

import com.dsep.common.exception.BusinessException;
import com.dsep.dao.dsepmeta.flow.EvalFlowDao;
import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.Teacher;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.Eval;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.briefManage.BriefManageService;
import com.dsep.service.file.BriefsheetService;
import com.dsep.service.rbac.TeacherService;
import com.dsep.util.WebServiceUtil;
import com.dsep.util.briefsheet.AbstractPDF;
import com.dsep.util.briefsheet.PDFFactory;
import com.dsep.vm.brief.DiscBriefVM;

public class BriefManageServiceImpl implements BriefManageService{

	private EvalFlowDao evalFlowDao;
	private AttachmentService attachmentService;
	private TeacherService teacherService;
	private BriefsheetService briefsheetService;
	
	@Override
	public DiscBriefVM getDiscBriefVM(String unitId, String discId) {
		// TODO Auto-generated method stub
		Eval eval = evalFlowDao.getEvalByUnitIdAndDiscId(unitId, discId);
		if(eval!=null){
			return new DiscBriefVM(eval);
		}else{
			throw new BusinessException("该学校学科没有参与本轮评估！");
		}
	}
	@Override
	public DiscBriefVM getTeacherBriefVM(String userId) {
		// TODO Auto-generated method stub
		Teacher teacher =  teacherService.geTeacherById(userId);
		if(teacher!=null){
			return new DiscBriefVM(teacher);
		}else{
			throw new BusinessException("没有该教师！");
		}
	}
	
	@Override
	public String produceBrief(String unitId, String discId) {
		// TODO Auto-generated method stub

		Object[] args= new Object[4];
		args[0] = new String(unitId);
		args[1] = new String(discId);
		args[2] = new String("zyxw");
		Eval eval = evalFlowDao.getEvalByUnitIdAndDiscId(unitId, discId);
		String discIntroduceId=eval.getDiscIntroduceId();
		if( discIntroduceId==null ) return "{\"result\":\""+"failure"+"\",\"data\":\" \"}";
		else 
		{
			String address =attachmentService.getAttachmentPath(discIntroduceId);
			args[3] = new String(address);
			Object[] result = WebServiceUtil.invoke("Generate", args);
			String id = (String)(result[0]);
			return "{\"result\":\""+"success"+"\",\"data\":\""+id+"\"}";
		}
	}
	@Override
	public String produceBrief(String userId) {
		// TODO Auto-generated method stub
		Object[] args = new Object[2];
		args[0] =  new String(userId);
		args[1] = new String("zj");
		Object[] result = WebServiceUtil.invoke("Generate", args);
		String id = (String)(result[0]);
		return "{\"result\":\""+"success"+"\",\"data\":\""+id+"\"}";
	}
	@Override
	public String downLoadBrief(String briefId) {
		// TODO Auto-generated method stub
		return attachmentService.getAttachmentPath(briefId);
	}
	@Override
	public String generateBrief(User user, String briefId) {
		//删除之前生成的brief记录
		if(!StringUtils.isEmpty(briefId)){
			attachmentService.deleteAttachment(briefId);//todo，需要回调支持
		}
		//String briefName = user.getLoginId() + user.getName();todo，移至工厂函数执行
		AbstractPDF brief = PDFFactory.briefFactory(user);
		AttachmentHelper ah = attachmentService.getBriefHelper(brief, user.getId(), AttachmentType.BRIEF, user.getUnitId());
		try{
			//生成pdf文件
			briefsheetService.generateBriefSheet(brief);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//写入数据库
		String fileId = attachmentService.addAttachment(ah.getAttachment());//todo，需要回调支持
		return "{\"result\":\""+"success"+"\",\"data\":\""+fileId+"\"}";
	}
	
	public EvalFlowDao getEvalFlowDao() {
		return evalFlowDao;
	}
	public void setEvalFlowDao(EvalFlowDao evalFlowDao) {
		this.evalFlowDao = evalFlowDao;
	}
	public AttachmentService getAttachmentService() {
		return attachmentService;
	}
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	public TeacherService getTeacherService() {
		return teacherService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	public BriefsheetService getBriefsheetService() {
		return briefsheetService;
	}
	public void setBriefsheetService(BriefsheetService briefsheetService) {
		this.briefsheetService = briefsheetService;
	}
}
