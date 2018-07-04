package com.dsep.util.survey;

import java.io.File;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SaxTest1 {
	public static void main(String[] args) throws Exception {
		SAXParserFactory factory  = SAXParserFactory.newInstance();
		
		SAXParser parser = factory.newSAXParser();
		
		parser.parse(new File(".xml"), new MyHandler());
	}
}

class MyHandler extends DefaultHandler {
	private Stack<String> stack = new Stack<String>();
	
	private String name;
	private String args1;
	private String args2;
	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		stack.push(qName);
		for (int i = 0; i < attributes.getLength(); i++) {
			String attrName = attributes.getQName(i);
			String attrValue = attributes.getValue(i);
			System.out.println(attrName + " " + attrValue);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String tag = stack.peek();
		if ("".equals(tag)) {
			name = new String(ch, start, length);
		} else if ("1".equals(tag)) {
			
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		stack.pop();// 该元素已经解析完毕
		if ("".equals(qName)) {
			System.out.println("xx" + name);
		}
	}
}
