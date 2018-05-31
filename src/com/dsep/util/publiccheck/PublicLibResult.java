package com.dsep.util.publiccheck;

public enum PublicLibResult {
	
	/**不在公共库中*/
    MISS('0'),
    /**存在异议*/
    OBJECTIONS('1');
    
	private char publibResult;
    
    private PublicLibResult(char publibResult){
    	this.setPublibResult(publibResult);
    }

	public char getPublibResult() {
		return publibResult;
	}

	public void setPublibResult(char publibResult) {
		this.publibResult = publibResult;
	}
}
