package com.csus.csc133.GUI;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.csus.csc133.Commands.*;

public class ViewSidebar extends Container {
	private class SidebarButton extends Button {
		SidebarButton(Command commandName) {
			this.setText(commandName.getCommandName());
			this.getStyle().setBorder(Border.createLineBorder(5, ColorUtil.GRAY));
			this.getStyle().setMargin(TOP, 10);
			this.getStyle().setBgTransparency(255);
			this.getStyle().setBgColor(ColorUtil.GRAY);
			this.getStyle().setFgColor(ColorUtil.WHITE);
			this.setCommand(commandName);
		}
		
	}
	SidebarButton change_strat = new SidebarButton(new GameCommands("Change Strategies"));
	SidebarButton move = new SidebarButton(new SystemCommands("Move"));
	SidebarButton pause = new SidebarButton(new SystemCommands("Pause"));
	
	public ViewSidebar() {
		Toolbar.setGlobalToolbar(true);
		Form viewsb = new Form(new BoxLayout(BoxLayout.Y_AXIS));
		Label l = new Label("Side Menu");
		l.getStyle().setFgColor(ColorUtil.WHITE);
		viewsb.getToolbar().addComponentToSideMenu(l);
		viewsb.getToolbar().addCommandToSideMenu(new GameCommands("Change Strategies"));
		viewsb.getToolbar().addCommandToSideMenu(new SystemCommands("About"));
		viewsb.getToolbar().addCommandToSideMenu(new SystemCommands("Exit"));
		viewsb.add(change_strat)
			.add(move)
			.add(pause);
		this.add(viewsb);
	}
}
