package com.dsep.util.crawler.zFire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class SessionParser {
	public static void main(String[] args) {
		String cookies = "CARTID=fdb420641b3527161116084fd40b4cd2;PHPSESSID=9md2u4p43jq08bhucmp038jerblm4lgh;PLATEFORMC=ae;PLATEFORML=en;SCAUAT=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxNDcxNTA1NiIsImZpcnN0TmFtZSI6ImJldHRlciIsImxhc3ROYW1lIjoiYmVsbCIsInJvbGUiOiJTRUxMRVIiLCJzZWxsZXJJZCI6MS40NzE1MDU2RTcsImdyb3VwSWQiOjEuNDcxNTA1NkU3LCJpc3MiOiJTT1VRIiwiaWQiOjEuNDcxNTA1NkU3LCJleHAiOjE1MTU1NDkzNTksInVzZXJOYW1lIjoiQmV0dGVyYmVsbCIsImlhdCI6MTUxNTU0NTc1OX0.anpg2OpxJGJBcZFUWwIwDTtCu-wJPIAR47zwtn2miis1kYk5hjCPdysCrF8OPMqpAAy7cqx4ThcZFtui3rK00XtynFYVdWrGU-UNFH7D2ZPsSdLkxs9ZKf6pNcVOewVQvuqGM07z2heFOLaXSvmxo0_xf5mpMSbmWToY6zElWhaovs6nLzCW7tQbF5avzVYn9x8KKB8UWsMEcy11IKD7IIyG8QGrbH1bqPtgrD59CK-njHH3CBJl2TSFMEwe_xLcEZM7Wc5mW8l4_HKJaksotWT5FVzxMDfnAquoPqNeVDIGTIUkq4D7MJjpBhpoJ1RzAjVM4-6PKtVDGb-_-BTWTii19f2nQcYSCs2pnfKTNdWozbg-wnZ8Tsco7HX5tdilaKLyyrHQn2qUEFwi6U-aunbyBKoKksfpmRTcgWVY75a8n4vZ-XCejbO93cGFTus6D195OGaEjEqW7G5LlHl3K3ciKLXWVq-crSFmTfJYyrOUu4hKnaXC9RLAlTvx5S_KSUrmxDQr_ZbJzTDDSN6XdyScaGy8ngS7VDftowGLKOzD6mrw_2V0RGp-WUheecBHFD3ey9pb2jPcQ-yPTPhc-LCeNeBf8mXOsxlj9MaVsPZA-fgtIqu_jscOPEdLHmLg0n8dZ3DamLDDgZTl09K8VtjGZnUUMjl_4-bCaBuGYQ4;SCAURT=d8326dcf3a0072e7b8ba681ebb4aa57b;SCAUTT=BEARER;SCXAT=VmqdU4rozxbQtWOieDf1QtRRUfpDBiYgTbf1IdNk+1515545759023;VT_HDR=2;_dc_gtm_UA-31806200-1=1;_ga=GA1.2.1611079935.1515545667;_gid=GA1.2.1020243536.1515545667;ab.storage.sessionId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%228395beb2-2689-d0b6-5f17-446031d8c14e%22%2C%22e%22%3A1515547512509%2C%22c%22%3A1515545143767%2C%22l%22%3A1515545712509%7D;c_Ident=15155456682264;customer_logged_in=0;idc=14715056;is_logged_in=1;remember_key=1969902d4947448cfba7f51a0878c745;s_campaign=NA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3Aae%3Aen%3ANA%3ANA%3ATab-Bookarked%3Afree;s_cc=true;s_depth=1;s_dl=1;s_fid=521A8D8129344763-01F9FEE7FB63594D;s_nr_lifetime=1515545712618-New;s_nr_quarter=1515545712619-New;s_nr_year=1515545712619-New;s_ppv=AccountPage%2C19%2C19%2C300%2C400%2C300%2C1440%2C900%2C1%2CP;s_ppvl=%5B%5BB%5D%5D;s_source=%5B%5BB%5D%5D;s_vs=1;";
		//String cookies = "CARTID=fdb420641b3527161116084fd40b4cd2;PHPSESSID=bq5l4flnnk8h1cdiq1esbsjfhhu4g57n;PLATEFORMC=ae;PLATEFORML=en;SCAUAT=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxNDcxNTA1NiIsImZpcnN0TmFtZSI6ImJldHRlciIsImxhc3ROYW1lIjoiYmVsbCIsInJvbGUiOiJTRUxMRVIiLCJzZWxsZXJJZCI6MS40NzE1MDU2RTcsImdyb3VwSWQiOjEuNDcxNTA1NkU3LCJpc3MiOiJTT1VRIiwiaWQiOjEuNDcxNTA1NkU3LCJleHAiOjE1MTQyMDAwMDIsInVzZXJOYW1lIjoiQmV0dGVyYmVsbCIsImlhdCI6MTUxNDE5NjQwMn0.QHnjaSYI85KVclWBgiWCIpihhpU5l6hb6rKmu9aWNWQiOe6Hf-U06Jq2aF18o2AUevM89G7DyfGHSG6NDgEDHUdHLSWbVGG2puFYTf44HH0atymr_Ry9RV-qjoTfi7M6wE33d1qsFX9d18oZIt_PAGKF_4KfHFbDNIFzLRjxJdAX0Ud-DSBumnL66xLyD-7Hj0eqTxldVfFeB4rwzZTnQKfSqT-_FqbjYhfOoTna8WgMHInbLAPcz7nqRZUQMi_Xa8tNFs0OUz6kmejqbY0iWwiwRD3vmcQYmRb1GozEtlh7NRM8Gd2jVIFIctrlyeHsSTtPgmzQRY_9pgnot62HZiZcAuBsnkqPQOJsTvjZrZ4r5O6luBLAtW4zhkAk6zENUdNeWe42wucN1SdZAsxwvdEpc59DFmex3mhGqqubnpuNIfwEaeqXpu2gQnp50xEEEs3-TYwrIPTiWB3L9wvPt2qfbQTeMcSoPn5gSsN7j98wtc_zZBKXic28Es0mIaOLYAVDz03wUnNIfJT5n3x9TvgNdWaFmA_u5AA2QNoSaDT8UPQ8Dr-Si3GMCrGlWy3Urs02ffd4xpgUWN1wHuDb08AE9MTT3iyifsUmJGLgJc773JWCeROeQ3NG1_eObkf0BbNiexbVpy5JEvyYrJi1LdTum7c2in-zU9YFldCrNF0;SCAURT=8b9f2c73f32efa02ae6977944b231d70;SCAUTT=BEARER;SCXAT=5bfyBbeJv7NjliBo9w1OdvGWoG1VoBejdUCWM4ZPw+1514196402087;_dc_gtm_UA-31806200-1=1;_ga=GA1.1.1886280074.1514196394;_gat=1;_gid=GA1.1.1065083838.1514196394;ab.storage.deviceId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%220ced8526-ac43-e348-8632-5f28de8791c3%22%2C%22c%22%3A1514196393693%2C%22l%22%3A1514196393693%7D;ab.storage.deviceId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%228340a0e6-2755-41af-e0da-1a7a32818310%22%2C%22c%22%3A1514196404330%2C%22l%22%3A1514196404330%7D;ab.storage.sessionId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%2211e86242-b73a-e744-c27b-74bde2ff766a%22%2C%22e%22%3A1514198199740%2C%22c%22%3A1514196399506%2C%22l%22%3A1514196399740%7D;ab.storage.sessionId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%22619757eb-05b6-72a0-869d-da4998b78bd2%22%2C%22e%22%3A1514198204325%2C%22c%22%3A1514196404325%2C%22l%22%3A1514196404325%7D;ab.storage.userId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%2214715056%22%2C%22c%22%3A1514196399504%2C%22l%22%3A1514196399504%7D;c_Ident=15141963925640;cmgvo=Typed%2FBookmarkedTyped%2FBookmarkedundefined;customer_logged_in=0;idc=14715056;is_logged_in=1;optimizelyBuckets=%7B%7D;optimizelyEndUserId=oeu1514196403189r0.4829042342470711;optimizelyPendingLogEvents=%5B%5D;optimizelySegments=%7B%22182429971%22%3A%22direct%22%2C%22182476429%22%3A%22false%22%2C%22182494213%22%3A%22gc%22%7D;remember_key=2941451204343ef5705b83b52f8a373d;s_campaign=NA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3Aae%3Aen%3ANA%3ANA%3ADirect%3Afree;s_cc=true;s_depth=2;s_dl=1;s_ev21=%5B%5B%27Typed%2FBookmarked%27%2C%271514196395359%27%5D%5D;s_ev22=%5B%5B%27Typed%2FBookmarked%253A%2520LoginPage%27%2C%271514196395360%27%5D%5D;s_fid=7222894EF39858C0-19FB26421EF825D4;s_nr_lifetime=1514196405422-New;s_nr_quarter=1514196405423-New;s_nr_year=1514196405423-New;s_ppv=AccountPage%2C63%2C63%2C708%2C1034%2C708%2C1440%2C900%2C1%2CP;s_ppvl=LoginPage%2C88%2C88%2C708%2C1034%2C708%2C1440%2C900%2C1%2CP;s_source=%5B%5BB%5D%5D;s_sq=%5B%5BB%5D%5D;s_vs=1";
		cookies = cookies.replaceAll("domain=sell.souq.com", "").replaceAll("domain=.souq.com", "");
		
		//cookies = cookies.replaceAll("", "");
		StringTokenizer st = new StringTokenizer(cookies,";");
		List<String> list = new ArrayList<String>();
		while(st.hasMoreTokens() ){
            //System.out.println(st.nextToken().trim());
            list.add(st.nextToken().trim());
        }
		
		Collections.sort(list, new SortByAlph());
		HashMap<String, String> map = new HashMap<String, String>();
		Set<String> set = new HashSet<String>();
		//System.out.println("====================================");
		String str = "";
		for (String ele : list) {
			
			System.out.println(ele);
			if (ele.equals("") || ele.equals(null) || ele.equals("secure")) continue;
			//System.out.println("ele=" + ele);
			String[] xs = ele.split("=");
			
			//System.out.println(".addCookie(\"" + xs[0] + "\", \""+xs[1]+"\")");
			System.out.println(xs[0] + "=" + xs[1]);
			if (xs[0].equals("expires")) {
				
			} else if (xs[0].equals("path")) {
				
			} else {
				if (set.contains(xs[0])) {
					
				} else {
					set.add(xs[0]);
					str += (xs[0] + "=" + xs[1] + ";");
				}
			}
		}
		System.out.println(str);
		
	}
	
	public static String parseCookieStr(String cookies) {
		cookies = cookies.replaceAll("domain=sell.souq.com", "").replaceAll("domain=.souq.com", "");
		
		//cookies = cookies.replaceAll("", "");
		StringTokenizer st = new StringTokenizer(cookies,";");
		List<String> list = new ArrayList<String>();
		while(st.hasMoreTokens() ){
            //System.out.println(st.nextToken().trim());
            list.add(st.nextToken().trim());
        }
		
		Collections.sort(list, new SortByAlph());
		HashMap<String, String> map = new HashMap<String, String>();
		Set<String> set = new HashSet<String>();
		//System.out.println("====================================");
		String str = "";
		for (String ele : list) {
			
			System.out.println(ele);
			if (ele.equals("") || ele.equals(null) || ele.equals("secure")) continue;
			//System.out.println("ele=" + ele);
			String[] xs = ele.split("=");
			
			//System.out.println(".addCookie(\"" + xs[0] + "\", \""+xs[1]+"\")");
			System.out.println(xs[0] + "=" + xs[1]);
			if (xs[0].equals("expires")) {
				
			} else if (xs[0].equals("path")) {
				
			} else {
				if (set.contains(xs[0])) {
					
				} else {
					set.add(xs[0]);
					str += (xs[0] + "=" + xs[1] + ";");
				}
			}
		}
		System.out.println(str);
		return str;
	}
}

class SortByAlph implements Comparator {
    public int compare(Object o1, Object o2) {
     String s1 = (String) o1;
     String s2 = (String) o2;
     return s1.compareTo(s2);
    }
}




