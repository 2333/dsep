package com.dsep.unitTest.extendtest;

import org.hibernate.Session;

public class One {
	
	private static int theSession = 0;
	private int hello;
	
	public void addSession(){
		this.theSession++;
	}
	
	public int getSession() {
		return this.theSession;
	}
	
	public int getHello(){
		hello = this.theSession + 1;
		return hello;
	}
	
	public void printSession(){
		System.out.println("theSession in "+this.getClass().getName()+":"+this.theSession);
	}
	
	public void printHello(){
		System.out.println("theHello in "+ this.getClass().getName() + ":"+this.getHello());
	}
	
	
}
