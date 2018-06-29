
package com.dsep.unitTest;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.dao.dsepmeta.databackup.DisciplineDataBackupDao;
import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.databackup.DataBackupService;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.service.rbac.RightService;
import com.dsep.service.rbac.RoleService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.GUID;
import com.dsep.util.UnitTest;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaEntity;
import com.meta.service.MetaDicService;
import com.meta.service.MetaDomainService;
import com.meta.service.MetaEntityCategoryService;
import com.meta.service.MetaEntityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/Web-INF/config/spring-util.xml" })
public class OriginalObjectionTest {

	@Autowired
	private OriginalObjectionService originalObjectionService;

	@Autowired
	private DisciplineDataBackupDao dataBackupDao;

	@Autowired
	private MetaDicService metaDicService;

	@Autowired
	private MetaDomainService metaDomainService;

	@Autowired
	private MetaEntityCategoryService metaEntityCategoryService;

	@Autowired
	private MetaEntityService metaEntityService;

	@Autowired
	private DataBackupService dataBackupService;

	@Autowired
	private UserService userService;

	@Autowired
	private RightService rightService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private DisciplineService disciplineService;

	@Test
	public void getVm_test() {
		/*
		 * try { originalObjectionService.showOriginalObjections("10006",
		 * "0836", "id", true, 1, 4); } catch (IllegalArgumentException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IllegalAccessException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	@Test
	public void queryObjection_test() {
		OriginalObjection conditionalObjection = new OriginalObjection();
		conditionalObjection.setUnitObjectType("1");
		conditionalObjection.setObjectUnitId("10006");
		try {
			originalObjectionService.getOriginalObjection(1, 4, true, "id",
					conditionalObjection);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getObjectType_test(){
		Map<String,String> theMap = originalObjectionService.getObjectTypeByEntityId("E20130101");
		UnitTest.testPrint(theMap.size());
		for(Map.Entry<String, String> theEntry:theMap.entrySet()){
			System.out.println(theEntry.getKey()+" "+theEntry.getValue());
		}
	}
	
	@Test
	public void deleteObjection_test() {
		originalObjectionService
				.deleteObjection("E5229FCB18D641EE8E132437635193BF");
	}

	@Test
	public void updateObjectionRowData_test() {
		/*originalObjectionService.updateObjectionRowData(
				"18014F60CE82410CB8373515A2157567", "test by panlinjie");*/
	}
	

	@Test
	public void updateObjection_test() {
		OriginalObjection theObjection = new OriginalObjection();
		theObjection.setId("E5229FCB18D641EE8E132437635193BF");
		theObjection.setUnitObjectType("2");
		theObjection.setUnitObjectContent("公示类型");
		try {
			originalObjectionService.updateObjection(theObjection);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addNewObjection_test() throws Exception {
		for (int i = 0; i < 100; i++) {
			for (int statu = 1; statu <= 4; statu++) {
				// MetaEntity entity = metaEntityService.getById("E20130101");
				// Set<MetaAttribute> objectCollectAttr =
				// entity.getAttributes();
				for (int type = 1; type <= 5; type++) {
					OriginalObjection theObjection = new OriginalObjection();
					theObjection.setId(GUID.get());
					/*theObjection.setStatus(String.valueOf(statu));*/
					theObjection.setUnitObjectType(String.valueOf(type));
					theObjection.setBeginTime(new Date());
					theObjection.setCheckTime(new Date());
					theObjection.setCurrentPublicRoundId("212312313212");
					theObjection.setObjectUnitId("10006");
					theObjection.setObjectDiscId("0803");
					theObjection.setProblemUnitId("10012");
					theObjection.setProblemDiscId("0837");
					theObjection.setUnitObjectContent("我对你有意见！！！");
					// theObjection.setObjectCollectEntity(entity);
					// theObjection.setObjectCollectAttr(objectCollectAttr
					// .iterator().next());
					originalObjectionService.addNewObjection(theObjection,"");
				}
			}
		}
	}

	@Test
	public void getOriginalObjection_test() {
		OriginalObjection theObjection = new OriginalObjection();
		theObjection.setUnitObjectType("1");
		theObjection.setObjectUnitId("10006");
		try {
			originalObjectionService.getOriginalObjection(1, 4, true,
					"currentPublicRoundId", theObjection);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
