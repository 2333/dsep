package com.dsep.service.project.email;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.project.PassItem;

@Transactional(propagation = Propagation.SUPPORTS)
public interface ProjectEmailService {

	public abstract int sendEmail(String basePath, List<PassItem> items,
			String projectId, String emailTitle, String emailContentFromJSP,
			String attachementPath, String attchmentName) throws Exception;
}
