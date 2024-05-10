package com.csus.csc133.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.csus.csc133.*;
import com.csus.csc133.Facilities.*;
import com.csus.csc133.Facilities.*;
import com.csus.csc133.Shapes.*;
import com.csus.csc133.Students.StudentPlayer;

public class ViewMap extends Container implements Observer {
	private GameModel gm = GameModel.getInstance();
	private CollisionHandler ch = CollisionHandler.getInstance();
	private SelectionHandler sh = SelectionHandler.getInstance();
	private ArrayList<GameObject> objectList = Collections.list(gm.getObjectList().elements());
	private ViewManager vm = ViewManager.getInstance();
	
	private int pPrevDragLocX, pPrevDragLocY;
	private Collider m = ch.createMouseCollision(0, 0, 0, 0);
	private Transform gXform = Transform.makeIdentity();
	
	public ViewMap() {
		getStyle().setBorder(Border.createLineBorder(5, ColorUtil.rgb(255,0,0)));
		getStyle().setPadding(Component.BOTTOM, GameModel.height);
		getStyle().setPadding(Component.RIGHT, GameModel.width);
	}

	@Override
	public void update(Observable observable, Object data) {
		repaint();	
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int absX = getParent().getAbsoluteX();
		int absY = getParent().getAbsoluteY();
		g.getTransform(gXform);
		gXform.translate(absX, absY);
		vm.updateVTM();
		gXform.concatenate(vm.getVTM());
		g.setTransform(gXform);
		if(!objectList.isEmpty()) {
			for(int i=0; i < objectList.size(); i++ ) {
				objectList.get(i).draw(g);
			}
		}
		g.setTransform(gXform);
		g.setColor(ColorUtil.BLACK);
		g.drawRect(150, 0, GameModel.width+25, GameModel.height+25);
		g.drawRect(m.getX(), m.getY(), m.getWidth(), m.getHeight());
		g.resetAffine();
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		pPrevDragLocX = x;
		pPrevDragLocY = y;
		
	}
	
	@Override
	public void pointerReleased(int x, int y) {
		m = ch.createMouseCollision(150+(-vm.viewL)+x, vm.viewHeight+(-y), 20, 20);
		for(int i=0; i < ch.getColliderList().size(); i++) {
			if(m.collides(ch.getColliderList().get(i))) {
				ch.getColliderList().get(i).getParent().setSelected(true);
				sh.setSelected(ch.getColliderList().get(i).getParent());
			} else {
				ch.getColliderList().get(i).getParent().setSelected(false);
			}
		}
	}
	
	@Override
	public void pointerDragged(int x, int y) {
		double dx = pPrevDragLocX - x;
		double dy = pPrevDragLocY - y;
		dx *= (vm.viewWidth/(float)GameModel.width);
		dy *= (vm.viewHeight/(float)GameModel.height);
		vm.panHorizontal(dx);
		this.repaint();
		vm.panVertical(dy);
		this.repaint();
		pPrevDragLocX = x;
		pPrevDragLocY = y;
	}
	
	@Override
	public boolean pinch(float scale) {
		vm.zoom(scale);
		this.repaint();
		return true;
	}
}
