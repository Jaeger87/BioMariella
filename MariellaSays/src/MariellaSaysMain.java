
import processing.core.PApplet;
import processing.core.PFont;
import processing.serial.*;

public class MariellaSaysMain extends PApplet {

	private PFont font;
	private PScreen currentScreen;
	private PScreen logInScreen;
	private PScreen registrationScreen;
	private PScreen coreScreen;
	private PScreen gameOverScreen;
	

	private Serial arduino;

	public static void main(String[] args) {
		PApplet.main("MariellaSaysMain");
	}

	public void settings() {
		size(1000, 700);
	}

	public void setup() {
		font = createFont("data/SNES_Italic.ttf", 10);
		textFont(font);

		String portName = Serial.list()[0];
		for (String port : Serial.list())
			println(port);
		arduino = new Serial(this, portName, 115200);

		
		coreScreen = new CoreGameplay(this, arduino);
		
		
		
		logInScreen = new LogInScreen(this);
		gameOverScreen = new GameOver(this);
		registrationScreen = new RegistrationScreen(this);
		changeScreen(logInScreen);

		// arduino.write("G\n");
		// println(arduino.readStringUntil('\n'));
	}

	public void draw() {
		currentScreen.draw();
		//arduino.clear();
		
		surface.setTitle("Mariella says");
	}

	private void changeScreen(PScreen screen) {
		currentScreen = screen;
		screen.setup();
	}

	public void keyPressed() {
		currentScreen.keyPressed();
	}

	public void keyReleased() {
		currentScreen.keyReleased();
	}

	public void gameOver() {
		changeScreen(gameOverScreen);
	}

	public void startMariella() {
		changeScreen(coreScreen);
	}

	public void logIn() {
		changeScreen(logInScreen);
	}

	public void registration() {
		changeScreen(registrationScreen);
	}

}
