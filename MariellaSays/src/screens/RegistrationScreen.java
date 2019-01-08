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
		
		int widthInputText = 400;
		
		
		
		inputText = new IFTextField("Text Field", parent.width /2 - widthInputText/2, parent.height /2 -15, 150);
		
		inputText.setHeight(25);
		inputText.setWidth(widthInputText);
		
		guiController.add(inputText);
		
	}

	@Override
	public void draw() 
	{
		parent.text("Insert username", parent.width /2 - 150, parent.height /2 -65);
	}

	@Override
	public void keyPressed() 
	{

	}

	@Override
	public void keyReleased() 
	{
		if(parent.key == MariellaSaysMain.DELETE)
		{
			guiController.setVisible(false);
			parent.logIn();
		}
		
		if(parent.key == MariellaSaysMain.ENTER)
		{
			guiController.setVisible(false);
			parent.camRegistration(inputText.getValue());
		}
	}
	

}
