package com.mytechia.robobo.rob;

import static com.mytechia.robobo.rob.WallConnectionStatus.WallConnectionStatusId.WallConnectionStatusId1;

public class WallConnectionStatus extends RobDeviceStatus<WallConnectionStatus.WallConnectionStatusId>{
	
	
	private int wallConnetion;

	public WallConnectionStatus() {
		super(WallConnectionStatusId1);
	}


	public int getWallConnetion() {
		return wallConnetion;
	}

	public void setWallConnetion(int wallConnetion) {
		this.wallConnetion = wallConnetion;
	}
	
	
    public  enum WallConnectionStatusId{
    	WallConnectionStatusId1;
    }
	
	

}
