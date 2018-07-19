package com.dsep.util.expert.logger;

public class ErrPrintSelectionLogger implements SelectionLogger {
	@Override
	public void record(String when, String whichDiscipline, String whichUnit,
			String whichEpert, String whichExpertType, String whatTrouble) {
		System.err.println("在" + when + "");
	}

	@Override
	public void read() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write() {
		// TODO Auto-generated method stub
		
	}
}
