package screens;
import processing.MariellaSaysMain;

public class LogInScreen implements PScreen{

	private MariellaSaysMain parent;
	
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
        parent.text("Press start to log-in", parent.width/2 - 195, parent.height / 2 - 50);
        parent.text("Press select to register", parent.width/2 - 195, parent.height / 2 + 50);
	}

	@Override
	public void keyPressed() {

	}

	@Override
	public void keyReleased() {
		if(parent.key == MariellaSaysMain.ENTER)
			parent.camLogIn();
		else if(parent.key == MariellaSaysMain.BACKSPACE)
			parent.registration();
		
		else if(parent.key == ' ')
			parent.fakeSelectPlayer();
	}

}
