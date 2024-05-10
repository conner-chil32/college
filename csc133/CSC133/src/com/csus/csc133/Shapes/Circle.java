package com.csus.csc133.Shapes;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Circle extends Shape {
	
	public static Circle createCircle(int x, int y, int size) {
		Circle circ = new Circle();
		circ.setXPoints(new int[] {x});
		circ.setYPoints(new int[] {y});
		circ.setSize(size);
		return circ;
	}

	@Override
	public void draw(Graphics g, boolean fill) {
		// TODO Auto-generated method stub
		g.drawArc(getXPoints()[0], getYPoints()[0], getSize(), getSize(), 0, 360);
		if(fill) g.fillArc(getXPoints()[0], getYPoints()[0], getSize(), getSize(), 0, 360);
	}
}
