package screens;
import interfascia.GUIController;
import interfascia.IFTextField;
import processing.MariellaSaysMain;

public class RegistrationScreen implements PScreen{

	
private MariellaSaysMain parent;
	

private GUIController guiController;
private IFTextField inputText;


	public RegistrationScreen(MariellaSaysMain parent) {
		super();
		this.parent = parent;
		

	}
	
	
	@Override
	public void setup() 
	{
		parent.background(240);
		guiController = new GUIController(parent);
		
		inputText = new IFTextField("Text Field", parent.width /2 - 40, parent.height /2 -15, 150);
		inputText.setHeight(30);
		guiController.add(inputText);
		
	}

	@Override
	public void draw() 
	{
		
	}

	@Override
	public void keyPressed() 
	{

	}

	@Override
	public void keyReleased() 
	{
		if(parent.key == MariellaSaysMain.ENTER)
		{
			guiController.setVisible(false);
			parent.logIn();
		}
	}
	

}
