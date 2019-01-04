package screens;

import processing.MariellaSaysMain;
import processing.SerialContainer;
import processing.video.Capture;

public class CamLogInScreen implements PScreen{

	
	private Capture cam;
	private MariellaSaysMain parent;
	private SerialContainer arduino;
	private boolean startPressed = false;
	
	public CamLogInScreen(MariellaSaysMain parent, Capture cam, SerialContainer arduino) {
		this.cam = cam;
		this.parent = parent; 
		this.arduino = arduino;
	}
	
	@Override
	public void setup() {
		arduino.write('W');
		
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
		
		  parent.set(-140, 20, cam);
		  
		  
		
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
			cam.save("photos/streetfighter2turbo.jpeg");
		}
		
	}

}
