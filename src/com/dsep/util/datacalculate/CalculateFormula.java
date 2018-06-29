package com.dsep.util.datacalculate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dsep.util.export.briefsheet.ResourceLoader;

public class CalculateFormula {
	public static double calculateFormula(List<Object> formula) throws Exception{
		formula = convertFormula(formula);
		Stack pre = preCalculateFormula(formula);
		double result = calculateStack(pre);
		return result;
	}
	
	//将中缀表达式转换成后缀表达式
	private static List<Object> convertFormula(List<Object> formula) throws Exception{
		Stack operators = new Stack();
		List<Object> out = new ArrayList<Object>();
		for(Object o:formula){
			if(isOperator(o)){
				if(operators.topSQ==-1)
					operators.push(o);
				else{
					try {
						int wt1 = getOperatorWt((String)operators.top,"top");
						int wt2 = getOperatorWt((String)o,"out");
						while(wt1>wt2){
							out.add(operators.top);
							operators.pop();
							wt1 = getOperatorWt((String)operators.top,"top");
						}
						if(wt1<wt2)
							operators.push(o);
						if(wt1==wt2&&(!o.equals("#"))){
							operators.pop();
							continue;
						}
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						throw new Exception("xml文件读取错误");
					}		
				}
			}else out.add(o);
		}
		return out;
	}
	
	public static boolean isOperator(Object T){
		if(T.getClass().toString().equals("class java.lang.String")){
			boolean flag = false;
			String[] operators = {"+","-","*","/","(",")","#"};
			for(int i=0;i<operators.length;i++){
				if(T.equals(operators[i])){
					flag = true;
					break;
				}		
			}
			return flag;
		}
		else return false;
	}
	
	private static int getOperatorWt(String operator,String location) throws DocumentException{
		SAXReader reader = new SAXReader(); 
   	    reader.setEncoding("utf-8");
   	    File in = new File(ResourceLoader.getPath("template/operatorWt.xml"));
   	    int result = 0;
        Document doc;
        /*try{*/
        	doc = reader.read(in);
			Element root = ((org.dom4j.Document) doc).getRootElement(); 
	    	List<Element> elements = root.elements(); 
	    	for(Element e:elements){
	    		if(e.getName().equals("weight")){
	    			List<Element> e1 = e.elements();
	    			for(Element e2:e1){
	    				if(e2.attributeValue("content").equals(operator)
	    						&&e2.attributeValue("location").equals(location))
	    					result = Integer.valueOf(e2.attributeValue("weight"));
	    			}
	    		}
	    	}
	    	return result;
        /*}catch (Exception e){
        	return result;
        }*/
	}
	
	private static Stack preCalculateFormula(List<Object> formula){
		Stack pre = new Stack();
		for(int i=formula.size()-1;i>=0;i--){
			Object T = formula.get(i);
			pre.push(T);		
		}
		return pre;
	}
	
	private static double calculateStack(Stack pre){
		double num1;
		double num2;
		double num = 0;
		Stack nums = new Stack();
		while(pre.top!=null){
			if(isOperator(pre.top)){
				num2 = (Double)nums.top;
				nums.pop();
				num1 = (Double)nums.top;
				nums.pop();
				switch((String)pre.top){
				case "+":{num = num1 + num2;break;}
				case "-":{num = num1 - num2;break;}
				case "*":{num = num1*num2;break;}
				case "/":{num = num1/num2;break;}
				default:
				}
				nums.push(num);
				pre.pop();
			}
			else{
				nums.push(pre.top);
				pre.pop();
			}
		}
		return (Double)nums.top;
	}
	
	
	public static void main(String [] args) throws Exception{
		double a = 76.1;
		double b = 3.0;
		if(!isOperator(a))
			System.out.println("haha");
		List<Object> objs = new ArrayList<Object>();
		objs.add(12.2);
		objs.add("-");
		objs.add("(");
		objs.add(36.3);
		objs.add("/");
		objs.add(a);
		objs.add(")");
		objs.add("/");
		objs.add(23.2);
		objs.add("-");
		objs.add(b);
		objs.add("#");
		System.out.println(objs);
		objs = convertFormula(objs);
		System.out.println(objs);
		Stack pre = preCalculateFormula(objs);
		double e2 = calculateStack(pre);
	}
}
