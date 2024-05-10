package com.csus.csc133;

import java.util.Vector;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public abstract class GameObject implements ISelectable {
	
	//RED = (255, 0, 0)
	//PINK = (248, 3, 252)
	/*
	private double x, y;
	public double getX() { return x; }
	public void setX(double x) { this.x = x; }
	public double getY() { return y; }
	public void setY(double y) { this.y = y; }
	*/
	private int size = 200;
	public int getSize() { return size; }
	public void setSize(int size) { this.size = size; }
	private int color = ColorUtil.rgb(248, 3, 252);
	public int getColor() { return color; }
	public void setColor(int color) { this.color = color; }
	private Collider collider;
	public Collider getCollider() { return collider; }
	public void setCollider(Collider collider) { this.collider = collider; }
	public CollisionHandler ch = CollisionHandler.getInstance();
	
	private boolean selected;
	
	private Vector<GameObject> ignoreList = new Vector<GameObject>();
	public Vector<GameObject> getIgnoreList() { return ignoreList; }
	public void addToIgnoreList(GameObject object) { ignoreList.add(object); }
	public void removeFromIgnoreList(GameObject object) { ignoreList.remove(object); }
	public abstract void handleCollisions(Student s);
	public abstract void draw(Graphics g);
	
	private Transform localTransform;
	public Transform getLocalTransform() {
		return localTransform;
	}
	public void setLocalTransform(Transform localTransform) {
		this.localTransform = localTransform;
	}
	
	@Override
	public void setSelected(boolean selected) {
		// TODO Auto-generated method stub
		this.selected = selected;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return selected;
	}

	@Override
	public GameObject contains() {
		// TODO Auto-generated method stub
		return this;
		
	}
}
