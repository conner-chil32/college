package com.csus.csc133.Students;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.csus.csc133.CollisionHandler;
import com.csus.csc133.GameModel;
import com.csus.csc133.Student;
import com.csus.csc133.Shapes.Triangle;

import java.util.Random;

public class StudentConfused extends Student {
	private Triangle tri;
	private Random r;
	private CollisionHandler ch = CollisionHandler.getInstance();
	public StudentConfused(double x, double y, double head) {
		setSize(25);
		setColor(ColorUtil.rgb(150, 41, 204));
		r = new Random();
		setLocalTransform(Transform.makeIdentity());
		getLocalTransform().setTranslation((float)x, (float)y);
		ch.createCollisonBox((int)(getLocalTransform().getTranslateX()), (int)(getLocalTransform().getTranslateY()), getSize(), getSize(), this);
		setHead(head);
	}
	@Override
	public void move() {
		if(getHead() > 359) {
			setHead(getHead()-360);
		}
		if(getTimeRemain() > -1.0) {
			setTimeRemain(getTimeRemain()-1);
		} else {
			if(checkBounds()) {
				setHead(getHead()+180.0); // head always flips when colliding with an edge
			}
			else {
				setHead(r.nextInt(359));
				getLocalTransform().setTranslation((float)Math.ceil(getLocalTransform().getTranslateX() + Math.cos(90.0 - Math.toRadians(getHead())) * getSpeed()), (float)Math.ceil(getLocalTransform().getTranslateY() + Math.sin(90.0 - Math.toRadians(getHead())) * getSpeed()));
				getCollider().setX((int) Math.ceil(getCollider().getX() + Math.cos(90.0 - Math.toRadians(getHead())) * getSpeed()));
				getCollider().setY((int) Math.ceil(getCollider().getY() + Math.sin(90.0 - Math.toRadians(getHead())) * getSpeed()));
			}
		}
		setHydration(getHydration()-getSweatingRate());
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
