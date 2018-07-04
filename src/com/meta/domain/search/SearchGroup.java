package com.meta.domain.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class SearchGroup {
	/**
	 * 本组连接符号，可以是"and"和"or"
	 */
	private String groupOp="and";//组的连接符号（ and/or）
	/**
	 * 本组内的查询条件，包括字段名、操作和字段值
	 */
	private List<SearchRule> rules = new LinkedList<SearchRule>();
	/**
	 * 本组内所包含的子组
	 */
	private List<SearchGroup> groups = new LinkedList<SearchGroup>();
	
	public SearchGroup(){
		
	}
	public SearchGroup(String groupOp, SearchRule rule){
		this.groupOp = groupOp;
		this.rules.add(rule);		
	}
	/**
	 * 返回本组内给查询条件的连接符
	 * @return 应为"and"或"or"
	 */
	public String getGroupOp() {
		return groupOp;
	}
	/**
	 * 是否本组操作是and
	 * @return
	 */
	public boolean isAnd(){
		if(groupOp.toLowerCase().equals("and")) return true;
		else return false;
	}
	/**
	 * 设置本组的连接符
	 * @param groupOp "and"或"or"
	 */
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public List<SearchGroup> getGroups() {
		return groups;
	}
	public void setGroups(List<SearchGroup> groups) {
		this.groups = groups;
	}
	
	public void addSubGroup(SearchGroup group){
		if(group!=null) this.groups.add(group);
	}
	
	public List<SearchRule> getRules() {
		return rules;
	}
	public void setRules(List<SearchRule> rules) {
		this.rules = rules;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		if(isAnd()) sb.append(" 1 = 1 ");
		else sb.append(" 1<> 1 ");
		for(SearchRule rule: rules){
			sb.append(" " + groupOp + " ");	//增加本组连接符号
			sb.append(rule.toString());
		}
		for(SearchGroup group: groups){
			sb.append(" "+ groupOp + " ");
			sb.append(" (" + group.toString() + ") ");
		}
		return sb.toString();
	}
	
	

}
