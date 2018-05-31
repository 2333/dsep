package com.dsep.util.crawler.spider;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

public class SiteSetting {
	public static String cookie = "CARTID=fdb420641b3527161116084fd40b4cd2;PHPSESSID=uiptchogr88h13plkhcr02dur900okk2;PLATEFORMC=ae;PLATEFORML=en;SCAUAT=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxNDcxNTA1NiIsImZpcnN0TmFtZSI6ImJldHRlciIsImxhc3ROYW1lIjoiYmVsbCIsInJvbGUiOiJTRUxMRVIiLCJzZWxsZXJJZCI6MS40NzE1MDU2RTcsImdyb3VwSWQiOjEuNDcxNTA1NkU3LCJpc3MiOiJTT1VRIiwiaWQiOjEuNDcxNTA1NkU3LCJleHAiOjE1MTQyMTE1OTMsInVzZXJOYW1lIjoiQmV0dGVyYmVsbCIsImlhdCI6MTUxNDIwNzk5M30.VenEbUjuP1WMHJZ4l3ibEMmTax59ZRFo7qtFgfqbUffx9z_TCp0L7jDqUxiwX1SeJBRGkvWqtvh2qT4Pd861QURy7tdcyrGOpWfYSs9yC-bhUvMFkXtKlLbGVQOZsqy3FWEc4ECDnmhdlpNsh09yFzDYMIgYSHei_wFUICUL_tgm_UDAVW66lTRNrX-4tVGFAI_kGJ6g1AZwvEGiX7ltJcTlm8D5S_20ERCdMxEr2mPRckciZrcmKWiouS1Rv8sMnSneaXJsJqRc3ZX3_S7bE8LncDhwghVAwWcMglK9wELhVZ14SfeqjAf28OkIal2s2Liy0cCbGsNgya3kqJaDko5kpAHRDPK571LHaxvi-9vE1fX8quE01u5UIkXwJa4srS0RfuW62RJLaGcLhMxk1Qph-St9iFeXN3Rt5PpqQtNLGNo3ccGfHS76x6ly9ZBJz3jyDuZONfqKetvk1FbphscnTabijXr541tTTQWmNcKFpppUQyj3X84YNlYj1dWhonCgQwkziAjQSrMm0so24DifEboQZ9kefK_DWY_4dA80_S3n6t8ya1_ac7335sSOFum5UUF_mMyX5hztby4qbHm7-XRarZceOesyPpg2X_GQg5sQGarkfFU65_QZfUMJJckjsq-1pu-Ds_ff80axMcyZpk6maMqMOSeBIG3qdqg;SCAURT=949dfbcdc1b6ebf27df6ea594c2219e0;SCAUTT=BEARER;SCXAT=r6uP1lcVFoG7QEGRQNLx2S4QmkSdjbKo1AodGKF7g+1514207993717;_dc_gtm_UA-31806200-1=1;_ga=GA1.1.1601367483.1514207922;_gid=GA1.1.35464425.1514207922;ab.storage.deviceId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%2290eec6ae-3741-d7c5-7f9a-55dd7d4cf413%22%2C%22c%22%3A1514207921237%2C%22l%22%3A1514207921237%7D;ab.storage.deviceId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%226f42e151-c9ff-57f9-0aa7-0aafc8035606%22%2C%22c%22%3A1514207994858%2C%22l%22%3A1514207994858%7D;ab.storage.sessionId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%225ecff1be-231f-f502-4983-ae544c59830c%22%2C%22e%22%3A1514209790687%2C%22c%22%3A1514207990488%2C%22l%22%3A1514207990687%7D;ab.storage.sessionId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%227aff0b5b-6349-2911-e448-3a6cd9ebf5d7%22%2C%22e%22%3A1514209794847%2C%22c%22%3A1514207994847%2C%22l%22%3A1514207994847%7D;ab.storage.userId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%2214715056%22%2C%22c%22%3A1514207990485%2C%22l%22%3A1514207990485%7D;c_Ident=15142079213077;cmgvo=Typed%2FBookmarkedTyped%2FBookmarkedundefined;customer_logged_in=0;idc=14715056;is_logged_in=1;optimizelyBuckets=%7B%7D;optimizelyEndUserId=oeu1514207993884r0.005135532235726714;optimizelyPendingLogEvents=%5B%5D;optimizelySegments=%7B%22182429971%22%3A%22direct%22%2C%22182476429%22%3A%22false%22%2C%22182494213%22%3A%22unknown%22%7D;remember_key=e85844f121b84ede516668bb9b28cd37;s_campaign=NA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3Aae%3Aen%3ANA%3ANA%3ADirect%3Afree;s_cc=true;s_depth=2;s_dl=1;s_ev21=%5B%5B%27Typed%2FBookmarked%27%2C%271514207963780%27%5D%5D;s_ev22=%5B%5B%27Typed%2FBookmarked%253A%2520LoginPage%27%2C%271514207963781%27%5D%5D;s_fid=6B503A857DF66665-079471104A62E3E1;s_nr_lifetime=1514207995663-New;s_nr_quarter=1514207995665-New;s_nr_year=1514207995664-New;s_ppv=AccountPage%2C19%2C19%2C300%2C400%2C300%2C1440%2C900%2C1%2CP;s_ppvl=LoginPage%2C34%2C34%2C300%2C400%2C300%2C1440%2C900%2C1%2CP;s_source=%5B%5BB%5D%5D;s_sq=%5B%5BB%5D%5D;s_vs=1;";
	//public static String testtt = "c_Ident=15122621543630; __gads=ID=c7e107b43dcc2259:T=1512262159:S=ALNI_MZnfGHNFgkCy7PWkHvPONMQ18080Q; ab.storage.userId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%2215436469%22%2C%22c%22%3A1512262222695%2C%22l%22%3A1512262222695%7D; optimizelyEndUserId=oeu1512262310888r0.5619670834946131; ab.storage.deviceId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%22ed102d83-ffa8-435d-87fd-7b1260c424b9%22%2C%22c%22%3A1512262328829%2C%22l%22%3A1512262328829%7D; ab.storage.deviceId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%225294b992-466a-1132-0fb2-6fb29b721223%22%2C%22c%22%3A1512262398021%2C%22l%22%3A1512262398021%7D; idc=15436469; is_logged_in=1; s_ev21=%5B%5B%27Typed%2FBookmarked%27%2C%271512262155451%27%5D%2C%5B%27Typed%2FBookmarked%27%2C%271513487244280%27%5D%5D; s_ev22=%5B%5B%27Typed%2FBookmarked%253A%2520HomePage%27%2C%271512262155451%27%5D%2C%5B%27Typed%2FBookmarked%253A%2520HomePage%27%2C%271513487244281%27%5D%5D; cmgvo=Typed%2FBookmarkedTyped%2FBookmarkedundefined; formisimo=ld5mJ67sJByjkdGiZsp3YZuyPJ; ab.storage.userId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%2215436469%22%2C%22c%22%3A1513513455713%2C%22l%22%3A1513513455713%7D; ab.storage.sessionId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%228b66ff25-edd0-927a-bea2-8d37661520cf%22%2C%22e%22%3A1513670180219%2C%22c%22%3A1513668380220%2C%22l%22%3A1513668380220%7D; s_ppvl=AccountPage%2C68%2C68%2C745%2C1440%2C745%2C1440%2C900%2C1%2CP; s_campaign=NA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3Aae%3Aen%3ANA%3ANA%3ATab-Bookarked%3Afree; _ga=GA1.2.1123207084.1512262154; remember_key=bcda00a12b66ce6845808f547578d5f8; PHPSESSID=fnscl66pnmtk93ssb797okaq4v288ldo; SCAUAT=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxNTQzNjQ2OSIsImZpcnN0TmFtZSI6IlRhbWlhIiwibGFzdE5hbWUiOiJ6aGFuZyIsInJvbGUiOiJTRUxMRVIiLCJzZWxsZXJJZCI6MS41NDM2NDY5RTcsImdyb3VwSWQiOjEuNTQzNjQ2OUU3LCJpc3MiOiJTT1VRIiwiaWQiOjEuNTQzNjQ2OUU3LCJleHAiOjE1MTM5NDAzNjcsInVzZXJOYW1lIjoiVGFtaWFhIiwiaWF0IjoxNTEzOTM2NzY3fQ.Em5yeMQro5OUnE2TarD2lcVveooPahksLRHtJOAnVHQoAahp3Q6JEyJDJ3XkAGrNtDfvVt1EJ4eU7bxFlMcGhTSHT0DzgE97ETiAgJCP3tudcEhB9vZTkiUKJdf0PBfrG1Tw3f7uatxk1MfQpF9RSu8zALyDxE8dRHL0O9GLUvu4aYn4ao0zD7XS0HRrfsjNJjF8N_xHSb31gUCEbo-ObLUiN9Yi8dVAABqDbBKYzASyoNkHpcRYhuZbmWQoqDjIdFp1KUvY6FLrx1KFnySbEFUG6XCR28OH3w6Ye-uR6jycSeXzMslwHJ-9-Fnr2YKwJTWhmGQ__dZWoWsirahVuTMPi_7NvtuAHdWdKsDRDlvPCkrE197_GYevORDhrgQy33kkWPOCQFmTyDS-ouCBGY1AVc8SMkcBp7_CeD1zcJ-FCLQagYr-GKIamPYD7plHCd3_BbHSbSPV1vENU932GEIzmrp4xk-c7D_7FgT-qBaSMq_hRlHUe1qSsJEZNjrIqVgRUW86TXIfDx4nZ_2PPEvJeXAJJLVnz1u13NLeYUrbha12qG2HfdHGiTXrHkORBMsUVXCp9GVxMMy4sWaBM9eFcu_fUAy4b3bfpYROHm87YvV1LyohxdIQVLBOMS0tdplyQNYjE1fi8zB9r1xk7FH6RpTvLdXXz5__UDiIjYM; SCAURT=5ae3dd59af55f9d3ecb5f084c9c1729c; SCAUTT=BEARER; SCXAT=KKzTEV3JbCROeUBXRODwkCduAcefrKCqbRfZudf0ijE+1513939373859; s_cc=true; s_fid=6EDC0491BEA2E2D2-0B9406464156F9EB; s_source=%5B%5BB%5D%5D; s_nr_lifetime=1513939382821-Repeat; s_nr_year=1513939382822-Repeat; s_nr_quarter=1513939382823-Repeat; s_sq=%5B%5BB%5D%5D; optimizelySegments=%7B%22182429971%22%3A%22referral%22%2C%22182476429%22%3A%22false%22%2C%22182494213%22%3A%22gc%22%7D; optimizelyBuckets=%7B%7D; optimizelyPendingLogEvents=%5B%5D; _ga=GA1.1.1123207084.1512262154; _gid=GA1.1.1185675207.1513780340; ab.storage.sessionId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%22107b75e1-91b1-e129-8195-61fd9d67d89d%22%2C%22e%22%3A1513941501753%2C%22c%22%3A1513939373539%2C%22l%22%3A1513939701753%7D; s_ppv=LoginPage%2C92%2C92%2C745%2C1440%2C745%2C1440%2C900%2C1%2CP; PLATEFORMC=ae; PLATEFORML=en; CARTID=34fdbd3ff4dc7e7aab16e14b78ea5980";
	public static String token = cookie.substring(cookie.indexOf("SCXAT=") + 6, cookie.indexOf("+"));
	
	public static String imgDownloadUrl = "https://cf1.s3.souqcdn.com/";
	// key is ean, value is myEan
	public static String myEanInfo = "D:/MYSOUQ/develop/myEanInfo.properties";
	public static String usernameAndPassword = "D:/MYSOUQ/develop/usernameAndPassword.properties";
	public static String key = "D:/MYSOUQ/develop/key";
	public static JTextArea jtb = new JTextArea(1, 1);
	
	public static int size = 0;
	
	public static int totalElements = 0;
	
	public static int totalPages = 0;
	// page number starts with 0
	public static int number = 0;
	
	public static int totalCounter = 0;
	
	
	public static String username = "";
	public static String password = "";
	public static String storeFolderPath = "";
	// folder structure:
	/***
	 * D:/
	 * ----MYSOUQ/
	 * --------data1/
	 * ------------orderId1/
	 * ----------------xxxxx.orderIdStr
	 * ----------------EAN.jpg
	 * ----------------orderId.html
	 * ------------orderId2/
	 * ------------2selectedOrders/
	 * ------------3printOrders/
	 * 
	 */
	public static String D_MYSOUQ = "D:/MYSOUQ/";
	public static String D_MYSOUQ_DEVE = "D:/MYSOUQ/develop/";
	public static String D_MYSOUQDate = "D:/MYSOUQ/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
	public static String D_MYSOUQDate1downloadOrders = "D:/MYSOUQ/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/1downloadOrders/";
	public static String D_MYSOUQDate2selectedOrders = "D:/MYSOUQ/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/2selectedOrders/";
	
	public static String getStoreFolderPath() {
		return storeFolderPath;
	}
	
	public static Logger logger = null;
}
