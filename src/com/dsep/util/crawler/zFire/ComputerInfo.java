package com.dsep.util.crawler.zFire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;


/*********************************************************************************
//* Copyright (C) 2014 ��������������������. All Rights Reserved.
//*
//* Filename:      ComputerInfo.java
//* Revision:      1.0
//* Author:        <yao xiucai>
//* Created On:    2014��5��21��
//* Modified by:   
//* Modified On:   
//*
//* Description:   <ȡ�������ַ--1.��Windows,Linuxϵͳ�¾���ã�2.ͨ��ipconifg,ifconfig��ü������Ϣ��3.����ģʽƥ�䷽ʽ����MAC��ַ�������ϵͳ�������޹�>
//* Description:   <ȡ�������--�ӻ���������ȡ>
 */
/********************************************************************************/


public class ComputerInfo {


    private static String         macAddressStr  = null;
    private static String         computerName   = System.getenv().get("COMPUTERNAME");


    private static final String[] windowsCommand = { "ipconfig", "/all" };
    private static final String[] linuxCommand   = { "/sbin/ifconfig", "-a" };
    private static final Pattern  macPattern     = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", Pattern.CASE_INSENSITIVE);


    private final static List<String> getMacAddressList() throws IOException {
        final ArrayList<String> macAddressList = new ArrayList<String>();
        final String os = System.getProperty("os.name");
        final String command[];


        if (os.startsWith("Windows")) {
            command = windowsCommand;
        }
        else if (os.startsWith("Linux")) {
            command = linuxCommand;
        }
        else {
            throw new IOException("Unknow operating system:" + os);
        }


        final Process process = Runtime.getRuntime().exec(command);


        BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null ; (line = bufReader.readLine()) != null ;) {
            Matcher matcher = macPattern.matcher(line);
            if (matcher.matches()) {
                macAddressList.add(matcher.group(1));
                //macAddressList.add(matcher.group(1).replaceAll("[-:]", ""));//ȥ��MAC�еġ�-��
            }
        }


        process.destroy();
        bufReader.close();
        return macAddressList;
    }


    public static String getMacAddress() {
        if (macAddressStr == null || macAddressStr.equals("")) {
            StringBuffer sb = new StringBuffer(); //��Ŷ�����ַ�ã�Ŀǰֻȡһ����0000000000E0�����ֵ
            try {
                List<String> macList = getMacAddressList();
                for (Iterator<String> iter = macList.iterator() ; iter.hasNext() ;) {
                    String amac = iter.next();
                    if (!amac.equals("0000000000E0")) {
                        sb.append(amac);
                        break;
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }


            macAddressStr = sb.toString();


        }


        //return macAddressStr;
        String mac_addr = "A4-1F-72-5C-0C-F8";
        return mac_addr;
    }


    public static String getComputerName() {
        if (computerName == null || computerName.equals("")) {
            computerName = System.getenv().get("COMPUTERNAME");
        }


        //return computerName;
        String computerName = "MATHY";
        return computerName;
		
    }


    private ComputerInfo() {


    }


    public static void main(String[] args) {
        System.out.println(ComputerInfo.getMacAddress());
        System.out.println(ComputerInfo.getComputerName());
        System.out.println(DigestUtils.shaHex(DigestUtils.shaHex(ComputerInfo.getMacAddress())));
        System.out.println(DigestUtils.shaHex(DigestUtils.shaHex(DigestUtils.shaHex(ComputerInfo.getComputerName()))));
    }
}