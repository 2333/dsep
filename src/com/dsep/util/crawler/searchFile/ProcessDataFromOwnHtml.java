package com.dsep.util.crawler.searchFile;

import java.io.File;

import com.dsep.util.crawler.spider.SiteSetting;

public class ProcessDataFromOwnHtml {
	public static void main(String[] args) throws Exception {
		String str = "1013557818836ORDERANDEAN2724341967201#1@@123EANADNEANFANGHONGYU1023517839546ORDERANDEAN2724318006711#1@@123EANADNEANFANGHONGYU1058557899393ORDERANDEAN2724341967201#1@@123EANADNEANFANGHONGYUFANGHONGYU";
		process(str);
		//System.out.println();
	}
	public static void process(String textFromHtml) throws Exception {
		DeleteDirectory.deleteDir(new File(SiteSetting.getStoreFolderPath()));
		String str = textFromHtml;
		String[] arr = str.split("FANGHONGYU");
		for (String ele : arr) {
			if (null == ele || ele.length() == 0) continue;
			String orderId = null;
			String ean = null;
			String myEan = null;
			int qty = 0;
			if (ele.length() != 0) {
				String[] arr2 = ele.split("ORDERANDEAN");
				System.out.println("order is:" + arr2[0]);
				orderId = arr2[0];
				FolderCopyer.copyFolder(SiteSetting.D_MYSOUQDate1downloadOrders + arr2[0],
						SiteSetting.getStoreFolderPath() + File.separator + arr2[0]);
				System.out.println(arr2[1]);
				String[] arr3 = arr2[1].split("EANADNEAN");
				for (String ele2 : arr3) {
					//System.out.println(ele2);
					String[] arr4 = ele2.split("@@");
					//for (String ele3 : arr4) {
					String[] arr5 = arr4[0].split("#");
					System.out.println("ean:" +arr5[0]);
					ean = arr5[0];
					System.out.println("qty:" +arr5[1]);
					qty = Integer.valueOf(arr5[1]);
					//}
					//System.out.println("ean:" + arr4[0]);
					System.out.println("myEan:" + arr4[1]);
					myEan = arr4[1];
					PropertiesUtil.WriteProperties(SiteSetting.myEanInfo, ean, myEan);
					String oldJPGName = SiteSetting.getStoreFolderPath() + "\\" + orderId + "\\" + ean + "_" + qty + ".jpg";
					String newJPGName = SiteSetting.getStoreFolderPath() + "\\" + orderId + "\\" + myEan +  numberToWord(qty) + ".jpg";
					System.out.println(oldJPGName);
					System.out.println(newJPGName);
					renameFile(oldJPGName, newJPGName);
				}
			}
			
			//System.out.println(SiteSetting.D2017malaoDate2selectedOrders + orderId + "/" + myEan +  numberToWord(qty) + ".eaninfo");
		}
	}
	public static void renameFile(String oldname,String newname) { 
        if(!oldname.equals(newname)){//�µ��ļ������ǰ�ļ���ͬʱ,���б�Ҫ���������� 
            File oldfile=new File(oldname); 
            File newfile=new File(newname); 
            if(!oldfile.exists()){
            	System.out.println("�������ļ�������");
                return;//�������ļ�������
            }
            if(newfile.exists())//���ڸ�Ŀ¼���Ѿ���һ���ļ������ļ�����ͬ�������������� 
                System.out.println(newname+"�Ѿ����ڣ�"); 
            else{ 
            	System.out.println("rename");
                oldfile.renameTo(newfile); 
            } 
        }else{
            System.out.println("���ļ���;��ļ�����ͬ...");
        }
    }
	
	private static String numberToWord(int num) {
		String str = "";
		switch (num) {
		case 1:
			str = "ONE";
			break;
		case 2:
			str = "TWO";
			break;
		case 3:
			str = "THREE";
			break;
		case 4:
			str = "FOUR";
			break;
		case 5:
			str = "FIVE";
			break;
		case 6:
			str = "SIX";
			break;
		case 7:
			str = "SEVEN";
			break;
		case 8:
			str = "EIGHT";
			break;
		case 9:
			str = "NINE";
			break;
		case 10:
			str = "TEN";
			break;
		case 11:
			str = "ELEVEN";
			break;
		case 12:
			str = "TWELVE";
			break;
		case 13:
			str = "THIRTEEN";
			break;
		case 14:
			str = "FOURTEEN";
			break;
		case 15:
			str = "FIFTEEN";
			break;
		case 16:
			str = "SIXTEEN";
			break;
		case 17:
			str = "SEVENTEEN";
			break;
		case 18:
			str = "EIGHTEEN";
			break;
		case 19:
			str = "NINETEEN";
			break;
		default:
			break;
		}
		return "(" + str + ")";
	}
}
