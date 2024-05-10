package com.csus.csc133;

public class Lecture {
	
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	private int time;
	public int getTime() { return time; }
	public void setTime(int time) { this.time = time; }
	
	public Lecture(String name, int time) {
		this.name = name;
		this.time = time;
	}
	
	public void end() {
		time = 0;
	}
	
	

}
