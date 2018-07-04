package com.dsep.entity;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.project.ApplyItem;
import com.dsep.util.GUID;

@Entity
@Table(name="DSEP_RBAC_TEACH")
public class Teacher extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8496973782756444256L;
	private String zjbh;//专家编号(n)v(10)
	private String mm;//密码（y）v(32)
	private String xbm;//性别码(y)v(1)
	private String csny;//出生年月(y)v(8)
	private String gjm;//国籍码（y）v(3)
	private String zjzlm;//证件种类码(y)v(2)
	private String szbm;//所在部门yv(60)
	private String xzzw;//行政职务yv(20)
	private String zzdh;//住宅电话Y 20
	private String czdh;//传真电话Y 20
	private String dzxx2;//电子信箱 Y 50
	private String zyjszwm;//专业技术职务码 Y 3
	private String dslbm;//导师类别码Y 1
	private String rdsny;//任导师年月 Y 8
	private String zjlbm;//专家类别码Y 2
	private String zgxwm;//最高学位码Y 3
	private String hxwny;//获学位年月Y 8
	private String ejxkm;//二级学科码 Y 6
	private String yjfx1;//研究方向1 Y 40
	private String yjfx2;//研究方向2 Y 40
	private String yjfx3;//研究方向3 Y 40
	private String yjfx4;//研究方向4 Y 40
	private String mzm;//民族码  Y 2
	private String zzmmm;//政治面貌码 Y 2
	private String yjxkm2;//一级学科码2 Y 4
	private String ejxkm2;//二级学科码2 Y 6
	private String wgyzm;//外国语中码  Y 2
	private String wyslcdm;//外语熟练程度码 Y 3 
	private String sfzyxwds;//是否在外单位担任兼职导师  N 2
	
	private String shjz;//社会兼职  y 200
	private String bz;//备注  y 200
	
	private String zjyhzh;//专家银行账号 y 40
	private String zjkhhmc;//专家开户行名称 y 100
	private String zjyhlhh;//专家银行联行号 50
	
	private String dslbm2;//导师类别码2 y 1
	private String rsgxsfbx;//人事关系是否本校 y 8
 	private String rsgxszdw;//人事关系所在单位 y 60
	private String szdwsfsyxw;//所在单位是否授予学位 y 8
	private String zyxwlbm;//第一专业学位类别码 n 4
	private String zyxwlbmc;//第一专业学位类别码名称 n 30
	private String zyxwlym;//第一专业学位领域吗 n 6
	private String zyxwlylbmc;//第一专业学位领域名称 n 30
	private String zyxwlbm2;//第二专业学位类别码 n 4
	private String zyxwlbcm2;//第二专业学位类别名称 n 30
	private String zyxwlym2;//第二专业学位领域码 n 6 
	private String zyxwlymc2;//第二专业学位领域名称 n 30 
	private String jwkssj;//开始时间 n 8
	private String jwjssj;//结束时间 n 8
	private String jwgjdq;//国家或地区 n 30
	private String jwjgmc;//高校或机构名称 n 100
	private String jwsf;//何种身份 n 8
	private String briefId;//简况表id
	private Date createBriefTime;//生成简况表时间
	//private Set<ApplyItem> applyItems = new HashSet<ApplyItem>();
	
	public Teacher(){
		
	}
	public Teacher(OuterExpert expert){
		setTeacher(expert);
	}
	@Column(name="ZJBH",length=10)
	public String getZjbh() {
		return zjbh;
	}
	public void setZjbh(String zjbh) {
		this.zjbh = zjbh;
	}
	@Column(name="XBM",length=1)
	public String getXbm() {
		return xbm;
	}
	public void setXbm(String xbm) {
		this.xbm = xbm;
	}
	@Column(name="CSNY",length=8)
	public String getCsny() {
		return csny;
	}
	public void setCsny(String csny) {
		this.csny = csny;
	}
	@Column(name="SZBM",length=60)
	public String getSzbm() {
		return szbm;
	}
	public void setSzbm(String szbm) {
		this.szbm = szbm;
	}
	@Column(name="XZZW",length=20)
	public String getXzzw() {
		return xzzw;
	}
	public void setXzzw(String xzzw) {
		this.xzzw = xzzw;
	}
	@Column(name="ZZDH",length=20)
	public String getZzdh() {
		return zzdh;
	}
	public void setZzdh(String zzdh) {
		this.zzdh = zzdh;
	}
	@Column(name="CZDH",length=20)
	public String getCzdh() {
		return czdh;
	}
	public void setCzdh(String czdh) {
		this.czdh = czdh;
	}
	@Column(name="ZYJSZWM",length=3)
	public String getZyjszwm() {
		return zyjszwm;
	}
	public void setZyjszwm(String zyjszwm) {
		this.zyjszwm = zyjszwm;
	}
	@Column(name="DSLBM",length=1)
	public String getDslbm() {
		return dslbm;
	}
	public void setDslbm(String dslbm) {
		this.dslbm = dslbm;
	}
	@Column(name="RDSNY",length=8)
	public String getRdsny() {
		return rdsny;
	}
	public void setRdsny(String rdsny) {
		this.rdsny = rdsny;
	}
	@Column(name="ZJLBM",length=2)
	public String getZjlbm() {
		return zjlbm;
	}
	public void setZjlbm(String zjlbm) {
		this.zjlbm = zjlbm;
	}
	@Column(name="ZGXWM",length=3)
	public String getZgxwm() {
		return zgxwm;
	}
	public void setZgxwm(String zgxwm) {
		this.zgxwm = zgxwm;
	}
	@Column(name="HXWNY",length=8)
	public String getHxwny() {
		return hxwny;
	}
	public void setHxwny(String hxwny) {
		this.hxwny = hxwny;
	}
	@Column(name="EJXKM",length=6)
	public String getEjxkm() {
		return ejxkm;
	}
	public void setEjxkm(String ejxkm) {
		this.ejxkm = ejxkm;
	}
	@Column(name="YJFX1",length=40)
	public String getYjfx1() {
		return yjfx1;
	}
	public void setYjfx1(String yjfx1) {
		this.yjfx1 = yjfx1;
	}
	@Column(name="YJFX2",length=40)
	public String getYjfx2() {
		return yjfx2;
	}
	public void setYjfx2(String yjfx2) {
		this.yjfx2 = yjfx2;
	}
	@Column(name="YJFX3",length=40)
	public String getYjfx3() {
		return yjfx3;
	}
	public void setYjfx3(String yjfx3) {
		this.yjfx3 = yjfx3;
	}
	@Column(name="YJFX4",length=40)
	public String getYjfx4() {
		return yjfx4;
	}
	public void setYjfx4(String yjfx4) {
		this.yjfx4 = yjfx4;
	}
	@Column(name="MZM",length=2)
	public String getMzm() {
		return mzm;
	}
	public void setMzm(String mzm) {
		this.mzm = mzm;
	}
	@Column(name="ZZMMM",length=2)
	public String getZzmmm() {
		return zzmmm;
	}
	public void setZzmmm(String zzmmm) {
		this.zzmmm = zzmmm;
	}
	@Column(name="YJXKM2",length=6)
	public String getYjxkm2() {
		return yjxkm2;
	}
	public void setYjxkm2(String yjxkm2) {
		this.yjxkm2 = yjxkm2;
	}
	@Column(name="EJXKM2",length=6)
	public String getEjxkm2() {
		return ejxkm2;
	}
	public void setEjxkm2(String ejxkm2) {
		this.ejxkm2 = ejxkm2;
	}
	@Column(name="WGYZM",length=2)
	public String getWgyzm() {
		return wgyzm;
	}
	public void setWgyzm(String wgyzm) {
		this.wgyzm = wgyzm;
	}
	
	@Column(name="SHJZ",length=200)
	public String getShjz() {
		return shjz;
	}
	public void setShjz(String shjz) {
		this.shjz = shjz;
	}
	@Column(name="BZ",length=200)
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	@Column(name="MM",length=32,nullable=true)
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	@Column(name="GJM",length=3,nullable=true)
	public String getGjm() {
		return gjm;
	}
	public void setGjm(String gjm) {
		this.gjm = gjm;
	}
	@Column(name="ZJZLM",length=2,nullable=true)
	public String getZjzlm() {
		return zjzlm;
	}
	public void setZjzlm(String zjzlm) {
		this.zjzlm = zjzlm;
	}
	@Column(name="DZXX2",length=50,nullable=true)
	public String getDzxx2() {
		return dzxx2;
	}
	public void setDzxx2(String dzxx2) {
		this.dzxx2 = dzxx2;
	}
	@Column(name="WYSLCDM",length=2,nullable=true)
	public String getWyslcdm() {
		return wyslcdm;
	}
	public void setWyslcdm(String wyslcdm) {
		this.wyslcdm = wyslcdm;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="SFZXWDS",length=2,nullable=true)
	public String getSfzyxwds() {
		return sfzyxwds;
	}
	public void setSfzyxwds(String sfzyxwds) {
		this.sfzyxwds = sfzyxwds;
	}
	@Column(name="ZJYHZH",length=40,nullable=true)
	public String getZjyhzh() {
		return zjyhzh;
	}
	public void setZjyhzh(String zjyhzh) {
		this.zjyhzh = zjyhzh;
	}
	@Column(name="ZJKHHMC",length=100,nullable=true)
	public String getZjkhhmc() {
		return zjkhhmc;
	}
	public void setZjkhhmc(String zjkhhmc) {
		this.zjkhhmc = zjkhhmc;
	}
	@Column(name="ZJYHLHH",length=50,nullable=true)
	public String getZjyhlhh() {
		return zjyhlhh;
	}
	public void setZjyhlhh(String zjyhlhh) {
		this.zjyhlhh = zjyhlhh;
	}
	@Column(name="DSLBM2",length=1,nullable=true)
	public String getDslbm2() {
		return dslbm2;
	}
	public void setDslbm2(String dslbm2) {
		this.dslbm2 = dslbm2;
	}
	@Column(name="RSGXSFBX",length=8,nullable=true)
	public String getRsgxsfbx() {
		return rsgxsfbx;
	}
	public void setRsgxsfbx(String rsgxsfbx) {
		this.rsgxsfbx = rsgxsfbx;
	}
	@Column(name="RSGXSZD",length=60,nullable=true)
	public String getRsgxszdw() {
		return rsgxszdw;
	}
	public void setRsgxszdw(String rsgxszdw) {
		this.rsgxszdw = rsgxszdw;
	}
	@Column(name="SZDWSFSYXW",length=8,nullable=true)
	public String getSzdwsfsyxw() {
		return szdwsfsyxw;
	}
	public void setSzdwsfsyxw(String szdwsfsyxw) {
		this.szdwsfsyxw = szdwsfsyxw;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="ZYXWLBM",length=4,nullable=true)
	public String getZyxwlbm() {
		return zyxwlbm;
	}
	public void setZyxwlbm(String zyxwlbm) {
		this.zyxwlbm = zyxwlbm;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="ZYXWLBMC",length=30,nullable=true)
	public String getZyxwlbmc() {
		return zyxwlbmc;
	}
	public void setZyxwlbmc(String zyxwlbmc) {
		this.zyxwlbmc = zyxwlbmc;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="ZYXWLYM",length=6,nullable=true)
	public String getZyxwlym() {
		return zyxwlym;
	}
	public void setZyxwlym(String zyxwlym) {
		this.zyxwlym = zyxwlym;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="ZYXWLYLBMC",length=40,nullable=true)
	public String getZyxwlylbmc() {
		return zyxwlylbmc;
	}
	public void setZyxwlylbmc(String zyxwlylbmc) {
		this.zyxwlylbmc = zyxwlylbmc;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="ZYXWLBM2",length=4,nullable=true)
	public String getZyxwlbm2() {
		return zyxwlbm2;
	}
	public void setZyxwlbm2(String zyxwlbm2) {
		this.zyxwlbm2 = zyxwlbm2;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="ZYXWLBCM2",length=30,nullable=true)
	public String getZyxwlbcm2() {
		return zyxwlbcm2;
	}
	public void setZyxwlbcm2(String zyxwlbcm2) {
		this.zyxwlbcm2 = zyxwlbcm2;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="ZYXWLYM2",length=6,nullable=true)
	public String getZyxwlym2() {
		return zyxwlym2;
	}
	public void setZyxwlym2(String zyxwlym2) {
		this.zyxwlym2 = zyxwlym2;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="ZYXWLYCM2",length=40,nullable=true)
	public String getZyxwlymc2() {
		return zyxwlymc2;
	}
	public void setZyxwlymc2(String zyxwlymc2) {
		this.zyxwlymc2 = zyxwlymc2;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="JWKSSJ",length=8,nullable=true)
	public String getJwkssj() {
		return jwkssj;
	}
	public void setJwkssj(String jwkssj) {
		this.jwkssj = jwkssj;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="JWJSSJ",length=8,nullable=true)
	public String getJwjssj() {
		return jwjssj;
	}
	public void setJwjssj(String jwjssj) {
		this.jwjssj = jwjssj;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="JWGJDQ",length=30,nullable=true)
	public String getJwgjdq() {
		return jwgjdq;
	}
	public void setJwgjdq(String jwgjdq) {
		this.jwgjdq = jwgjdq;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="JWJGMC",length=100,nullable=true)
	public String getJwjgmc() {
		return jwjgmc;
	}
	public void setJwjgmc(String jwjgmc) {
		this.jwjgmc = jwjgmc;
	}
	/**
	 * 原来为false
	 * @return
	 */
	@Column(name="JWSF",length=8,nullable=true)
	public String getJwsf() {
		return jwsf;
	}
	public void setJwsf(String jwsf) {
		this.jwsf = jwsf;
	}
	@Column(name="BRIEF_ID",length=32,nullable=true)
	public String getBriefId() {
		return briefId;
	}
	public void setBriefId(String briefId) {
		this.briefId = briefId;
	}
	@Column(name="CREATE_BRIEF_TIME",nullable=true)
	public Date getCreateBriefTime() {
		return createBriefTime;
	}
	public void setCreateBriefTime(Date createBriefTime) {
		this.createBriefTime = createBriefTime;
	}
	
	private void setTeacher(OuterExpert expert){
		setId(GUID.get());
		setZjbh(expert.getZJBH());
		setName(expert.getZJXM());
		setUnitId(expert.getXXDM());
		setDiscId(expert.getYJXKM());
		setAddress(expert.getTXDZ());
		setBz(expert.getBZ());
		setCellPhone(expert.getYDDH());
		setCsny(expert.getCSNY());
		setCzdh(expert.getCZDH());
		setDslbm(expert.getDSLBM());
		setEjxkm(expert.getEJXKM());
		setEjxkm2(expert.getEJXKM2());
		setEmail(expert.getDZXX());
		setHxwny(expert.gethXWNY());
		setIdCardNo(expert.getSFZH());
		setLoginId(expert.getDZXX());
		setMzm(expert.getMZM());
		setName(expert.getZJXM());
		setOfficePhone(expert.getBGDH());
		setPassword("test");
		setRdsny(expert.getRDSNY());
		setShjz(expert.getSHJZ());
		setSource(9);
		setSzbm(expert.getSZBM());
		setUserType("4");
		setWgyzm(expert.getWGYZM());
		setXbm(expert.getXBM());
		setXzzw(expert.getXZZW());
		setYjfx1(expert.getYJFX1());
		setYjfx2(expert.getYJFX2());
		setYjfx3(expert.getYJFX3());
		setYjfx4(expert.getYJFX4());
		setYjxkm2(expert.getYJXKM2());
		setZgxwm(expert.getzGXWM());
		setZjbh(expert.getZJBH());
		setZjlbm(expert.getZJLBM());
		setZyjszwm(expert.getZYJSZWM());
		setZzdh(expert.getZZDH());
		setZzmmm(expert.getZZMM());
		setZipCode(expert.getYZBM());
	}
	
	private void setTeacherByReflect(OuterExpert expert){
		Method[] methods = OuterExpert.class.getDeclaredMethods();
	}
	
}

