package screens;

import apicalls.LogInCall;
import apiengine.Callback;
import apiengine.RunnerConsumer;
import apimodel.UserProfile;
import processing.MariellaSaysMain;
import processing.SerialContainer;
import processing.video.Capture;

public class CamLogInScreen implements PScreen, Callback{

	
	private Capture cam;
	private MariellaSaysMain parent;
	private SerialContainer arduino;
	private boolean startPressed = false;
	private static final String loginPath = "photos/login";
	private LogInCall logincall;
	private long nextphoto = 0;
	private int photoIndex = 0;
	private boolean photoFlag= false;
	
	public CamLogInScreen(MariellaSaysMain parent, Capture cam, SerialContainer arduino) {
		this.cam = cam;
		this.parent = parent; 
		this.arduino = arduino;
	}
	
	@Override
	public void setup() {
		arduino.write('W');
		
		nextphoto = 0;
		photoIndex = 0;
		photoFlag= false;
		
	}

	@Override
	public void draw() {
		if (cam.available() == true) {
		    cam.read();
		  }
		  //image(cam, 0, 0, width, height);
		  // The following does the same as the above image() line, but 
		  // is faster when just drawing the image without any additional 
		  // resizing, transformations, or tint.
		
		
		//parent.println("" + parent.mouseX + "  " + parent.mouseY);
		parent.set(-140, 20, cam);
		  
		parent.camGraphicsInterface();
		  
		if(photoFlag)
		{
			if(parent.millis()>nextphoto) {
				nextphoto = parent.millis() + 1000;
				cam.save(loginPath + photoIndex +".jpg");
				photoIndex++;
			}
			if(photoIndex>=5) {
				photoFlag=false;
				logincall = new LogInCall(this, loginPath);
				RunnerConsumer.getRunnerConsumer().consumeRunner(logincall);
					
			}
		}
			
		
		
	}

	@Override
	public void keyPressed() {
		if(parent.key == MariellaSaysMain.ENTER)
			startPressed = true;
		
	}

	@Override
	public void keyReleased() {
		if(startPressed)
			if(parent.key == MariellaSaysMain.ENTER)
			{
				arduino.write('D');
				parent.startMariella();
			}
		
		if (parent.key == 'A' || parent.key == 'a')
		{
			photoIndex = 0;
			nextphoto = parent.millis()+1000;
			photoFlag = true;
			
			
			//cam.save(loginPath);
			
		}
		
	}

	@Override
	public void callback() {
		UserProfile up = logincall.getUserProfile();
		
		if(up == null)
		{
			if(parent.isInCamLogIn())
			{
				cam.save(loginPath);
				logincall = new LogInCall(this, loginPath);
				RunnerConsumer.getRunnerConsumer().consumeRunner(logincall);
			}
			
			return;
		}
		
		parent.startMariella(up);

		
	}

}
