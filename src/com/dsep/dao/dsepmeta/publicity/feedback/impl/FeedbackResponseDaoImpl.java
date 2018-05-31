package com.dsep.dao.dsepmeta.publicity.feedback.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.publicity.feedback.FeedbackResponseDao;
import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.entity.enumeration.feedback.FeedbackResponseStatus;
import com.dsep.entity.enumeration.feedback.ResponseType;
import com.meta.dao.AdditionalOperation;

public class FeedbackResponseDaoImpl extends DsepMetaDaoImpl<FeedbackResponse,String>
	implements FeedbackResponseDao{

	private String getTableName(){
		return super.getTableName("F", "FEEDBACK_RESPONSE");
	}
	
	
	@Override
	public boolean updateWhenCenterBeginFeedback(String currentRoundId) {
		// TODO Auto-generated method stub
		String sql = "update FeedbackResponse set feedbackStatus = ? where "
				+ " feedbackRoundId = ? and feedbackStatus is null";
		Object[] values = new Object[2];
		values[0] = FeedbackResponseStatus.UNIT.getStatus();
		values[1] = currentRoundId;
		int result = super.hqlBulkUpdate(sql, values);
		if( result < 0 ){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public int getNoAdviceNumber(String unitId, String feedbackType, String feedbackRoundId) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from "+this.getTableName()+" where "
			+" PROBLEM_UNIT_ID = ? and FEEDBACK_TYPE = ? and "
			+" FEEDBACK_ROUND_ID = ? and ((response_type is null and "
			+" problem_collect_entity_id is not null) or (response_advice is null and "
			+" problem_collect_entity_id is null))";
		Object[] values = new Object[3];
		values[0] = unitId;
		values[1] = feedbackType;
		values[2] = feedbackRoundId;
		int result = super.sqlCount(sql, values);
		return result;
	}


	@Override
	public boolean submitFeedbackResponse(String unitId, String feedbackType, String currentRoundId) {
		// TODO Auto-generated method stub
		StringBuilder sqlBuilder = new StringBuilder("update FeedbackResponse set feedbackStatus = ? where ");
		sqlBuilder.append(" feedbackRoundId = ? and problemUnitId = ? and ");
		sqlBuilder.append(" feedbackStatus = ? and feedbackType = ?");
		Object[] values = new Object[5];
		values[0] = FeedbackResponseStatus.SUMBIT.getStatus();
		values[1] = currentRoundId;
		values[2] = unitId;
		values[3] = FeedbackResponseStatus.UNIT.getStatus();
		values[4] = feedbackType;
		int result = super.hqlBulkUpdate(sqlBuilder.toString(), values);
		if( result < 0 ){
			return false;
		}
		else{
			return true;
		}
	}


	@Override
	public int deleteFeedbackSource(String feedbackRoundId,
			String feedbackType) {
		// TODO Auto-generated method stub
		StringBuilder strBuilder = new StringBuilder("delete from ");
		strBuilder.append(this.getTableName());
		strBuilder.append(" where feedback_round_id = ? ");
		strBuilder.append("and feedback_type = ?");
		Object[] params = new Object[2];
		params[0] = feedbackRoundId;
		params[1] = feedbackType;
		return super.sqlBulkUpdate(strBuilder.toString(), params);
	}


	private void setGroupbySql(StringBuilder strBuilder,FeedbackResponse queryResponse){
		//以problem_collect_item_id为组源进行group by分组操作
		strBuilder.append("select problem_collect_item_id,problem_collect_entity_id,");
		strBuilder.append("problem_collect_entity_name,");
		strBuilder.append("problem_unit_id,problem_disc_id,IMPORTANT_ATTR_VALUE,PROBLEM_SEQ_NO from ");
		strBuilder.append(this.getTableName());
		strBuilder.append(" where feedback_round_id = ? ");
		if( queryResponse.getProblemUnitId() != null){
			strBuilder.append(" and problem_unit_id =  '");
			strBuilder.append(queryResponse.getProblemUnitId());
			strBuilder.append("'");
		}
		if( queryResponse.getProblemDiscId() != null){
			strBuilder.append(" and problem_disc_id = '");
			strBuilder.append(queryResponse.getProblemDiscId());
			strBuilder.append("'");
		}
		strBuilder.append(" group by ");
		strBuilder.append("problem_collect_item_id,problem_collect_entity_id,");
		strBuilder.append("problem_collect_entity_name,");
		strBuilder.append("problem_unit_id,problem_disc_id,IMPORTANT_ATTR_VALUE,PROBLEM_SEQ_NO ");
		strBuilder.append("having count(*) > 1 ");
	}
	
	public int getSameItemResponseCount(FeedbackResponse queryResponse){
		StringBuilder strBuilder = new StringBuilder("");
		strBuilder.append("select count(*) from (");
		strBuilder.append("select problem_collect_item_id from ");
		strBuilder.append(this.getTableName());
		strBuilder.append(" where feedback_round_id = ?");
		if( queryResponse.getProblemUnitId() != null ){
			strBuilder.append(" and problem_unit_id = '");
			strBuilder.append(queryResponse.getProblemUnitId());
			strBuilder.append("'");
		}
		if( queryResponse.getProblemDiscId() != null ){
			strBuilder.append(" and problem_disc_id = '");
			strBuilder.append(queryResponse.getProblemDiscId());
			strBuilder.append("'");
		}
		strBuilder.append(" group by problem_collect_item_id");
		strBuilder.append(" having count(*) > 1) ");
		String[] params = new String[]{queryResponse.getFeedbackRoundId()};
		return super.sqlCount(strBuilder.toString(), params);
	}
	
	@Override
	public List<Map<String,String>> getSameItemResponse(int pageIndex,
			int pageSize,boolean desc, String orderPropName,
			FeedbackResponse conditionalResponse) {
		// TODO Auto-generated method stub
		StringBuilder strBuilder = new StringBuilder("");
		
		String[] params = new String[]{conditionalResponse.getFeedbackRoundId()};
		
		this.setGroupbySql(strBuilder,conditionalResponse);
		
		if( orderPropName != null ){//如果排序字段不为空则增加排序
			strBuilder.append("order by ");
			strBuilder.append(orderPropName);
			if( desc )
				strBuilder.append(" desc");
			else
				strBuilder.append(" asc");
		}
		
		String[] databaseColumnNames = new String[]{"problem_collect_item_id","problem_collect_entity_id",
			"problem_collect_entity_name","problem_unit_id","problem_disc_id",
			"important_attr_value","problem_seq_no"};
		
		String[] jqgridColumnNames = new String[]{"problemCollectItemId","problemCollectEntityId",
				"entityName","unitId","discId","importantAttrValue","seqNo"};
		
		
		
		List<Object[]> originalResult = super.sqlScalarResultsByPage(strBuilder.toString(), 
				databaseColumnNames, params, pageIndex, pageSize);
		
		List<Map<String,String>> result = new LinkedList<Map<String, String>>();
		//将List<Object[]>转化为List<Map<String,String>>
		for (Object[] r : originalResult) {
			Map<String, String> row = new LinkedHashMap<String, String>();
			for (int i = 0; i < r.length; i++) {
				if (r[i] == null) {
					r[i] = "/";
				}
				if(jqgridColumnNames[i].equals("seqNo") && r[i].equals("-1")){
					r[i] = "/";
				}
				row.put(jqgridColumnNames[i], r[i].toString());
			}
			result.add(row);
		}
		return result;
	}


	@Override
	public int saveDeleteResponse(String feedbackRoundId,
			String entityItemId) {
		// TODO Auto-generated method stub
		StringBuilder strBuilder = new StringBuilder("");
		strBuilder.append("update ");
		strBuilder.append(this.getTableName());
		strBuilder.append(" set response_type = ? where problem_collect_item_id = ? ");
		strBuilder.append("and feedback_round_id = ? and feedback_status = ?");
		String[] params = new String[4];
		params[0] = ResponseType.DELETE.getStatus();
		params[1] = entityItemId;
		params[2] = feedbackRoundId;
		params[3] = FeedbackResponseStatus.UNIT.getStatus();
		return super.sqlBulkUpdate(strBuilder.toString(),params);
	}

}
