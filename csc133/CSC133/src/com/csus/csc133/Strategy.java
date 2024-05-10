package com.csus.csc133;

public abstract class Strategy {
	private String stratName;

	public String getStratName() {
		return stratName;
	}

	public void setStratName(String stratName) {
		this.stratName = stratName;
	}
	
	public abstract void move();

}
