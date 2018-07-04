package com.dsep.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;

public class Dictionaries {

	
	/**
	 * Map的key为ID，value为名字
	 */
	
	private static Dictionaries dics;
	private UnitService unitService;
	private Map<String, String> unitDic;


	
	private DisciplineService disciplineService;
	private Map<String, String> disciplineDic;
	
	

	public void init() {
		dics = this;
		dics.unitService = this.unitService;
		dics.disciplineService = this.disciplineService;
	}

	

	
	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public DisciplineService getDisciplineService() {
		return disciplineService;
	}

	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}
	
    
	public static String getUnitName(String unitId) {
		Map<String,String> unitDic = dics.unitService.getAllUnitMaps();
		/*if (Dictionaries.unitDic == null)
			Dictionaries.unitDic = dics.unitService.getAllUnitMaps();*/
		String unitName = "无此学校";
		if (unitDic.containsKey(unitId))
			unitName = unitDic.get(unitId);
		return String.format("(%s)%s", unitId, unitName);
	}
	
	public static String getPureUnitName(String unitId) {
		Map<String,String> unitDic = dics.unitService.getAllUnitMaps();
		/*if (Dictionaries.unitDic == null)
			Dictionaries.unitDic = dics.unitService.getAllUnitMaps();*/
		String unitName = "无此学校";
		if (unitDic.containsKey(unitId))
			unitName = unitDic.get(unitId);
		return unitName;
	}

	public static String getDisciplineName(String disciplineId) {
		Map<String,String> discDic = dics.disciplineService.getAllDiscMap();
		/*if (Dictionaries.disciplineDic == null)
			Dictionaries.disciplineDic = dics.disciplineService
					.getAllDiscMap();*/
		String disciplineName = "无此学科";
		if (discDic.containsKey(disciplineId))
			disciplineName = discDic.get(disciplineId);
		return String.format("(%s)%s", disciplineId, disciplineName);
	}
	public static String getPureDisciplineName(String disciplineId) {
		Map<String,String> discDic = dics.disciplineService.getAllDiscMap();
		/*if (Dictionaries.disciplineDic == null)
			Dictionaries.disciplineDic = dics.disciplineService
					.getAllDiscMap();*/
		String disciplineName = "无此学科";
		if (discDic.containsKey(disciplineId))
			disciplineName = discDic.get(disciplineId);
		return disciplineName;
	}

	public static String getRightTypeName(String typeId) {
		switch (typeId) {
		case "1":
			return "菜单权限";
		case "2":
			return "动作权限";
		default:
			return "无此权限";
		}
	}

	public static String getUserTypeName(String typeId) {
		if (typeId == null)
			return "无此类型";
		if("D201301".equals(Configurations.getCurrentDomainId())){
			switch (typeId) {
			case "0":
				return "管理员";
			case "1":
				return "学位中心用户";
			case "2":
				return "学校用户";
			case "3":
				return "学科用户";
			case "4":
				return "教师用户";
			case "5":
				return "专家用户";
			case "6":
				return "问卷对象";
			default:
				return "无此类型";
			}
		}
		else{
			switch (typeId) {
			case "0":
				return "管理员";
			case "1":
				return "学位中心用户";
			case "2":
				return "学校用户";
			case "3":
				return "学位点用户";
			case "4":
				return "教师用户";
			case "5":
				return "专家用户";
			case "6":
				return "问卷对象";
			default:
				return "无此类型";
			}
		}

	}

	//返回项目指南状态
	public static String getSchoolProjectState(int state)
	{
		switch(state){
		case 0:
			return "已结束";
		case 1:
			return "未发布";
		case 2:
			return "申报中";
		case 3:
			return "评审中";
		case 4:
			return "进行中";
		case 5:
			return "中期检查";
		case 6:
			return "中期检查结束";
		case 7:
			return "结题检查";
		case 8:
			return "结果发布";
		default:
			return "已结束";
		}
	}
	// 返回教师采集-学术论文的收录类型
	// hotfix
	public static String getProfThesisCollectionTypes(String typeId) {
		switch (typeId) {
		case "1":
			return "类型一";
		case "2":
			return "类型二";
		case "3":
			return "类型三";
		default:
			return "类型错误";
		}
	}

	// 返回流程的状态码信息
	/**
	 * Status 0:流程在学科，初始化状态 1:流程在学校，由学科传来 2:流程在中心，由学校传来 3:流程在学科，由学校退回
	 * 4:流程在学校，由中心退回
	 * 
	 * discip->college:0->1/3->1 college->discip:1->3 college->center:1->2/4->2
	 * center->college:2->4
	 */
	public static String getCollectFlowTypes(Integer status) {
		if("D201301".equals(Configurations.getCurrentDomainId())){
			switch (status) {
			case 0:
				return "学科正在修改";
			case 1:
				return "已提交至学校";
			case 2:
				return "已提交至学位中心";
			case 3:
				return "退回至学科";
			case 4:
				return "学校撤销提交";
			case 5:
				return "提交终止";
			case 6:
				return "学校正在修改";
			default:
				return "类型错误";
			}
		}
		else{
			switch (status) {
			case 0:
				return "学位点正在修改";
			case 1:
				return "已提交至学校";
			case 2:
				return "已提交至学位中心";
			case 3:
				return "退回至位点";
			case 4:
				return "学校撤销提交";
			case 5:
				return "提交终止";
			case 6:
				return "学校正在修改";
			default:
				return "类型错误";
			}
		}

	}
	/**
	 * 获取当前主流程状态
	 * 0，本轮参评未开始进行
	 * 1，正在预参评
	 * 2，预参评结束
	 * 3，正在申报
	 * 4，申报结束
	 * 5，预公示
	 * 6，预公示结束
	 * 7，正在公示
	 * 8，公示结束
	 * 9，正在反馈
	 * 10，反馈结束
	 * 11，本轮参评结束
	 * @param state
	 * @return
	 */
	public static String getMainFlowType(String state)
	{
		switch(state)
		{
			/*case "0":
				return "本轮参评未开始进行";
			case "1":
				return "正在预参评";
			case "2":
				return "预参评结束";
			case "3":
				return "正在申报";
			case "4":
				return "申报结束";
			case "5":
				return "预公示";
			case "6":
				return "预公示结束";
			case "7":
				return "正在公示";
			case "8":
				return "公示结束";
			case "9":
				return "正在反馈";
			case "10": 
				return "反馈结束";
			case "11":
				return "本轮参评结束";*/
			case "0":
				return "数据申报";
			case "1":
				return "内部处理处理";
			case "2":
				return "本轮评估结束";
			default:
				return "类型错误";
		}
	}

	//
	public static Map<String, String> getCollectFlowTypesMap() {
		Map<String, String> map = new HashMap<String, String>();
		if("D201301".equals(Configurations.getCurrentDomainId())){
			map.put("0","学科正在修改");
			map.put("3","退回至学科");
		}
		else{
			map.put("0", "学位点正在修改");
			map.put("3", "退回至学位点");
		}
		map.put("1", "已提交至学校");
		map.put("2", "已提交至学位中心");
		map.put("4", "撤销提交");
		map.put("5", "提交终止");
		return map;
	}
	
	//得到所有的权限类型Map
	public static Map<String, String> getCategoryType(){
	    Map<String, String> categorytype=new TreeMap<String,String>();
		categorytype.put("1", "菜单权限");
		categorytype.put("2", "动作权限");
		return categorytype;
	}
	//得到所有用户类型的Map
	public static Map<String, String>getUserType() {
		Map<String, String>userType=new TreeMap<String,String>();
		userType.put("1", "学位中心用户");
		userType.put("2", "学校用户");
		if("D201301".equals(Configurations.getCurrentDomainId()))
			userType.put("3","学科用户");
		else
			userType.put("3", "学位点用户");
		userType.put("4", "教师用户");
		userType.put("5", "专家用户");
		userType.put("6", "问卷对象");
		userType.put("0", "管理员");
		return userType;
	}
	/**
	 * 已选专家管理表格中，对应专家的【确认情况】
	 * @param confirm
	 * @return
	 */
	public static String getExpertConfirmName(int confirm) {

		switch (confirm) {
		case 0:
			return "未发送邮件";
		case 1:
			return "已发送邮件";
		case 2:
			return "已再次发送邮件";
		case 3:
			return "确认参评";
		case 4:
			return "拒绝参评";
		case 5:
			return "正在打分";
		case 6:
			return "完成打分";
		default:
			return "其他";
		}
	}
	
	/**
	 * 已选专家管理表格中，对应专家的【专家类型】
	 * @param type
	 * @return
	 */
	public static String getExpertTypeName(String type) {

		switch (type) {
		case "0":
			return "学术";
		case "1":
			return "行业";
		default:
			return "其他";
		}
	}
	
	/**
	 * 已选专家管理表格中，对应专家的【邮件通知】、【成果】、【单项】、【声誉】
	 * @param b
	 * @return
	 */
	public static String getExpertStatusName(boolean b) {

		if(b)
			return "是";
		else {
			return "否";
		}
	}
	
	public static String getErrorType(String errorNo){
		switch (errorNo) {
		case "S":
			return "字符串";
		case "D":
			return "日期、时间";
		case "N":
			return "数字";
		case "P":
			return "百分比、比例";
		default:
			return "其他";
		}
	}

	/*
	 * 获取新闻重要级别值
	 */
	public static String getNewsImportantLevel(String level){
		switch (level) {
		case "0":
			return "普通";
		case "1":
			return "重要";
		default:
			return "其他";
		}
	}
	
	/**
	 * 获取新闻重要级别
	 * @return
	 */
	public static Map<String, String> getNewsImportantLevel() {
		Map<String, String> levelType=new TreeMap<String,String>();
		levelType.put("0", "普通");
		levelType.put("1", "重要");
		levelType.put("2", "其他");
		return levelType;
	}
	
	/*
	 * 新闻类型
	 */
	public static String getNewsType(String type){
		switch (type) {
		case "0":
			return "通知";
		case "1":
			return "报表";
		case "2":
			return "宣传";
		case "3":
			return "账单";
		default:
			return "其他";
		}
	}
	/**
	 * 获取新闻类型
	 * @return
	 */
	public static Map<String, String> getNewsType() {
		Map<String, String> newsType=new TreeMap<String,String>();
		newsType.put("0", "通知");
		newsType.put("1", "报表");
		newsType.put("2", "宣传");
		newsType.put("3", "账单");
		newsType.put("4", "其他");
		return newsType;
	}
	
	/*
	 * 调查对象
	 */
	public static String getSurveyType(String type){
		switch (type) {
		case "1":
			return "在校生";
		case "2":
			return "毕业生";
		case "3":
			return "专家";
		case "4":
			return "用人单位";
		default:
			return "其他";
		}
	}
	/**
	 * 获取调查对象类型
	 * @return
	 */
	public static Map<String, String> getSurveyType() {
		Map<String, String> Type=new TreeMap<String,String>();
		Type.put("1", "在校生");
		Type.put("2", "毕业生");
		Type.put("3", "专家");
		Type.put("4", "用人单位");
		return Type;
	}
	
	
	public static String getGenderType(String type){
		switch (type) {
		case "0":
			return "女";
		case "1":
			return "男";
		default:
			return "其他";
		}
	}
	
	public static Map<String, String> getGenderType() {
		Map<String, String> Type=new TreeMap<String,String>();
		Type.put("0", "女");
		Type.put("1", "男");
		return Type;
	}
}
