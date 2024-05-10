package com.csus.csc133;

import com.csus.csc133.Facilities.LectureHall;

public abstract class Facility extends GameObject{
	
	// for LectureHalls
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String toString() {
		
		String classname = getClass().toString();
		
		// this is the same cheeky hack as in the Student class
		// I will try to look for a better way of doing this,
		// but I can't think of anything.
		classname = (String) classname.subSequence(classname.lastIndexOf(".")+1, classname.length());
		
		String returnString = classname;
		
		if(this instanceof LectureHall) {
			returnString += " " + name;
		}
		
		returnString += ", pos: (" + getLocalTransform().getTranslateX() + ", " + getLocalTransform().getTranslateY() + ")";
		
		if(this instanceof LectureHall) {
			returnString += ", Current Lecture: ";
			if(((LectureHall)this).getLecture() != null) {
				returnString += (((LectureHall)this).getLecture().getName() + ", "
								+ "Time Remaining: " + ((LectureHall)this).getLecture().getTime() + "\n");
			}
			else returnString += "null\n";
		} else returnString += "\n";
	
		
		return returnString;
	}
	
}
