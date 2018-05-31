package com.dsep.util.briefsheet;

import java.io.File;

public class DiscBriefsheet extends BasicPDF {
	public DiscBriefsheet(String unitId, String discId){
		this.path = PathProvider.getStoreRootPath()
					+ "disc" + File.separator;
		this.name = unitId + discId + ".pdf";
		this.imagePath = PathProvider.getStoreRootPath()
						+ unitId + File.separator
						+ discId + File.separator;
	}
}
