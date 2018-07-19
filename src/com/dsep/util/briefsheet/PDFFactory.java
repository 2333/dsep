package com.dsep.util.briefsheet;

import com.dsep.entity.User;

public class PDFFactory {
	public static BasicPDF briefsheetFactory(int type,String unitId, String discId, String teacherId){
		switch(type){
		case 1:
			return new TeacherBriefsheet(teacherId);
		case 2:
			return new DiscBriefsheet(unitId, discId);
		default:
			return null;
		}
	}

	public static AbstractPDF briefFactory(User user) {
		// TODO Auto-generated method stub
		String type = user.getUserType();
		switch(type){
		case "0":
			break;
		case "1":
			break;
		case "2":
			break;
		case "3":
			break;
		case "4":
			break;
		case "5":
			break;
		}
		return null;
	}
}
