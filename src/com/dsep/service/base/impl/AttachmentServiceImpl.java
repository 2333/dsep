package com.dsep.service.base.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;








import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.dao.base.AttachmentDao;
import com.dsep.dao.base.StorageDao;
import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.Attachment;
import com.dsep.entity.Storage;
import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.service.base.AttachmentService;
import com.dsep.util.Configurations;
import com.dsep.util.GUID;
import com.dsep.util.briefsheet.AbstractPDF;
import com.dsep.vm.PageVM;

public class AttachmentServiceImpl implements AttachmentService{
	
	private StorageDao storageDao;
	private AttachmentDao attachmentDao;

	public StorageDao getStorageDao() {
		return storageDao;
	}

	public void setStorageDao(StorageDao storageDao) {
		this.storageDao = storageDao;
	} 
	
	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	@Override
	public AttachmentHelper getAttachmentHelper(MultipartFile file, String uploader,
			DsepEnum occasion, String unitId) {
		
		AttachmentHelper ah = new AttachmentHelper();
		
		String id = GUID.get();
		//生成新的文件名
		String originalName = file.getOriginalFilename();
		String storageName = getProcessedFileName(originalName, id);
		if("TEMPLATE".equals(occasion.getShowing())){
			storageName = originalName;
		}
		//获取领域信息
		String domainId = Configurations.getCurrentDomainId();
		//生成文件路径（相对路径）
		if(StringUtils.isBlank(unitId)) unitId = "center";
		String relativePath=null;
		if("TEMPLATE".equals(occasion.getShowing())){
			relativePath = domainId + "/" + occasion.getShowing() + "/";
		}else{
			relativePath = domainId + "/" + occasion.getShowing() + "/" + unitId + "/";
		}
		//读取根目录路径
		Storage storage = storageDao.getActiveStorage();
		String rootPath = getStoragePath(storage);//根目录信息
		
		//获取存储位置信息
		String path = rootPath + relativePath;
		
		Attachment attachment = new Attachment();
		attachment.setId(id);
		attachment.setName(originalName);
		attachment.setOccasion(occasion.getStatus());
		attachment.setPath(relativePath+storageName);
		attachment.setType(file.getContentType());
		attachment.setUnitId(unitId);
		attachment.setUploader(uploader);
		attachment.setDate(new Date());
		attachment.setStorage(storage);
		
		ah.setAttachment(attachment);
		ah.setPath(path);
		ah.setStorageName(storageName);

		return ah;
	}
	
	
	@Override
	public AttachmentHelper getAttachmentHelper(MultipartFile file, String uploader,
			DsepEnum occasion, String unitId, String discId, String colectItemName, String keyValue) {
		
		AttachmentHelper ah = new AttachmentHelper();
		
		String id = GUID.get();
		//生成新的文件名
		String originalName = file.getOriginalFilename();
		String storageName = null;
		if("TEMPLATE".equals(occasion.getShowing())){
			storageName = originalName;
		}
		storageName = getProcessedFileName(originalName, id);
		
		String flag = "";
		if(StringUtils.isBlank(unitId)){
			unitId = "center";
		}
		flag += unitId +"_";
		if(StringUtils.isNotBlank(discId)){
			flag += discId +"_";
		}
		if(StringUtils.isNotBlank(colectItemName)){
			flag += colectItemName +"_";
		}
		if(StringUtils.isNotBlank(keyValue)){
			flag += keyValue +"_";
		}

		//完整文件名称
		storageName = flag + storageName;
		
		//获取领域信息
		String domainId = Configurations.getCurrentDomainId();
		//生成文件路径（相对路径）
		
		String relativePath = domainId + "/" + occasion.getShowing() + "/" + unitId + "/";
		
		//读取根目录路径
		Storage storage = storageDao.getActiveStorage();
		String rootPath = getStoragePath(storage);//根目录信息
		
		//获取存储位置信息
		String path = rootPath + relativePath;
		
		Attachment attachment = new Attachment();
		attachment.setId(id);
		attachment.setName(originalName);
		attachment.setOccasion(occasion.getStatus());
		attachment.setPath(relativePath+storageName);
		attachment.setType(file.getContentType());
		attachment.setUnitId(unitId);
		attachment.setUploader(uploader);
		attachment.setDate(new Date());
		attachment.setStorage(storage);
		
		ah.setAttachment(attachment);
		ah.setPath(path);
		ah.setStorageName(storageName);

		return ah;
	}
	
	@Override
	public AttachmentHelper getBriefHelper(AbstractPDF brief, String uploader,
			DsepEnum occasion, String unitId) {
		String id = GUID.get();
		//生成新的文件名
		String originalName = brief.getName();
		String storageName = brief.getName();
		//获取领域信息
		String domainId = Configurations.getCurrentDomainId();
		//生成文件路径（相对路径）
		if(StringUtils.isBlank(unitId)) unitId = "center";
		String relativePath=null;
		if("TEMPLATE".equals(occasion.getShowing())){
			relativePath = domainId + "/" + occasion.getShowing() + "/";
		}else{
			relativePath = domainId + "/" + occasion.getShowing() + "/" + unitId + "/";
		}
		//读取根目录路径
		Storage storage = storageDao.getActiveStorage();
		String rootPath = getStoragePath(storage);//根目录信息	
		//获取存储位置信息
		String path = rootPath + relativePath;
		
		Attachment attachment = new Attachment();
		attachment.setId(id);
		attachment.setName(originalName);
		attachment.setOccasion(occasion.getStatus());
		attachment.setPath(relativePath+storageName);
		attachment.setType("pdf");//???
		attachment.setUnitId(unitId);
		attachment.setUploader(uploader);
		attachment.setDate(new Date());
		attachment.setStorage(storage);
		
		AttachmentHelper ah = new AttachmentHelper();
		ah.setAttachment(attachment);
		ah.setPath(path);
		ah.setStorageName(storageName);
		
		brief.setName(ah.getStorageName());
		brief.setPath(ah.getPath());
		return ah;
	}
	
	@Override
	public AttachmentHelper getQuestionnairHelper(String questionnairId, String uploader, DsepEnum occasion) {
		String id = GUID.get();
		//生成新的文件名
		String originalName = questionnairId;
		String storageName = questionnairId;
		//获取领域信息
		String domainId = Configurations.getCurrentDomainId();
		//生成文件路径（相对路径）
		String unitId = "center";
		String relativePath=null;
		if("TEMPLATE".equals(occasion.getShowing())){
			relativePath = domainId + "/" + occasion.getShowing() + "/";
		}else{
			relativePath = domainId + "/" + occasion.getShowing() + "/" + unitId + "/";
		}
		//读取根目录路径
		Storage storage = storageDao.getActiveStorage();
		String rootPath = getStoragePath(storage);//根目录信息	
		//获取存储位置信息
		String path = rootPath + relativePath;
		
		Attachment attachment = new Attachment();
		attachment.setId(id);
		attachment.setName(originalName);
		attachment.setOccasion(occasion.getStatus());
		attachment.setPath(relativePath+storageName);
		attachment.setType("jsp");//???
		attachment.setUnitId(unitId);
		attachment.setUploader(uploader);
		attachment.setDate(new Date());
		attachment.setStorage(storage);
		
		AttachmentHelper ah = new AttachmentHelper();
		ah.setAttachment(attachment);
		ah.setPath(path);
		ah.setStorageName(storageName);
		return ah;
	}
	
	//如果协议为local，则返回本地路径，否则返回web路径
	private String getStoragePath(Storage storage){
		if(storage.getProtocol().equals("local"))
			return storage.getPath();
		else
			return storage.getWebUrl();
	}
	
	@Override
	public String addAttachment(Attachment attachment) {
		return attachmentDao.save(attachment);
	}
	
	@Override
	public String getAttachmentPath(String id) {
		Attachment attachment = attachmentDao.get(id);
		if(attachment != null){
			String relativePath = attachment.getPath();
			String rootPath = getStoragePath(attachment.getStorage());
			String path = rootPath + relativePath;
			return path;
		}else{
			return null;
		}
	}
	
	@Override
	public String getAttachmentPath(String id, String storageId) {
		Attachment attachment = attachmentDao.get(id);
		String relativePath = attachment.getPath();
		String rootPath = getStoragePath(storageDao.getStorageById(storageId));
		String path = rootPath + relativePath;
		return path;
	}
	@Override
	public String getZipAttachmentPath(List<String> attachIds){
		Storage s = storageDao.getActiveStorage();
		String rootPath = s.getPath();
		String fileName = "证明材料.zip";
		byte[] buffer = new byte[1024];
		String filePath = rootPath + "zipTemp/";
		String strZipPath = filePath+fileName;
		List<File> fileList = new ArrayList<File>();
		for(int i = 0 ; i < attachIds.size() ; i++){
			String id = attachIds.get(i);
			String attachPath = this.getAttachmentPath(id);
			fileList.add(new File(attachPath));
		}
		try{
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
			for(int i = 0 ; i < fileList.size() ; i++){
				FileInputStream fis = new FileInputStream(fileList.get(i));
				out.putNextEntry(new ZipEntry(fileList.get(i).getName()));
				out.setEncoding("GBK");
				int len;
				//读入需要下载的内容，打包到zip文件
				while((len = fis.read(buffer)) >0){
					out.write(buffer,0,len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return strZipPath;
	}
	@Override
	public Attachment getAttachment(String id) {
		return attachmentDao.get(id);
	}
	
	@Override
	public void deleteAttachment(String id) {
		if(id != null){
			attachmentDao.deleteByKey(id);
		}
	}
	
	/**
	 * 获取处理后的文件名
	 * @param formerFileName 原文件名，包含扩展名
	 * @param userString 需要在原文件名后添加的后缀字符串
	 * @return
	 */
	private static String getProcessedFileName(String formerFileName,String userString){
		String[] fileArray = formerFileName.split("\\.");
		String formerSimpleFileName = "";//不带后缀名的原文件名
		String suffix = fileArray[fileArray.length-1];//原文件的后缀名
		for(int i=0;i < fileArray.length-1;i++){
			formerSimpleFileName = fileArray[i];
		}
		return formerSimpleFileName+"_"+userString+"."+suffix;
	}
	
	public PageVM<Attachment> getAttachPageVMByOccassion(String occassion,
			String orderName,int pageIndex,
			int pageSize,Boolean asc){
		List<Attachment> attachments = attachmentDao.getAttachmentsByOccassion(pageIndex, pageSize,
				!asc, orderName, occassion);
		int totalCount = attachmentDao.getCountByOccassion(occassion);
		PageVM<Attachment> pageVM = new PageVM<Attachment>(pageIndex, 
				totalCount, pageSize,attachments);
		return pageVM;
	}
}
