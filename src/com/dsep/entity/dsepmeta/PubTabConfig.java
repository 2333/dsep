package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 公共库比对表映射表
 * @author Monar
 *
 */
public class PubTabConfig  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8324578172303203282L;
	
	private String id;
	private String publibId;
	private String entityId;
	private String querySql;
	
	private Set<PubFieldConfig> pubFieldConfigs = new HashSet<PubFieldConfig>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPublibId() {
		return publibId;
	}
	public void setPublibId(String publibId) {
		this.publibId = publibId;
	}
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	
	public Set<PubFieldConfig> getPubFieldConfigs() {
		return pubFieldConfigs;
	}
	public void setPubFieldConfigs(Set<PubFieldConfig> pubFieldConfigs) {
		this.pubFieldConfigs = pubFieldConfigs;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
