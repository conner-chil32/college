package com.csus.csc133.Strategies;

import java.util.Random;

import com.csus.csc133.Strategy;
import com.csus.csc133.Student;

public class RandomStrategy extends Strategy {

	Random r = new Random();
	Student s;
	public RandomStrategy(Student s) {
		setStratName("Random Strategy");
		this.s = s;
	}
	@Override
	public void move() {
		if(s.getHead() > 359) {
			s.setHead(s.getHead()-360);
		}
		if(s.getTimeRemain() > -1.0) {
			s.setTimeRemain(s.getTimeRemain()-1);
		} else {
			if(s.checkBounds()) {
				s.setHead(s.getHead()+180.0); // head always flips when colliding with an edge
			}
			else {
				s.setHead(r.nextInt(359));
				s.getLocalTransform().setTranslation((float)Math.ceil(s.getLocalTransform().getTranslateX() + Math.cos(90.0 - Math.toRadians(s.getHead())) * s.getSpeed()), (float)Math.ceil(s.getLocalTransform().getTranslateY() + Math.sin(90.0 - Math.toRadians(s.getHead())) * s.getSpeed()));
			}
		}
		s.setHydration(s.getHydration()-s.getSweatingRate());
		
	}
	
}
