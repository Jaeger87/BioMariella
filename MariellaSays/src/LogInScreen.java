
public class LogInScreen implements PScreen{

	private MariellaSaysMain parent;
	private boolean startPressed = false;
	
	public LogInScreen(MariellaSaysMain parent) {
		super();
		this.parent = parent;
	}
	
	
	@Override
	public void setup() {
		startPressed = true;
		parent.background(240);
		
		parent.textSize(65);
        parent.fill(10);
        parent.text("Press start to log-in", parent.width/2 - 95, parent.height / 2 - 20);
		
	}

	@Override
	public void draw() {

		
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
				parent.startMariella();
			else if(parent.key == MariellaSaysMain.BACKSPACE)
				parent.registration();
		
	}

}
