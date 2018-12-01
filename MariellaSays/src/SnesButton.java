import processing.core.PApplet;

public class SnesButton implements Drawable{

	
	private int pressColor;
	private int normalColor;
	private int x;
	private int y;
	private char name;
	private final static int radius = 70;
	private PApplet parent;
	private boolean press = false;
	
	
	public SnesButton(PApplet parent, int pressColor, int normalColor, int x, int y, char name) 
	{
		this.parent = parent;
		this.pressColor = pressColor;
		this.normalColor = normalColor;
		this.x = x;
		this.y = y;
		this.name = name;
	}


	@Override
	public void display() {
		if(press)
			parent.fill(pressColor);
		else
			parent.fill(normalColor);
		parent.strokeWeight(2);
		parent.ellipse(x, y, radius, radius);
		
	}
	
	public void press(char input)
	{
		if(input == name || input == Character.toLowerCase(name))
			press = true;
	}
	
	public void release(char input)
	{
		if(input == name || input == Character.toLowerCase(name))
			press = false;
	}
	
	
	public void press()
	{
		press = true;
	}
	
	public void release()
	{
		press = false;
	}
	
}
