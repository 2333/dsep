package com.dsep.domain.dsepmeta.logicrule;




/**
 * 逻辑校验函数工厂类，通过反射机制生成新的校验函数对象
 * @author lubin
 *
 */
public class LogicCheckFactory {
	
	public static MetaAttrCheck getMetaAttrCheckInstance(String classCheck){
		MetaAttrCheck check = null ;
		try {
			check=(MetaAttrCheck)Class.forName("com.dsep.domain.dsepmeta.logicrule.rules."+classCheck).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return check;
	}
	
	public static MetaEntityCheck getMetaEntityCheckInstance(String classCheck){
		MetaEntityCheck check = null ;
		try {
			check=(MetaEntityCheck)Class.forName("com.dsep.domain.dsepmeta.logicrule.rules."+classCheck).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return check;
	}

}
