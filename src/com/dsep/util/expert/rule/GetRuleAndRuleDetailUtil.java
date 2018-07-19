package com.dsep.util.expert.rule;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.util.GUID;

public class GetRuleAndRuleDetailUtil {
	public static ExpertSelectionRule packageRule(HttpServletRequest request,
			Timestamp oldEntityCreateTime, String ruleName, String comment) {
		//String ruleNameParam = "rule.ruleName";
		String idParam = "rule.id";
		String createTimeParam = "rule.createTime";
		String modifyTimeParam = "rule.modifyTime";
		//String commentParam = "rule.commentForRule";
		//String ruleName = request.getParameter(ruleNameParam);

		String createTime = request.getParameter(createTimeParam);

		//String comment = request.getParameter(commentParam);

		// 以下处理从前台传来的中文规则名字
		/*try {
			//ruleName = new String(ruleName.getBytes("ISO-8859-1"), "UTF-8");
			//comment = new String(comment.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}*/

		String ruleId = request.getParameter(idParam);

		ExpertSelectionRule rule = new ExpertSelectionRule();

		// 初次建立rule，没有id，需要设置ID和创建时间
		if ((null == ruleId) || (ruleId.equals(""))) {
			rule.setId(GUID.get());
			// 赋创建时间初值
			rule.setCreateDate(getCurrentTime());
			rule.setModifyDate(null);
			rule.setCommentForRule(comment);
		}
		// 有id，则删除原来的rule，把原来的rule的有用信息赋给新建的rule
		else {
			// trim()去除前台空格
			rule.setId(GUID.get());
			rule.setCreateDate(oldEntityCreateTime);
			rule.setModifyDate(getCurrentTime());
			rule.setCommentForRule(comment);
		}

		rule.setRuleName(ruleName);
		return rule;
	}

	private static Timestamp getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
		Timestamp now = new Timestamp(System.currentTimeMillis());// 获取系统当前时间
		return now;
	}

	public static ExpertSelectionRuleDetail packageRuleDetail(
			HttpServletRequest request, int number) {
		ExpertSelectionRuleDetail detail = new ExpertSelectionRuleDetail();

		String idParam = "detail" + number + ".id";
		String id = request.getParameter(idParam);
		if (null != id) {
			// trim()去除前台空格
			detail.setId(id.trim());
		}
		String operatorParam = "detail" + number + ".operator";
		String operator = request.getParameter(operatorParam);
		detail.setOperator(operator);
		if (operator.equals("check")) {
			String checkParam = "detail" + number + ".check";
			String isChecked = request.getParameter(checkParam);
			System.out.println(number + " " + isChecked);
			if ("on".equals(isChecked)) {
				// 被勾选了
				detail.setConditionChecked(true);
			}
		}

		String restrict1Param = "detail" + number + ".restrict1";
		String restrict2Param = "detail" + number + ".restrict2";
		String restrict3Param = "detail" + number + ".restrict3";
		String restrict4Param = "detail" + number + ".restrict4";
		String restrict5Param = "detail" + number + ".restrict5";
		String restrict6Param = "detail" + number + ".restrict6";
		String restrict7Param = "detail" + number + ".restrict7";
		String restrict8Param = "detail" + number + ".restrict8";
		String restrict9Param = "detail" + number + ".restrict9";
		String restrict10Param = "detail" + number + ".restrict10";

		String restrict1 = request.getParameter(restrict1Param);
		String restrict2 = request.getParameter(restrict2Param);
		String restrict3 = request.getParameter(restrict3Param);
		String restrict4 = request.getParameter(restrict4Param);
		String restrict5 = request.getParameter(restrict5Param);
		String restrict6 = request.getParameter(restrict6Param);
		String restrict7 = request.getParameter(restrict7Param);
		String restrict8 = request.getParameter(restrict8Param);
		String restrict9 = request.getParameter(restrict9Param);
		String restrict10 = request.getParameter(restrict10Param);

		detail.setRestrict1(restrict1);
		detail.setRestrict2(restrict2);
		detail.setRestrict3(restrict3);
		detail.setRestrict4(restrict4);
		detail.setRestrict5(restrict5);
		detail.setRestrict6(restrict6);
		detail.setRestrict7(restrict7);
		detail.setRestrict8(restrict8);
		detail.setRestrict9(restrict9);
		detail.setRestrict10(restrict10);

		String itemNameParam = "detail" + number + ".itemName";
		String itemName = request.getParameter(itemNameParam);
		detail.setItemName(itemName);

		String sequPara = "detail" + number + ".sequ";
		String sequ = request.getParameter(sequPara);
		detail.setSequ(sequ);
		return detail;
	}

}