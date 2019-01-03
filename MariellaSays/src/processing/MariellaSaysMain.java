package processing;

import processing.core.PApplet;
import processing.core.PFont;
import processing.serial.*;
import screens.CoreGameplay;
import screens.GameOver;
import screens.LogInScreen;
import screens.PScreen;
import screens.RegistrationScreen;

public class MariellaSaysMain extends PApplet {

	private PFont font;
	private PScreen currentScreen;
	private PScreen logInScreen;
	private PScreen registrationScreen;
	private PScreen coreScreen;
	private PScreen gameOverScreen;
	

	private SerialContainer arduino;

	public static void main(String[] args) {
		PApplet.main("processing.MariellaSaysMain");
	}

	public void settings() {
		size(1000, 700);
	}

	public void setup() {
		//System.out.println(System.getProperty("sun.arch.data.model") );
		
		font = createFont("data/SNES_Italic.ttf", 10);
		textFont(font);


		for (String port : Serial.list())
			println(port);
		
		if(Serial.list().length > 0)
			arduino = new SerialContainer(new Serial(this, Serial.list()[0], 115200));
		else
			arduino = new SerialContainer();
		

		
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
