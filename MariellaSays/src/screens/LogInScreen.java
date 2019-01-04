package screens;
import processing.MariellaSaysMain;

public class LogInScreen implements PScreen{

	private MariellaSaysMain parent;
	private boolean startPressed = false;
	
	public LogInScreen(MariellaSaysMain parent) {
		super();
		this.parent = parent;
	}
	
	
	@Override
	public void setup() {

		
	}

	@Override
	public void draw() {
		parent.background(240);
		parent.textSize(65);
        parent.fill(10);
        parent.text("Press start to log-in", parent.width/2 - 195, parent.height / 2 - 20);
		
	}

	@Override
	public void keyPressed() {
		if(parent.key == MariellaSaysMain.ENTER)
			startPressed = true;
		
	}

	@Override
	public void keyReleased() {
		if(startPressed)
			if(parent.key == MariellaSaysMain.ENTER)
				parent.camLogIn();
			else if(parent.key == MariellaSaysMain.BACKSPACE)
				parent.registration();
	}

}
