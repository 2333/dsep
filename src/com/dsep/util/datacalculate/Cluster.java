package com.dsep.util.datacalculate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.dsep.entity.dsepmeta.CalResult;

public class Cluster {

	private int count;
	private double [][] disc;
	private List<Bunch> bunchs;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double[][] getDisc() {
		return disc;
	}
	
	public List<Bunch> getBunchs() {
		return bunchs;
	}
	public void setBunchs(List<Bunch> bunchs) {
		this.bunchs = bunchs;
	}
	public Cluster(List<CalResult> data){
		this.bunchs=new LinkedList<Bunch>();
		for(int i=0;i<data.size();i++){
			CalResult m=data.get(i);
			Bunch bunch=new Bunch(m,i);
			this.bunchs.add(bunch);
		}
		this.count=data.size();
	}
	
	/**
	 * 聚类
	 * @param limit  类间最小距离的阈值
	 */
	public void toCluster(double limit){
		boolean flag;
		do{
			this.resetDisc();
			flag = this.mergeBunch(limit);
		}while(flag==true);
		
		this.updateEntity();
	}
	
	/**
	 * 获取完成聚类后的所有结果实体
	 * @return
	 */
	public List<CalResult> getAllEntity(){
		List<CalResult> result=new ArrayList<CalResult>();
		for(int i=0;i<this.count;i++){
			Bunch bunch=this.bunchs.get(i);
			result.addAll(bunch.getElements());
		}
		return result;
	}
	/**
	 * 计重置每个类之间的距离
	 */
	private void resetDisc() {
		this.disc=new double[this.count][this.count];  //每次充值距离要申请一个新的距离矩阵
		for(Bunch bunch1:bunchs){
			for(Bunch bunch2:bunchs){
				int num1=bunch1.getNumber();
				int num2=bunch2.getNumber();
				
				if(num1!=num2&&this.disc[num1][num2]==0&&this.disc[num2][num1]==0){
					List<CalResult> listEntity1=bunch1.getElements();
					List<CalResult> listEntity2=bunch2.getElements();
					List<Double> list1=this.getScoreData(listEntity1);
					List<Double> list2=this.getScoreData(listEntity2);
					double distance=calDistance(list1,list2);
					this.disc[num1][num2]=distance;
					this.disc[num2][num1]=distance;
				}
			}
		}
	}
	
	/**
	 * 找出类间距离最小的两个类，并合并这两个类
	 * @param limit
	 * @return
	 */
	private boolean mergeBunch(double limit){
		/////////////////打印聚类情况//////////////////////////////////
		System.out.println("聚类：");
		List<Bunch> listbefore=this.getBunchs();
		for(int i=0;i<listbefore.size();i++){
			Bunch bunch = listbefore.get(i);
			int num=bunch.getNumber();
			List<CalResult> elements=bunch.getElements();
			System.out.print("第"+num+"类"+":");
			for(int j=0;j<elements.size();j++){
				System.out.println(elements.get(j).getDiscName()+" "+elements.get(j).getUnitName()+" "+elements.get(j).getScore()+" ");
			}
			System.out.println();
		}
		/////////////////打印聚类情况 end//////////////////////////////
		//找出距离最短的两个类
		double minDisc=999;
		int index1=0,index2=0;
		List<Bunch> bunchs2=new ArrayList<Bunch>();
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
			Bunch bunch=bunchs.get(i);
			if(bunch.getNumber()==index1){
				//找到index1
				List<CalResult> bunchlist=bunch.getElements();
				for(int j=0;j<this.count;j++){
					Bunch bunch2=bunchs.get(j);
					if(bunch2.getNumber()==index2){
						List<CalResult> listBunch2=bunch2.getElements();
						bunchlist.addAll(listBunch2);
						bunch.setElements(bunchlist);
						bunchs2.add(bunch);                //添加进新队列
					}
				}
			}
			else if(bunch.getNumber()>index2){      //对于编号大于index2的簇，簇编号依次减一
				bunch.setNumber(bunch.getNumber()-1);
				bunchs2.add(bunch);
			}else if(bunch.getNumber()!=index2){    //其余的簇编号非index1、index2的簇依次加到新簇队列中
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
	 private List<Double> getScoreData(List<CalResult> list){
		 List<Double> result=new ArrayList<Double>();
		 for(int i=0;i<list.size();i++){
			 CalResult item=list.get(i);
			 double score=item.getScore();
			 result.add(score);
		 }
		 return result;
	 }
	 private void updateEntity(){
		 List<Bunch> bunchList2=new ArrayList<Bunch>();
		 for(int i=0;i<this.count;i++){
			 Bunch bunch=this.bunchs.get(i);
			 int num=bunch.getNumber()+1;
			 String numstr=Integer.toString(num);
			 List<CalResult> list=bunch.getElements();
			 List<CalResult> list2=new ArrayList<CalResult>();
			 for(int j=0;j<list.size();j++){
				 CalResult calResult=list.get(j);
				 calResult.setCluClass(numstr);
				 list2.add(calResult);
			 }
			 bunch.setElements(list2);
			 bunchList2.add(bunch);
		 }
		 this.bunchs=bunchList2;
	 }
	public static void main(String[] args) {
		List<Double> data=new ArrayList<Double>();
		String str1;
		
		data.add(98.5);
		data.add(98.0);
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
		data.add(80.5);
		data.add(79.9);
		
		for(int i=0;i<data.size();i++){
			System.out.print(data.get(i)+"   ");
			if(i%5==0)
				System.out.println();
		}
		System.out.println();
//		Cluster cluster = new Cluster(data);
//		cluster.toCluster(1.8);
	}
	
}

class Bunch{
	private int number; //簇编号
	private List<CalResult> elements;   //簇内的数据成员
	public Bunch(CalResult m,int n){
		this.number=n;
		List<CalResult> list=new ArrayList<CalResult>();
		m.setCluClass(Integer.toString(n));
		list.add(m);
		this.elements=list;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<CalResult> getElements() {
		return elements;
	}
	public void setElements(List<CalResult> elements) {
		this.elements = elements;
	}
}
