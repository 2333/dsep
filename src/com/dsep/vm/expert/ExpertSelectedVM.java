package com.dsep.vm.expert;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.Expert;
import com.dsep.util.Dictionaries;
import com.dsep.util.expert.ExpertEvalCurrentStatus;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class ExpertSelectedVM {
	private String id;
	private Expert expertSelected;
	private String currentStatus;
	private String indicatorName;
	private String indicatorWeightName;
	private String achievementName;
	private String reputationName;
	private String expertTypeName;

	private Integer isRefused;

	public Integer getIsRefused() {
		return isRefused;
	}

	public void setIsRefused(Integer isRefused) {
		this.isRefused = isRefused;
	}

	private String collegeName;
	private String disciplineName;

	private String disciplineCode;

	private String expertName;
	private String expertNumber;
	private String phone;
	private String email;
	
	private String discId;
	private String discId2;
	
	

	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public String getDiscId2() {
		return discId2;
	}

	public void setDiscId2(String discId2) {
		this.discId2 = discId2;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getExpertNumber() {
		return expertNumber;
	}

	public void setExpertNumber(String expertNumber) {
		this.expertNumber = expertNumber;
	}

	public String getDisciplineCode() {
		return disciplineCode;
	}

	public void setDisciplineCode(String disciplineCode) {
		this.disciplineCode = disciplineCode;
	}

	public ExpertSelectedVM(Expert expert) {
		this.expertSelected = expert;
		this.id = this.expertSelected.getId();
		setCurrentStatus(String.valueOf(expert.getCurrentStatus()));
		setExpertName(expert.getExpertName());

		/*String discId = (null != expert.getDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();*/

		// 只set学校和学科代码，到VM中进行转化
		setCollegeName(Dictionaries.getUnitName(expert.getUnitId()));
		setDisciplineName(Dictionaries.getDisciplineName(expert.getRealDiscId()));
		// BGDH 代表“办公电话”
		setPhone(expert.getOfficePhone());
		// DZXX 代表“电子信箱”
		setEmail(expert.getExpertEmail1());
		// ZJFL 代表“专家分类（同行还是学术）”
		setExpertTypeName(expert.getExpertType());
		// ZJBH 代表“专家编号”
		setExpertNumber(expert.getExpertNumber());
		// 一级学科码
		setDisciplineCode(expert.getRealDiscId());
		if (ExpertEvalCurrentStatus.Refused.toInt() == expert
				.getCurrentStatus()) {
			setIsRefused(1);
		} else {
			setIsRefused(0);
		}

	}

	public ExpertSelectedVM(Expert expertSelected,
			OuterExpert expert) {
		this.expertSelected = expertSelected;
		this.id = this.expertSelected.getId();

		setCurrentStatus(String.valueOf(expertSelected.getCurrentStatus()));
		setExpertName(expert.getZJXM());

		// 只set学校和学科代码，到VM中进行转化
		setCollegeName(expert.getXXDM());
		setDisciplineName(expert.getYJXKM());
		// BGDH 代表“办公电话”
		setPhone(expert.getBGDH());
		// DZXX 代表“电子信箱”
		setEmail(expert.getDZXX());
		// ZJFL 代表“专家分类（同行还是学术）”
		setExpertTypeName(expert.getZJFL());
		// ZJBH 代表“专家编号”
		setExpertNumber(expert.getZJBH());
		// 一级学科码
		setDisciplineCode(expert.getYJXKM());

	}

	public void showYJXKMAndYJXKM2() {
		//以后要加，现在写死
		//if (null != this.expertSelected.getDisciplineCode2()) {
		//	setDisciplineCode(this.expertSelected.getDisciplineCode() + "(默认)|" + this.expertSelected.getDisciplineCode2() + "(可指定)");
		//}
		if (null == this.expertSelected.getDiscId2()) {
			setDisciplineName(this.expertSelected.getDiscId());
			setDiscId(this.expertSelected.getDiscId());
		} else {
			setDisciplineName(this.expertSelected.getDiscId() + "(默认)|"
					+ this.expertSelected.getDiscId2() + "(可指定)");
			setDiscId(this.expertSelected.getDiscId());
			setDiscId2(this.expertSelected.getDiscId2());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpertTypeName() {
		return expertTypeName;
	}

	public void setExpertTypeName(String expertTypeName) {
		this.expertTypeName = expertTypeName;
	}

	public String getCurrentStatus() {
		this.currentStatus = Dictionaries
				.getExpertConfirmName(this.expertSelected.getCurrentStatus());
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getIndicatorName() {
		// 以下是test，需要删除
		//boolean indicator = false;
		//boolean indicator = this.expertSelected.getIsIndicator();
		//this.indicatorName = Dictionaries.getExpertStatusName(indicator);
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		if (null == indicatorName)
			this.indicatorName = "未开始";
		else
			this.indicatorName = indicatorName;
	}

	public String getIndicatorWeightName() {
		return indicatorWeightName;
	}

	public void setIndicatorWeightName(String indicatorWeightName) {
		if (null == indicatorWeightName)
			this.indicatorWeightName = "未开始";
		else
			this.indicatorWeightName = indicatorWeightName;
	}

	public String getAchievementName() {
		// 以下是test，需要删除
		//boolean achievement = false;
		//boolean achievement = this.expertSelected.getIsAchievement();
		//this.achievementName = Dictionaries.getExpertStatusName(achievement);
		return achievementName;
	}

	public void setAchievementName(String achievementName) {
		if (null == achievementName)
			this.achievementName = "未开始";
		else
			this.achievementName = achievementName;
	}

	public String getReputationName() {
		// 以下是test，需要删除
		//boolean reputation = false;
		//boolean reputation = this.expertSelected.getIsReputation();
		//this.reputationName = Dictionaries.getExpertStatusName(reputation);
		return reputationName;
	}

	public void setReputationName(String reputationName) {
		if (null == reputationName)
			this.reputationName = "未开始";
		else
			this.reputationName = reputationName;
	}

	public Expert getExpertSelected() {
		return expertSelected;
	}

	public void setExpertSelected(Expert expertSelected) {
		this.expertSelected = expertSelected;
	}

	public String getCollegeName() {
		// this.collegeName = expertSelected.getExpert().getUnit().getName();
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getDisciplineName() {
		// this.disciplineName =
		// expertSelected.getExpert().getDiscipline1().getName();
		return disciplineName;
	}

	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
