package com.dsep.vm.expert;

import com.dsep.domain.dsepmeta.expert.EvalIndicWtAndScore;
import com.dsep.entity.expert.EvalResult;

public class EvalIndicWtVM {

	private String id;
	// 问题表的id
	private String questionId;
	// 结果表中的id
	private String resultId;

	private String effectItemNum;
	// 有的题目是针对重点实验室、基地、中心；有的是建筑设计获奖（仅对建筑学类学科）
	private String questionNamespace;
	// 有效项目的名字，比如一等奖
	private String item1;
	private String item2;
	private String item3;
	private String item4;
	private String item5;
	private String item6;
	private String item7;
	
	// 初始分数，建议值
	private String initVal1;
	private String initVal2;
	private String initVal3;
	private String initVal4;
	private String initVal5;
	private String initVal6;
	private String initVal7;
	
	private String oldVal;
	// 其他的题干信息
	private String questionStem;

	public EvalIndicWtVM(
			EvalIndicWtAndScore evalIndicatorWeightAndScore,
			EvalResult result) {
		this.id = evalIndicatorWeightAndScore.getId();

		this.questionId = evalIndicatorWeightAndScore.getQuestionId();
		this.effectItemNum = evalIndicatorWeightAndScore.getEffectItemNum();
		this.questionNamespace = evalIndicatorWeightAndScore
				.getQuestionNamespace();
		this.item1 = evalIndicatorWeightAndScore.getItem1();
		this.item2 = evalIndicatorWeightAndScore.getItem2();
		this.item3 = evalIndicatorWeightAndScore.getItem3();
		this.item4 = evalIndicatorWeightAndScore.getItem4();
		this.item5 = evalIndicatorWeightAndScore.getItem5();
		this.item6 = evalIndicatorWeightAndScore.getItem6();
		this.item7 = evalIndicatorWeightAndScore.getItem7();
		this.initVal1 = evalIndicatorWeightAndScore.getInitVal1();
		this.initVal2 = evalIndicatorWeightAndScore.getInitVal2();
		this.initVal3 = evalIndicatorWeightAndScore.getInitVal3();
		this.initVal4 = evalIndicatorWeightAndScore.getInitVal4();
		this.initVal5 = evalIndicatorWeightAndScore.getInitVal5();
		this.initVal6 = evalIndicatorWeightAndScore.getInitVal6();
		this.initVal7 = evalIndicatorWeightAndScore.getInitVal7();
		this.questionStem = evalIndicatorWeightAndScore.getQuestionStem();

		// 前台真正的题干
		this.questionStem = createQuestionStem(effectItemNum, questionStem,
				item1, item2, item3, item4, item5, item6, item7);
		// 前台真正的建议值
		this.initVal1 = createSuggestValue(initVal1, initVal2, initVal3,
				initVal4, initVal5, initVal6, initVal7);

		if (null != result) {
			// 结果表中的id，为了前端提交时saveUpdate用
			this.resultId = result.getId();
			this.oldVal = result.getEvalValue();
		} else {
			this.resultId = "";
			this.oldVal = "";
		}
		/*if (this.oldVal == null) {
			this.val1 = this.val2 = this.val3 = this.val4 = this.val5 = "";
		} else {
			String[] valArr = this.oldVal.split(",", -1);
			if (0 == valArr.length) {
				this.val1 = this.val2 = this.val3 = this.val4 = this.val5 = "";
			} else if (1 == valArr.length) {
				this.val1 = valArr[0];
				this.val2 = this.val3 = this.val4 = this.val5 = "";
			} else if (2 == valArr.length) {
				this.val1 = valArr[0];
				this.val2 = valArr[1];
				this.val3 = this.val4 = this.val5 = "";
			} else if (3 == valArr.length) {
				this.val1 = valArr[0];
				this.val2 = valArr[1];
				this.val3 = valArr[2];
				this.val4 = this.val5 = "";
			} else if (4 == valArr.length) {
				this.val1 = valArr[0];
				this.val2 = valArr[1];
				this.val3 = valArr[2];
				this.val4 = valArr[3];
				this.val5 = "";
			} else if (5 == valArr.length) {
				this.val1 = valArr[0];
				this.val2 = valArr[1];
				this.val3 = valArr[2];
				this.val4 = valArr[3];
				this.val5 = valArr[4];
			}
		}
*/
	}

	private String createSuggestValue(String initVal1, String initVal2,
			String initVal3, String initVal4, String initVal5, 
			String initVal6, String initVal7) {
		String suggestValue = initVal1;
		System.out.println((null == initVal2));
		System.out.println(("".equals(initVal2)));
		if (!(null == initVal2 || "".equals(initVal2))) {
			suggestValue += "：" + initVal2;
		}
		if (!(null == initVal3 || "".equals(initVal3))) {
			suggestValue += "：" + initVal3;
		}
		if (!(null == initVal4 || "".equals(initVal4))) {
			suggestValue += "：" + initVal4;
		}
		if (!(null == initVal5 || "".equals(initVal5))) {
			suggestValue += "：" + initVal5;
		}
		if (!(null == initVal6 || "".equals(initVal6))) {
			suggestValue += "：" + initVal6;
		}
		if (!(null == initVal7 || "".equals(initVal7))) {
			suggestValue += "：" + initVal7;
		}
		return suggestValue;
	}

	private String createQuestionStem(String effectItemNum,
			String questionStem, String item1, String item2, String item3,
			String item4, String item5 ,String item6, String item7) {
		String[] itemArr = new String[7];
		itemArr[0] = item1;
		itemArr[1] = item2;
		itemArr[2] = item3;
		itemArr[3] = item4;
		itemArr[4] = item5;
		itemArr[5] = item6;
		itemArr[6] = item7;

		Integer num = Integer.valueOf(effectItemNum);
		String stem = "";
		if (questionStem == null || "".equals(questionStem)) {

		} else {
			stem = questionStem;
		}

		if (num == 1) {
			stem += item1;
		} else {
			stem += item1;
			for (int i = 1; i < num; i++) {
				stem += "：" + itemArr[i];
			}
		}
		return stem;
	}

	private String createOldVal(String effectItemNum, String val1, String val2,
			String val3, String val4, String val5) {
		Integer num = Integer.valueOf(effectItemNum);
		String[] itemArr = new String[5];
		itemArr[0] = val1;
		itemArr[1] = val2;
		itemArr[2] = val3;
		itemArr[3] = val4;
		itemArr[4] = val5;
		String oldVal = "";

		if (num == 1) {
			if (!(val1 == null || "".equals(val1))) {
				oldVal += val1;
			}
		} else {
			if (!(val1 == null || "".equals(val1))) {
				oldVal += val1;
			}
			for (int i = 1; i < num; i++) {
				if (!(itemArr[i] == null || "".equals(itemArr[i]))) {
					// 英文逗号！
					oldVal += "," + itemArr[i];
				}
			}
		}
		return oldVal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getEffectItemNum() {
		return effectItemNum;
	}

	public void setEffectItemNum(String effectItemNum) {
		this.effectItemNum = effectItemNum;
	}

	public String getQuestionNamespace() {
		return questionNamespace;
	}

	public void setQuestionNamespace(String questionNamespace) {
		this.questionNamespace = questionNamespace;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getItem4() {
		return item4;
	}

	public void setItem4(String item4) {
		this.item4 = item4;
	}

	public String getItem5() {
		return item5;
	}

	public void setItem5(String item5) {
		this.item5 = item5;
	}
	
	public String getItem6() {
		return item6;
	}

	public void setItem6(String item6) {
		this.item6 = item6;
	}

	public String getItem7() {
		return item7;
	}

	public void setItem7(String item7) {
		this.item7 = item7;
	}

	public String getInitVal1() {
		return initVal1;
	}

	public void setInitVal1(String initVal1) {
		this.initVal1 = initVal1;
	}

	public String getInitVal2() {
		return initVal2;
	}

	public void setInitVal2(String initVal2) {
		this.initVal2 = initVal2;
	}

	public String getInitVal3() {
		return initVal3;
	}

	public void setInitVal3(String initVal3) {
		this.initVal3 = initVal3;
	}

	public String getInitVal4() {
		return initVal4;
	}

	public void setInitVal4(String initVal4) {
		this.initVal4 = initVal4;
	}

	public String getInitVal5() {
		return initVal5;
	}

	public void setInitVal5(String initVal5) {
		this.initVal5 = initVal5;
	}
	
	public String getInitVal6() {
		return initVal6;
	}

	public void setInitVal6(String initVal6) {
		this.initVal6 = initVal6;
	}

	public String getInitVal7() {
		return initVal7;
	}

	public void setInitVal7(String initVal7) {
		this.initVal7 = initVal7;
	}

	public String getQuestionStem() {
		return questionStem;
	}

	public void setQuestionStem(String questionStem) {
		this.questionStem = questionStem;
	}

	public String getOldVal() {
		return oldVal;
	}

	public void setOldVal(String oldVal) {
		this.oldVal = oldVal;
	}
}
