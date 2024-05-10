package com.csus.csc133;

import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;
import com.csus.csc133.Students.*;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.csus.csc133.Commands.SystemCommands;
import com.csus.csc133.Facilities.*;
import com.csus.csc133.GUI.*;

import java.util.Random;

public class GameModel extends Observable {
	
	/*			[Game Compass]
	 * 		
	 *(0,0)
	 * 				North 
	 * 
	 * 		West	  		East
	 * 
	 * 				South 
	 * 
	 * 							(1000,1000)
	 */
	
	public static int width = 1000;
	public static int height = 1000;
	public static boolean debug = false;
	private int gametime;
	
	public int getGametime() { return gametime; }
	
	private Vector<GameObject> objectList = new Vector<GameObject>();
	public Vector<GameObject> getObjectList() { return objectList; }
	public void setObjectList(Vector<GameObject> objectList) { this.objectList = objectList; }
	public Vector<GameObject> getFacilityList() { 
		Vector<GameObject> facList = getObjectList();
		for(GameObject j: facList) {
			if(j instanceof Student) {
				facList.remove(j);
			}
		}
		return facList;
	}
	public Vector<GameObject> getStudentList() {
		resetObjectIterator();
		Iterator<GameObject> studentList = getObjectIterator();
		Vector<GameObject> students = new Vector<GameObject>();
		int x = 0;
		while(studentList.hasNext()) {
			if(studentList.next() instanceof Student) {
				students.add(getObjectList().elementAt(x));
			}
			x++;
		}
		resetObjectIterator();
		return students;
	}
	public Vector<GameObject> getStudentStrategy() {
		Iterator<GameObject> studentList = getStudentList().iterator();
		Vector<GameObject> students = new Vector<GameObject>();
		int x = 0;
		while(studentList.hasNext()) {
			if(studentList.next() instanceof StudentStrategy) {
				students.add(getObjectList().elementAt(x));
			}
			x++;
		}
		return students;
	}
 	public LectureHall getLectureHall () {
		objectIterator = getObjectList().iterator();
		Iterator<GameObject> facList = getObjectIterator();
		int x = 0;
		while(facList.hasNext()) {
			if(facList.next() instanceof LectureHall) {
				objectIterator = getObjectList().iterator();
				return (LectureHall) getObjectList().elementAt(x);
			}
			x++;
		}
		return null;
	}
	public WaterDispenser getWaterDispenser() {
		objectIterator = getObjectList().iterator();
		Iterator<GameObject> facList = getObjectIterator();
		int x = 0;
		while(facList.hasNext()) {
			if(facList.next() instanceof WaterDispenser) {
				objectIterator = getObjectList().iterator();
				return (WaterDispenser) getObjectList().elementAt(x);
			}
			x++;
		}
		return null;
	}
	public Restroom getRestroom() {
		objectIterator = getObjectList().iterator();
		Iterator<GameObject> facList = getObjectIterator();
		int x = 0;
		while(facList.hasNext()) {
			if(facList.next() instanceof Restroom) {
				objectIterator = getObjectList().iterator();
				return (Restroom) getObjectList().elementAt(x);
			}
			x++;
		}
		return null;
	}
	
	private static GameModel gm = null;
	public static synchronized GameModel getInstance() {
		if(gm == null) {
			gm = new GameModel();
		}
		return gm;
	}
	
	private LectureManager lm = LectureManager.getInstance();
	private CollisionHandler ch = CollisionHandler.getInstance();
	private ViewManager vm = ViewManager.getInstance();
	private Iterator<GameObject> objectIterator;
	public void resetObjectIterator() { objectIterator = getObjectList().iterator(); }
	public Iterator<GameObject> getObjectIterator() { return objectIterator; }
	private Random r = new Random();
	private boolean pause = false;
	
	public boolean isPause() {
		return pause;
	}
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	public void init(){
		gametime = 0;
		createObjects();
		objectIterator = getObjectList().iterator();
	}
	
	private void createObjects() {
		//create player
		getObjectList().add(StudentPlayer.getInstance());
		
//		create npc's
		getObjectList().add(new StudentAngry(r.nextInt(width), r.nextInt(height), r.nextInt(359)));
		getObjectList().add(new StudentBiking(r.nextInt(width), r.nextInt(height), r.nextInt(359)));
		getObjectList().add(new StudentCar(r.nextInt(width), r.nextInt(height), r.nextInt(359)));
		getObjectList().add(new StudentConfused(r.nextInt(width), r.nextInt(height), r.nextInt(359)));
		getObjectList().add(new StudentFriendly(r.nextInt(width), r.nextInt(height), r.nextInt(359)));
		getObjectList().add(new StudentHappy(r.nextInt(width), r.nextInt(height), r.nextInt(359)));
		getObjectList().add(new StudentNonstop(r.nextInt(width), r.nextInt(height), r.nextInt(359)));
		getObjectList().add(new StudentSleeping(r.nextInt(width), r.nextInt(height), r.nextInt(359)));
		getObjectList().add(new StudentRunning(r.nextInt(width), r.nextInt(height), r.nextInt(359)));
		getObjectList().add(new StudentStrategy());
		getObjectList().add(new StudentStrategy());
		getObjectList().add(new StudentStrategy());
		
		
//		//create facilities
		getObjectList().add(new LectureHall(r.nextInt(width-10)-10.0, r.nextInt(height-10)-10.0,"RVR1013"));
		getObjectList().add(new LectureHall(r.nextInt(width-15)-15.0, r.nextInt(height-15)-15.0,"RVR1012"));
		getObjectList().add(new LectureHall(r.nextInt(width-20)-20.0, r.nextInt(height-10)-20.0,"RVR1023"));
		getObjectList().add(new Restroom(r.nextInt(width),r.nextInt(height)));
		getObjectList().add(new WaterDispenser(r.nextInt(width),r.nextInt(height)));
	}
	
	public void printGameInfo() {
		/*
		System.out.println("Time " + gametime + " ============================");
		while(getObjectIterator().hasNext()) {
			System.out.print(getObjectIterator().next());
		}
		objectIterator = getObjectList().iterator(); // reset iterator
		*/
		
		System.out.println(StudentPlayer.getInstance());
	}
	
	public void playerMove() {
		this.setChanged();
		if(hasChanged()) notifyObservers("Player started moving!   ");
		StudentPlayer.getInstance().startMoving();
	}
	
	public void playerStop() {
		setChanged();
		if(hasChanged()) notifyObservers("Player stopped moving!   ");
		StudentPlayer.getInstance().stopMoving();
	}
	
	public void playerTurnLeft() {
		setChanged();
		if(hasChanged()) notifyObservers("Player turned left!      ");
		StudentPlayer.getInstance().turnLeft();
	}
	
	public void playerTurnRight() {
		setChanged();
		if(hasChanged()) notifyObservers("Player turned right!     ");
		StudentPlayer.getInstance().turnRight(); 
	}
	
	public void change() {
		setChanged();
	}
	
	public void nextFrame() {
		gametime++;
		StudentPlayer s = StudentPlayer.getInstance();
		for(GameObject j: getObjectList()) {
			if(j instanceof IMoveable) {
				((IMoveable) j).move();
			}
			if(j instanceof LectureHall) {
				if(lm.getCurrentLecture() == null) {
					if(r.nextInt(90) < 12) { // 10% probability
						Lecture l = new Lecture(lm.getLectureNames()[r.nextInt(lm.getLectureNames().length)], 60);
						((LectureHall) j).setLecture(l);
						lm.setCurrentLecture(l);
						lm.setCurrentLectureHall((LectureHall) j);
					}
				} else {
					if(lm.getCurrentLectureHall() == ((LectureHall) j)) {
						int currentTime = ((LectureHall) j).getLecture().getTime();
						((LectureHall) j).getLecture().setTime(currentTime - 1);
						if(((LectureHall) j).getLecture().getTime() < 0) { // lecture end
							((LectureHall) j).endLecture();
							lm.setCurrentLecture(null);
							lm.setCurrentLectureHall((LectureHall) j);
							s.setAbsenceTime(s.getAbsenceTime()+1);
						}
					}
				}
			}
			setChanged();
			if(hasChanged()) notifyObservers("Next Frame!    ");
			/*
			if(debug) {
				System.out.print(getObjectList());
			}
			*/
		}
		
		/*
		if(StudentPlayer.getInstance().getAbsenceTime() > 6 | StudentPlayer.getInstance().getWaterIntake() > 1000 | StudentPlayer.getInstance().getHydration() <= -100) {
			System.out.println("Gameover. Time: " + gametime);
			setPause(!isPause());
			createGameOverPopup();
		}
		*/
		objectIterator = getObjectList().iterator(); // reset iterator
	}
	
	public void checkCollision() {
		for(int i=0; i < ch.getColliderList().size(); i++) {
			if(ch.getColliderList().get(i).getParent() != StudentPlayer.getInstance()) {
				if(StudentPlayer.getInstance().getCollider().collides(ch.getColliderList().get(i))) {
					if(GameModel.debug) {
						System.out.println("StudentPlayer collided with " + ch.getColliderList().get(i).getParent().getClass().getSimpleName() + " at " + gametime);
					}
					ch.getColliderList().get(i).getParent().handleCollisions(StudentPlayer.getInstance());
					setChanged();
					if(hasChanged()) notifyObservers("StudentPlayer collided with " + ch.getColliderList().get(i).getParent().getClass().getSimpleName() + "             ");
					
					if(!(StudentPlayer.getInstance().getTimeRemain() > -1.0)) {
						ch.getColliderList().get(i).setEnabled(false);
						StudentPlayer.getInstance().getCollider().setEnabled(false);
					} else {
						StudentPlayer.getInstance().getCollider().setEnabled(true);
					}
				} else {
					ch.getColliderList().get(i).setEnabled(true);
				}
			}
		}
	}
	
	private void createGameOverPopup() {
		Dialog d = new Dialog("Game Over");
		TextArea t = new TextArea("Please Try Again", 3, 10);
		Button b = new Button("Confirm");
		b.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {
				CN.exitApplication();
			 }
		});
		t.setEditable(false);
		d.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		d.setSize(new Dimension(1000, 1000));
		d.add(t);
		d.add(b);
		d.show();
		
	}
	
}
