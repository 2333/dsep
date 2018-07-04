package com.dsep.unitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.util.NumberUtil;

public class NumberUtilTest {
	
	@Test
	public void testNumberUtil(){
	    boolean result = NumberUtil.isNumber("-10.2");
	    System.out.println(result);
	}
}
