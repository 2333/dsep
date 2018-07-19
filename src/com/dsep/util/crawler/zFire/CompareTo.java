package com.dsep.util.crawler.zFire;

/**
 *    ����Ƚϴ�Сcompare���÷� �ַ�����
 *    ��ϰ����, ���ַ�" nba" "cba" "ncaa" "wba" ...    
    
    ˼·: 
    1.����,�뵽���������.���������뵽ѡ������,����ð������
    2.�����������˼·������.(�ַ�ͬ��)
    3.��������,�Ƚ�ÿһ��Ԫ������һ��Ԫ�صĴ�С��ϵ
    4.��󽻻�����Ԫ��λ��
    5.������
    
    ����ѧϰ�ܽ�:
    1.String�����ǲ��߱��Ƚϴ�С���ܵ�,��������String����ʵ����
    Comparable�Ľӿ�.����ӿ��ǿɱȽϹ��ܵĽӿ�.���ﶨ���˱ȽϷ���compareTo����.
    Api�ֲ��е�����:
    �˽ӿ�ǿ�ж�ʵ�����ÿ����Ķ��������������
    �������򱻳�Ϊ�����Ȼ������� compareTo ��������Ϊ�����Ȼ�ȽϷ���
    
    2.����������Ƕ�����һ����,��������Ķ���,��Ҫ�ȴ�Сʱ,���ǾͲ����Լ�д������,
    ֱ��ʵ��Comparable�ӿ�,��дcompareTo�����Ϳ�����.ע:������String���Ѿ���д��
    comparTo����.
    
    3.
    �÷����Ƚ϶��� �� ָ������� ˳��
    д��: 
    campareTo(T o); 
    ����ֵ: int  ���� 0(�������),����(����С�ڲ���),���� (������ڲ���) 
    ����:   o ΪҪ�ȽϵĶ���

 */
public class CompareTo{

    public static void main(String[] args) {
        String[] str = {"nba","cba" ,"ncaa" ,"wba","ccba","abc"};
        //printArr(str);    
        strSort(str);        //��ϰ���򷽷�
//        Arrays.sort(str);    �����÷�
        printArr(str);
        
    }
    
    /**
     * ���ַ������С��������
     * @param str    String[] ��Ҫ������ַ�����
     */
    public static void strSort(String[] str){
        for (int i = 0; i < str.length; i++) {
            for (int j = i+1; j < str.length; j++) {
                if(str[i].compareTo(str[j])>0){    //����������camparTo����
                    swap(str,i,j);
                }
            }
        }
        
    }
    /**
     * ��������Ԫ�ص�λ�õķ���
     * @param strSort    ��Ҫ����Ԫ�ص�����    
     * @param i    ����i
     * @param j ����j
     */
    private static void swap(String[] strSort, int i, int j) {
        String t = strSort[i];
        strSort[i] = strSort[j];
        strSort[j] = t;
    }
    /**
     * ��ӡ�ַ�����
     * @param str
     */
    private static void printArr(String[] str) {
        for (int i = 0; i < str.length; i++) {
            System.out.print(str[i]+"\t");
        }
        System.out.println();
    }


}
