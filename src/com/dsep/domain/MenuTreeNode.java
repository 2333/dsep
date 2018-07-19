package com.dsep.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wbolin
 * 加载菜单的树节点
 */
public class MenuTreeNode implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6030967347882415429L;
	public String id;
	public String name;
	public String url;
	public List<MenuTreeNode> children;
	public int relativeSeq;
	public MenuTreeNode()
	{
		
	}
	
	public MenuTreeNode(String id,String name,String url,int seq)
	{
		this.id=id;
		this.name=name;
		this.url=url;	
		this.relativeSeq=seq;
	}
	
	public MenuTreeNode addChild(MenuTreeNode newChild)
	{
		if(this.children == null)
			this.children=new ArrayList<MenuTreeNode>();
		this.children.add(newChild);
		return this;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public List<MenuTreeNode> getChildren() {
		return children;
	}

	public int getRelativeSeq() {
		return relativeSeq;
	}

	
	
}
