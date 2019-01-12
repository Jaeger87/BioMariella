package screens;
import java.util.ArrayList;
import java.util.List;

import apicalls.UpdateScoreAPI;
import apiengine.Callback;
import apiengine.RunnerConsumer;
import apimodel.UserProfile;
import gameplay.CoreGameplaystatus;
import gameplay.Mariella;
import gameplay.SnesButton;
import gameplay.SparaFlashManager;
import processing.MariellaSaysMain;
import processing.SerialContainer;
import processing.core.PApplet;

public class CoreGameplay implements PScreen, Callback{

	
	private MariellaSaysMain parent;
	private List<SnesButton> buttons;
	private Mariella mariella;
	private CoreGameplaystatus coreStatus;
	private List<Integer> currentSequence;
	private final static int TIMETOSAYS = 1000;//millis
	private final static int TIMETOPAUSE = 100;
	
	private SparaFlashManager sparaFlashManager;
	
    private int saysIndex;
    private long nextStopSays;
    private long nextStopPause;
    private boolean pauseSays;
	
    int listenIndex;
    
    private SerialContainer arduino;
    private static final int defaultBackground = 240;
    private static final int darkBackground = 110;
    private int currentBackground = defaultBackground;
    
    private UserProfile up;
    
    private UpdateScoreAPI updateScoreAPI;
    
    private boolean gameOver = false;
    
	public CoreGameplay(MariellaSaysMain parent, SerialContainer arduino) {
		super();
		this.parent = parent;
		this.arduino = arduino;
	}

	
	
	@Override
	public void setup() {
		drawCoreBackground();
        
        
        buttons = new ArrayList<>();
    	
    	buttons.add(new SnesButton(parent, arduino, parent.color(0,255,0),
    			parent.color(0,160,0), 352, 336, 'Y', 'G'));//verde
    	buttons.add(new SnesButton(parent, arduino, parent.color(0,0,255), 
    			parent.color(0,0,160), 584, 218, 'X', 'B'));//blu
    	buttons.add(new SnesButton(parent, arduino, parent.color(255,0,0), 
    			parent.color(160, 0,0), 634, 338, 'A', 'R'));//rosso
    	buttons.add(new SnesButton(parent, arduino, parent.color(255,255,0), 
    			parent.color(160,160,0), 402, 457, 'B', 'Y'));//giallo
    	
    	mariella = new Mariella();
    	MariellaSaysMain.println("ok setup");
    	
    	coreStatus = CoreGameplaystatus.THINK;
    	
    	saysIndex = 0;
    	nextStopSays = 0;
    	nextStopPause = 0;
    	pauseSays = false;
    	
    	listenIndex = 0;
    	
    	gameOver = false;
    	
	}

	@Override
	public void draw() {
		drawCoreBackground();
    	//println(mouseX);
    	//println(mouseY);
    	for(SnesButton snesB : buttons)
    		snesB.display();
    	

    	writeScore();
        
        switch(coreStatus)
        {
		case LISTEN:
			if(listenIndex == currentSequence.size())
				if((mariella.getScore() + 1) % 3 == 0)
				{
					coreStatus = CoreGameplaystatus.SPARAFLASH;
					sparaFlashManager = new SparaFlashManager(parent, arduino);
				}
				else
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
			
			long currentTime = parent.millis();
			
			if(pauseSays)
			{
				if(currentTime > nextStopPause)
				{
					nextStopSays = parent.millis() +  TIMETOSAYS;
					pauseSays = false;
					saysIndex++;
				}
			}
			else
			{
				if(currentTime > nextStopSays)
				{
					selected.release();
					nextStopPause = parent.millis() +  TIMETOPAUSE;
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
			nextStopSays = parent.millis() +  TIMETOSAYS;
			pauseSays = false;
			
			coreStatus = CoreGameplaystatus.SAYS;
			break;
			
		case GAMEOVER:
			if(up != null)
			{
				updateScoreAPI = new UpdateScoreAPI(this, mariella.getScore(), up.getUsername());
				RunnerConsumer.getRunnerConsumer().consumeRunner(updateScoreAPI);
				up = null;
			}
			
			
			if(gameOver)
				parent.gameOver(up);
			
			break;
			
		case SPARAFLASH:
			if(sparaFlashManager.isDark())
				currentBackground = darkBackground;
			else
				currentBackground = defaultBackground;
			
			if (sparaFlashManager.isEnd())
				coreStatus = CoreGameplaystatus.THINK;
			
			
			break;
		default:
			break;
        
        }
	}
	
	
	
	
	
    private void drawCoreBackground()
    {
    	parent.background(currentBackground);
    	parent.fill(85,94,109);
    	parent.ellipse(500,350,490,490);
        
    	parent.pushMatrix();
        int xFirstRect = 122;
        int yFirstRect = 420;
        parent.rotate(PApplet.radians(-27));
        parent.fill(210);
        parent.rect(xFirstRect, yFirstRect, 340, 80, 300);
        
        parent.rect(xFirstRect - 10, yFirstRect + 130, 340, 80, 300);
        parent.popMatrix();
        
        parent.text("Y", 275, 372);
        parent.text("B", 319, 496);
        parent.text("A", 684, 323);
        parent.text("X", 628, 210);
        

        
    }
	
    
    private void writeScore()
    {
        parent.textSize(65);
        parent.fill(10);
        parent.text(mariella.getScore(), parent.width/2 - 50, parent.height / 10);
        
        if(up != null)
        {
        	parent.textSize(50);
        	parent.text(up.getUsername() + "\nBest score: " + up.getScore(), 50, 600);
        }
    }
	
	
    public void keyPressed()
    {
    	if(coreStatus == CoreGameplaystatus.LISTEN)
    		for(SnesButton snesB : buttons)
    			snesB.press(parent.key);
    }
      
    public void keyReleased()
    {
    	if(coreStatus == CoreGameplaystatus.LISTEN)
    	{
    		SnesButton released = null;
    		for(SnesButton snesB : buttons)
    			if(snesB.release(parent.key))
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



	public void setUserprofile(UserProfile up) {
		this.up = up;
	}



	@Override
	public void callback() {
		gameOver = true;
		
	}
	


}
