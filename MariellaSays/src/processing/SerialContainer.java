package processing;

import processing.serial.Serial;

public class SerialContainer {
	
	
	private Serial arduino;
	
	
	public SerialContainer() {

	}
	
	public SerialContainer(Serial arduino) {
		super();
		this.arduino = arduino;
	}


	public void write(char c)
	{
		if(arduino != null)
			arduino.write(c);
	}

}
