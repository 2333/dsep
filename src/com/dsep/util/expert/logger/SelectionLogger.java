package com.dsep.util.expert.logger;

public interface SelectionLogger {
	public abstract void record(String when, String whichDiscipline,
			String whichUnit, String whichEpert, String whichExpertType,
			String whatTrouble);
	
	public abstract void read();
	public abstract void write();
}
