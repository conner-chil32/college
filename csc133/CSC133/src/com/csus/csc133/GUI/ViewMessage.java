package com.csus.csc133.GUI;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.plaf.Border;

public class ViewMessage extends Container implements Observer{
	private Label console = new Label();
	public ViewMessage() {
		getStyle().setBorder(Border.createLineBorder(1, ColorUtil.red(10)));
		getStyle().setPadding(BOTTOM, 5);
		getStyle().setMargin(RIGHT, 20);
		setConsoleText("Game Start!   ");
		this.add(console);
	}
	
	public void setConsoleText(String newText) {
		console.setText(newText);
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		String s = (String) data;
		if(!s.contains("Next Frame")) setConsoleText(s);
	}
	
}
