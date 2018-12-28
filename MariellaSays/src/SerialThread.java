import processing.serial.Serial;

public class SerialThread extends Thread{

	private boolean dataToSend = false;
	private char charToSend = 'a';
	private Serial arduino;
	
	
	public SerialThread(Serial arduino) {
		super();
		this.arduino = arduino;
	}

	
	public void write(char c)
	{
		this.charToSend = c;
		dataToSend = true;
		
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			
			if(dataToSend)
			{
				System.out.println(charToSend);
				arduino.write(charToSend);
				dataToSend = false;
			}
		}
	}
}
