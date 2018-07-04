package com.meta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;


public class MetaDicItemUionPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8773474539007534712L;
	private String id;
	private String dicId;
	@GenericGenerator(name = "generator", strategy = "assigned")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", length = 50, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@GenericGenerator(name = "generator", strategy = "assigned")
	@GeneratedValue(generator = "generator")
	@Column(name = "DICID", length = 20, nullable = false)
	public String getDicId() {
		return dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof MetaDicItemUionPK){ 
        	MetaDicItemUionPK pk=(MetaDicItemUionPK)obj; 
            if(this.id.equals(pk.id)&&this.dicId.equals(pk.dicId)){ 
                return true; 
            } 
        } 
        return false; 
    }

    @Override 
    public int hashCode() { 
        return super.hashCode(); 
    }
	
	
}
