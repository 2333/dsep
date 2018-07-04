package com.dsep.util.crawler.searchFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FolderCopyer {

	public static void main(String[] args) {
		FolderCopyer.copyFolder("D:/2017malao/20171220/1downloadOrders/1010587884468",
				"D:/2017malao/20171220/2selectedOrders/1010587884468");
	}

	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // ����ļ��в����� �������ļ���
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// ��������ļ���
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("��������ļ������ݲ�������");
			e.printStackTrace();

		}

	}
}
