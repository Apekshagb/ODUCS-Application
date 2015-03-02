package com.example.oducs_mobile;

public interface AccelerometerListener {
	
	    public void onAccelerationChanged(float x, float y, float z);
	  
	    public void onShake(float force);
	    
	    public void onAccStop();
	  

}
