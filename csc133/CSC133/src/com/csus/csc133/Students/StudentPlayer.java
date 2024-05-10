package com.csus.csc133.Students;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Shape;
import com.csus.csc133.CollisionHandler;
import com.csus.csc133.GameModel;
import com.csus.csc133.Student;
import com.csus.csc133.Shapes.Triangle;

import java.util.Random;

public class StudentPlayer extends Student {
	private static StudentPlayer s = null;
	private Random r = new Random();
	private CollisionHandler ch = CollisionHandler.getInstance();
	private Triangle tri;
	// TODO player controlled code
	private StudentPlayer() {
		setSpeed(0);
		setSize(25);
		setColor(ColorUtil.rgb(248, 3, 252));
		setHead(r.nextInt(359));
		setLocalTransform(Transform.makeIdentity());
		getLocalTransform().setTranslation((float)r.nextInt(GameModel.width), (float)r.nextInt(GameModel.height));
		ch.createCollisonBox((int)(getLocalTransform().getTranslateX()), (int)(getLocalTransform().getTranslateY()), getSize(), getSize(), this);
	}
	
	//Singleton of StudentPlayer
	public static synchronized StudentPlayer getInstance() {
		if(s == null) {
			s = new StudentPlayer();
		}
		return s;
	}
	
	
	public void startMoving() {
		setSpeed(DEFAULT_SPEED);
	}
	
	public void stopMoving() {
		setSpeed(0);
	}
	
	public void turnLeft() {
		setHead(getHead()-20);
	}
	
	public void turnRight() {
		setHead(getHead()+20);
	}

	@Override
	public void draw(Graphics g) {
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
