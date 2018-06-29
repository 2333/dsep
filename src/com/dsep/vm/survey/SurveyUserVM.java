package com.dsep.vm.survey;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.dsepmeta.SurveyUser;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class SurveyUserVM {
	private String id;
	private SurveyUser surveyUser;
	private String currentStatus;



	private String unitName;
	private String discName;

	private String discId;
	
}
