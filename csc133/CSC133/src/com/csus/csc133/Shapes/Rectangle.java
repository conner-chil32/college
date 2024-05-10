package com.csus.csc133.Shapes;

import com.codename1.ui.Graphics;

public class Rectangle extends Shape {
	public static Rectangle createRectangle(int x, int y, int size) {
		Rectangle rect = new Rectangle();
		rect.setXPoints(new int[] {-size+x, size+x, size+x, -size+x});
		rect.setYPoints(new int[] {size+y, size+y, -size+y, -size+y});
		rect.setNPoints(rect.getXPoints().length);
		return rect;
	}

	@Override
	public void draw(Graphics g, boolean fill) {
		// TODO Auto-generated method stub
		g.drawPolygon(getXPoints(), getYPoints(), getNPoints());
		if(fill) g.fillPolygon(getXPoints(), getYPoints(), getNPoints());
	}
}
