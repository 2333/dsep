package com.dsep.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.dsepmeta.TeachDisc;

@Entity
@Table(name="DSEP_RBAC_USER")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonAutoDetect
@JsonIgnoreProperties(value = {"roles"})
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7807810285529048338L;
	private String id;
	private String loginId;
	private String password;
	private String name;
	private String idCardNo;//身份证号
	private String userType;
	private String unitId;
	private String discId;//一级学科码
	//private String discId2;
	private String schoolName;
	private String email;//电子信箱1
	private String officePhone;//办公电话
	private String cellPhone;//移动电话
	private String officeAddr;//办公地址
	private String address;//通讯地址
	private String zipCode;//邮政编码
	private String memo;
	
	private String QQ;
	private String game;
	private int winnum;
	private float money;
	private int nownum;
	private String region;
	private String IPType;
	private String higherUserId;
	
	
	//id  username password moblie  money（余额） QQ   game（所属游戏）  winnum（窗口数） nownum（实时窗口数） region（当前分配地区）Iptype(IP类型 1为固定IP 2为动态IP)  exptime（到期时间）  
	//level(用户等级 1为普通用户 2为普通代理 3为高级代理 4为管理员)  higher(上级用户ID) 
	
	private String loginIp;
	private Date loginTime;
	private int source;
	
	private Set<Role> roles= new HashSet<Role>(0);
	/*private Set<TeachDisc> teachDiscs= new HashSet<TeachDisc>(0);*/
	/*private ExpertSelected expertSelected;*/
	
	
	@Id
	@Column(name="ID" ,length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="LOGIN_ID",length=50,nullable=false)
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	@Column(name="PASSWORD",length=20,nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="NAME",length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="IDCARD_NO",length=20)
	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	@Column(name="UNIT_ID",length=5)
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	@Column(name="DISC_ID",length=6)
	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}
	/*@Column(name="DISC_ID2",length=4)
	public String getDiscId2() {
		return discId2;
	}
	public void setDiscId2(String discId2) {
		this.discId2 = discId2;
	}*/
	@Column(name="SCHOOL_NAME",length=50)
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	@Column(name="EMAIL",length=100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="OFFICE_PHONE",length=20)
	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	@Column(name="CELL_PHONE",length=20)
	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	@Column(name="OFFICE_ADDR",length=100)
	public String getOfficeAddr() {
		return officeAddr;
	}

	public void setOfficeAddr(String officeAddr) {
		this.officeAddr = officeAddr;
	}
	@Column(name="ADDRESS",length=200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="ZIP_CODE",length=10)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Column(name="MEMO",length=256)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name="USER_TYPE",length=1)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Column(name="LOGIN_IP")
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	@Column(name="LOGIN_TIME")
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	@Column(name="SOURCE")
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	
	
	@Column(name="QQ")
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	
	@Column(name="GAME")
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	
	@Column(name="WINNUM")
	public int getWinnum() {
		return winnum;
	}
	public void setWinnum(int winnum) {
		this.winnum = winnum;
	}
	
	@Column(name="MONEY")
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	
	@Column(name="NOWNUM")
	public int getNownum() {
		return nownum;
	}
	public void setNownum(int nownum) {
		this.nownum = nownum;
	}
	
	@Column(name="REGION")
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	@Column(name="IPTYPE")
	public String getIPType() {
		return IPType;
	}
	public void setIPType(String iPType) {
		IPType = iPType;
	}
	
	@Column(name="HIGHERUSERID")
	public String getHigherUserId() {
		return higherUserId;
	}
	public void setHigherUserId(String higherUserId) {
		this.higherUserId = higherUserId;
	}
	@ManyToMany(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinTable(name = "DSEP_RBAC_USER_ROLE", 
	inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false) }, 
	joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false) }) 
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	/*@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name="TEACH_ID")
	public Set<TeachDisc> getTeachDiscs() {
		return teachDiscs;
	}
	public void setTeachDiscs(Set<TeachDisc> teachDiscs) {
		this.teachDiscs = teachDiscs;
	}*/
	
	
	/*@OneToOne(mappedBy="user",cascade = CascadeType.ALL)
	public ExpertSelected getExpertSelected() {
		return expertSelected;
	}
	
	public void setExpertSelected(ExpertSelected expertSelected) {
		this.expertSelected = expertSelected;
	}*/
	
}
