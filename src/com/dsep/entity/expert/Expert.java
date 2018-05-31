package com.dsep.entity.expert;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 请代码维护者参考 {$SVN}\discipline\开发文档\专家模块 \专家遴选代码规范(开发和维护人员看！).docx
 */
@JsonAutoDetect
@JsonIgnoreProperties(value = { "evalBatch" })
public class Expert {
	// 主键GUID
	private String id;
	// 专家姓名 对应外表ZJXM
	private String expertName;
	// 专家编号 对应外表ZJBH
	private String expertNumber;
	// 专家分类 对应外表ZJFL
	private String expertType;
	// 一级学科码 对应外表YJXKM
	private String discId;
	// 一级学科码2 对应外表YJXKM2
	private String discId2;
	// 标记使用discId还是discId2,true表示使用discId
	private Boolean useDiscId;
	// 实际被遴选出的学科码，应当与userDiscId保持一致，方便排序
	private String realDiscId;
	// 专家所在学校 对应外表XXDM
	private String unitId;
	// 专家办公电话 对应外表BGDH
	private String officePhone;
	// 专家移动电话 对应外表YDDH
	private String personalPhone;
	// 电子信箱 对应外表DZXX
	private String expertEmail1;
	// 可能外表DZXX中会有多个邮箱地址
	private String expertEmail2;
	// 可能外表DZXX中会有多个邮箱地址
	private String expertEmail3;
	// 第一个电子信箱的验证码
	private String validateCode1;
	// 第二个电子信箱的验证码
	private String validateCode2;
	// 第三个电子信箱的验证码
	private String validateCode3;
	// 专家来源
	private Integer source;
	// 专家当前状态
	private Integer currentStatus;

	// 专家拒绝打分理由
	private String remark;

	private EvalBatch evalBatch = new EvalBatch();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getExpertType() {
		return expertType;
	}

	public void setExpertType(String expertType) {
		this.expertType = expertType;
	}

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

	public String getUnitId() {
		return unitId;
	}

	public Boolean getUseDiscId() {
		return useDiscId;
	}

	public void setUseDiscId(Boolean useDiscId) {
		this.useDiscId = useDiscId;
	}

	public String getRealDiscId() {
		return realDiscId;
	}

	public void setRealDiscId(String realDiscId) {
		this.realDiscId = realDiscId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	public String getExpertEmail1() {
		return expertEmail1;
	}

	public void setExpertEmail1(String expertEmail1) {
		this.expertEmail1 = expertEmail1;
	}

	public String getExpertEmail2() {
		return expertEmail2;
	}

	public void setExpertEmail2(String expertEmail2) {
		this.expertEmail2 = expertEmail2;
	}

	public String getExpertEmail3() {
		return expertEmail3;
	}

	public void setExpertEmail3(String expertEmail3) {
		this.expertEmail3 = expertEmail3;
	}

	public String getValidateCode1() {
		return validateCode1;
	}

	public void setValidateCode1(String validateCode1) {
		this.validateCode1 = validateCode1;
	}

	public String getValidateCode2() {
		return validateCode2;
	}

	public void setValidateCode2(String validateCode2) {
		this.validateCode2 = validateCode2;
	}

	public String getValidateCode3() {
		return validateCode3;
	}

	public void setValidateCode3(String validateCode3) {
		this.validateCode3 = validateCode3;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public EvalBatch getEvalBatch() {
		return evalBatch;
	}

	public void setEvalBatch(EvalBatch evalBatch) {
		this.evalBatch = evalBatch;
	}

}
