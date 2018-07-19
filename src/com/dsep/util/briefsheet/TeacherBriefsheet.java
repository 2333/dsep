package com.dsep.util.briefsheet;

import java.io.File;

public class TeacherBriefsheet extends BasicPDF {
	public TeacherBriefsheet(String teacherId){
		this.path = PathProvider.getStoreRootPath()
					+ "teacher" + File.separator;
		this.name = teacherId + ".pdf";
		this.imagePath = PathProvider.getStoreRootPath()
						+ teacherId + File.separator;
	}
}
