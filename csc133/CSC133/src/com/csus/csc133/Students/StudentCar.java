package com.csus.csc133.Students;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.csus.csc133.CollisionHandler;
import com.csus.csc133.GameModel;
import com.csus.csc133.Student;
import com.csus.csc133.Shapes.Triangle;

public class StudentCar extends Student {
	private Triangle tri;
	private CollisionHandler ch = CollisionHandler.getInstance();
	public StudentCar(double x, double y, double head) {
		setSize(25);
		setColor(ColorUtil.rgb(150, 41, 204));
		setSpeed(Student.DEFAULT_SPEED * 5);
		setSweatingRate(0);
		setMessage("Driving");
		setLocalTransform(Transform.makeIdentity());
		getLocalTransform().setTranslation((float)x, (float)y);
		ch.createCollisonBox((int)(getLocalTransform().getTranslateX()), (int)(getLocalTransform().getTranslateY()), getSize(), getSize(), this);
		setHead(head);
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getColor());
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform oldXForm = gXform.copy();
		gXform.translate(150,-100);
		gXform.rotate(-(float)Math.toRadians(getHead()-20), getLocalTransform().getTranslateX(), getLocalTransform().getTranslateY()+200);
		gXform.concatenate(getLocalTransform());
		g.setTransform(gXform);
		tri = Triangle.createTriangle((int)(gXform.getTranslateX()), (int)(gXform.getTranslateY()), getSize());
		tri.draw(g, true);
		if(GameModel.debug || isSelected()) {
			g.setColor(getCollider().getColor());
			g.drawRect((int)(gXform.getTranslateX()-getSize()), (int)(gXform.getTranslateY()-getSize()), getSize()*2, getSize()*2);
		}
		g.setTransform(oldXForm);
		
	}
}
