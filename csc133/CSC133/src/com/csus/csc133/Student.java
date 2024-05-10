package com.csus.csc133;

import com.codename1.ui.Transform;
import com.csus.csc133.Students.StudentPlayer;

public abstract class Student extends GameObject implements IMoveable, ISelectable{
	
	// default values for Student objects
	public static double DEFAULT_SPEED = 20.0;
	public static double DEFAULT_TALKATIVE_LEVEL = 2.0;
	
	// set values for all student children
	private double head = 0; // Range 0->359
	public void setHead(double head) { this.head = head; }
	public double getHead() { return head; }
	
	private double speed = DEFAULT_SPEED;
	public void setSpeed(double speed) { this.speed = speed; }
	public double getSpeed() { return speed; }

	private double TalkativeLevel = DEFAULT_TALKATIVE_LEVEL;
	public void setTalkativeLevel(double TalkativeLevel) { this.TalkativeLevel = TalkativeLevel; }
	public double getTalkativeLevel() { return TalkativeLevel; }
	
	private double timeRemain = 0; 
	public void setTimeRemain(double time) { timeRemain = time; }
	public double getTimeRemain() { return timeRemain; }
	
	private double Hydration = 0;
	public void setHydration(double hydration) { Hydration = hydration; }
	public double getHydration() { return Hydration; }
	
	private double waterIntake = 0;
	public void setWaterIntake(double waterIntake) { this.waterIntake = waterIntake; }
	public double getWaterIntake() { return waterIntake; }
	
	private double sweatingRate = 1;
	public void setSweatingRate(double sweatingRate) { this.sweatingRate = sweatingRate; }
	public double getSweatingRate() { return sweatingRate; }
	
	private double absenceTime = 0;
	public void setAbsenceTime(double absence) { absenceTime = absence; }
	public double getAbsenceTime() { return absenceTime; }
	
	private String message = "";
	public void setMessage(String message) { this.message = message; }
	
	private GameModel gm = GameModel.getInstance();
	
	private Collider collider;
	public Collider getCollider() {
		return collider;
	}
	public void setCollider(Collider collider) {
		this.collider = collider;
	}
	public void drink(double currentHydration) {
		Hydration = 200;
		waterIntake += Hydration - currentHydration;
	}

	public void move() {
		if(head > 360) {
			head -= 360;
		}
		if(head < 0) {
			head += 360;
		}
		if(timeRemain > -1.0) {
			timeRemain--;
		} else {
			if(checkBounds()) {
				head += 180.0; // head always flips when colliding with an edge
			}
			else {
				getLocalTransform().setTranslation((float)Math.ceil(getLocalTransform().getTranslateX() + Math.cos(90.0 - Math.toRadians(head)) * speed), (float)Math.ceil(getLocalTransform().getTranslateY() + Math.sin(90.0 - Math.toRadians(head)) * speed));
				getCollider().setX((int) Math.ceil(getCollider().getX() + Math.cos(90.0 - Math.toRadians(head)) * speed));
				getCollider().setY((int) Math.ceil(getCollider().getY() + Math.sin(90.0 - Math.toRadians(head)) * speed));
			}
		}
		Hydration -= sweatingRate;
	}
	
	// Checking if a student is out of bounds.
	public boolean checkBounds() {
		if(getLocalTransform().getTranslateX() > GameModel.width) {	// checking Student's x coordinate goes past the
			getLocalTransform().setTranslation(GameModel.width, getLocalTransform().getTranslateY());		// east edge of the screen
			return true;
		}
		if(getLocalTransform().getTranslateY() > GameModel.height) {	// checking Student's y coordinate goes past the
			getLocalTransform().setTranslation(getLocalTransform().getTranslateX(),GameModel.height);		// north edge of the screen
			return true;
		}
		if(getLocalTransform().getTranslateX() < 0.0) {				// checking Student's x coordinate goes past the
			getLocalTransform().setTranslation(0, getLocalTransform().getTranslateY());					// west edge of the screen
			return true;
		}
		if(getLocalTransform().getTranslateY() < 0.0) {				// checking Student's y coordinate goes past the 
			getLocalTransform().setTranslation(getLocalTransform().getTranslateX(),0);					// south edge of the screen
			return true;	
		}
		return false;					// only false if Students x and y coordinates are
										// within bounds
	}
	
	@Override
	public void handleCollisions(Student s) {	 // The student collided with another student
		if(getCollider().isEnabled()) {
			if(timeRemain == -1.0) {
				s.timeRemain = TalkativeLevel;
				timeRemain = s.TalkativeLevel;
				getCollider().setEnabled(false);
			} else {
				s.timeRemain--;
			}
		}
		String classname = s.getClass().toString();
		classname = (String) classname.subSequence(classname.lastIndexOf(".")+1, classname.length());
		
		gm.change();
		gm.notifyObservers("StudentPlayer collided with " + classname + "!");
	}
	
	public String toString() {
		String classname = getClass().toString();
		
		// this a cheeky hack to get the classname as a simple string since getClass().getSimplename()
		// is depreciated on codenameone. WHY??? :(
		classname = (String) classname.subSequence(classname.lastIndexOf(".")+1, classname.length()); 
		
		String returnString = (classname + ", " 
								+ "pos: (" + getLocalTransform().getTranslateX() + ", " + getLocalTransform().getTranslateY() + "), "
								+ "head: " + head + ", "
								+ "speed: " + speed + ", "
								+ "Hydration: " + Hydration + ", "
								+ "talkativeLevel: " + TalkativeLevel + ", "
								+ "timeRemain: " + timeRemain);
		
		if(this instanceof StudentPlayer) {
			returnString += (", absenceTime: " + absenceTime + ", "
							+ "WaterIntake: " + waterIntake);
		} else {
			if(message != null) {
				returnString += (", " + message); 
			}
		}
		
		if(this instanceof StudentStrategy) {
			StudentStrategy s = (StudentStrategy) this;
			returnString += (", Strategy: (" + s.getStrategy().getStratName() + ")");
		}
		returnString += ("\n");
		
		return returnString;
	}
	
	
	
}
