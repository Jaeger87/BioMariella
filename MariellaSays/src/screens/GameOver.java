package screens;
import processing.MariellaSaysMain;

public class GameOver implements PScreen{

	private MariellaSaysMain parent;
	
	
	public GameOver(MariellaSaysMain parent) {
		super();
		this.parent = parent;
	}
	
	@Override
	public void setup() {

		parent.background(240);
		parent.textSize(65);
        parent.fill(10);
        parent.text("GAME OVER", parent.width/2 - 95, parent.height / 2 - 20);
        
        parent.textSize(45);
        parent.text("Press start to continue", parent.width/2 - 150, parent.height / 2 + 100);
	}

	@Override
	public void draw() {
		
		
	}

	@Override
	public void keyPressed() {
		
		
	}

	@Override
	public void keyReleased() {
		if(parent.key == MariellaSaysMain.ENTER)
			parent.logIn();
		
	}

}
