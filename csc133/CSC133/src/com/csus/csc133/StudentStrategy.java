package com.csus.csc133;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.csus.csc133.Shapes.Triangle;
import com.csus.csc133.Strategies.*;

public class StudentStrategy extends Student {
	
	private Triangle tri;
	private Strategy s;
	public Strategy getStrategy() {
		return s;
	}
	public void setStrategy(Strategy s) {
		this.s = s;
	}

	private Random r = new Random();
	public StudentStrategy() {
		setSize(25);
		setColor(ColorUtil.rgb(150, 41, 204));
		setHead(r.nextInt(359));
		setLocalTransform(Transform.makeIdentity());
		getLocalTransform().setTranslation((float)r.nextInt(GameModel.width), (float)r.nextInt(GameModel.height));
		ch.createCollisonBox((int)(getLocalTransform().getTranslateX()), (int)(getLocalTransform().getTranslateY()), getSize(), getSize(), this);
		changeStrategy();
	}
	
	public void changeStrategy() {
		int i = r.nextInt(100) % 3;
		switch(i) {
		case 0: s = new VerticalStrategy(this); break;
		case 1: s = new HorizontalStrategy(this); break;
		case 2: s = new RandomStrategy(this); break;
		}
	}	
	
	@Override
	public void move() {
		s.move();
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
