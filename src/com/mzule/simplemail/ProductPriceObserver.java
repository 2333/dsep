package com.mzule.simplemail;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
 
 
public class ProductPriceObserver implements Observer {
	int counter = 1;
    @Override
    public void update(Observable obj, Object arg) {
        /*// 发送邮件
        SimpleMailSender sms = MailSenderFactory
            .getSender();
        List<String> recipients = new ArrayList<String>();
        //recipients.add("dijing@caict.ac.cn");
        recipients.add("fanghongyu@caict.ac.cn");
        try {
            for (String recipient : recipients) {
            sms.send(recipient, "请${XXX}尽快完成CRP系统中3个月以上未办理的事项",
            		"${XXX}您好：<br>        请您尽快完成CRP系统中3个月以上未办理的事项：<br>3-IDC部级抽测_工信部（甘肃）<br>3-IDC部级抽测_工信部（上海电信）<br>3-IDC企业自测_青海联通<br>3-IDC企业自测_青海移动<br>3-IDC企业自测_青海电信<br>3-IDC企业自测_新疆移动<br>3-IDC企业自测_上海移动<br>出差测试派遣单(杜伟 2016-08-16 16:38)<br>出差测试派遣单(杜伟 2016-08-16 16:35)<br>出差测试派遣单(杜伟 2016-08-16 16:32)<br>出差测试派遣单(杜伟 2016-08-16 16:28)<br>出差测试派遣单(杜伟 2016-08-16 14:37)<br>出差测试派遣单(杜伟 2016-08-15 15:23)<br>3-IDC部级抽测_工信部<br>3-IDC管局抽测_重庆管局<br>3-IDC管局抽测_福建管局<br>3-IDC管局抽测_湖南管局<br>3-IDC管局抽测_湖北管局<br>3-IDC管局抽测_河北管局<br>3-IDC管局抽测_山东管局<br>3-IDC管局抽测_天津管局<br>3-IDC管局抽测_北京管局<br>3-IDC管局抽测_上海管局<br>3-IDC企业自测_湖北移动<br>3-IDC企业自测_河南移动<br>3-IDC企业自测_河南电信<br>3-IDC企业自测_江西移动<br>3-IDC企业自测_江苏移动<br>3-IDC企业自测_江苏电信<br>3-IDC企业自测_安徽移动<br>3-IDC企业自测_安徽电信<br>3-IDC企业自测_云南电信<br>        如果超过1年未办理，CRP管理员将在系统中进行批量处理，届时相关事项将不再显示在您的“待办工作”中。");
            }
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }
    
    public void send(String content, String name, String email) {
    	// 发送邮件
        SimpleMailSender sms = MailSenderFactory
            .getSender();
        List<String> recipients = new ArrayList<String>();
        recipients.add(email);
        
        //recipients.add("dijing@caict.ac.cn");
        //recipients.add("fanghongyu@caict.ac.cn");
        try {
            for (String recipient : recipients) {
            sms.send(recipient, "信管中心请各位院领导处理未办结事项", content);
            }
        } catch (AddressException e) {
        	
        	System.out.println(email + " " + name + "发送失败");
            e.printStackTrace();
        } catch (MessagingException e) {
        	System.out.println(email + " " + name + "发送失败");
            e.printStackTrace();
        } finally {
        	System.out.println(email + " " + name + "发送成功" + counter++);
        }
    }
    
    
    public void getMember() throws Exception {
    	BufferedReader input = new BufferedReader(new  InputStreamReader(new FileInputStream(new File("E:\\memberList2.txt")))); 

        String message = ""; 

        String line = null;   

        //ArrayList<Info> infos = new ArrayList<Info>();

        //Info info = null;
        input.readLine();
        
        while((line = input.readLine()) != null) { 

              System.out.println(line);
              String[] ele = line.split("\t");
              //System.out.println(ele[1]);
              String memberId = ele[1];
              //System.out.println(ele[3]);
              String memberName = ele[3];
              //System.out.println(ele[4]);
              String memberType = ele[4];
              //System.out.println(ele[6]);
              String email = ele[6] + "@caict.ac.cn";
              //System.out.println("========================");
              List<CTPInfo> list = AnalyseCtpAffair.getCtpAffairInfo(memberId);
              String content = "<html><style>p{text-indent:35px;text-indent:2em;}</style><body>";
              content += "尊敬的" + memberName;
              if (memberType.contains("主任")) {
            	  content += "主任";
              } else if (memberType.contains("院长")) {
            	  content += "院长";
              } else if (memberType.contains("书记")) {
            	  content += "书记";
              } else if (memberType.contains("所长")) {
            	  content += "所长";
              } else if (memberType.contains("总工")) {
            	  content += "总工";
              }
              content += "，您好：<br>";
              content += "<p>您的CRP系统中存在3个月以上未办理的事项（如下所列），请各位院领导抽空进入“CRP首页——待办工作”进行办理。</p>";
              //content += "<p>如果超过1年未办理，CRP管理员将进行批量处理，届时相关事项将不再显示在您的“待办工作”中，请您知晓。</p>";
              content += "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\"><th><td>流程名称</td><td>未处理节点操作</td><td>接收日期</td><td>发起日期</td></th>";
              int seq = 1;
              for (CTPInfo info : list) {
            	  content += "<tr><td>" + (seq++) + "</td><td>" 
            			  + info.subject + "</td><td>" 
            			  + info.node_policy + "</td><td>" 
            			  + info.receive_time + "</td><td>"
            			  + info.create_time + "</td></tr>";
              }
              content += "</table>";
              content += "<br>";
              content += "<p>如有问题，请联系信管中心服务台62301104。</p>";
              content += "</body></html>";
              //System.out.println();
              //System.out.println(content);
              if (seq > 1) {
            	  send(content, memberName, email);
              }
              
        }
        
    }
    
    public static void main(String[] args) throws Exception {
    	System.setProperty("java.net.preferIPv4Stack", "true");
    	ProductPriceObserver x = new ProductPriceObserver();
    	x.getMember();
    	//x.update(null, null);
	}
 
}