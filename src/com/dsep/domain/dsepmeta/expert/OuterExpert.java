package com.dsep.domain.dsepmeta.expert;

public class OuterExpert {
	// 专家编号
	private String ZJBH;
	// 专家姓名
	private String ZJXM;
	//性别码
	private String XBM;
	// 学校代码
	private String XXDM;
	// 一级学科码
	private String YJXKM;
	// 一级学科码2
	private String YJXKM2;
	// 出生年月
	private String CSNY;
	//身份证号
	private String SFZH;
	//所在部门
	private String SZBM;
	// 行政职务
	private String XZZW;
	//通讯地址
	private String TXDZ;
	//邮政编码
	private String YZBM;
	//住宅电话
	private String ZZDH;
	//传真电话
	private String CZDH;
	//专业技术职务码
	private String ZYJSZWM;
	//任导师年月
	private String RDSNY;
	//最高学位码
	private String zGXWM;
	//获学位年月
	private String hXWNY;
	//二级学科码
	private String EJXKM;
	//研究方向1
	private String YJFX1;
	//研究方向2
	private String YJFX2;
	//研究方向3
	private String YJFX3;
	//研究方向4
	private String YJFX4;
	//备注
	private String BZ;
	//民族码
	private String MZM;
	//政治面貌
	private String ZZMM;
	//二级学科码2
	private String EJXKM2;
	//外国语种码
	private String WGYZM;
	//外语熟练程度
	private String WYSLCD;
	//是否在外单外担任兼职导师
	private String SFZWDRDS;
	//兼职单位名称
	private String JZDWMC;
	//指导博士数
	private int ZDBSS;
	//招收博士数
	private int ZSBSS;
	//指导硕士数
	private int ZDSSS;
	//招收硕士数
	private int ZSSSS;
	//指导博士数2
	private int ZDBSS2;
	//招收博士数2
	private int ZSBSS2;
	//指导硕士数2
	private int ZDSSS2;
	//招收硕士数2
	private int ZSSSS2;
	//社会兼职
	private String SHJZ;
	// 专家类别
	private String ZJLBM;
	// 导师类别
	private String DSLBM;
	// 专家分类（同行还是学术）
	private String ZJFL;
	// 办公电话
	private String BGDH;
	// 电子信箱
	private String DZXX;
	// 移动电话
	private String YDDH;
	// 211大学
	private String IS_211;
	// 985大学
	private String IS_985;
	// 研究生院高校
	private String IS_GRADUATE;
	// 还有国家重点学科（含培育学科）专家
	// 以及博士一级学科专家的字段没有加进来

	private Boolean choosed = false;

	private int weight = 0;

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getZJBH() {
		return ZJBH;
	}

	public void setZJBH(String zJBH) {
		ZJBH = zJBH;
	}

	public String getZJXM() {
		return ZJXM;
	}

	public void setZJXM(String zJXM) {
		ZJXM = zJXM;
	}

	public String getXXDM() {
		return XXDM;
	}

	public void setXXDM(String xXDM) {
		XXDM = xXDM;
	}

	public String getYJXKM() {
		return YJXKM;
	}

	public void setYJXKM(String yJXKM) {
		YJXKM = yJXKM;
	}

	public String getYJXKM2() {
		return YJXKM2;
	}

	public void setYJXKM2(String yJXKM2) {
		YJXKM2 = yJXKM2;
	}

	public String getCSNY() {
		return CSNY;
	}

	public void setCSNY(String cSNY) {
		CSNY = cSNY;
	}

	public String getXZZW() {
		return XZZW;
	}

	public void setXZZW(String xZZW) {
		XZZW = xZZW;
	}

	public String getZJLBM() {
		return ZJLBM;
	}

	public void setZJLBM(String zJLBM) {
		ZJLBM = zJLBM;
	}

	public String getDSLBM() {
		return DSLBM;
	}

	public void setDSLBM(String dSLBM) {
		DSLBM = dSLBM;
	}

	public String getZJFL() {
		return ZJFL;
	}

	public void setZJFL(String zJFL) {
		ZJFL = zJFL;
	}

	public String getBGDH() {
		return BGDH;
	}

	public void setBGDH(String bGDH) {
		BGDH = bGDH;
	}

	public String getDZXX() {
		return DZXX;
	}

	public void setDZXX(String dZXX) {
		DZXX = dZXX;
	}

	public String getYDDH() {
		return YDDH;
	}

	public void setYDDH(String yDDH) {
		YDDH = yDDH;
	}

	public String getIS_211() {
		return IS_211;
	}

	public void setIS_211(String iS_211) {
		IS_211 = iS_211;
	}

	public String getIS_985() {
		return IS_985;
	}

	public void setIS_985(String iS_985) {
		IS_985 = iS_985;
	}

	public String getIS_GRADUATE() {
		return IS_GRADUATE;
	}

	public void setIS_GRADUATE(String iS_GRADUATE) {
		IS_GRADUATE = iS_GRADUATE;
	}

	public Boolean getChoosed() {
		return choosed;
	}

	public void setChoosed(Boolean choosed) {
		this.choosed = choosed;
	}

	public String getXBM() {
		return XBM;
	}

	public void setXBM(String xBM) {
		XBM = xBM;
	}

	public String getSFZH() {
		return SFZH;
	}

	public void setSFZH(String sFZH) {
		SFZH = sFZH;
	}

	public String getSZBM() {
		return SZBM;
	}

	public void setSZBM(String sZBM) {
		SZBM = sZBM;
	}

	public String getTXDZ() {
		return TXDZ;
	}

	public void setTXDZ(String tXDZ) {
		TXDZ = tXDZ;
	}

	public String getYZBM() {
		return YZBM;
	}

	public void setYZBM(String yZBM) {
		YZBM = yZBM;
	}

	public String getZZDH() {
		return ZZDH;
	}

	public void setZZDH(String zZDH) {
		ZZDH = zZDH;
	}

	public String getCZDH() {
		return CZDH;
	}

	public void setCZDH(String cZDH) {
		CZDH = cZDH;
	}

	public String getZYJSZWM() {
		return ZYJSZWM;
	}

	public void setZYJSZWM(String zYJSZWM) {
		ZYJSZWM = zYJSZWM;
	}

	public String getRDSNY() {
		return RDSNY;
	}

	public void setRDSNY(String rDSNY) {
		RDSNY = rDSNY;
	}

	public String getzGXWM() {
		return zGXWM;
	}

	public void setzGXWM(String zGXWM) {
		this.zGXWM = zGXWM;
	}

	public String gethXWNY() {
		return hXWNY;
	}

	public void sethXWNY(String hXWNY) {
		this.hXWNY = hXWNY;
	}

	public String getEJXKM() {
		return EJXKM;
	}

	public void setEJXKM(String eJXKM) {
		EJXKM = eJXKM;
	}

	public String getYJFX1() {
		return YJFX1;
	}

	public void setYJFX1(String yJFX1) {
		YJFX1 = yJFX1;
	}

	public String getYJFX2() {
		return YJFX2;
	}

	public void setYJFX2(String yJFX2) {
		YJFX2 = yJFX2;
	}

	public String getYJFX3() {
		return YJFX3;
	}

	public void setYJFX3(String yJFX3) {
		YJFX3 = yJFX3;
	}

	public String getYJFX4() {
		return YJFX4;
	}

	public void setYJFX4(String yJFX4) {
		YJFX4 = yJFX4;
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
	}

	public String getMZM() {
		return MZM;
	}

	public void setMZM(String mZM) {
		MZM = mZM;
	}

	public String getZZMM() {
		return ZZMM;
	}

	public void setZZMM(String zZMM) {
		ZZMM = zZMM;
	}

	public String getEJXKM2() {
		return EJXKM2;
	}

	public void setEJXKM2(String eJXKM2) {
		EJXKM2 = eJXKM2;
	}

	public String getWGYZM() {
		return WGYZM;
	}

	public void setWGYZM(String wGYZM) {
		WGYZM = wGYZM;
	}

	public String getWYSLCD() {
		return WYSLCD;
	}

	public void setWYSLCD(String wYSLCD) {
		WYSLCD = wYSLCD;
	}

	public String getSHJZ() {
		return SHJZ;
	}

	public void setSHJZ(String sHJZ) {
		SHJZ = sHJZ;
	}

	public String getSFZWDRDS() {
		return SFZWDRDS;
	}

	public void setSFZWDRDS(String sFZWDRDS) {
		SFZWDRDS = sFZWDRDS;
	}

	public String getJZDWMC() {
		return JZDWMC;
	}

	public void setJZDWMC(String jZDWMC) {
		JZDWMC = jZDWMC;
	}

	public int getZDSSS() {
		return ZDSSS;
	}

	public void setZDSSS(int zDSSS) {
		ZDSSS = zDSSS;
	}

	public int getZDBSS() {
		return ZDBSS;
	}

	public void setZDBSS(int zDBSS) {
		ZDBSS = zDBSS;
	}

	public int getZSBSS() {
		return ZSBSS;
	}

	public void setZSBSS(int zSBSS) {
		ZSBSS = zSBSS;
	}

	public int getZSSSS() {
		return ZSSSS;
	}

	public void setZSSSS(int zSSSS) {
		ZSSSS = zSSSS;
	}

	public int getZDBSS2() {
		return ZDBSS2;
	}

	public void setZDBSS2(int zDBSS2) {
		ZDBSS2 = zDBSS2;
	}

	public int getZSBSS2() {
		return ZSBSS2;
	}

	public void setZSBSS2(int zSBSS2) {
		ZSBSS2 = zSBSS2;
	}

	public int getZDSSS2() {
		return ZDSSS2;
	}

	public void setZDSSS2(int zDSSS2) {
		ZDSSS2 = zDSSS2;
	}

	public int getZSSSS2() {
		return ZSSSS2;
	}

	public void setZSSSS2(int zSSSS2) {
		ZSSSS2 = zSSSS2;
	}

}