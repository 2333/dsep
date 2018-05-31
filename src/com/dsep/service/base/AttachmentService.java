package com.dsep.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.Attachment;
import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.util.briefsheet.AbstractPDF;
import com.dsep.vm.PageVM;

@Transactional(propagation=Propagation.SUPPORTS)
public interface AttachmentService {

	/**
	 * 获取上传文件附件信息
	 * @param file 待上传的文件
	 * @param uploader 上传人
	 * @param occasion 附件应用场合
	 * @param unitId 学校ID（可为空）
	 * @return 附件帮助类对象
	 */
	abstract public AttachmentHelper getAttachmentHelper(MultipartFile file, String uploader, DsepEnum occasion, String unitId);
	/**
	 * 获取一个简况表帮助类，进行路径与文件名的统一管理，并获得文件Id信息等
	 * @param brief TODO
	 * @param uploader
	 * @param occasion
	 * @param unitId
	 * @return 附件帮助类对象。实际上并不是针对附件的，是一个文件帮助类，用于路径管理和文件Id管理
	 */
	abstract public AttachmentHelper getBriefHelper(AbstractPDF brief, String uploader, DsepEnum occasion, String unitId);
	/**
	 * 获取一个调查问卷帮助类，进行路径与文件名的统一管理，并获得文件Id信息等
	 * @param questionnairId 作为文件名称
	 * @param creater
	 * @param occasion
	 * @return 调查问卷帮助类对象。实际上并不是针对附件的，是一个问卷调查文件帮助类，用于路径管理和文件Id管理
	 */
	abstract public AttachmentHelper getQuestionnairHelper(String questionnairId, String uploader, DsepEnum occasion);
	/**
	 * 获取上传文件附件信息(用于采集和证明材料下载)
	 * @param file 待上传的文件
	 * @param uploader 上传人
	 * @param occasion 附件应用场合
	 * @param unitId 学校ID
	 * @param discId 学科ID
	 * @param colectItemName 采集项名称
	 * @param keyValue 关键词
 	 * @return 附件帮助类对象
	 */
	abstract public AttachmentHelper getAttachmentHelper(MultipartFile file, String uploader, DsepEnum occasion, String unitId, String discId, String colectItemName, String keyValue);
	
	/**
	 * 写入数据库附件信息
	 * @param attachment 要保存的附件对象
	 * @return 附件ID
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public String addAttachment(Attachment attachment);
	
	/**
	 * 根据附件id返回附件下载路径
	 * @param id 附件id
	 * @return 下载路径path
	 */
	abstract public String getAttachmentPath(String id);
	
	/**
	 * 根据附件id返回附件下载路径
	 * @param id 附件id
	 * @param storageId 存储设备id
	 * @return 下载路径path
	 */
	abstract public String getAttachmentPath(String id, String storageId);
	/**
	 * 根据一个list对象中的附件id集合，得到所有附件，将附件打包得到zip文件后，返回该zip文件下载路径
	 * @param attachIds
	 * @return
	 */
	abstract public String getZipAttachmentPath(List<String> attachIds);
	/**
	 * 根据附件id获取附件对象
	 * @param id
	 * @return
	 */
	abstract public Attachment getAttachment(String id);
	
	/**
	 * 根据附件id删除数据库附件信息
	 * @param id
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void deleteAttachment(String id);
	
	abstract public PageVM<Attachment> getAttachPageVMByOccassion(String occassion,
			String orderName,int pageIndex,
			int pageSize,Boolean asc);
	
}
