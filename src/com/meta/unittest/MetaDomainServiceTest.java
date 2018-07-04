package com.meta.unittest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javassist.compiler.ast.NewExpr;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.common.logger.LoggerTool;
import com.dsep.service.dsepmeta.BaseDBOper;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.dsepmeta.dsepmetas.DMSimilarityCheckService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.util.WebServiceUtil;
import com.dsep.vm.CollectionTreeVM;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaDicDomain;
import com.meta.domain.MetaEntityDomain;
import com.meta.entity.MetaAttrStyle;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaDataType;
import com.meta.entity.MetaDomain;
import com.meta.entity.MetaEntity;
import com.meta.entity.MetaPercentData;
import com.meta.service.MetaDicService;
import com.meta.service.MetaDomainService;
import com.meta.service.MetaEntityService;
import com.meta.service.sql.SqlBuilder;
import com.meta.service.sql.SqlExecutor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/spring-common2014.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml",
		"file:WebContent/WEB-INF/config/2014/util/utils.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml"})
public class MetaDomainServiceTest{
	@Autowired
	private MetaDomainService metaDomainService;
	@Autowired
	private MetaEntityService metaEntityService;
	@Autowired
	private MetaDicService metaDicService;
	@Autowired
	private SqlBuilder sqlBuilder;
	@Autowired
	private SqlExecutor sqlExecutor;
	@Autowired
	private LoggerTool loggerTool;
	@Autowired
	private DMCollectService collectService;
	@Autowired
	private DMSimilarityCheckService dmSimilarityCheckService;
	@Autowired
	private EvalFlowService collectFlowService;
	//@Autowired
	//private LogicCheckService dataCheckService;
	@Autowired
	private BaseDBOper baseDBOper;
	@Before
	public void setUp(){
		/*metaDomainService = (MetaDomainService)dsContext.getBean("metaDomainService");
		sqlBuilder = (SqlBuilder)dsContext.getBean("sqlBuilder");
		sqlExecutor = (SqlExecutor)dsContext.getBean("sqlExecutor");
		metaEntityService = (MetaEntityService)dsContext.getBean("metaEntityService");
		metaDicService = (MetaDicService)dsContext.getBean("metaDicService");*/
	}
	@Test
	public void testInnerOp(){
		UUID uuid = UUID.randomUUID();
		loggerTool.info(uuid.toString());
		loggerTool.info(uuid.toString().toUpperCase().replaceAll("-", ""));
	}
	@Test
	public void testGetAvailDomain() {
		MetaDomain domain=metaDomainService.getAvailDomain("DSEP");
		//Set<MetaEntity> entities = domain.getEntities();
		assertEquals("D201301",domain.getId());
		//assertEquals(2,entities.size());
	}

	@Test
	public void testGetAllAvailDomain() {
		List<MetaDomain> domains = metaDomainService.getAllAvailDomain("DSEP");
		assertEquals(1,domains.size());
	}

	@Test
	public void testCreateTables()	{
		//MetaDomain domain=metaDomainService.getAvailDomain("DSEP");
		//Set<MetaEntity> entities = domain.getEntities();
		//List<MetaEntity> entities = metaEntityService.getEntities("D201301", "C20130202");
		List<MetaEntity> entities = metaEntityService.getEntities("D201401", "");
		for(MetaEntity entity:entities){
			try{
				sqlBuilder.clear();
				if(!entity.getId().startsWith("G")){
					sqlBuilder.existTable(entity.getName());
					int i = sqlExecutor.execCountQuery(sqlBuilder);
					if(i>0){
						sqlBuilder.clear();
						sqlBuilder.buildDropTableSql(entity);
						sqlExecutor.execUpdate(sqlBuilder);
						sqlBuilder.buildCreateTableSql(entity);
						sqlExecutor.execUpdate(sqlBuilder);
						//sqlBuilder.buildCreateIndexSql(entity);
						sqlBuilder.buildCreateIndexSql(entity);
						sqlExecutor.execUpdate(sqlBuilder);
					}else{
						sqlBuilder.clear();
						sqlBuilder.buildCreateTableSql(entity);
						sqlExecutor.execUpdate(sqlBuilder);
						//sqlBuilder.buildCreateIndexSql(entity);
						sqlBuilder.buildCreateIndexSql(entity);
						sqlExecutor.execUpdate(sqlBuilder);
					}
					sqlBuilder.existTable(entity.getName()+"_BAK");
					int j = sqlExecutor.execCountQuery(sqlBuilder);
					if(j>0){
						sqlBuilder.clear();
						sqlBuilder.buildDropTableSql(entity,"bak");
						sqlExecutor.execUpdate(sqlBuilder);
						sqlBuilder.buildCreateTableSql(entity,"bak");
						sqlExecutor.execUpdate(sqlBuilder);
						//sqlBuilder.buildCreateIndexSql(entity);
						sqlBuilder.buildCreateIndexSql(entity,"bak");
						sqlExecutor.execUpdate(sqlBuilder);
					}else{
						sqlBuilder.clear();
						sqlBuilder.buildCreateTableSql(entity,"bak");
						sqlExecutor.execUpdate(sqlBuilder);
						//sqlBuilder.buildCreateIndexSql(entity);
						sqlBuilder.buildCreateIndexSql(entity,"bak");
						sqlExecutor.execUpdate(sqlBuilder);
					}
					
				}	
			}
			catch(Exception e){
				if(e.getMessage().equals("could not execute statement")){
					sqlBuilder.buildCreateTableSql(entity, "backup");
					sqlExecutor.execUpdate(sqlBuilder);
					//sqlBuilder.buildCreateIndexSql(entity);
					sqlBuilder.buildCreateIndexSql(entity,"backup");
					sqlExecutor.execUpdate(sqlBuilder);
				}
			}
			//}
		}
	}
	@Test
	public void testCreateTable()	{
		String []tableIds = new String[]{"E20140405"};
		{
			for(String tableId: tableIds){
				MetaEntity entity = metaEntityService.getById(tableId);
				sqlBuilder.existTable(entity.getName());
				int i = sqlExecutor.execCountQuery(sqlBuilder);
				if(i>0){
					sqlBuilder.clear();
					sqlBuilder.buildDropTableSql(entity);
					sqlExecutor.execUpdate(sqlBuilder);
					sqlBuilder.buildCreateTableSql(entity);
					sqlExecutor.execUpdate(sqlBuilder);
					sqlBuilder.buildCreateIndexSql(entity);
					sqlExecutor.execUpdate(sqlBuilder);
				}	
				sqlBuilder.clear();
				sqlBuilder.existTable(entity.getName()+"_BAK");
				int j = sqlExecutor.execCountQuery(sqlBuilder);
				if(j>0){
					sqlBuilder.clear();
					sqlBuilder.buildDropTableSql(entity,"bak");
					sqlExecutor.execUpdate(sqlBuilder);	
					sqlBuilder.buildCreateTableSql(entity,"bak");
					sqlExecutor.execUpdate(sqlBuilder);
					sqlBuilder.buildCreateIndexSql(entity,"bak");
					sqlExecutor.execUpdate(sqlBuilder);
				}else{
					sqlBuilder.clear();
					sqlBuilder.buildCreateTableSql(entity,"bak");
					sqlExecutor.execUpdate(sqlBuilder);
					sqlBuilder.buildCreateIndexSql(entity,"bak");
					sqlExecutor.execUpdate(sqlBuilder);
				}
			}
		}
	}
	@Test
	public void testAlterTable(){
		String[] categories = {"C201301","C201302","C201303","C201304","C20130201","C20130202"};
		//String[] categories = {"CT201301"};
		for(String category:categories){
			List<MetaEntity> entities = metaEntityService.getEntities("D201301", category);
			for(MetaEntity entity:entities){
				MetaEntityDomain entityDomain = metaEntityService.getEntityDomain(entity);
				List<MetaAttrDomain> attrDomains = entityDomain.getAttrDomains();
				for(MetaAttrDomain attrDomain:attrDomains){
					/*
					  //修改字典类型
					  if(StringUtils.isNotBlank(attrDomain.getDicId())){
						StringBuilder sql = new StringBuilder("alter table "+entity.getName());
						sql.append(" modify " + attrDomain.getName() +" NVARCHAR2(100) ");
						sqlBuilder.clear();
						sqlBuilder.setSql(sql.toString());
						sqlExecutor.execUpdate(sqlBuilder);
					}*/
					//修改百分比类型
					if(attrDomain.getDataTypeObject() == MetaDataType.PERCENT){
						String tableName= entity.getName();
						for(int i=0;i<2;i++){
							if(i==1) tableName = tableName + "_BAK";
							StringBuilder sql = new StringBuilder("alter table "+tableName);
							sql.append(" add " + attrDomain.getName() +" VARCHAR2(20) ");
							sqlBuilder.clear();
							sqlBuilder.setSql(sql.toString());
							sqlExecutor.execUpdate(sqlBuilder);
							
							sql.delete(0, sql.length());
							sql.append("update "+tableName+" set "+attrDomain.getName() +" = ");
							sql.append(" concat("+ MetaPercentData.getNumColumn(attrDomain.getName()) );
							sql.append(", concat('(', concat(" + MetaPercentData.getValueColumn(attrDomain.getName())+",')'))) ");
							sql.append(" where " + MetaPercentData.getTypeColumn(attrDomain.getName()) + " = 0");
							sqlBuilder.clear();
							sqlBuilder.setSql(sql.toString());
							sqlExecutor.execUpdate(sqlBuilder);
							
							sql.delete(0, sql.length());
							sql.append("update "+tableName+" set "+attrDomain.getName() +" = ");
							sql.append(" concat("+ MetaPercentData.getNumColumn(attrDomain.getName()) );
							sql.append(", concat('[', concat(" + MetaPercentData.getValueColumn(attrDomain.getName())+",']'))) ");
							sql.append(" where " + MetaPercentData.getTypeColumn(attrDomain.getName()) + " = 1");
							sqlBuilder.clear();
							sqlBuilder.setSql(sql.toString());
							sqlExecutor.execUpdate(sqlBuilder);
							
							sql.delete(0, sql.length());
							sql.append("alter table "+tableName);
							sql.append(" drop (" + MetaPercentData.getTypeColumn(attrDomain.getName()));
							sql.append(" , " + MetaPercentData.getNumColumn(attrDomain.getName()));
							sql.append(" , " + MetaPercentData.getValueColumn(attrDomain.getName())+")");
							sqlBuilder.clear();
							sqlBuilder.setSql(sql.toString());
							sqlExecutor.execUpdate(sqlBuilder);
						}
					}
				}
			}
		}
	}
	@Test
	public void testRenameColumn(){
		String[] categories = {"CT201301"};
		String[] oldNames = {"ACHIEVE_OF_PERSON_ID","ACHIEVE_OF_PERSON_LOGINID","ACHIEVE_OF_PERSON_NAME"};
		String[] newNames = {"CGSSR_ID","CGSSR_LOGINID","CGSSR_NAME"};
		for(String category: categories){
			List<MetaEntity> entities = metaEntityService.getEntities("D201301", category);
			for(MetaEntity entity : entities){
				Set<MetaAttribute> attributes = entity.getAttributes();
				for(MetaAttribute attr:attributes){
					for(int i = 0;i<3;i++){
						if(attr.getName().equals(oldNames[i])){
							StringBuilder sql = new StringBuilder("alter table " + entity.getName()+" rename column "+oldNames[i]+" to "+newNames[i]);
							sqlBuilder.clear();
							sqlBuilder.setSql(sql.toString());
							sqlExecutor.execUpdate(sqlBuilder);
						}
					}
					
				}
			}
			
			
		}
	}
	@Test
	public void testModifyData(){
		String[] categories = {"C201301","C201302","C201303","C201304","C20130201","C20130202"};
		for(String category:categories){
			List<MetaEntity> entities = metaEntityService.getEntities("D201301", category);
			for(MetaEntity entity:entities){
				MetaEntityDomain entityDomain = metaEntityService.getEntityDomain(entity);
				List<MetaAttrDomain> attrDomains = entityDomain.getAttrDomains();
				for(MetaAttrDomain attrDomain:attrDomains){
					if(StringUtils.isNotBlank(attrDomain.getDicId())){
						StringBuilder sql = new StringBuilder("update "+ entity.getName());
						sql.append(" set "+attrDomain.getName()+" = ");
						sql.append(" (select value from meta_dic_item where id = ");
						sql.append(entity.getName() + "." + attrDomain.getName() +")");
						sqlBuilder.clear();
						sqlBuilder.setSql(sql.toString());
						sqlExecutor.execUpdate(sqlBuilder);
					}
				}
			}
		}
	}
	@Test
	public void testSelectListSql(){
		List<String> attrNames=new LinkedList<String>();
		attrNames.add("ID");
		attrNames.add("HJ_PROJECT");
		//List<String[]> values = dsepMetaService.getData("E20130201", attrNames, null, null, false, 0, 0);
		//for(String[] data: values){
		//	loggerTool.info(data[0]+"\t"+data[1]+"\n");
		//}
	}
	/**
	 * 测试如何查询单个实体
	 */
	@Test
	public void testSelectSql(){
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("UNIT_ID", "10006");//单位代码
		params.put("DISC_ID", "0835");//学科代码
		//MetaEntity entity = metaEntityService.getByName("DSEP_C_KYHJ_2013");
		MetaEntityDomain entityDomain = metaEntityService.getEntityDomain("E20130201", "C");//获得数据采集类别中的科研获奖领域对象
		//显示实体的属性信息
		loggerTool.info(entityDomain.getName()+"\t"+entityDomain.getChsName());
		//显示实体的属性信息
		MetaDicDomain dic = null;
		for(MetaAttrDomain attr:entityDomain.getAttrDomains()){
			loggerTool.info(attr.getName()+"\t"+attr.getChsName()+"\t"+attr.getDicId());
			if(attr.getDataTypeObject()==MetaDataType.DIC){
				dic = attr.getDic();
			}
		}
		if(dic!=null){//有字典类型，显示字典类型的内容
			loggerTool.info(dic.getId()+"\t"+dic.getName());
			for(String key:dic.getDicItems().keySet()){
				loggerTool.info(key+"\t"+dic.get(key));
			}
		}

		sqlBuilder.buildSingleSelectSql(entityDomain.getEntity(), params, "SEQ_NO", true, "backup");
		List<Map<String,String>> results = sqlExecutor.execQuery(sqlBuilder, 1, 20);
		if(results!=null){
			for(Map<String, String> value :results){
				loggerTool.info(value.get("UNIT_ID") + "\t" + value.get("SEQ_NO") + "\t" + value.get("HJ_Unit_Num") +"\t" + value.get("HJ_Disc_Num") );
			}
		}
	}
	
	@Test
	public void testEhcache(){
		MetaEntityDomain ed = metaEntityService.getEntityDomain("E20130201");		
		loggerTool.info("使用缓存，下一句应该不查询数据库");
		MetaEntityDomain ed2 = metaEntityService.getEntityDomain("E20130201");
		loggerTool.info(ed.getId()+"=="+ed2.getId());
		MetaEntity me = metaEntityService.getById("E20130201");
		MetaEntityDomain ed3 = metaEntityService.getEntityDomain(me, "C");
		loggerTool.info(ed3.getId()+"=="+ed2.getId());
	}
	@Test 
	public void testCollectFlow(){
		//st<Map<String,String>> list = collectFlowService.getPreData("10006", "DISC_ID", true , 0, 0);
		//ggerTool.info(list.size());
		/*List<Map<String,String>> list = collectFlowService.getPreData("10006", "0835");
		for(Map<String, String> value: list){
			loggerTool.info(value.get("UNIT_ID") +value.get(" DISC_ID" )+"\t"+value.get("INSERT_TIME"));
		}*/
		/*List<Eval> list = collectFlowService.getByUnitId("10006");
		for(Eval value: list){
			loggerTool.info(value.getUnitId() +value.getDiscId()+"\t"+value.getInsertTime().toString());
		}*/
	}

	@Test
	public void testMetaDic(){
		MetaDicDomain dic = metaDicService.getById("DKY002");
		System.out.println(dic.getId() + "\t" + dic.getName());
		loggerTool.warn( "-----------logger----------");
		for(String key : dic.keySet()){
			loggerTool.warn(key + "\t" + dic.get(key));
		}

		MetaDicDomain dic2 = metaDicService.getById("DKY002");
	}
	
	@Test
	public void testDiscTree(){
		List<CollectionTreeVM> tree = collectService.getDisciplineCollectTrees("");
		loggerTool.info(tree.get(0).getName());
	}
	
	@Test
	public void testInsertSql(){
		Hashtable<String, String> rowData = new Hashtable<String, String>();		
		rowData.put("SEQ_NO", "32");//顺序号
		rowData.put("HJ_NAME", "DKY002002");
		rowData.put("HJ_PROJECT", "无人侦察机");
		rowData.put("ZS_NO", "31320");
		rowData.put("HJ_PROFESSOR", "王七");
		rowData.put("HJ_YEAR", "2014");
		rowData.put("HJ_LEVEL", "DKY001001");
		rowData.put("HJ_UNIT_NUM", "4(1)");
		rowData.put("HJ_DISC_NUM", "5[30%]");
		loggerTool.info(collectService.newRow("E20130201", "10006", "0835", "10006_0835", rowData));
	}
	
	@Test
	public void testUpdateSql(){
		Hashtable<String, String> rowData = new Hashtable<String, String>();
		rowData.put("HJ_PROJECT", "测试更新值");
		rowData.put("HJ_PROFESSOR", "使用更新修改的名字");
		rowData.put("HJ_UNIT_NUM", "8(1)");
		loggerTool.info(collectService.updateRow("E20130201", "10006_0835", "358A97B06073416A9D2056B38BAFB750","","", rowData));
	}
	
	@Test
	public void testDeleteSql(){
		//String pkValue = "C3824A90B69C4AB2B5C006BBF57DC773";
		//loggerTool.info(collectService.deleteRow("E20130201", pkValue));
	}
	@Test
	public void testBaseDBOper(){		
		List<Map<String, String>> rowData = 
			baseDBOper.getRowData("dsep_c_zj_2013", 
				new String[]{"SEQ_NO","ZJ_TYPE","ZJ_NAME","BIRTHDATE"}, "ZJ_NAME like '%三'"
				,"SEQ_NO", true, 0, 0);
		for(Map<String,String> row:rowData){
			loggerTool.info(row.get("SEQ_NO")
					+ "\t" + row.get("ZJ_TYPE")
					+ "\t" + row.get("ZJ_NAME")
					+ "\t" + row.get("BIRTHDATE"));
		}
	}
	@Test
	public void testAddAttribute(){
		String [] attrs1 = {"ORI_ID","原始ID","VARCHAR2","32","1","0"};
		String [] attrs2 = {"ORI_TYPE","数据来源","VARCHAR2","1","1","0"};
		String [] attrStyle1 ={"32","0","1","I","0","0","0","0","center"};
		String[] categories = {"C201301","C201302","C201303","C201304","C20130201","C20130202"};
		//String[] categories = {"C20130201"};
		MetaAttribute attribute1 = new MetaAttribute();//添加第一个属性
		attribute1.setName(attrs1[0]);
		attribute1.setChsName(attrs1[1]);
		attribute1.setDataType(attrs1[2]);
		attribute1.setDataLength(Integer.valueOf(attrs1[3]));
		attribute1.setIsNull(attrs1[4]);
		attribute1.setIndexNo(Integer.valueOf(attrs1[5]));
		
		MetaAttribute attribute2 = new MetaAttribute();//添加第二个属性
		attribute2.setName(attrs2[0]);
		attribute2.setChsName(attrs2[1]);
		attribute2.setDataType(attrs2[2]);
		attribute2.setDataLength(Integer.valueOf(attrs2[3]));
		attribute2.setIsNull(attrs2[4]);
		attribute2.setIndexNo(Integer.valueOf(attrs2[5]));
		
		MetaAttrStyle attrStyle = new MetaAttrStyle();
		attrStyle.setDispLength(Integer.valueOf(attrStyle1[0]));
		attrStyle.setEditable(attrStyle1[1]);
		attrStyle.setIsHidden(attrStyle1[2]);
		attrStyle.setControlType(attrStyle1[3]);
		attrStyle.setColNo(Integer.valueOf(attrStyle1[4]));
		attrStyle.setRowNo(Integer.valueOf(attrStyle1[5]));
		attrStyle.setColNums(Integer.valueOf(attrStyle1[6]));
		attrStyle.setRowNums(Integer.valueOf(attrStyle1[7]));
		attrStyle.setAlign(attrStyle1[8]);
		for(String category:categories){
			List<MetaEntity> entities = metaEntityService.getEntities("D201301", category);
			for(MetaEntity entity:entities){
				//MetaEntity entity = metaEntityService.getById("E20130201");
				if(entity.getId().equals("E20130201")||
				   entity.getId().equals("E20130202")||
				   entity.getId().equals("E20130203")){
				   continue;
				}else{
					metaEntityService.addAttribute(entity.getId(), attribute2,attrStyle);
					//metaEntityService.addAttribute(entity.getId(), attribute2,attrStyle);
				}
				
			}
		}	
	}
	@Test
	public void testAddAttrByEntityId(){
		String entityId = "E20130201";
		String [] attrs1 = {"ATTACH_ID","附件ID","FILE","0","1","0"};
		String [] attrStyle1 ={"100","1","0","F","0","0","0","0","center"};
		MetaAttribute attribute1 = new MetaAttribute();//添加第一个属性
		attribute1.setName(attrs1[0]);
		attribute1.setChsName(attrs1[1]);
		attribute1.setDataType(attrs1[2]);
		attribute1.setDataLength(Integer.valueOf(attrs1[3]));
		attribute1.setIsNull(attrs1[4]);
		attribute1.setIndexNo(Integer.valueOf(attrs1[5]));
		
		MetaAttrStyle attrStyle = new MetaAttrStyle();
		attrStyle.setDispLength(Integer.valueOf(attrStyle1[0]));
		attrStyle.setEditable(attrStyle1[1]);
		attrStyle.setIsHidden(attrStyle1[2]);
		attrStyle.setControlType(attrStyle1[3]);
		attrStyle.setColNo(Integer.valueOf(attrStyle1[4]));
		attrStyle.setRowNo(Integer.valueOf(attrStyle1[5]));
		attrStyle.setColNums(Integer.valueOf(attrStyle1[6]));
		attrStyle.setRowNums(Integer.valueOf(attrStyle1[7]));
		attrStyle.setAlign(attrStyle1[8]);
		metaEntityService.addAttribute(entityId, attribute1, attrStyle);
	}
	@Test
	public void testAddColumnByEntitId(){
		String entityId = "E20130201";
		MetaEntity entity = metaEntityService.getById(entityId);
		String tableName= entity.getName()+"_BAK";
		String [] attrs1 = {"ATTACH_ID","附件ID","FILE","0","1","0"};
		StringBuilder sql = new StringBuilder(" alter table "+tableName+" add "+
				attrs1[0]+" varchar2(200) NULL");
				sqlBuilder.clear();
				sqlBuilder.setSql(sql.toString());
				sqlExecutor.execUpdate(sqlBuilder);	
	}
	@Test
	public void testAddColumn(){
		String [] attrs1 = {"SJLY","来源主键","FILE","0","1","0"};
	    //String[] categories = {"C201301","C201302","C201303","C201304","C20130201","C20130202"};
		//String[] categories = {"C20130201"};
	   // String[] categories = {"C20130201","C20130202"};
		//for(String category:categories){
			//List<MetaEntity> entities = metaEntityService.getEntities("D201301", category);
			//for(MetaEntity entity : entities){
			//MetaEntity entity = metaEntityService.getById("E20130201");
				/*if(entity.getId().equals("E20130201")||
						   entity.getId().equals("E20130202")||
						   entity.getId().equals("E20130203")){
						   continue;
				}else{*/
		        
		List<MetaEntity> entities = metaEntityService.getEntities("D201401", "");
		{
			for(MetaEntity entity: entities){
				String tableName = entity.getName();
				StringBuilder sql = new StringBuilder(" alter table "+tableName+" add "+
				attrs1[0]+" varchar(2) default 0 ");
				sqlBuilder.clear();
				sqlBuilder.setSql(sql.toString());
			    sqlExecutor.execUpdate(sqlBuilder);	
				}
		}
		            
						
				//}
			//}
	}
	@Test
	public void testWebService()
	{
		Object[] args= new Object[4];
		args[0] = new String("10006");
		args[1] = new String("085213");
		args[2] = new String("zyxw");
		args[3] = new String("");//学科简介文件地址
		Object[] result = WebServiceUtil.invoke("Generate", args);
		String id = (String)(result[0]);
		loggerTool.info(id);
	}	
	@Test
	public void testEntityMap()
	{
		//MBA数据：DSEP_C_MBARZQK_2014
		int i = collectService.importPubData("E2014020204","10003", "125200", "test");
		loggerTool.info(i);
	}
	@Test
	public void testSimilarity()
	{
		MetaEntity me = metaEntityService.getById("E20130204");//国内论文
		List<MetaEntity> mes = new LinkedList<MetaEntity>();
		mes.add(me);
		//MetaEntity me = metaEntityService.getById("E20130205");//国外论文
		dmSimilarityCheckService.startCheck("test", "", "", "E20130204");
	}
}
