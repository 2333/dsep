package com.dsep.util.datacalculate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClusterForDouble {
	private int count;
	private double [][] disc;
	private List<BunchDouble> bunchs;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double[][] getDisc() {
		return disc;
	}
	
	public List<BunchDouble> getBunchs() {
		return bunchs;
	}
	public void setBunchs(List<BunchDouble> bunchs) {
		this.bunchs = bunchs;
	}
	public ClusterForDouble(List<Double> data){
		this.bunchs=new LinkedList<BunchDouble>();
		for(int i=0;i<data.size();i++){
			double m=data.get(i);
			BunchDouble bunch=new BunchDouble(m,i);
			this.bunchs.add(bunch);
		}
		this.count=data.size();
	}
	
	public void toCluster(double limit){
		boolean flag;
		do{
			this.resetDisc();
			flag = this.mergeBunch(limit);
		}while(flag==true);
	}
	private void resetDisc() {
		this.disc=new double[this.count][this.count];  //每次充值距离要申请一个新的距离矩阵
		for(BunchDouble bunch1:bunchs){
			for(BunchDouble bunch2:bunchs){
				int num1=bunch1.getNumber();
				int num2=bunch2.getNumber();
				
				if(num1!=num2&&this.disc[num1][num2]==0&&this.disc[num2][num1]==0){
					List<Double> list1=bunch1.getElements();
					List<Double> list2=bunch2.getElements();
					double distance=calDistance(list1,list2);
					this.disc[num1][num2]=distance;
					this.disc[num2][num1]=distance;
				}
			}
		}
	}
	
	private boolean mergeBunch(double limit){
		///////////////////////////////////////////////////
		System.out.println("聚类：");
		List<BunchDouble> listbefore=this.getBunchs();
		for(int i=0;i<listbefore.size();i++){
			BunchDouble bunch = listbefore.get(i);
			int num=bunch.getNumber();
			List<Double> elements=bunch.getElements();
			System.out.print("第"+num+"类"+":");
			for(int j=0;j<elements.size();j++){
				System.out.print(elements.get(j)+" , ");
			}
			System.out.println();
		}
		////////////////////////////////////////////////////
		//找出距离最短的两个类
		double minDisc=999;
		int index1=0,index2=0;
		List<BunchDouble> bunchs2=new ArrayList<BunchDouble>();
		for(int i=0;i<this.count;i++){
			for(int j=0;j<this.count;j++){
				if(i!=j&&this.disc[i][j]<minDisc){
					minDisc=this.disc[i][j];
					if(i<j){
						index1=i;index2=j;
					}else{
						index1=j;index2=i;
					}
				}
			}
		}
		if(minDisc>=limit)
			return false;
		//合并类
		for(int i=0;i<bunchs.size();i++){
			BunchDouble bunch=bunchs.get(i);
			if(bunch.getNumber()==index1){
				//找到index1
				List<Double> bunchlist=bunch.getElements();
				for(int j=0;j<this.count;j++){
					BunchDouble bunch2=bunchs.get(j);
					if(bunch2.getNumber()==index2){
						List<Double> listBunch2=bunch2.getElements();
						bunchlist.addAll(listBunch2);
						bunch.setElements(bunchlist);
						bunchs2.add(bunch);                //添加进新队列
					}
				}
			}
			else if(bunch.getNumber()>index2){
				bunch.setNumber(bunch.getNumber()-1);
				bunchs2.add(bunch);
			}else if(bunch.getNumber()!=index2){
				bunchs2.add(bunch);
			}
		}
		bunchs=bunchs2;
		this.count--;
		
		return true;
	}
	
	/**
	 * 计算两个类之间的距离
	 * @param list1
	 * @param list2
	 * @return
	 */
	private double calDistance(List<Double> list1,List<Double> list2){
		int n1=list1.size();
		int n2=list2.size();
		
		double result=0.0;
		for(double value1:list1){
			for(double value2:list2){
				
				double temp=Math.pow(value2-value1, 2.0);
				result+=temp;
			}
		}
		
		result=result/(n1*n2);
		
		result=Math.sqrt(result);
		
		return result;
	}
	
	public static void main(String[] args) {
		List<Double> data=new ArrayList<Double>();
		String str1;
		
		data.add(98.5);
		data.add(98.5);
		data.add(97.5);
		data.add(96.2);
		data.add(95.2);
		data.add(93.23);
		data.add(92.4);
		data.add(90.04);
		data.add(89.45);
		data.add(88.87);
		data.add(87.65);
		data.add(85.0);
		data.add(84.03);
		data.add(82.8);
		data.add(82.8);
		data.add(79.9);
		
		for(int i=0;i<data.size();i++){
			System.out.print(data.get(i)+"   ");
			if(i%5==0)
				System.out.println();
		}
		System.out.println();
		ClusterForDouble cluster = new ClusterForDouble(data);
		cluster.toCluster(1.8);
		
	}
	
	
}

class BunchDouble{
	private int number; //簇编号
	private List<Double> elements;
	public BunchDouble(double m,int n){
		this.number=n;
		List<Double> list=new ArrayList<Double>();
		list.add(m);
		this.elements=list;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<Double> getElements() {
		return elements;
	}
	public void setElements(List<Double> elements) {
		this.elements = elements;
	}
}
