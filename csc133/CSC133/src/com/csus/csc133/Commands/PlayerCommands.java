package com.csus.csc133.Commands;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.csus.csc133.GameModel;

public class PlayerCommands extends Command {
	
	private GameModel gm = GameModel.getInstance();
	

	public PlayerCommands(String command) {
		super(command);
		// TODO Auto-generated constructor stub
	}
	
	public void actionPerformed(ActionEvent ev) {
		switch(getCommandName()) {
		case "Move": gm.playerMove();  break;
		case "Stop": gm.playerStop(); break;
		case "Turn Left": gm.playerTurnLeft(); break;
		case "Turn Right": gm.playerTurnRight(); break;
		default: System.out.println("Command Not Supported"); break;
		}
	}

}
