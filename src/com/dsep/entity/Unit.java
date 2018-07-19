package com.dsep.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="DSEP_BASE_UNIT")
@JsonAutoDetect
@JsonIgnoreProperties(value = {"disciplines"})
public class Unit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -165114928722799041L;
	private String id;
	private String name; 
	private String oldName;
	private String univType;
	
	private Leader leader;
	private Province province;
	
	private String zipCode; 
	private String zipAddress;
	private String linkman;
	private boolean is211;
	private boolean is985;
	private boolean isGraduate;
	private String authType;
	private String authYear;
	
	private Set<Discipline> disciplines=new HashSet<Discipline>(0);
	
	@Id
	@Column(name="ID",length=5, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="NAME",length=50, nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="OLD_NAME",length=50)
	public String getOldName() {
		return oldName;
	}
	
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	
	@Column(name="UNIV_TYPE",length=2, nullable = false)
	public String getUnivType() {
		return univType;
	}
	
	public void setUnivType(String univType) {
		this.univType = univType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)  
	@Cascade(value=CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "LEADER_ID") 
	public Leader getLeader() {
		return leader;
	}
	
	public void setLeader(Leader leader) {
		this.leader = leader;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)  
	@Cascade(value=CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "PROVINCE_ID")
	public Province getProvince() {
		return province;
	}
	
	public void setProvince(Province province) {
		this.province = province;
	}
	
	@Column(name="ZIP_CODE",length=6)
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Column(name="ZIP_ADDRESS",length=100)
	public String getZipAddress() {
		return zipAddress;
	}
	
	public void setZipAddress(String zipAddress) {
		this.zipAddress = zipAddress;
	}
	
	@Column(name="LINKMAN",length=20)
	public String getLinkman() {
		return linkman;
	}
	
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	@Column(name="IS_211", nullable = false)
	public boolean getIs211() {
		return is211;
	}
	
	public void setIs211(boolean is211) {
		this.is211 = is211;
	}
	
	@Column(name="IS_985", nullable = false)
	public boolean getIs985() {
		return is985;
	}
	
	public void setIs985(boolean is985) {
		this.is985 = is985;
	}
	
	@Column(name="IS_GRADUATE", nullable = false)
	public boolean getIsGraduate() {
		return isGraduate;
	}
	
	public void setIsGraduate(boolean isGraduate) {
		this.isGraduate = isGraduate;
	}
	
	@Column(name="AUTH_TYPE",length=1, nullable = false)
	public String getAuthType() {
		return authType;
	}
	
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	@Column(name="AUTH_YEAR",length=4)
	public String getAuthYear() {
		return authYear;
	}
	
	public void setAuthYear(String authYear) {
		this.authYear = authYear;
	}
	
	@Cascade(value={CascadeType.PERSIST})  
	@ManyToMany(fetch = FetchType.LAZY)  
	@JoinTable(name = "DSEP_BASE_UNIT_DISC", joinColumns = { @JoinColumn(name = "UNIT_ID", referencedColumnName = "ID", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "DISC_ID", referencedColumnName = "ID", nullable = false) })  
	public Set<Discipline> getDisciplines() {
		return disciplines;
	}
	public void setDisciplines(Set<Discipline> disciplines) {
		this.disciplines = disciplines;
	}
	
//	@Cascade(value={CascadeType.SAVE_UPDATE})
//	@OneToMany()
//	@JoinColumn(name="XXDM") 
//	public Set<Expert> getExperts() {
//		return experts;
//	}
//	
//	public void setExperts(Set<Expert> experts) {
//		this.experts=experts;
//	}

}
