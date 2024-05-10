package com.csus.csc133.Commands;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.csus.csc133.GameModel;
import com.csus.csc133.SelectionHandler;
import com.csus.csc133.SoundManager;

public class SystemCommands extends Command {

	private GameModel gm = GameModel.getInstance();
	private SelectionHandler sh = SelectionHandler.getInstance();
	private SoundManager sm = SoundManager.getInstance();
	public SystemCommands(String command) {
		super(command);
		// TODO Auto-generated constructor stub
	}
	
	public void actionPerformed(ActionEvent ev) {
		switch(getCommandName()) {
		case "About": createAboutPopup(); break;
		case "Exit": createExitPopup(); break;
		case "Next Frame": gm.nextFrame(); break;
		case "Pause": 
			gm.setPause(!gm.isPause());
			if(gm.isPause()) {
				sm.pause();
			} else {
				sm.play();
			}
			break;
		case "Move": createMovePopup(); break;
		default: System.out.println("Command Not Supported"); break;
		}
	}

	private void createMovePopup() {
		// TODO Auto-generated method stub
		Dialog d;
		if(sh.getSelected() != null) {
			d = new Dialog("Move Object");
			Container f = new Container();
			Label l = new Label("Bounds: 0,0 -> 1000,800");
			Label l1 = new Label("Currently Selected: " + sh.getSelected().getClass().getSimpleName());
			Label l2 = new Label("Current Coordinates: " + sh.getSelected().getLocalTransform().getTranslateX() + ", " + sh.getSelected().getLocalTransform().getTranslateY());
			
			Label x1 = new Label("X:");
			Label y1 = new Label("Y:");
			TextArea x = new TextArea("", 1, 5);
			TextArea y = new TextArea("", 1, 5);
			Button b = new Button("Confirm");
			b.addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent evt) {
					 if(x.getText() == "" || y.getText() == "") {
						 Dialog j = new Dialog("Error");
						 Label l3 = new Label("Please enter a valid x and/or y coordinate");
						 Button b3 = new Button("Confirm");
						 b3.addActionListener(new ActionListener() {
							 public void actionPerformed(ActionEvent evt) {
								 j.dispose();
							 }
						 });
						 j.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
						 j.add(l3);
						 j.add(b3);
						 j.show();
					 } else {
						 sh.getSelected().getLocalTransform().setTranslation(Float.parseFloat(x.getText()), Float.parseFloat(y.getText()));
						 sh.getSelected().getCollider().setX(Integer.parseInt(x.getText()));
						 sh.getSelected().getCollider().setY(Integer.parseInt(y.getText()));
						 d.dispose();
					 }
				 }
			});
			Button b2 = new Button("Exit");
			b2.addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent evt) {
					 d.dispose();
				 }
			});
			d.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
			f.setLayout(new BoxLayout(BoxLayout.X_AXIS));
			f.add(x1);
			f.add(x);
			f.add(y1);
			f.add(y);
			d.setSize(new Dimension(1000, 200));
			d.add(l);
			d.add(l1);
			d.add(l2);
			d.add(f);
			d.add(b);
			d.add(b2);
		} else {
			d = new Dialog("Error");
			Label x = new Label("Please Select An Object To Move");
			Button b = new Button("Confirm");
			b.addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent evt) {
					 d.dispose();
				 }
			});
			d.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
			d.add(x);
			d.add(b);
		}
		d.show();
	}

	private void createAboutPopup() {
		Dialog d = new Dialog("A3");
		TextArea t = new TextArea("Conner Childers Spring Semester 2024", 3, 10);
		Button b = new Button("Confirm");
		b.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {
				 d.dispose();
			 }
		});
		t.setEditable(false);
		d.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		d.setSize(new Dimension(500, 100));
		d.add(t);
		d.add(b);
		d.show();
	}
	
	private void createExitPopup() {
		Dialog d = new Dialog("Exit");
		TextArea t = new TextArea("Are You Sure?", 3, 10);
		Button y = new Button("Yes");
		y.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {
				 CN.exitApplication();
			 }
		});
		Button n = new Button("No");
		n.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {
				 d.dispose();
			 }
		});
		t.setEditable(false);
		
		Form buttons = new Form(new BorderLayout());
		buttons.add(BorderLayout.WEST, y);
		buttons.add(BorderLayout.EAST, n);
		
		d.setLayout(new BorderLayout());
		d.setSize(new Dimension(500, 100));
		d.add(BorderLayout.CENTER, t);
		d.add(BorderLayout.SOUTH, buttons);
		d.show();
	}

}
