package com.dsep.unitTest;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.service.dsepmeta.dsepmetas.DMSimilarityCheckService;
import com.dsep.util.datacheck.CheckSimByCompositive;
import com.dsep.util.datacheck.CheckSimByRate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml","file:WebContent/WEB-INF/config/IKAnalyzer.cfg.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",})
public class TestSim {
	@Autowired
	private DMSimilarityCheckService dmSimilarityCheckService;
	private CheckSimByCompositive checkSimByCompositive;
	
	public DMSimilarityCheckService getDmSimilarityCheckService() {
		return dmSimilarityCheckService;
	}

	public void setDmSimilarityCheckService(
			DMSimilarityCheckService dmSimilarityCheckService) {
		this.dmSimilarityCheckService = dmSimilarityCheckService;
	}

	@Test
	public void testGetSimCheckFields(){
		
	}
	@Test
	public void testGetSheetNames(){
		
	}
	@Test
	public void testcheckSimilarityForColumns(){
/*		List<Object[]> list = new ArrayList<Object[]>();
		String[] Item1 = new String[3];
		String[] Item2 = new String[3];
		String[] Item3 = new String[3];
		String[] Item4 = new String[3];
		Item1[0]="as01";
		Item1[1]="基于Spring MVC框架的Web研究与应用基于Spring MVC框架的Web研究与应用";
		Item1[2]="白云鄂博矿稀土及铌资源高效利用";
		list.add(Item1);
		Item2[0]="as02";
		Item2[1]="基于Spring MVC框架的Web研究与应用基于Spring MVC框架的Web研究与应用";
		Item2[2]="半导体纳米复合材料";
		list.add(Item2);
		Item3[0]="as03";
		Item3[1]="面向对象的PolSAR图像典型地物提取关键技术研究";
		Item3[2]="白云鄂博矿稀土及铌资源高效利用";
		list.add(Item3);
		Item4[0]="as04";
		Item4[1]="基于Spring MVC框架的Web研究与应用基于Spring MVC框架的Web研究与应用";
		Item4[2]="基于小波变换的遥感图像去云方法研究";
		list.add(Item4);
		for(int i=0;i<list.size();i++){
	//		System.out.println(Item[0]+"  "+Item[1]+" "+Item[2]);
			System.out.println(list.get(i)[0]+"  "+list.get(i)[1]+"  "+list.get(i)[2]+"  ");
		}
		List<String[]> result = CheckSimByCompositive.checkSimilarityForColumns(list, 0.9);
		System.out.println("result的长度"+result.size());
		for(int i=0;i<result.size();i++){
			int n=result.get(i).length;
			System.out.print(n+"个元素  ");
			System.out.print("Sim_with_id  ");
			for(int j=0;j<n;j++){
				if(j==(n-1)){
					System.out.print("Sim_id" +"  ");
				}
				System.out.print(result.get(i)[j]+"  ");

			}
			System.out.println();
		}
		System.out.println("测试完毕！！");*/
		
	}
/*	@Test
	//最小编辑距离算法
	public void testRate() {
		for(int m=80;m<82;m++){
			Date d1=new Date();
			List<String[]> list=checkSimilarityService.checkIsSimilarityByRate("DSEP_CHECKTEST", "ID", "CONTENT", (double) m / 100);
			if(list==null) SimByLuWen.writerText("未找到相重复的",true);
			else{
				SimByLuWen.writerText("测试相似度为"+(double) m/100,true); 
				for(int i=0;i<list.size();i++){
					SimByLuWen.writerText("第"+(i+1)+"组：",false);
					for(int j=0;j<list.get(i).length;j++)
					SimByLuWen.writerText((list.get(i)[j]+" , "),false); 
					SimByLuWen.writerText("",true); 
				}	
			}
			Date d2=new Date();
			SimByLuWen.writerText("一共耗时："+(d2.getTime()-d1.getTime())+"ms",true);
		}
	}*/

	public static void main(String[] args) {
		System.out.print("Sim_with_id  ");
	}
	
	

	
	//@Test
	/*public void testThree() {
//		for(int m=80;m<100;m++){
			Date d1=new Date();
			List<String[]> list=checkSimilarityService.checkByCompositive("DSEP_CHECK_RESULT_0", "CHECK_ID", "CHECK_CONTENT", 0.95);
			if(list==null) CheckSimByCompositive.writerText("未找到相重复的",true);
			else{
//				SimByLuWen.writerText("测试相似度为"+(double) m/100,true); 
				for(int i=0;i<list.size();i++){
					CheckSimByCompositive.writerText("第"+(i+1)+"组：",false);
					for(int j=0;j<list.get(i).length;j++)
					CheckSimByCompositive.writerText((list.get(i)[j]+" , "),false); 
					CheckSimByCompositive.writerText("",true); 
				}	
			}
			Date d2=new Date();
			CheckSimByCompositive.writerText("一共耗时："+(d2.getTime()-d1.getTime())+"ms",true);
//		}
		
	}*/
/*
	@Test

	public void testCos() {
		Date d1=new Date();
		System.out.println("余弦定理结果为：");
		List<String[]> list=checkSimilarityService.checkIsSimilarityByTfIdf("DSEP_CHECKTEST", "ID", "CONTENT", 0.80);
		if(list==null) System.out.println("未找到相重复的");
		else
			for(int i=0;i<list.size();i++){
				System.out.print("第"+(i+1)+"组：");
				for(int j=0;j<list.get(i).length;j++)
				System.out.print(list.get(i)[j]+" , ");
				System.out.println();  
			}	
		Date d2=new Date();
		System.out.println(d2.getTime()-d1.getTime());
	}
	*/
	@Test
	public void testGetSimCheckConfig(){
		
	}

	@Test
	public void testcheckSimByRateForColumns(){
		List<String[]> list = new ArrayList<String[]>();
		String[] Item1 = new String[3];
		String[] Item2 = new String[3];
		String[] Item3 = new String[3];
		String[] Item4 = new String[3];
		Item1[0]="as01";
		Item1[1]="基于Spring MVC框架的Web研究与应用基于Spring MVC框架的Web研究与应用";
		Item1[2]="白云鄂博矿稀土及铌资源高效利用";
		list.add(Item1);
		Item2[0]="as02";
		Item2[1]="基于Spring MVC框架的Web研究与应用基于Spring MVC框架的Web研究与应用";
		Item2[2]="半导体纳米复合材料";
		list.add(Item2);
		Item3[0]="as03";
		Item3[1]="面向对象的PolSAR图像典型地物提取关键技术研究";
		Item3[2]="白云鄂博矿稀土及铌资源高效利用";
		list.add(Item3);
		Item4[0]="as04";
		Item4[1]="基于Spring MVC框架的Web研究与应用基于Spring MVC框架的Web研究与应用";
		Item4[2]="基于小波变换的遥感图像去云方法研究";
		list.add(Item4);
		for(int i=0;i<list.size();i++){
	//		System.out.println(Item[0]+"  "+Item[1]+" "+Item[2]);
			System.out.println(list.get(i)[0]+"  "+list.get(i)[1]+"  "+list.get(i)[2]+"  ");
		}
//		List<String[]> result = CheckSimByCompositive.checkSimilarityForColumns(list, 0.9);
		List<String[]> result = CheckSimByRate.checkSimByRateForColumns(list, 0.9);
		System.out.println("result的长度"+result.size());
		for(int i=0;i<result.size();i++){
			int n=result.get(i).length;
			System.out.print(n+"个元素  ");
			System.out.print("Sim_with_id  ");
			for(int j=0;j<n;j++){
				if(j==(n-1)){
					System.out.print("Sim_id" +"  ");
				}
				System.out.print(result.get(i)[j]+"  ");

			}
			System.out.println();
		}
		System.out.println("测试完毕！！");
		
	}
	@Test
	public void test(){
		System.out.println("**********测试信息*************");
	}
	
}
