package com.dsep.service.survey.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.dsep.dao.dsepmeta.survey.SurveyUserDao;
import com.dsep.entity.dsepmeta.SurveyUser;
import com.dsep.service.survey.SurveyUserService;
import com.dsep.util.expert.email.MD5Util;
import com.dsep.vm.PageVM;

public class SurveyUserServiceImpl implements SurveyUserService {

	private SurveyUserDao surveyUserDao;

	@Override
	public PageVM<SurveyUser> retriveUsers(String unitId, String discId,
			int pageIndex, int pageSize, Boolean desc, String orderProperName) {

		List<SurveyUser> list = surveyUserDao.page(pageIndex, pageSize, desc,
				orderProperName);

		int totalCount = surveyUserDao.Count();

		PageVM<SurveyUser> result = new PageVM<SurveyUser>(pageIndex,
				totalCount, pageSize, list);
		return result;
	}

	@Override
	public void extractSurveyUsersFromExcel(String excelPath, String unitId, String discId) {
		File inputWorkbook = new File(excelPath);
		Workbook w = null;
		try {
			try {
				w = Workbook.getWorkbook(inputWorkbook);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			List<SurveyUser> surveyUsers = new ArrayList<SurveyUser>();
			
			for (int i = 1; i < sheet.getRows(); i++) {
				SurveyUser surveyUser = new SurveyUser();
				String allContent = "";
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					allContent += cell.getContents();
				}
				// 如果某行没有值，结束
				if (allContent.equals("")) break;
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					String content = cell.getContents();
					// 这个必须要和excel中一致
					switch (j) {
					case 0:
						surveyUser.setName(content);
						break;
					case 1:
						surveyUser.setGender(content);
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						if (content.equals("在校生")) surveyUser.setUserType(1);
						else if (content.equals("毕业生")) surveyUser.setUserType(2);
						else if (content.equals("用人单位")) surveyUser.setUserType(3);
						break;
					case 5:
						surveyUser.setEmail(content);
						break;
					case 6:
						if (null == discId) surveyUser.setUnitId(content);
						break;
					}

				}
				if (null != discId) surveyUser.setDiscId(discId);
				surveyUser.setUnitId(unitId);
				surveyUsers.add(surveyUser);
			}
			
			for (SurveyUser surveyUser : surveyUsers) {
				this.surveyUserDao.saveOrUpdate(surveyUser);
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveOrUpdate(SurveyUser surveyUser) {
		String code = surveyUser.getValidateCode();
		if (null == code || "".equals(code)) {
			surveyUser.setValidateCode(MD5Util.encode2hex(surveyUser.getEmail()));
		}
		this.surveyUserDao.saveOrUpdate(surveyUser);
	}

	@Override
	public void deleteSurveyUser(String userId) {
		this.surveyUserDao.deleteByKey(userId);

	}

	@Override
	public SurveyUser getSurveyUser(String userId) {
		return this.surveyUserDao.get(userId);
	}

	public SurveyUserDao getSurveyUserDao() {
		return surveyUserDao;
	}

	public void setSurveyUserDao(SurveyUserDao surveyUserDao) {
		this.surveyUserDao = surveyUserDao;
	}

}
