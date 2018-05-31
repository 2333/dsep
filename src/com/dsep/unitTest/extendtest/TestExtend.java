package com.dsep.unitTest.extendtest;

import org.junit.Test;


public class TestExtend {
	
	@Test
	public void test(){
		One theOne = new One();
		ExtendOne theExtend = new ExtendOne();
		ExtendOneAgain again = new ExtendOneAgain();
		/*theOne.printSession();
		theExtend.printSession();
		again.printSession();
		again.addSession();
		theExtend.addSession();
		theOne.printSession();
		theExtend.printSession();
		again.printSession();*/
		theOne.printHello();
		theExtend.printHello();
		again.printHello();
		theExtend.addSession();
		theOne.printHello();
		theExtend.printHello();
		again.printHello();
	}
}
