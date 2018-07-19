package com.dsep.controller.dsepmeta.teacher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dsep.common.exception.BusinessException;
import com.dsep.entity.User;
import com.dsep.util.UserSession;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Controller
@RequestMapping("/TCollect")
public class TCollectController {
	
	@RequestMapping("toTCollect")
	public String toTeacherCollect(Model model,HttpServletRequest reuqest,HttpSession session){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String userType = user.getUserType();
		switch(userType){
			case "1":
				break;
			case "2":
				model.addAttribute("user", user);
				model.addAttribute("unitId",user.getUnitId());
				return "/TDataCollect/unit_collect";
			case "3":
				model.addAttribute("user", user);
				model.addAttribute("unitId",user.getUnitId());
				model.addAttribute("discId",user.getDiscId());
				return "/TDataCollect/discipline_collect";
			case "4":
				model.addAttribute("user", user);
				return "/TDataCollect/teacher_collect";
			default: 
				return "";
		}
		throw new BusinessException("业务异常！");	
	}
	/**
	 * 对比例和名次控件的请求处理
	 * @return
	 */
	@RequestMapping("toTCollect/percentdialog")
	public String percentdialog(Model model,HttpSession session)
	{
		UserSession userSession = new UserSession(session);
		model.addAttribute("user", userSession.getCurrentUser());
	    return "/CollectMeta/editpercent";
	}
						
	@RequestMapping("toTCollect_total")
	public String totalCollects(){
		return "/TDataCollect/teach_collect_total";
	}
}
