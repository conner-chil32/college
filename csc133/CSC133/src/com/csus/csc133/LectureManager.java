package com.csus.csc133;

import com.csus.csc133.Facilities.LectureHall;

public class LectureManager {
	
	private String[] lectureNames = new String[] {
		"CSC133", "CSC134", "CSC137", "CSC101", "CSC103"	
	};
	public String[] getLectureNames() {
		return lectureNames;
	}

	private Lecture currentLecture = null;
	public Lecture getCurrentLecture() {
		return currentLecture;
	}
	public void setCurrentLecture(Lecture currentLecture) {
		this.currentLecture = currentLecture;
	}
	
	private LectureHall currentLectureHall = null;
	public LectureHall getCurrentLectureHall() {
		return currentLectureHall;
	}
	public void setCurrentLectureHall(LectureHall currentLectureHall) {
		this.currentLectureHall = currentLectureHall;
	}

	private static LectureManager lm = null;
	public static synchronized LectureManager getInstance() {
		if(lm == null) {
			lm = new LectureManager();
		}
		return lm;
	}
}
