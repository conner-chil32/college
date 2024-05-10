package com.csus.csc133.Facilities;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.csus.csc133.Facility;
import com.csus.csc133.GameModel;
import com.csus.csc133.Student;
import com.csus.csc133.Shapes.Rectangle;

public class Restroom extends Facility {
	
	private Rectangle rect;

	public Restroom(double x, double y) {
		setSize(25);
		setLocalTransform(Transform.makeIdentity());
		getLocalTransform().setTranslation((float)x, (float)y);
		ch.createCollisonBox((int)(getLocalTransform().getTranslateX()), (int)(getLocalTransform().getTranslateY()), getSize(), getSize(), this);
		setColor(ColorUtil.GREEN);
	}

	@Override
	public void handleCollisions(Student s) {
		if(!getIgnoreList().contains(s)) {
			s.setWaterIntake(0);
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getColor());
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform oldXForm = gXform.copy();
		gXform.translate(150,-100);
		gXform.concatenate(getLocalTransform());
		g.setTransform(gXform);
		rect = Rectangle.createRectangle((int)(gXform.getTranslateX()), (int)(gXform.getTranslateY()), getSize());
		rect.draw(g, true);
		if(GameModel.debug || isSelected()) {
			g.setColor(getCollider().getColor());
			g.drawRect((int)(gXform.getTranslateX()-getSize()), (int)(gXform.getTranslateY()-getSize()), getSize()*2, getSize()*2);
		}
		g.setTransform(oldXForm);
		
	}

	

}
