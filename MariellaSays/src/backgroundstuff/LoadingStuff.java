package backgroundstuff;

import apiengine.AbstractRunAndCall;
import processing.MariellaSaysMain;
import processing.SerialContainer;
import processing.core.PApplet;
import processing.serial.Serial;
import processing.video.Capture;
import processing.serial.*;


public class LoadingStuff extends AbstractRunAndCall{

	private PApplet parent;
	private SerialContainer arduino;
	private Capture cam;
	
	public LoadingStuff(MariellaSaysMain mariellaMain) {
		super(mariellaMain);
		parent = mariellaMain;
	}

	@Override
	public void callbackRun() {
		
		for (String port : Serial.list())
			MariellaSaysMain.println(port);
		
		if(Serial.list().length > 0)
			arduino = new SerialContainer(new Serial(parent, Serial.list()[0], 115200));
		else
			arduino = new SerialContainer();
		
		String[] cameras = Capture.list();
		  
		if (cameras.length == 0) {
			  
		  } 
		else {
			MariellaSaysMain.println("Available cameras:");
		    
			for (int i = 0; i < cameras.length; i++) 
			{
		    	MariellaSaysMain.println(cameras[i]);
		    }
		    
		    cam = new Capture(parent, cameras[0]);
		    
		    cam.start(); 
		    
		  }
		  
		  
	}

	public SerialContainer getArduino()
	{
		return arduino;
	}

	public Capture getCam() 
	{
		return cam;
	}

	
	
	
	
}
