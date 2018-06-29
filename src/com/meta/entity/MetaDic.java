package com.meta.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import net.sf.ehcache.util.concurrent.ConcurrentHashMap.ValuesView;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.metamodel.source.hbm.Helper.ValueSourcesAdapter;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Entity
@Table(name = "META_DIC")
public class MetaDic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3878148670047278819L;
	private String id;
	private String name;
	
	private Set<MetaDicItem> dicItemSet = new TreeSet<MetaDicItem>();
	
	@Id
	@GenericGenerator(name = "generator", strategy = "assigned")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", length = 20, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "NAME", length = 50, nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@OneToMany(targetEntity = MetaDicItem.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "DICID")
	@OrderBy(value = "SEQ_NO")
	public Set<MetaDicItem> getDicItemSet() {
		return dicItemSet;
	}
	public void setDicItemSet(Set<MetaDicItem> dicItemSet) {
		this.dicItemSet = dicItemSet;
	}
}
