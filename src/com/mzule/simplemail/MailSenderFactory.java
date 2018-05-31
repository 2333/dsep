package com.mzule.simplemail;
 
import com.mzule.simplemail.SimpleMailSender;
 
/**
 * 发件箱工厂
 * 
 * @author MZULE
 * 
 */
public class MailSenderFactory {
 
    /**
     * 服务邮箱
     */
    private static SimpleMailSender serviceSms = null;
 
    /**
     * 获取邮箱
     * 
     * @param type 邮箱类型
     * @return 符合类型的邮箱
     */
    public static SimpleMailSender getSender() {
    //if (type != null) {
        if (serviceSms == null) {
        serviceSms = new SimpleMailSender("1104@caict.ac.cn",
            "Caict1104");
        //	serviceSms = new SimpleMailSender("xg@caict.ac.cn",
        //	            "123123806");
        //serviceSms = new SimpleMailSender("fanghongyu@caict.ac.cn",
        //            "Jesse0503");
        }
        return serviceSms;
    //}
    //return null;
    }
 
}