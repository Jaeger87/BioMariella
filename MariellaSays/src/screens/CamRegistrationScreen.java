package screens;

import apiengine.Callback;
import processing.MariellaSaysMain;
import processing.SerialContainer;
import processing.video.Capture;

public class CamRegistrationScreen implements PScreen, Callback{

	
	private Capture cam;
	private MariellaSaysMain parent;
	private SerialContainer arduino;
	private String nickname;
	
	public CamRegistrationScreen(MariellaSaysMain parent, Capture cam, SerialContainer arduino) {
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
		if (cam.available() == true) 
		    cam.read();
		
		  parent.set(-140, 20, cam);
		
	}

	@Override
	public void keyPressed() {
		
	}

	@Override
	public void keyReleased() {
		
		if(parent.key == MariellaSaysMain.ENTER)
		{
			arduino.write('D');
			parent.logIn();
		}
	
		if (parent.key == 'A' || parent.key == 'a')
		{
			cam.save("photos/streetfighter2turbo.jpeg");
		}
		
	}

	
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	
	@Override
	public void callback() {
		
	}

}
