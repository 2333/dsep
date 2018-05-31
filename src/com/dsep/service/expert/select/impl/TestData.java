package com.dsep.service.expert.select.impl;

import java.util.HashMap;
import java.util.Map;

// 参评:1~200,330~400
// 授权:1~250,350~700
// 未参加:251~329
// 211高校(211所)：
// 985高校(100所)：
// 研究生高校(400所)：
public class TestData {
	// 模拟.本轮评估所有学科
	public static String[] allDisc = new String[] { "0101", "0201", "0202",
			"0301", "0302", "0303", "0304", "0305", "0401", "0501", "0502",
			"0503", "0504", "0601", "0701", "0702", "0703", "0705", "0706",
			"0709", "0710", "0712", "0801", "0802", "0805", "0807", "0808",
			"0809", "0810", "0811", "0812", "0814", "0816", "0817", "0822",
			"0823", "0825", "0827", "0828", "0829", "0830", "0831", "0832",
			"0835", "0903", "0904", "0907", "1005", "1007", "1201", "1202",
			"1203", "1204", "1303", "1304", "1305" };

	public static String[] keyDisUnits1 = new String[] { "10001", "10002",
			"10006" };
	public static String[] keyDisUnits2 = new String[] { "10001", "10003",
			"10007", "10010" };
	public static String[] keyDisUnits3 = new String[] { "10001", "10002",
			"10007" };

	// 国家重点学科(含培育学科)对应的学校
	public static Map<String, String[]> keyDis = new HashMap<String, String[]>() {
		{
			put("0101", keyDisUnits1);
			put("0501", keyDisUnits2);
			put("0835", keyDisUnits3);
		}
	};

	
	public static String[] docDisUnits1 = new String[] { "10001", "10006" };
	public static String[] docDisUnits2 = new String[] { "10001", "10002",
			"82201" };
	public static String[] docDisUnits3 = new String[] { "10002", "10003",
			"10006" };
	// 博士一级学科对应的学校
	public static Map<String, String[]> docDis = new HashMap<String, String[]>() {
		{
			put("0101", docDisUnits1);
			put("0501", docDisUnits2);
			put("0829", docDisUnits3);
		}
	};
	
	// 全部学位授予单位
	public static String[] allUnits = new String[] { "10001", "10002", "10003",
			"10004", "10005", "10006", "10007", "10008", "10009", "10010",
			"10011", "10012", "10013", "10014", "10015", "10016", "10017",
			"10018", "10019", "10020", "10021", "10022", "10023", "10024",
			"10025", "10026", "10027", "10028", "10029", "10030", "10031",
			"10032", "10033", "10034", "10035", "10036", "10037", "10038",
			"10039", "10040", "10041", "10042", "10043", "10044", "10045",
			"10046", "10047", "10048", "10049", "10050", "10051", "10052",
			"10053", "10054", "10055", "10056", "10057", "10058", "10059",
			"10060", "10061", "10062", "10063", "10064", "10065", "10066",
			"10067", "10068", "10069", "10070", "10071", "10072", "10073",
			"10074", "10075", "10076", "10077", "10078", "10079", "10080",
			"10081", "10082", "10083", "10084", "10085", "10086", "10087",
			"10088", "10089", "10090", "10091", "10092", "10093", "10094",
			"10095", "10096", "10097", "10098", "10099", "10100", "10101",
			"10102", "10103", "10104", "10105", "10106", "10107", "10108",
			"10109", "10110", "10111", "10112", "10113", "10114", "10115",
			"10116", "10117", "10118", "10119", "10120", "10121", "10122",
			"10123", "10124", "10125", "10126", "10127", "10128", "10129",
			"10130", "10131", "10132", "10133", "10134", "10135", "10136",
			"10137", "10138", "10139", "10140", "10141", "10142", "10143",
			"10144", "10145", "10146", "10147", "10148", "10149", "10150",
			"10151", "10152", "10153", "10154", "10155", "10156", "10157",
			"10158", "10159", "10160", "10161", "10162", "10163", "10164",
			"10165", "10166", "10167", "10168", "10169", "10170", "10171",
			"10172", "10173", "10174", "10175", "10176", "10177", "10178",
			"10179", "10180", "10181", "10182", "10183", "10184", "10185",
			"10186", "10187", "10188", "10189", "10190", "10191", "10192",
			"10193", "10194", "10195", "10196", "10197", "10198", "10199",
			"10200", "10201", "10202", "10203", "10204", "10205", "10206",
			"10207", "10208", "10209", "10210", "10211", "10212", "10213",
			"10214", "10215", "10216", "10217", "10218", "10219", "10220",
			"10221", "10222", "10223", "10224", "10225", "10226", "10227",
			"10228", "10229", "10230", "10231", "10232", "10233", "10234",
			"10235", "10236", "10237", "10238", "10239", "10240", "10241",
			"10242", "10243", "10244", "10245", "10246", "10247", "10248",
			"10249", "10250", "10251", "10252", "10253", "10254", "10255",
			"10256", "10257", "10258", "10259", "10260", "10261", "10262",
			"10263", "10264", "10265", "10266", "10267", "10268", "10269",
			"10270", "10271", "10272", "10273", "10274", "10275", "10276",
			"10277", "10278", "10279", "10280", "10281", "10282", "10283",
			"10284", "10285", "10286", "10287", "10288", "10289", "10290",
			"10291", "10292", "10293", "10294", "10295", "10296", "10297",
			"10298", "10299", "10300", "10301", "10302", "10303", "10304",
			"10305", "10306", "10307", "10308", "10309", "10310", "10311",
			"10312", "10313", "10314", "10315", "10316", "10317", "10318",
			"10319", "10320", "10321", "10322", "10323", "10324", "10325",
			"10326", "10327", "10328", "10329", "10330", "10331", "10332",
			"10333", "10334", "10335", "10336", "10337", "10338", "10339",
			"10340", "10341", "10342", "10343", "10344", "10345", "10346",
			"10347", "10348", "10349", "10350", "10351", "10352", "10353",
			"10354", "10355", "10356", "10357", "10358", "10359", "10360",
			"10361", "10362", "10363", "10364", "10365", "10366", "10367",
			"10368", "10369", "10370", "10371", "10372", "10373", "10374",
			"10375", "10376", "10377", "10378", "10379", "10380", "10381",
			"10382", "10383", "10384", "10385", "10386", "10387", "10388",
			"10389", "10390", "10391", "10392", "10393", "10394", "10395",
			"10396", "10397", "10398", "10399", "10400", "10401", "10402",
			"10403", "10404", "10405", "10406", "10407", "10408", "10409",
			"10410", "10411", "10412", "10413", "10414", "10415", "10416",
			"10417", "10418", "10419", "10420", "10421", "10422", "10423",
			"10424", "10425", "10426", "10427", "10428", "10429", "10430",
			"10431", "10432", "10433", "10434", "10435", "10436", "10437",
			"10438", "10439", "10440", "10441", "10442", "10443", "10444",
			"10445", "10446", "10447", "10448", "10449", "10450", "10451",
			"10452", "10453", "10454", "10455", "10456", "10457", "10458",
			"10459", "10460", "10461", "10462", "10463", "10464", "10465",
			"10466", "10467", "10468", "10469", "10470", "10471", "10472",
			"10473", "10474", "10475", "10476", "10477", "10478", "10479",
			"10480", "10481", "10482", "10483", "10484", "10485", "10486",
			"10487", "10488", "10489", "10490", "10491", "10492", "10493",
			"10494", "10495", "10496", "10497", "10498", "10499", "10500",
			"10501", "10502", "10503", "10504", "10505", "10506", "10507",
			"10508", "10509", "10510", "10511", "10512", "10513", "10514",
			"10515", "10516", "10517", "10518", "10519", "10520", "10521",
			"10522", "10523", "10524", "10525", "10526", "10527", "10528",
			"10529", "10530", "10531", "10532", "10533", "10534", "10535",
			"10536", "10537", "10538", "10539", "10540", "10541", "10542",
			"10543", "10544", "10545", "10546", "10547", "10548", "10549",
			"10550", "10551", "10552", "10553", "10554", "10555", "10556",
			"10557", "10558", "10559", "10560", "10561", "10562", "10563",
			"10564", "10565", "10566", "10567", "10568", "10569", "10570",
			"10571", "10572", "10573", "10574", "10575", "10576", "10577",
			"10578", "10579", "10580", "10581", "10582", "10583", "10584",
			"10585", "10586", "10587", "10588", "10589", "10590", "10591",
			"10592", "10593", "10594", "10595", "10596", "10597", "10598",
			"10599", "10600", "10601", "10602", "10603", "10604", "10605",
			"10606", "10607", "10608", "10609", "10610", "10611", "10612",
			"10613", "10614", "10615", "10616", "10617", "10618", "10619",
			"10620", "10621", "10622", "10623", "10624", "10625", "10626",
			"10627", "10628", "10629", "10630", "10631", "10632", "10633",
			"10634", "10635", "10636", "10637", "10638", "10639", "10640",
			"10641", "10642", "10643", "10644", "10645", "10646", "10647",
			"10648", "10649", "10650", "10651", "10652", "10653", "10654",
			"10655", "10656", "10657", "10658", "10659", "10660", "10661",
			"10662", "10663", "10664", "10665", "10666", "10667", "10668",
			"10669", "10670", "10671", "10672", "10673", "10674", "10675",
			"10676", "10677", "10678", "10679", "10680", "10681", "10682",
			"10683", "10684", "10685", "10686", "10687", "10688", "10689",
			"10690", "10691", "10692", "10693", "10694", "10695", "10696",
			"10697", "10698", "10699", "10700", "82201" };

	// 研究生院高校
	public static String[] graduateUnits = new String[] { "10001", "10002",
			"10003", "10005", "10006", "10007", "10008", "10009", "10010",
			"10012", "10013", "10015", "10016", "10018", "10019", "10021",
			"10022", "10024", "10025", "10026", "10027", "10029", "10030",
			"10031", "10034", "10035", "10036", "10037", "10038", "10039",
			"10041", "10044", "10045", "10047", "10048", "10049", "10050",
			"10051", "10053", "10054", "10056", "10057", "10058", "10061",
			"10063", "10067", "10068", "10069", "10073", "10075", "10076",
			"10077", "10078", "10079", "10081", "10082", "10083", "10084",
			"10086", "10087", "10088", "10089", "10092", "10094", "10095",
			"10096", "10097", "10101", "10103", "10108", "10110", "10112",
			"10114", "10115", "10116", "10118", "10119", "10120", "10121",
			"10122", "10124", "10125", "10130", "10136", "10138", "10140",
			"10141", "10142", "10146", "10147", "10148", "10150", "10151",
			"10153", "10154", "10155", "10156", "10157", "10158", "10161",
			"10162", "10165", "10168", "10169", "10171", "10173", "10174",
			"10176", "10177", "10178", "10181", "10184", "10187", "10188",
			"10190", "10191", "10194", "10198", "10199", "10200", "10202",
			"10204", "10205", "10206", "10209", "10210", "10211", "10212",
			"10213", "10214", "10215", "10216", "10217", "10218", "10219",
			"10221", "10222", "10223", "10224", "10225", "10226", "10227",
			"10229", "10230", "10231", "10233", "10234", "10238", "10240",
			"10242", "10244", "10245", "10246", "10247", "10248", "10250",
			"10252", "10253", "10254", "10255", "10259", "10260", "10261",
			"10263", "10264", "10270", "10273", "10274", "10275", "10277",
			"10281", "10284", "10285", "10286", "10287", "10288", "10289",
			"10291", "10292", "10293", "10297", "10298", "10299", "10302",
			"10303", "10306", "10307", "10308", "10309", "10314", "10315",
			"10316", "10317", "10319", "10320", "10321", "10328", "10329",
			"10330", "10331", "10332", "10338", "10339", "10342", "10343",
			"10346", "10347", "10348", "10349", "10350", "10351", "10352",
			"10353", "10355", "10356", "10358", "10361", "10363", "10366",
			"10367", "10368", "10370", "10371", "10372", "10373", "10377",
			"10378", "10382", "10384", "10386", "10388", "10389", "10390",
			"10391", "10392", "10393", "10395", "10396", "10399", "10403",
			"10407", "10411", "10412", "10415", "10416", "10417", "10418",
			"10419", "10420", "10421", "10422", "10423", "10432", "10433",
			"10434", "10436", "10437", "10438", "10443", "10444", "10445",
			"10446", "10447", "10448", "10449", "10451", "10452", "10453",
			"10454", "10457", "10458", "10459", "10461", "10462", "10464",
			"10465", "10466", "10468", "10470", "10471", "10473", "10474",
			"10476", "10477", "10478", "10479", "10481", "10484", "10485",
			"10486", "10487", "10488", "10490", "10491", "10492", "10493",
			"10494", "10495", "10497", "10498", "10499", "10501", "10502",
			"10503", "10505", "10506", "10512", "10515", "10522", "10524",
			"10525", "10526", "10527", "10528", "10530", "10531", "10532",
			"10533", "10536", "10538", "10540", "10541", "10542", "10543",
			"10545", "10546", "10548", "10551", "10554", "10555", "10556",
			"10557", "10558", "10559", "10561", "10565", "10569", "10570",
			"10571", "10573", "10574", "10579", "10581", "10583", "10585",
			"10586", "10588", "10589", "10591", "10596", "10597", "10599",
			"10600", "10604", "10607", "10609", "10610", "10612", "10614",
			"10615", "10618", "10619", "10623", "10624", "10628", "10629",
			"10631", "10633", "10634", "10635", "10636", "10639", "10641",
			"10644", "10649", "10650", "10652", "10655", "10656", "10657",
			"10659", "10663", "10666", "10668", "10669", "10670", "10673",
			"10676", "10678", "10680", "10681", "10684", "10685", "10686",
			"10688", "10689", "10690", "10693", "10694", "10697", "10698" };

	public static String[] _211Units = new String[] { "10002", "10004",
			"10009", "10011", "10017", "10018", "10019", "10025", "10026",
			"10032", "10033", "10040", "10041", "10046", "10047", "10049",
			"10056", "10057", "10063", "10064", "10070", "10071", "10078",
			"10079", "10085", "10086", "10094", "10095", "10099", "10101",
			"10102", "10109", "10110", "10115", "10116", "10117", "10123",
			"10124", "10131", "10133", "10139", "10140", "10147", "10148",
			"10153", "10154", "10155", "10161", "10162", "10163", "10168",
			"10169", "10171", "10176", "10178", "10185", "10186", "10191",
			"10192", "10193", "10200", "10201", "10207", "10208", "10214",
			"10216", "10217", "10223", "10224", "10230", "10231", "10238",
			"10239", "10244", "10245", "10246", "10253", "10255", "10261",
			"10262", "10268", "10269", "10276", "10277", "10283", "10284",
			"10291", "10293", "10297", "10298", "10300", "10307", "10308",
			"10314", "10315", "10321", "10322", "10329", "10330", "10336",
			"10338", "10345", "10346", "10351", "10352", "10353", "10359",
			"10360", "10361", "10366", "10367", "10368", "10374", "10375",
			"10383", "10384", "10388", "10390", "10391", "10398", "10399",
			"10404", "10405", "10406", "10412", "10413", "10415", "10420",
			"10422", "10428", "10429", "10436", "10437", "10442", "10443",
			"10444", "10451", "10452", "10458", "10465", "10467", "10468",
			"10474", "10475", "10481", "10482", "10489", "10490", "10495",
			"10496", "10497", "10505", "10506", "10512", "10513", "10519",
			"10520", "10527", "10528", "10534", "10535", "10542", "10544",
			"10548", "10550", "10551", "10558", "10559", "10565", "10566",
			"10572", "10573", "10580", "10582", "10587", "10589", "10596",
			"10597", "10602", "10603", "10604", "10610", "10611", "10612",
			"10617", "10618", "10619", "10625", "10627", "10634", "10635",
			"10640", "10641", "10642", "10649", "10650", "10655", "10656",
			"10657", "10663", "10664", "10666", "10670", "10672", "10673",
			"10679", "10680", "10687", "10688", "10693", "10694", "10695" };

	public static String[] _985Units = new String[] { "10002", "10004",
			"10011", "10019", "10026", "10040", "10041", "10056", "10057",
			"10064", "10079", "10094", "10095", "10102", "10109", "10110",
			"10117", "10124", "10131", "10133", "10147", "10148", "10155",
			"10162", "10163", "10171", "10178", "10185", "10186", "10200",
			"10201", "10208", "10217", "10224", "10238", "10239", "10253",
			"10255", "10262", "10277", "10291", "10293", "10300", "10307",
			"10308", "10315", "10329", "10330", "10345", "10346", "10353",
			"10360", "10361", "10368", "10375", "10383", "10384", "10398",
			"10399", "10406", "10413", "10415", "10422", "10429", "10436",
			"10437", "10451", "10452", "10468", "10475", "10489", "10490",
			"10505", "10506", "10513", "10528", "10542", "10544", "10551",
			"10558", "10559", "10566", "10573", "10580", "10582", "10596",
			"10597", "10604", "10611", "10612", "10619", "10627", "10634",
			"10635", "10649", "10650", "10657", "10666", "10673", "10687",
			"10688", "82201" };
	
	// 参评学校
	public static String[] attendUnits = new String[] { "10001", "10002",
			"10003", "10004", "10005", "10006", "10007", "10008", "10009",
			"10010", "10011", "10012", "10013", "10014", "10015", "10016",
			"10017", "10018", "10019", "10020", "10021", "10022", "10023",
			"10024", "10025", "10026", "10027", "10028", "10029", "10030",
			"10031", "10032", "10033", "10034", "10035", "10036", "10037",
			"10038", "10039", "10040", "10041", "10042", "10043", "10044",
			"10045", "10046", "10047", "10048", "10049", "10050", "10051",
			"10052", "10053", "10054", "10055", "10056", "10057", "10058",
			"10059", "10060", "10061", "10062", "10063", "10064", "10065",
			"10066", "10067", "10068", "10069", "10070", "10071", "10072",
			"10073", "10074", "10075", "10076", "10077", "10078", "10079",
			"10080", "10081", "10082", "10083", "10084", "10085", "10086",
			"10087", "10088", "10089", "10090", "10091", "10092", "10093",
			"10094", "10095", "10096", "10097", "10098", "10099", "10100",
			"10101", "10102", "10103", "10104", "10105", "10106", "10107",
			"10108", "10109", "10110", "10111", "10112", "10113", "10114",
			"10115", "10116", "10117", "10118", "10119", "10120", "10121",
			"10122", "10123", "10124", "10125", "10126", "10127", "10128",
			"10129", "10130", "10131", "10132", "10133", "10134", "10135",
			"10136", "10137", "10138", "10139", "10140", "10141", "10142",
			"10143", "10144", "10145", "10146", "10147", "10148", "10149",
			"10150", "10151", "10152", "10153", "10154", "10155", "10156",
			"10157", "10158", "10159", "10160", "10161", "10162", "10163",
			"10164", "10165", "10166", "10167", "10168", "10169", "10170",
			"10171", "10172", "10173", "10174", "10175", "10176", "10177",
			"10178", "10179", "10180", "10181", "10182", "10183", "10184",
			"10185", "10186", "10187", "10188", "10189", "10190", "10191",
			"10192", "10193", "10194", "10195", "10196", "10197", "10198",
			"10199", "10200", "10330", "10331", "10332", "10333", "10334",
			"10335", "10336", "10337", "10338", "10339", "10340", "10341",
			"10342", "10343", "10344", "10345", "10346", "10347", "10348",
			"10349", "10350", "10351", "10352", "10353", "10354", "10355",
			"10356", "10357", "10358", "10359", "10360", "10361", "10362",
			"10363", "10364", "10365", "10366", "10367", "10368", "10369",
			"10370", "10371", "10372", "10373", "10374", "10375", "10376",
			"10377", "10378", "10379", "10380", "10381", "10382", "10383",
			"10384", "10385", "10386", "10387", "10388", "10389", "10390",
			"10391", "10392", "10393", "10394", "10395", "10396", "10397",
			"10398", "10399", "10400", "82201" };
	
	// 授权学校
	public static String[] accridtUnits = new String[] { "10001", "10002",
			"10003", "10004", "10005", "10006", "10007", "10008", "10009",
			"10010", "10011", "10012", "10013", "10014", "10015", "10016",
			"10017", "10018", "10019", "10020", "10021", "10022", "10023",
			"10024", "10025", "10026", "10027", "10028", "10029", "10030",
			"10031", "10032", "10033", "10034", "10035", "10036", "10037",
			"10038", "10039", "10040", "10041", "10042", "10043", "10044",
			"10045", "10046", "10047", "10048", "10049", "10050", "10051",
			"10052", "10053", "10054", "10055", "10056", "10057", "10058",
			"10059", "10060", "10061", "10062", "10063", "10064", "10065",
			"10066", "10067", "10068", "10069", "10070", "10071", "10072",
			"10073", "10074", "10075", "10076", "10077", "10078", "10079",
			"10080", "10081", "10082", "10083", "10084", "10085", "10086",
			"10087", "10088", "10089", "10090", "10091", "10092", "10093",
			"10094", "10095", "10096", "10097", "10098", "10099", "10100",
			"10101", "10102", "10103", "10104", "10105", "10106", "10107",
			"10108", "10109", "10110", "10111", "10112", "10113", "10114",
			"10115", "10116", "10117", "10118", "10119", "10120", "10121",
			"10122", "10123", "10124", "10125", "10126", "10127", "10128",
			"10129", "10130", "10131", "10132", "10133", "10134", "10135",
			"10136", "10137", "10138", "10139", "10140", "10141", "10142",
			"10143", "10144", "10145", "10146", "10147", "10148", "10149",
			"10150", "10151", "10152", "10153", "10154", "10155", "10156",
			"10157", "10158", "10159", "10160", "10161", "10162", "10163",
			"10164", "10165", "10166", "10167", "10168", "10169", "10170",
			"10171", "10172", "10173", "10174", "10175", "10176", "10177",
			"10178", "10179", "10180", "10181", "10182", "10183", "10184",
			"10185", "10186", "10187", "10188", "10189", "10190", "10191",
			"10192", "10193", "10194", "10195", "10196", "10197", "10198",
			"10199", "10200", "10201", "10202", "10203", "10204", "10205",
			"10206", "10207", "10208", "10209", "10210", "10211", "10212",
			"10213", "10214", "10215", "10216", "10217", "10218", "10219",
			"10220", "10221", "10222", "10223", "10224", "10225", "10226",
			"10227", "10228", "10229", "10230", "10231", "10232", "10233",
			"10234", "10235", "10236", "10237", "10238", "10239", "10240",
			"10241", "10242", "10243", "10244", "10245", "10246", "10247",
			"10248", "10249", "10250", "10350", "10351", "10352", "10353",
			"10354", "10355", "10356", "10357", "10358", "10359", "10360",
			"10361", "10362", "10363", "10364", "10365", "10366", "10367",
			"10368", "10369", "10370", "10371", "10372", "10373", "10374",
			"10375", "10376", "10377", "10378", "10379", "10380", "10381",
			"10382", "10383", "10384", "10385", "10386", "10387", "10388",
			"10389", "10390", "10391", "10392", "10393", "10394", "10395",
			"10396", "10397", "10398", "10399", "10400", "10401", "10402",
			"10403", "10404", "10405", "10406", "10407", "10408", "10409",
			"10410", "10411", "10412", "10413", "10414", "10415", "10416",
			"10417", "10418", "10419", "10420", "10421", "10422", "10423",
			"10424", "10425", "10426", "10427", "10428", "10429", "10430",
			"10431", "10432", "10433", "10434", "10435", "10436", "10437",
			"10438", "10439", "10440", "10441", "10442", "10443", "10444",
			"10445", "10446", "10447", "10448", "10449", "10450", "10451",
			"10452", "10453", "10454", "10455", "10456", "10457", "10458",
			"10459", "10460", "10461", "10462", "10463", "10464", "10465",
			"10466", "10467", "10468", "10469", "10470", "10471", "10472",
			"10473", "10474", "10475", "10476", "10477", "10478", "10479",
			"10480", "10481", "10482", "10483", "10484", "10485", "10486",
			"10487", "10488", "10489", "10490", "10491", "10492", "10493",
			"10494", "10495", "10496", "10497", "10498", "10499", "10500",
			"10501", "10502", "10503", "10504", "10505", "10506", "10507",
			"10508", "10509", "10510", "10511", "10512", "10513", "10514",
			"10515", "10516", "10517", "10518", "10519", "10520", "10521",
			"10522", "10523", "10524", "10525", "10526", "10527", "10528",
			"10529", "10530", "10531", "10532", "10533", "10534", "10535",
			"10536", "10537", "10538", "10539", "10540", "10541", "10542",
			"10543", "10544", "10545", "10546", "10547", "10548", "10549",
			"10550", "10551", "10552", "10553", "10554", "10555", "10556",
			"10557", "10558", "10559", "10560", "10561", "10562", "10563",
			"10564", "10565", "10566", "10567", "10568", "10569", "10570",
			"10571", "10572", "10573", "10574", "10575", "10576", "10577",
			"10578", "10579", "10580", "10581", "10582", "10583", "10584",
			"10585", "10586", "10587", "10588", "10589", "10590", "10591",
			"10592", "10593", "10594", "10595", "10596", "10597", "10598",
			"10599", "10600", "10601", "10602", "10603", "10604", "10605",
			"10606", "10607", "10608", "10609", "10610", "10611", "10612",
			"10613", "10614", "10615", "10616", "10617", "10618", "10619",
			"10620", "10621", "10622", "10623", "10624", "10625", "10626",
			"10627", "10628", "10629", "10630", "10631", "10632", "10633",
			"10634", "10635", "10636", "10637", "10638", "10639", "10640",
			"10641", "10642", "10643", "10644", "10645", "10646", "10647",
			"10648", "10649", "10650", "10651", "10652", "10653", "10654",
			"10655", "10656", "10657", "10658", "10659", "10660", "10661",
			"10662", "10663", "10664", "10665", "10666", "10667", "10668",
			"10669", "10670", "10671", "10672", "10673", "10674", "10675",
			"10676", "10677", "10678", "10679", "10680", "10681", "10682",
			"10683", "10684", "10685", "10686", "10687", "10688", "10689",
			"10690", "10691", "10692", "10693", "10694", "10695", "10696",
			"10697", "10698", "10699", "10700" };
	
	// 未参评学校
	public static String[] notAttendUnits = new String[] { "10251", "10252",
			"10253", "10254", "10255", "10256", "10257", "10258", "10259",
			"10260", "10261", "10262", "10263", "10264", "10265", "10266",
			"10267", "10268", "10269", "10270", "10271", "10272", "10273",
			"10274", "10275", "10276", "10277", "10278", "10279", "10280",
			"10281", "10282", "10283", "10284", "10285", "10286", "10287",
			"10288", "10289", "10290", "10291", "10292", "10293", "10294",
			"10295", "10296", "10297", "10298", "10299", "10300", "10301",
			"10302", "10303", "10304", "10305", "10306", "10307", "10308",
			"10309", "10310", "10311", "10312", "10313", "10314", "10315",
			"10316", "10317", "10318", "10319", "10320", "10321", "10322",
			"10323", "10324", "10325", "10326", "10327", "10328", "10329" };

	public static void main(String[] args) {
		// 参评:1~200,330~400
		// 授权:1~250,350~700
		// 未参加:251~329
		for (int i = 1; i <= 700; i++) {
			if (i < 10)
				System.out.print(",\"1000" + i + "\"");
			else if (i < 100)
				System.out.print(",\"100" + i + "\"");
			else
				System.out.print(",\"10" + i + "\"");
		}
		System.out.println();
		/*Set<Integer> s = new HashSet<Integer>();
		//int i = 0;
		while (s.size() <= 400) {
			//i += 2;
			Random r = new Random();
			Integer tset = r.nextInt(700);
			System.out.println(tset);
			s.add(tset);
		}

		Iterator<Integer> ii = s.iterator();
		while (ii.hasNext()) {
			Integer tttt = ii.next();
			if (tttt < 10)
				System.out.print(",\"1000" + tttt + "\"");
			else if (tttt < 100)
				System.out.print(",\"100" + tttt + "\"");
			else
				System.out.print(",\"10" + tttt + "\"");
		}
		System.out.println();*/
		/*sort(_985Units, 0, _985Units.length - 1);
		for (String str : _985Units) {
			System.out.print(",\"" + str + "\"");
		}
		System.out.println();*/
	}

	private static int stoi(String str) {
		return Integer.valueOf(str);
	}

	public static void sort(String[] arr, int low, int high) {
		int l = low;
		int h = high;
		int temp = stoi(arr[low]);

		while (l < h) {
			while (l < h && stoi(arr[h]) >= temp)
				h--;

			if (l < h) {
				String temp2 = arr[h];
				arr[h] = arr[l];
				arr[l] = String.valueOf(temp2);
				l++;
			}

			while (l < h && stoi(arr[l]) <= temp)
				l++;

			if (l < h) {
				String temp2 = arr[h];
				arr[h] = arr[l];
				arr[l] = temp2;
				h--;
			}
		}
		/*System.out.print("l=" + (l + 1) + "h=" + (h + 1) + "povit=" + temp
				+ "\n");*/
		if (l > low)
			sort(arr, low, h - 1);
		if (h < high)
			sort(arr, l + 1, high);
	}
}