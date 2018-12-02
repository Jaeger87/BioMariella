

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;

public class MariellaSaysMain extends PApplet{

	private PFont font;
	private List<SnesButton> buttons;
	private Mariella mariella;
	private CoreGameplaystatus coreStatus;
	private List<Integer> currentSequence;
	private final static int TIMETOSAYS = 1000;//millis
	private final static int TIMETOPAUSE = 100;
	
	public static void main(String[] args) {
		PApplet.main("MariellaSaysMain");
	}

    public void settings(){
        size(1000,700);
    }

    
    public void setup(){
    	font = createFont("data/SNES_Italic.ttf", 10);
    	textFont(font);
    	
    	drawCoreBackground();
        
        
        buttons = new ArrayList<>();
    	
    	buttons.add(new SnesButton(this, color(0,255,0), color(0,160,0), 352, 336, 'Y'));
    	buttons.add(new SnesButton(this, color(0,0,255), color(0,0,160), 584, 218, 'X'));
    	buttons.add(new SnesButton(this, color(255,0,0), color(160, 0,0), 634, 338, 'A'));
    	buttons.add(new SnesButton(this, color(255,255,0), color(160,160,0), 402, 457, 'B'));
    	
    	mariella = new Mariella();
    	coreStatus = CoreGameplaystatus.THINK;
     
    }

    private int saysIndex;
    private long nextStopSays;
    private long nextStopPause;
    private boolean pauseSays = false;
    
    int listenIndex;
    
    public void draw(){
    	
    	drawCoreBackground();
    	//println(mouseX);
    	//println(mouseY);
    	for(SnesButton snesB : buttons)
    		snesB.display();
    	
        textSize(65);
        fill(10);
        text(mariella.getScore(), width/2 - 50, height / 10);
        
        switch(coreStatus)
        {
		case LISTEN:
			if(listenIndex == currentSequence.size())
				coreStatus = CoreGameplaystatus.THINK;
			break;
		case SAYS:
		
			if(saysIndex == currentSequence.size())
			{
				listenIndex = 0;
				coreStatus = CoreGameplaystatus.LISTEN;
				break;
			}
			SnesButton selected = buttons.get(currentSequence.get(saysIndex));
			
			long currentTime = millis();
			
			if(pauseSays)
			{
				if(currentTime > nextStopPause)
				{
					println("asd");
					nextStopSays = millis() +  TIMETOSAYS;
					pauseSays = false;
					saysIndex++;
				}
			}
			else
			{
				if(currentTime > nextStopSays)
				{
					selected.release();
					nextStopPause = millis() +  TIMETOPAUSE;
					pauseSays = true;
					break;
				}
				selected.press();
			
			}	
			break;
			
		case THINK:
			mariella.extendSequence();
			currentSequence = mariella.getSequence();
			saysIndex = 0;
			nextStopSays = millis() +  TIMETOSAYS;
			pauseSays = false;
			
			coreStatus = CoreGameplaystatus.SAYS;
			break;
		case GAMEOVER:
			println("Game over!");
			break;
		default:
			break;
        
        }
        
        
    }
    
    private void drawCoreBackground()
    {
    	background(240);
        fill(85,94,109);
        ellipse(500,350,490,490);
        
        pushMatrix();
        int xFirstRect = 122;
        int yFirstRect = 420;
        rotate(radians(-27));
        fill(210);
        rect(xFirstRect, yFirstRect, 340, 80, 300);
        
        rect(xFirstRect - 10, yFirstRect + 130, 340, 80, 300);
        popMatrix();
        
        text("Y", 275, 372);
        text("B", 319, 496);
        text("A", 684, 323);
        text("X", 628, 210);
        
    }
    
    public void keyPressed()
    {
    	if(coreStatus == CoreGameplaystatus.LISTEN)
    		for(SnesButton snesB : buttons)
    			snesB.press(key);
    }
      
    public void keyReleased()
    {
    	if(coreStatus == CoreGameplaystatus.LISTEN)
    	{
    		SnesButton released = null;
    		for(SnesButton snesB : buttons)
    			if(snesB.release(key))
    			{
    				released = snesB;
    				break;
    			}
    		if(!buttons.get(currentSequence.get(listenIndex)).equals(released))
    			coreStatus = CoreGameplaystatus.GAMEOVER;
    		else
    			listenIndex++;
    	}
    	
    }
    
    
}
