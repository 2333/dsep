function initRules(data)
{   
	for(var i=0;i<data.colConfigs.length;i++)
	{		
		if(data.colConfigs[i].editrules.custom)
		{   
			var funcName= "jsCheckFuncsFactory";
			data.colConfigs[i].editrules.custom_func = eval(funcName);
		}
		if(data.colConfigs[i].editoptions.dataInit=='loadAutoComplete'){
			data.colConfigs[i].editoptions.dataInit=eval('loadAutoComplete');
		}
		if(data.colConfigs[i].editoptions.dataInit=='linkLoadAutoComplete'){
			data.colConfigs[i].editoptions.dataInit=eval('linkLoadAutoComplete');
		}
		if(data.colConfigs[i].editoptions.dataInit=='mulLoadAutoComplete'){
			data.colConfigs[i].editoptions.dataInit=eval('mulLoadAutoComplete');
		}
	}
}