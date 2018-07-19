package com.dsep.dao.dsepmeta.publicity.feedback.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.publicity.feedback.FeedbackImportDao;
import com.dsep.entity.enumeration.feedback.FeedbackResponseStatus;
import com.dsep.entity.enumeration.feedback.FeedbackType;
import com.dsep.entity.enumeration.publicity.CenterObjectStatus;

public class FeedbackImportDaoImpl extends DsepMetaDaoImpl
	implements FeedbackImportDao{

	/**
	 * 异议结果表表名
	 * @return
	 */
	private String getObjectionTableName(){
		return super.getTableName("F", "ORIGINAL_OBJECTION");
	}
	
	/**
	 * 反馈数据表表名
	 * @return
	 */
	private String getFeedbackTableName(){
		return super.getTableName("F", "FEEDBACK_RESPONSE");
	}
	
	/**
	 * 公共库比对结果表名
	 * @return
	 */
	private String getPubLibTableName(){
		return super.getTableName("D", "PUB_RESULT");
	}
	
	
	/**
	 * 查重结果表表名
	 * @return
	 */
	private String getSimilarityTableName(){
		return super.getTableName("D", "SIMILARITY_RESULT");
	}
	
	

	/**
	 * 获取反馈表的列，供插入语句使用
	 * @return
	 */
	private String getFeedbackColumn(){
		String columnString = "problem_unit_id,problem_disc_id,problem_collect_entity_id,"
			+ "problem_collect_entity_name,problem_collect_item_id,problem_collect_attr_id,"
			+ "problem_collect_attr_name,problem_collect_attr_value,important_attr_value,problem_seq_no,"
			+ "problem_content,feedback_type,feedback_status,feedback_round_id";
		return columnString;
	}

	/**
	 * 获取异议表的列，供插入语句使用
	 * @param feedbackRoundId
	 * @return
	 */
	private String getOriginalObjectionColumn(String feedbackRoundId){
		String columnString = "problem_unit_id,problem_disc_id,object_collect_entity_id,"
			+ "object_collect_entity_name,object_collect_item_id,center_object_attr_id,"
			+ "center_object_attr_name,center_object_attr_value,important_attr_value,seq_no,"
			+ "center_object_content,'"+ FeedbackType.OBJECTION.getStatus()+"','"+
			FeedbackResponseStatus.UNIT.getStatus()+ "','"+feedbackRoundId + "'";
		return columnString;
	}
	
	/**
	 * 公共库比对的错误信息有“未找到”和“异议”两种，针对两种情况分别处理，转化后的错误信息插入到反馈表的错误内容字段中
	 * @return
	 */
	private String getPubLibProblemColumn(){
		String problemString = "(case when pub_value is null then '该数据未在官方公布的公共信息中查询到' else '该数据与官方公布的信息'||pub_value||'不一致' END) as problem";
		return problemString;
	}
	
	private String getPubLibResultColumn(String feedbackRoundId){
		String columnString = "unit_id,disc_id,entity_id,entity_chs_name,local_item_id,"
			+"local_field,local_chs_field,local_value,flag_field,seq_no,"+ this.getPubLibProblemColumn() +",'"
			+ FeedbackType.PUBNOTRIGHT.getStatus()+ "','" + FeedbackResponseStatus.UNIT.getStatus()+ "','"+feedbackRoundId + "'";
		return columnString;
	}
	
	
	private String getSimilarityResultColumn(String feedbackRoundId){
		String columnString = "unit_id,disc_id,entity_id,entity_chs_name,data_id,"
			+"field,field_name,data_value,key_value,seq_no,similarity_ids,'"+ FeedbackType.REPEATSUBMIT.getStatus() +"','"
			+ FeedbackResponseStatus.UNIT.getStatus() + "','"+feedbackRoundId +"'";
		return columnString;
	}
	
	
	@Override
	public int originalObjectionImport(String feedbackRoundId,String publicRoundId) {
		// TODO Auto-generated method stub
		String sql = "insert into " + this.getFeedbackTableName()+"("+
			getFeedbackColumn()+") select " + getOriginalObjectionColumn(feedbackRoundId)+ 
			" from " +this.getObjectionTableName() + " where CURRENT_PUBLIC_ROUND_ID = '" + 
			publicRoundId + "' and center_status = '"+ CenterObjectStatus.PROCESSED.getStatus() +"'";
		return super.sqlBulkUpdate(sql);
	}



	@Override
	public int pubLibraryImport(String feedbackRoundId) {
		// TODO Auto-generated method stub
		String sql = "insert into " + this.getFeedbackTableName()+"("+
				getFeedbackColumn()+") select " + this.getPubLibResultColumn(feedbackRoundId)+ 
				" from " +this.getPubLibTableName()+"";
		return super.sqlBulkUpdate(sql);
	}

	@Override
	public int similarityImport(String feedbackRoundId) {
		// TODO Auto-generated method stub
		String sql = "insert into " + this.getFeedbackTableName() + "("
			+ this.getFeedbackColumn()+") select "+ this.getSimilarityResultColumn(feedbackRoundId)
			+ " from " + this.getSimilarityTableName()+" where user_id = '8F583D9CD2B54029A562AE639D36B393'";
		int result = super.sqlBulkUpdate(sql);
		return result;
	}
}
