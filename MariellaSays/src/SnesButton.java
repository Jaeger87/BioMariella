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
	
	public boolean press(char input)
	{
		if(input != name && input != Character.toLowerCase(name))
			return false;
		press = true;
		return true;
	}
	
	public boolean release(char input)
	{
		if(input != name && input != Character.toLowerCase(name))
			return false;
		press = false;
		return true;
	}
	
	
	public void press()
	{
		press = true;
	}
	
	public void release()
	{
		press = false;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SnesButton other = (SnesButton) obj;
		if (name != other.name)
			return false;
		return true;
	}
	

	
	
}
