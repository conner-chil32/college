package com.csus.csc133.Shapes;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.csus.csc133.Student;

public abstract class Shape {
	private int[] x_points;
	public int[] getXPoints() { return x_points; }
	public void setXPoints(int[] x_points) {
		this.x_points = x_points;
	}
	private int[] y_points;
	public int[] getYPoints() { return y_points; }
	public void setYPoints(int[] y_points) {
		this.y_points = y_points;
	}
	private int nPoints;
	public int getNPoints() { return nPoints; }
	public void setNPoints(int nPoints) {
		this.nPoints = nPoints;
	}
	private int size;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public abstract void draw(Graphics g, boolean fill);
	
}
