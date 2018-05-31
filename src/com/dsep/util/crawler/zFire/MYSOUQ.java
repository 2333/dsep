package com.dsep.util.crawler.zFire;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dsep.util.crawler.searchFile.DeleteDirectory;
import com.dsep.util.crawler.searchFile.PrintHtmlConvertor;
import com.dsep.util.crawler.searchFile.ProcessDataFromOwnHtml;
import com.dsep.util.crawler.searchFile.PropertiesUtil;
import com.dsep.util.crawler.searchFile.SearchFile;
import com.dsep.util.crawler.spider.SiteSetting;
import com.dsep.util.crawler.spider.Starter;
import com.dsep.util.crawler.spider.TestPhantomJsDriver;

public class MYSOUQ extends JFrame{
	
	private static final long serialVersionUID = -6788045638380819221L;
	//用户名
	private JTextField ulName;
	//密码
	private JPasswordField ulPasswd;
	
	//文件夹名称
	private JTextField ulFolder;
	
	
	//小容器
	private JLabel j1;
	private JLabel j2;
	private JLabel j3;
	private JLabel j4;
	//小按钮
	private JButton bCrawler;
	private JButton bPrinter;
	private JButton bChooser;
	private JButton bChromer;
	//复选框
	private JCheckBox c1;
	private JCheckBox c2;
	//列表框
	private JComboBox<String> cb1;
	/**
	 * 初始化QQ登录页面
	 * */
	public MYSOUQ(){
		//设置登录窗口标题
		this.setTitle("SOUQ.COM爬虫程序");
		//去掉窗口的装饰(边框)
//		this.setUndecorated(true); 
		//采用指定的窗口装饰风格
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		//窗体组件初始化
		init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置布局为绝对定位
		this.setLayout(null);
		this.setBounds(0, 0, 355, 465);
		//设置窗体的图标
		Image img0 = new ImageIcon("D:/logo.png").getImage();
		this.setIconImage(img0);
		//窗体大小不能改变
		this.setResizable(false);
		//居中显示
		this.setLocationRelativeTo(null);
		//窗体显示
		this.setVisible(true);
	}
	/**
	 * 窗体组件初始化
	 * */
	public void init(){
		//创建一个容器,其中的图片大小和setBounds第三、四个参数要基本一致(需要自己计算裁剪)
		Container container = this.getContentPane();
		j1 = new JLabel();
		//设置背景色
		Image img1 = new ImageIcon("D:/MYSOUQ/develop/MYSOUQ_BG.jpg").getImage();
		j1.setIcon(new ImageIcon(img1));
		j1.setBounds(0, 0, 355, 465);
		//qq头像设定
		j2 = new JLabel();
		Image img2 = new ImageIcon("D:/小雏菊.png").getImage();
		j2.setIcon(new ImageIcon(img2));
		j2.setBounds(40, 95, 50, 53);
		//用户名输入框
		ulName = new JTextField();
		ulName.setBounds(100, 10, 210, 20);
		ulName.setText(PropertiesUtil.GetValueByKey(SiteSetting.usernameAndPassword,"username"));
		//注册账号
		j3 = new JLabel("登录名:");
		j3.setBounds(40, 10, 70, 20);
		//密码输入框
		ulPasswd = new JPasswordField();
		ulPasswd.setBounds(100, 40, 210, 20);
		ulPasswd.setText(PropertiesUtil.GetValueByKey(SiteSetting.usernameAndPassword,"password"));
		//找回密码
		j4= new JLabel("密   码:");
		j4.setBounds(40, 40, 70, 20);
		
		
		
		
		bChooser = new JButton("文件夹");
		bChooser.setBounds(20, 70, 80, 20);
		
		//给按钮添加
		bChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc=new JFileChooser(SiteSetting.D_MYSOUQ);  
		        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );  
		        jfc.showDialog(new JLabel(), "选择");  
		        File file=jfc.getSelectedFile();  
		        if (null != file && null != file.getAbsolutePath())
		        	ulFolder.setText(file.getAbsolutePath());
			}
		});
		
		//用户名输入框
		ulFolder = new JTextField();
		ulFolder.setBounds(100, 70, 210, 20);
		
		
		bCrawler = new JButton("爬  取");
		//设置字体和颜色和手形指针
		//b1.setFont(new Font("宋体", Font.PLAIN, 12));
		bCrawler.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bCrawler.setBounds(20, 100, 80, 20);
		
		//给按钮添加
		bCrawler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean validate = false;
				try {
					validate = Validator.validate();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
				if (!validate) {
					SiteSetting.jtb.append("授权失败!\n\r");
	    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
	    			return;
				}
				SiteSetting.username = ulName.getText().trim();
				SiteSetting.password = ulPasswd.getText().trim();
				
				try {
					PropertiesUtil.WriteProperties(SiteSetting.usernameAndPassword,"username", SiteSetting.username);
					PropertiesUtil.WriteProperties(SiteSetting.usernameAndPassword,"password", SiteSetting.password);
				} catch (IOException e2) {
					e2.printStackTrace();
				}				
				SiteSetting.storeFolderPath = ulFolder.getText().trim();
				System.out.println("username:" + SiteSetting.username);
				System.out.println("password:" + SiteSetting.password);
				System.out.println("storeFolderPath:" + SiteSetting.storeFolderPath);
				
				String myCookie = SessionParser.parseCookieStr(TestPhantomJsDriver.getCookieStr());
				//System.out.println(jta.getText());
				//System.out.println(jtb.getText());
				SiteSetting.cookie = myCookie.trim();
				SiteSetting.token = SiteSetting.cookie.substring(SiteSetting.cookie.indexOf("SCXAT=") + 6, SiteSetting.cookie.indexOf("+"));
				// jta.getText means total number of goods
				Starter.begin();
				
				try {
					SearchFile.getFileList(SiteSetting.D_MYSOUQDate1downloadOrders);
					new Thread(new Runnable(){
			    		public void run() {
			    		try {
			    			SiteSetting.jtb.append("爬取完毕!\n\r");
			    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
			    		Thread.sleep(1000); 
			    		} catch (InterruptedException e) {
			    		e.printStackTrace();
			    		}
			    		}
			    	}).start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//多账号
		bPrinter = new JButton("打  印");
		//给按钮添加
		bPrinter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SiteSetting.storeFolderPath = ulFolder.getText().trim();
					System.out.println(SiteSetting.getStoreFolderPath());
					DeleteDirectory.deleteDir(new File(SiteSetting.getStoreFolderPath()));
					ProcessDataFromOwnHtml.process(SiteSetting.jtb.getText().trim());
					PrintHtmlConvertor.convert1(SiteSetting.getStoreFolderPath());
					//PrintGoodsConvertor.convert2(SiteSetting.getStoreFolderPath());
					new Thread(new Runnable(){
			    		public void run() {
			    		try {
			    			SiteSetting.jtb.setText("");
			    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
			    			SiteSetting.jtb.append("打印完毕，打印面单在："+SiteSetting.D_MYSOUQDate + "PRINT.html"+"\n\r");
			    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
			    		Thread.sleep(1000); 
			    		} catch (InterruptedException e) {
			    		e.printStackTrace();
			    		}
			    		}
			    	}).start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		
		
		
		//设置字体和颜色和手形指针
		//b2.setFont(new Font("宋体", Font.PLAIN, 12));
		bPrinter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bPrinter.setBounds(230, 100, 80, 20);
				
		
		bChromer = new JButton("认  证");
		//给按钮添加
		bChromer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SiteSetting.jtb.append(ComputerInfo.getComputerName()+"\n\r");
				SiteSetting.jtb.append(ComputerInfo.getMacAddress()+"\n\r");
    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
			}
		});
		//设置字体和颜色和手形指针
		//b2.setFont(new Font("宋体", Font.PLAIN, 12));
		bChromer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bChromer.setBounds(140, 100, 80, 20);
		
		SiteSetting.jtb = new JTextArea(1, 1);
		SiteSetting.jtb.setTabSize(4);
		SiteSetting.jtb.setFont(new Font("Helvetica", Font.PLAIN, 10));
		SiteSetting.jtb.setLineWrap(true);// 激活自动换行功能
		SiteSetting.jtb.setWrapStyleWord(true);// 激活断行不断字功能
		//SiteSetting.jtb.append("请填写订单总数量后，点击爬取按钮...\r\n");
		JScrollPane jscrollPane = new JScrollPane(SiteSetting.jtb);
		jscrollPane.setBounds(20, 130, 290, 300);
		
		j1.add(j2);
		j1.add(j3);
		j1.add(j4);
		j1.add(bCrawler);
		j1.add(bPrinter);
		j1.add(bChromer);
		j1.add(bChooser);
		j1.add(jscrollPane);


		container.add(ulName);
		container.add(ulPasswd);
		container.add(ulFolder);
		container.add(j1);
	}
	public static void main(String[] args) {
		PropertyConfigurator.configure( "D:/MYSOUQ/develop/log4j.properties" );
        SiteSetting.logger  =  Logger.getLogger(MYSOUQ.class );
        SiteSetting.logger.debug( "debug" );
        SiteSetting.logger.error( "error" );
		new MYSOUQ();
	}
}
