package com.csus.csc133;

import java.util.Vector;

import com.codename1.charts.util.ColorUtil;

public class CollisionHandler {
	
	private Vector<Collider> colliderList = new Vector<Collider>();
	public Vector<Collider> getColliderList() {
		return colliderList;
	}

	private static CollisionHandler cb = null;
	public static synchronized CollisionHandler getInstance() {
		if(cb == null) {
			cb = new CollisionHandler();
		}
		return cb;
	}
	
	public void createCollisonBox(int x, int y, int width, int height, GameObject parent) {
		Collider c = new Collider(x, y, width, height, ColorUtil.BLUE, parent, true);
		parent.setCollider(c);
		colliderList.add(c);
		/*
		if(GameModel.debug) {
			System.out.println("Added Collider For: " + parent.getClass().getSimpleName());
			System.out.println("Current colliderList:" + colliderList.toString());
		}
		*/
	}
	
	public Collider createMouseCollision(int x, int y, int width, int height) {
		Collider c = new Collider(x, y, width, height, ColorUtil.BLUE, null, true);
		return c;
	}
}
