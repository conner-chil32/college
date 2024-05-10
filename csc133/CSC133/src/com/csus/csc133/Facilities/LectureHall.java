package com.csus.csc133.Facilities;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.csus.csc133.Facility;
import com.csus.csc133.GameModel;
import com.csus.csc133.Lecture;
import com.csus.csc133.LectureManager;
import com.csus.csc133.Student;
import com.csus.csc133.Shapes.Circle;
import com.csus.csc133.Shapes.Rectangle;
import com.csus.csc133.Students.StudentPlayer;

public class LectureHall extends Facility {
	
	private Lecture lecture;
	public Lecture getLecture() { return lecture; }
	public void setLecture(Lecture lecture) { this.lecture = lecture; }
	private Rectangle rect;
	
	private LectureManager lm = LectureManager.getInstance();
	
	public LectureHall(double x, double y,String name) {
		setSize(25);
		setLocalTransform(Transform.makeIdentity());
		getLocalTransform().setTranslation((float)x, (float)y);
		ch.createCollisonBox((int)(getLocalTransform().getTranslateX()), (int)(getLocalTransform().getTranslateY()), getSize(), getSize(), this);
		setName(name);
		setColor(ColorUtil.BLUE);
	}
	
	@Override
	public void handleCollisions(Student s) {
		if(s instanceof StudentPlayer && !getIgnoreList().contains(s)) {
			endLecture();
		}
		
	}
	
	public void endLecture() {
		if(lecture != null) {
			getLecture().end();
			setLecture(null);
			lm.setCurrentLecture(null);
		}
	}
	
	public void checkEnd() {
		if(getLecture().getTime() < 0) {
			endLecture();
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
		
		Transform oldXForm2 = gXform.copy();
		gXform.translate(-75, 285);
		gXform.scale(-1f, 1f);
		gXform.rotate((float)Math.toRadians(180), gXform.getTranslateX(), gXform.getTranslateY());
		g.setTransform(gXform);
		if(lecture == null) {
			g.drawChars("NO CLASS".toCharArray(), 0, 8, (int)(gXform.getTranslateX()), (int)(gXform.getTranslateY()));
		} else {
			g.drawChars(lecture.getName().toCharArray(), 0, 6, (int)(gXform.getTranslateX()), (int)(gXform.getTranslateY()));
		}
		g.setTransform(oldXForm2);
		if(GameModel.debug || isSelected()) {
			g.setColor(getCollider().getColor());
			g.drawRect((int)(gXform.getTranslateX()), (int)(gXform.getTranslateY()), getSize(), getSize());
		}
		g.setTransform(oldXForm);
		
	}
	
	
	
	

}
