package com.dsep.service.datacalculate.RuleEngine.commander;

public class CommanderFactory {

	public static ComputeCommander getComputeCommander(String classCommander){
		ComputeCommander compCommander=null;
		try{
			compCommander=(ComputeCommander)Class.forName("com.dsep.service.datacalculate.RuleEngine.commander."+classCommander).newInstance();
		} catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		return compCommander;
		
	}
}
