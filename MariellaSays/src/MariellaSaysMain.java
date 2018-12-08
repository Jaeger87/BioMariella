
import processing.core.PApplet;
import processing.core.PFont;

public class MariellaSaysMain extends PApplet{

	private PFont font;
	private PScreen currentScreen;
	private PScreen logInScreen;
	private PScreen registrationScreen;
	private PScreen coreScreen;
	private PScreen gameOverScreen;
	
	
	public static void main(String[] args) {
		PApplet.main("MariellaSaysMain");
	}

    public void settings(){
        size(1000,700);
    }

    
    public void setup(){
    	font = createFont("data/SNES_Italic.ttf", 10);
    	textFont(font);
    	
    	coreScreen = new CoreGameplay(this);
    	logInScreen = new LogInScreen(this);
    	gameOverScreen = new GameOver(this);
    	registrationScreen = new RegistrationScreen(this);
    	changeScreen(logInScreen);
    }
    
    public void draw(){
    	currentScreen.draw();        
    }
    
    
    private void changeScreen(PScreen screen)
    {
    	currentScreen = screen;
    	screen.setup();
    }
    
    public void keyPressed()
    {
    	currentScreen.keyPressed();    
    }
      
    public void keyReleased()
    {
    	currentScreen.keyReleased();
    }
    
    public void gameOver()
    {
    	changeScreen(gameOverScreen);
    }
    
    public void startMariella()
    {
    	changeScreen(coreScreen);
    }
    
    public void logIn()
    {
    	changeScreen(logInScreen);
    }
    
    
    public void registration()
    {
    	changeScreen(registrationScreen);
    }
    
}
