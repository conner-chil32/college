package com.csus.csc133;

public class SelectionHandler {

	private GameObject selected;
	
	public GameObject getSelected() {
		return selected;
	}
	public void setSelected(GameObject gameObject) {
		this.selected = gameObject;
	}
	
	private static SelectionHandler sh = null;
	public static synchronized SelectionHandler getInstance() {
		if(sh == null) {
			sh = new SelectionHandler();
		}
		return sh;
	}
}
