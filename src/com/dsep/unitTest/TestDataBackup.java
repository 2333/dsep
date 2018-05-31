package com.dsep.unitTest;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.DateTime;
import oracle.sql.DATE;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.hibernate.exception.SQLGrammarException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.common.logger.LoggerTool;
import com.dsep.dao.common.*;
import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.dsepmeta.databackup.DisciplineDataBackupDao;
import com.dsep.dao.dsepmeta.databackup.impl.DisciplineDataBackupDaoImpl;
import com.dsep.dao.rbac.RightDao;
import com.dsep.dao.rbac.impl.*;
import com.dsep.entity.dsepmeta.BackupManagement;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.databackup.DataBackupService;
import com.dsep.service.databackup.impl.*;
import com.dsep.service.dsepmeta.dsepmetas.DMBackupService;
import com.dsep.service.rbac.*;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.BackupManageVM;
import com.dsep.vm.PageVM;
import com.meta.service.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common2014.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/WEB-INF/config/2014/util/utils.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml",})
public class TestDataBackup extends DisciplineDataBackupDaoImpl {
	
	@Autowired
	private DMBackupService backupService;

	
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
	
	@Autowired
	private LoggerTool loggerTool;
	
	public void ArgsPrint(String[] args) {
		for (int i = 0; i < args.length; i++) {
			System.out.print(args[0] + " ");
		}
	}
	
	
	
	@Test
	public void test_backupDiscipline(){
		try {
			dataBackupService.backupDiscipline("10006", "0835");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_deleteDiscipline() throws Exception{
		dataBackupService.deleteDiscipline("10006_0835_20140609_105040", "0835");
	}
	
	@Test
	public void test_restoreDiscipline(){
		try {
			assertEquals(dataBackupService.restoreDiscipline("24302FE5E3114D2DB8A0894E6B7519DF", "10006", "0835", "10006_0835_20140512_163411"),true);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void Test_BackupManageVM(){
		List<BackupManagement> backupList = new ArrayList<BackupManagement>();
		List<BackupManageVM> vmList = new ArrayList<BackupManageVM>();
		BackupManagement management1 = new BackupManagement();
		management1.setUnitId("12313");
		management1.setDiscId("0835");
		management1.setRemark("very good");
		management1.setVersionId("10006_0835_123121");
		BackupManagement management2 = new BackupManagement();
		management2.setUnitId("12313");
		management2.setDiscId("0835");
		management2.setRemark("very good");
		management2.setVersionId("10006_0835_123121");
		backupList.add(management1);
		backupList.add(management2);
		
		for(int i=0;i < backupList.size();i++){
			BackupManagement test = backupList.get(i);
 			BackupManageVM newVm = new BackupManageVM(test);
			vmList.add(newVm);
		}
	}
	
	@Test
	public void Test_GetAllBackupVersion(){
		try {
			for(int i=0;i < 1000;i++){
				dataBackupService.getAllBackupVersion(0, 10, true, "", "10006", "085213");
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_cache(){
		/*PageVM<BackupManageVM> theVm = dataBackupService.getAllBackupVersion(1, 10, "10006", "0835");
		System.out.println("++++++++++++++++++++++++++++++++++++");
		PageVM<BackupManageVM> theVm2 = dataBackupService.getAllBackupVersion(1, 10, "10006", "0835");
		System.out.println("++++++++++++++++++++++++++++++++++++");*/
	}
	
	@Test
	public void Test_saveBackupVersion(){
		Map<String, Object> editRow = new HashMap<>();
		editRow.put("remark", "hello");
		/*String formatString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String theValue = "to_date('"+formatString+"','yyyy-MM-dd HH:mm:ss')";*/
		try {
			dataBackupService.updateBackupVersion("0F5A037AF28F4D048B259FA074A66A39", "hello");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Test
	public void test_createBackupTable(){
		backupService.createAllBackupTable();
	}


	@Test
	public void Test_Service_GetDataCount_test() {
		int count = 0;
		try {
			count = dataBackupService.GetDataCount("test", "10001", "0836");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(2, count);
	}

	@Test
	public void Test_Service_GetDataCount_kyhj() {
		int count = 0;
		try {
			count = dataBackupService.GetDataCount("dsep_kyhj_0", "10006",
					"0835");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(14, count);
	}


	@Test
	public void TestTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String newTime = sdf.format(new Date());

		DateFormat df = DateFormat.getDateInstance();
		String newTime2 = df.format(new Date());
		System.out.println(newTime);
		System.out.println(newTime2);
	}


	public void Test_Try_Catch_Finally() {
		for (int i = 0; i < 3; i++) {
			try {
				System.out.println("I'm trying " + i);
				if (i == 1) {
					throw new BackupException("I'm backupException " + i);
				}
			} catch (BackupException e) {
				e.printStackTrace();
			} finally {
				System.out.println("I'm in finally " + i);
			}
		}
	}

	@Test
	public void Main_Try_Catch_Finally() {
		Test_Try_Catch_Finally();
	}
	

}
