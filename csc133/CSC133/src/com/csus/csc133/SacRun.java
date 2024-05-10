/*
* This file is the skeleton code of SacRun for the CSC133 assignment at the 
* Computer Science Department of California State University,
* Sacramento. Note that the CSC133 assignments are confidential and 
* copyrighted. It is not allowed to publish any CSC133 source code 
* to the public. Any existing source code for CSC133 assignments on the   
* internet represents that this student did/will disclose confidential  
* materials to the public and violate the designer's copyright.
*
*/

package com.csus.csc133;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import com.csus.csc133.GUI.*;

public class SacRun extends Form implements Runnable{
	
	private GameModel gm;
	private CollisionHandler ch = CollisionHandler.getInstance();
	private ViewManager vm = ViewManager.getInstance();
	private SoundManager sm = SoundManager.getInstance();
	
	public SacRun(){
		gm = GameModel.getInstance();
		gm.init();
		sm.setup();
		A3();
		UITimer timer = new UITimer(this);
		timer.schedule(150, true, this);
	}
	
	private void A3() {
		Container view = new Form(new BorderLayout());
		ViewSidebar vsb = new ViewSidebar();
		ViewStatus vst = new ViewStatus();
		ViewMap vmp = new ViewMap();
		ViewMessage vmm = new ViewMessage();
		view.add(BorderLayout.WEST, vsb);
		view.add(BorderLayout.EAST, vst);
		view.add(BorderLayout.CENTER, vmp);
		view.add(BorderLayout.SOUTH, vmm);
		gm.addObserver(vst);
		gm.addObserver(vmp);
		gm.addObserver(vmm);
		this.setScrollable(false);
		this.add(view);
		this.show();
		sm.play();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(!gm.isPause()) {
			gm.nextFrame();
			gm.checkCollision();
			//System.out.println(StudentPlayer.getInstance().getX() + ", " + StudentPlayer.getInstance().getY());
		}
		repaint();
	}
	
	@Override
	public void keyPressed(int keyCode) {
		switch ((char)keyCode) {
		case 'W':
		case 'w': gm.playerMove(); break;
		case 'A':
		case 'a': gm.playerTurnLeft(); break;
		case 'S':
		case 's': gm.playerStop(); break;
		case 'D':
		case 'd': gm.playerTurnRight(); break;
		case 'G':
		case 'g': vm.zoom(1.05f); break;
		case 'H':
		case 'h': vm.zoom(0.95f); break;
		default: gm.playerStop(); break;
		}
	}
}