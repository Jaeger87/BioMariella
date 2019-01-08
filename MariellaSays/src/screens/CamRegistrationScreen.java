package screens;

import java.io.File;


import apicalls.DetectionAndSendFace;
import apiengine.Callback;
import apiengine.RunnerConsumer;
import processing.MariellaSaysMain;
import processing.SerialContainer;
import processing.video.Capture;

public class CamRegistrationScreen implements PScreen, Callback{

	private Capture cam;
	private MariellaSaysMain parent;
	private SerialContainer arduino;
	private String nickname;
	private boolean flag= false;
	private long nextphoto = 0;
	int i = 0;
	private DetectionAndSendFace det;
	
	
	
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
		
		
		
		if(flag)
		{
			if(parent.millis()>nextphoto) {
				nextphoto = parent.millis() + 1000;
				cam.save("photos/"+nickname+"/"+i+".jpg");
				i++;
			}
			if(i>=5) {
				flag=false;
				det = new DetectionAndSendFace(this, nickname);
				RunnerConsumer.getRunnerConsumer().consumeRunner(det);
				
			}
		}
		  
		  
		  
		
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
			flag = true;
			nextphoto = parent.millis()+1000; 
			
			String path = "photos/"+nickname;
			createDir(path);
		}	
	
	}
	
	
	public void createDir(String path) {
		try {
	         // returns pathnames for files and directory
	         File f = new File(path);
	         
	         // create
	         boolean bool = f.mkdir();
	         
	         // print
	         System.out.println("Directory created? " + bool);
	         
	      } catch(Exception e) {
	         // if any error occurs
	         e.printStackTrace();
	      }
	}
	
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	
	@Override
	public void callback() {
		parent.logIn();
	}

	
}
