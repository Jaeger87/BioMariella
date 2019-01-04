package gameplay;

import processing.MariellaSaysMain;
import processing.SerialContainer;
import processing.core.PApplet;

public class SparaFlashManager {

	private static final int DURATION = 250;
	private long nextStageTime;
	private static final int END = 5;
	private int currentStage = 0;
	
	private PApplet parent;
	private SerialContainer arduino;
	
	
	public SparaFlashManager(MariellaSaysMain parent, SerialContainer arduino) 
	{
		this.parent = parent;
		this.arduino = arduino;
		
		nextStageTime = parent.millis() + DURATION;
		onOffLeds();
	}


	public boolean isEnd()
	{
		return currentStage == END;
	}
	
	public boolean isDark()
	{
		long currentTime = parent.millis();
		
		if(nextStageTime < currentTime)
		{
			currentStage++;
			nextStageTime = parent.millis() + DURATION;
			onOffLeds();
		}
		
		return (currentStage % 2) == 0;
	}
	
	private void onOffLeds()
	{
		if(currentStage % 2 == 0)
			arduino.write('W');
		else
			arduino.write('D');
			
	}
	
}
