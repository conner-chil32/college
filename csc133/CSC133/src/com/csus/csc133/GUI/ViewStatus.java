 package com.csus.csc133.GUI;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.csus.csc133.GameModel;
import com.csus.csc133.LectureManager;
import com.csus.csc133.Students.StudentPlayer;


public class ViewStatus extends Container implements Observer {
	private GameModel gm = GameModel.getInstance();
	private StudentPlayer sp = StudentPlayer.getInstance();
	private LectureManager lm = LectureManager.getInstance();
	
	private Label lecture_status = new Label("no class now");
	private Label time_remaining = new Label("0.0");
	private Label game_time = new Label("0.0");
	private Label absence = new Label("0.0");
	private Label hydration = new Label("100.0");
	private Label water_intake = new Label("0.0");
	private Label hold = new Label("0.0");
	
	public ViewStatus() {
		getStyle().setPadding(15, 15, 15, 15);
		Form ViewStatus = new Form("Game Info     ", new BoxLayout(BoxLayout.Y_AXIS));
		ViewStatus.setScrollable(false);
		ViewStatus.add(new Label("Current Lecture:"))
			.add(lecture_status)
			.add(new Label("Lecture Time Remaining:"))
			.add(time_remaining)
			.add(new Label("Game Time:"))
			.add(game_time)
			.add(new Label("Absence:"))
			.add(absence)
			.add(new Label("Hydration:"))
			.add(hydration)
			.add(new Label("WaterIntake:"))
			.add(water_intake)
			.add(new Label("Hold:"))
			.add(hold);
		this.add(ViewStatus);
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		if(lm.getCurrentLecture() != null) {
			lecture_status.setText(lm.getCurrentLectureHall().getLecture().getName());
			time_remaining.setText(String.valueOf(lm.getCurrentLectureHall().getLecture().getTime()));
		} else {
			lecture_status.setText("no class now");
			time_remaining.setText("0.0");
		}
		game_time.setText(String.valueOf(gm.getGametime()));
		absence.setText(String.valueOf(sp.getAbsenceTime()));
		hydration.setText(String.valueOf(sp.getHydration()));
		water_intake.setText(String.valueOf(sp.getWaterIntake()));
		hold.setText(String.valueOf(sp.getTimeRemain()));
	}

}
