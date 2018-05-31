package com.dsep.service.publicity.process.production.publicity;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.entity.enumeration.publicity.UnitObjectStatus;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.vm.JqgridVM;

@Controller
public abstract class PublicityProcess {
	
	/**
	 * 获取加载页面时显示的学科Map
	 * @param unitId
	 * @return
	 */
	public abstract Map<String,String> getDiscMap(DisciplineService disciplineService);
	
	/**
	 * 获取加载页面时显示的学校Map
	 * @param discId
	 * @return
	 */
	public abstract Map<String,String> getUnitMap(UnitService unitService);

	/**
	 * 选择某一学科时，返回相应的学校Map，以json格式返回
	 * @param discId 用户选择的学科ID
	 * @return
	 */
	public abstract Map<String,String> changeDiscipline(String discId,UnitService unitService);
	
	/**
	 * 选择某一学校时，返回相应的学科Map，以json格式返回
	 * @param unitId 用户选择的学校ID
	 * @return
	 */
	public abstract Map<String,String> changeUnit(String unitId,DisciplineService discService);

	/**
	 * 处理公示数据，加入是否已提异议的标示
	 * @param theVM
	 * @param currentRoundId 当前公示轮次ID
	 * @param originalObjectionService 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void processJqgridVM(JqgridVM theVM, String currentRoundId, OriginalObjectionService originalObjectionService) throws IllegalArgumentException, IllegalAccessException
	{
		List<Map<String,String>> rowData = theVM.getRows();
		for(int i=0;i < rowData.size();i++){
			Map<String,String> rowMap = rowData.get(i);
			String dataId = rowMap.get("ID");
			OriginalObjection theObjection = new OriginalObjection();
			theObjection.setCurrentPublicRoundId(currentRoundId);
			theObjection.setUnitStatus(UnitObjectStatus.NOTSUBMIT.getStatus());
			this.setOriginalObjection(theObjection);//抽象方法，每个子类的操作不相同
			theObjection.setObjectCollectItemId(dataId);
			String result = originalObjectionService.getObjectionCount(theObjection)+"";
			rowMap.put("objectionCount", result);
		}
	}
	
	/**
	 * 抽象方法，中心、学校、学科查看公示数据时设置的条件不同
	 * @param originalObjection
	 */
	protected abstract void setOriginalObjection(OriginalObjection originalObjection);
	
	/**
	 * 上传证明材料
	 * @param file 原文件
	 * @return 上传后的文件名
	 */
	public abstract String uploadFile(MultipartFile file);
	
	
	
}
