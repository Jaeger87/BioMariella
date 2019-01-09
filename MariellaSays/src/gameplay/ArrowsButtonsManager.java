package gameplay;

import processing.MariellaSaysMain;

public class ArrowsButtonsManager {

	private int arrow;
	private long lastTimePressed = 0;
	private static final int timeForMovement = 500;
	private int steps;
	
	
	public ArrowsButtonsManager(int arrow) {
		super();
		this.arrow = arrow;
		
		if(this.arrow == MariellaSaysMain.UP)
			steps = -1;
		if(this.arrow == MariellaSaysMain.DOWN)
			steps = 1;
		if(this.arrow == MariellaSaysMain.LEFT)
			steps = -3;
		if(this.arrow == MariellaSaysMain.RIGHT)
			steps = 3;
		
	}
	
	public int hasToMove(int who, long now)
	{
		if(who != arrow)
			return 0;
		
		if (now - lastTimePressed < timeForMovement)
			return 0;
		
		
		
		lastTimePressed = now;
		return steps;
	}
	
	public void reset(int who)
	{
		if(who == arrow)
			lastTimePressed = 0;
	}
	
}
