

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;

public class MariellaSaysMain extends PApplet{

	private PFont font;
	private List<SnesButton> buttons;
	private Mariella mariella;
	private CoreGameplaystatus coreStatus;
	private List<Integer> currentSeuence;
	private final static int TIMETOSAYS = 1000;
	
	public static void main(String[] args) {
		PApplet.main("MariellaSaysMain");
	}

    public void settings(){
        size(1000,700);
    }

    
    public void setup(){
    	font = createFont("data/Legothick.ttf", 10);
    	textFont(font);
    	
    	drawBackground();
        
        
        buttons = new ArrayList<>();
    	
    	buttons.add(new SnesButton(this, color(0,255,0), color(0,160,0), 352, 336, 'Y'));
    	buttons.add(new SnesButton(this, color(0,0,255), color(0,0,160), 584, 218, 'X'));
    	buttons.add(new SnesButton(this, color(255,0,0), color(160, 0,0), 634, 338, 'A'));
    	buttons.add(new SnesButton(this, color(255,255,0), color(160,160,0), 402, 457, 'B'));
    	
    	mariella = new Mariella();
    	coreStatus = CoreGameplaystatus.THINK;
     
    }

    int saysIndex;
    long nextStopSays;
    
    
    int listenIndex;
    
    public void draw(){
    	
    	drawBackground();
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
			
			break;
		case SAYS:
		
			if(saysIndex == currentSeuence.size())
			{
				listenIndex = 0;
				coreStatus = CoreGameplaystatus.LISTEN;
				break;
			}
			SnesButton selected = buttons.get(currentSeuence.get(saysIndex));
			
			long currentTime = millis();
			
			if(currentTime > nextStopSays)
			{
				selected.release();
				saysIndex++;
				nextStopSays = millis() +  TIMETOSAYS;
				break;
			}
			
			selected.press();
			break;
			
		case THINK:
			mariella.extendSequence();
			currentSeuence = mariella.getSequence();
			saysIndex = 0;
			nextStopSays = millis() +  TIMETOSAYS;
			
			coreStatus = CoreGameplaystatus.SAYS;
			
			break;
		default:
			break;
        
        }
        
        
    }
    
    private void drawBackground()
    {
    	background(240);
        fill(85,94,109);
        ellipse(500,350,450,450);
        
        pushMatrix();
        int xFirstRect = 122;
        int yFirstRect = 420;
        rotate(radians(-27));
        fill(210);
        rect(xFirstRect, yFirstRect, 340, 80, 300);
        
        rect(xFirstRect - 10, yFirstRect + 130, 340, 80, 300);
        popMatrix();
    }
    
    public void keyPressed()
    {
    	if(coreStatus == CoreGameplaystatus.LISTEN)
    		for(SnesButton snesB : buttons)
    			snesB.press(key);
    }
      
    public void keyReleased()//qui condizione gameOver
    {
    	if(coreStatus == CoreGameplaystatus.LISTEN)
    		for(SnesButton snesB : buttons)
    			snesB.release(key);
    }
    
    
}
