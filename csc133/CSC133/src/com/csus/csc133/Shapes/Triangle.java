package com.csus.csc133.Shapes;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.csus.csc133.Student;

public class Triangle extends Shape {
	
	public static Triangle createTriangle(int x, int y, int size) {
		Triangle tri = new Triangle();
		tri.setXPoints(new int[] {(-size+x), 0+x, size+x});
		tri.setYPoints(new int[] {y-size, (size+(size/5))+y, y-size});
		tri.setNPoints(tri.getXPoints().length);
		return tri;
	}

	@Override
	public void draw(Graphics g, boolean fill) {
		// TODO Auto-generated method stub
		g.drawPolygon(getXPoints(), getYPoints(), getNPoints());
		if(fill) g.fillPolygon(getXPoints(), getYPoints(), getNPoints());
	}
}
