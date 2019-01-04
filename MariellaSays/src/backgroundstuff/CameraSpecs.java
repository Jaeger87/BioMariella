package backgroundstuff;

public class CameraSpecs {

	private String name;
	private int width;
	private int height;
	private int fps;
	private String specs;
	
	public CameraSpecs(String specs)
	{
		this.specs = specs;
		String [] arraySpecs = specs.split(",");
		
		name = arraySpecs[0].substring(5);
		String sizes[] = arraySpecs[1].substring(5).split("x");
		
		try {
			width = Integer.valueOf(sizes[0]);
			height = Integer.valueOf(sizes[1]);
			fps =  Integer.valueOf(arraySpecs[2].substring(4));
		}
		
		catch(Exception e)
		{
			
		}
		
	}


	public String getName() {
		return name;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public int getFps() {
		return fps;
	}


	public String getSpecs() {
		return specs;
	}
	
	
	
}
