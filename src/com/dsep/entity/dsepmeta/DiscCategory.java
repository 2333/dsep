package com.dsep.entity.dsepmeta;

import com.dsep.entity.Discipline;

public class DiscCategory {
	private String Id;
	private Category category;
	private Discipline discipline;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Discipline getDiscipline() {
		return discipline;
	}
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}
	
	
}
