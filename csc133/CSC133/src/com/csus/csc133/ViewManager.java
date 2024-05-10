package com.csus.csc133;

import com.codename1.ui.Transform;

public class ViewManager {
	
	private static ViewManager vm = null;
	public static synchronized ViewManager getInstance() {
		if(vm == null) {
			vm = new ViewManager();
		}
		return vm;
	}
	
	public int viewL = 0;
	public int viewB = 100;
	
	public int viewWidth = 500;
	public int viewHeight = 500;
	
	private Transform VTM;

	public Transform getVTM() {
		return VTM;
	}
	public void setVTM(Transform vTM) {
		VTM = vTM;
	}
	public void updateVTM() {
		VTM = Transform.makeIdentity();
		Transform W2ND = Transform.makeScale(1.0f/viewWidth, 1.0f/viewHeight);
		W2ND.translate(-viewL, -viewB);
		Transform ND2D = Transform.makeTranslation(0, GameModel.height);
		ND2D.scale(GameModel.width, -GameModel.height);
		VTM.concatenate(ND2D);
		VTM.concatenate(W2ND);
	}
	
	public void zoom(float factor) {
		float newWidth = viewWidth * factor;
	    float newHeight = viewHeight * factor;
	    if(newWidth<400||newHeight<200||newWidth>8192||newHeight>4320)
	        return;
	    viewL += (viewWidth - newWidth)/2;
	    viewB += (viewHeight - newHeight)/2;
	    viewWidth = (int) newWidth; viewHeight = (int) newHeight;
	}
	
	public void panHorizontal(double delta) {
		viewL += delta;
	}
	
	public void panVertical(double delta) {
		viewB -= delta;
	}
}
