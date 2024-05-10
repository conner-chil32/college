package com.csus.csc133;

public class Collider {
	private int x;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	private int y;
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	private GameObject parent;
	public GameObject getParent() {
		return parent;
	}
	public void setParent(GameObject parent) {
		this.parent = parent;
	}

	private int width;
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	private int height;
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	private int color;
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	private boolean enabled;
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Collider(int x, int y, int width, int height, int color, GameObject parent, boolean enabled) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.parent = parent;
		this.enabled = enabled;
	}
	
	public boolean collides(Collider other) {
		int L1 = getX() - getWidth()/2;
		int L2 = other.getX() - other.getWidth()/2;
		int R1 = getX() + getWidth()/2;
		int R2 = other.getX() + other.getWidth()/2;
		int T1 = getY() - getHeight()/2;
		int T2 = other.getY() - other.getHeight()/2;
		int B1 = getY() + getHeight()/2;
		int B2 = other.getY() + other.getHeight()/2;
		if(( R1 < L2) || (R2 < L1) || (T2 > B1) || (T1 > B2)) {
			return false;
		}
		return true;
	}
	
	
	
}
