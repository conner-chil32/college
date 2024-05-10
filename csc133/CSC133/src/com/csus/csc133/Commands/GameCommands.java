package com.csus.csc133.Commands;

import java.util.Random;
import java.util.Vector;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.csus.csc133.GameModel;
import com.csus.csc133.GameObject;
import com.csus.csc133.Student;
import com.csus.csc133.StudentStrategy;
import com.csus.csc133.Students.StudentPlayer;

public class GameCommands extends Command {

	private GameModel gm = GameModel.getInstance();
	private Random r;
	
	public GameCommands(String command) {
		super(command);
		// TODO Auto-generated constructor stub
	}
	public void actionPerformed(ActionEvent ev) {
		switch(getCommandName()) {
		case "Change Strategies": changeStrats(); break;
		case "Lecture Hall": gm.getLectureHall().handleCollisions(StudentPlayer.getInstance()); gm.change(); gm.notifyObservers("Lecture has ended!"); break;
		case "WaterDispenser": gm.getWaterDispenser().handleCollisions(StudentPlayer.getInstance()); gm.change(); gm.notifyObservers("Player drank!"); break;
		case "Restroom": gm.getRestroom().handleCollisions(StudentPlayer.getInstance()); gm.change(); gm.notifyObservers("Player used the restroom!"); break;
		case "Student": createStudentPopup(); break;
		default: System.out.println("Command Not Supported"); break;
		}
	}
	
	private void changeStrats() {
		Vector<GameObject> students = new Vector<GameObject>();
		students = gm.getStudentStrategy();
		StudentStrategy go;
		for(int i=0; i < students.size(); i++) {
			go = (StudentStrategy) students.get(i);
			go.changeStrategy();
		}
		gm.change();
		gm.notifyObservers("Student Strategies have changed     ");
		
	}
	
	private void createStudentPopup() {
		Dialog d = new Dialog("Student Select");
		Label L1 = new Label("0-StudentAngry, 1-StudentBiking,");
		Label L2 = new Label("2-StudentCar, 3-StudentConfused,");
		Label L3 = new Label("4-StudentFriendly, 5-StudentHappy,");
		Label L4 = new Label("6-StudentNonstop, 7-StudentSleeping,");
		Label L5 = new Label("8-StudentRunning, 9-Student Strategy");
		TextArea t = new TextArea();
		Container f = new Container();
		f.setLayout(new BorderLayout());
		Button b = new Button("Confirm");
		Button e = new Button("Exit");
		b.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {
				 if(t.getText() == null) {
					 createErrorPopup();
				 } else {
					 switch(t.getText()) {
					 case "0": StudentPlayer.getInstance().handleCollisions((Student) gm.getStudentList().get(0)); d.dispose(); break;
					 case "1": StudentPlayer.getInstance().handleCollisions((Student) gm.getStudentList().get(1)); d.dispose(); break;
					 case "2": StudentPlayer.getInstance().handleCollisions((Student) gm.getStudentList().get(2)); d.dispose(); break;
					 case "3": StudentPlayer.getInstance().handleCollisions((Student) gm.getStudentList().get(3)); d.dispose(); break;
					 case "4": StudentPlayer.getInstance().handleCollisions((Student) gm.getStudentList().get(4)); d.dispose(); break;
					 case "5": StudentPlayer.getInstance().handleCollisions((Student) gm.getStudentList().get(5)); d.dispose(); break;
					 case "6": StudentPlayer.getInstance().handleCollisions((Student) gm.getStudentList().get(6)); d.dispose(); break;
					 case "7": StudentPlayer.getInstance().handleCollisions((Student) gm.getStudentList().get(7)); d.dispose(); break;
					 case "8": StudentPlayer.getInstance().handleCollisions((Student) gm.getStudentList().get(8)); d.dispose(); break;
					 case "9": break; //Student Strategy
					 default: createErrorPopup(); break;
					 }
				 }
			 }
		});
		e.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {
				 d.dispose();
			 }
		});
		f.add(BorderLayout.WEST, b).add(BorderLayout.EAST,e);
		d.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		d.add(L1);
		d.add(L2);
		d.add(L3);
		d.add(L4);
		d.add(L5);
		d.add(t);
		d.add(f);
		d.show();
		
	}
	
	private void createErrorPopup() {
		Dialog d = new Dialog("Error");
		Label l = new Label("Please Enter a valid input");
		Button b = new Button("Confirm");
		d.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		b.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {
				 d.dispose();
			 }
		});
		d.add(l);
		d.add(b);
		d.show();
	}

}
