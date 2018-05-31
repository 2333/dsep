package com.dsep.unitTest;

import org.junit.Test;

import com.dsep.util.XFireWithWindows;

public class TestReportGenetare {
	
	@Test
	public void GenetateWinReport(){
		String result = XFireWithWindows.generatorWinReport("10006",
				"085213", "zyxw", "");
		System.out.println(result);
	}

}
