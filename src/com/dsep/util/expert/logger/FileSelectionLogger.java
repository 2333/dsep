package com.dsep.util.expert.logger;

public class FileSelectionLogger implements SelectionLogger {
	@Override
	public void record(String when, String whichDiscipline, String whichUnit,
			String whichEpert, String whichExpertType, String whatTrouble) {
		System.err.println("在" + when + "");
	}

	@Override
	public void read() {
		
	}

	@Override
	public void write() {
		
	}
}
