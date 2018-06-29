package com.dsep.entity.enumeration;

public class AttachmentType extends EnumModule{

	public static final DsepEnum COLLECTION= new DsepEnum("1","COLLECTION");
	public static final DsepEnum PUBLICITY= new DsepEnum("2","PUBLICITY");
	public static final DsepEnum NEWS = new DsepEnum("3","NEWS");
	public static final DsepEnum TEACHER = new DsepEnum("4","TEACHER");
	public static final DsepEnum FEEDBACK = new DsepEnum("5","FEEDBACK");
	public static final DsepEnum TEMPLATE = new DsepEnum("6", "TEMPLATE");
	public static final DsepEnum SURVEY = new DsepEnum("7", "SURVEY");
	public static final DsepEnum BACKGROUND_DOCUMENT = new DsepEnum("0", "BACKGROUND_DOCUMENT");
	public static final DsepEnum PROJECT = new DsepEnum("8","PROJECT");
	public static final DsepEnum BRIEF = new DsepEnum("9","BRIEF");
	
	public AttachmentType() {
		setTypeMaterial();
	}

	
	@Override
	protected void setTypeMaterial() {
		
		this.typeMaterial = new DsepEnum[] { COLLECTION, PUBLICITY, NEWS, TEACHER,FEEDBACK,TEMPLATE,SURVEY, BACKGROUND_DOCUMENT,PROJECT,BRIEF};
		
	}

}
