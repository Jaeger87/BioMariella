package processing;

import apiengine.Callback;
import apiengine.RunnerConsumer;
import backgroundstuff.LoadingStuff;
import processing.core.PApplet;
import processing.core.PFont;
import screens.CamLogInScreen;
import screens.CoreGameplay;
import screens.GameOver;
import screens.LogInScreen;
import screens.PScreen;
import screens.RegistrationScreen;
import processing.video.*;

public class MariellaSaysMain extends PApplet implements Callback{

	private PFont font;
	private PScreen currentScreen;
	private PScreen logInScreen;
	private PScreen registrationScreen;
	private PScreen coreScreen;
	private PScreen gameOverScreen;
	private PScreen camLoginScreen;
	private Capture cam;
	

	private LoadingStuff loadingStuffThread;
	private SerialContainer arduino;

	public static void main(String[] args) {
		PApplet.main("processing.MariellaSaysMain");
	}

	public void settings() {
		size(1000, 700);
	}

	public void setup() {
		//System.out.println(System.getProperty("sun.arch.data.model") );
		surface.setTitle("Mariella says");
		font = createFont("data/SNES_Italic.ttf", 10);
		textFont(font);
		
		
		background(240);
		textSize(65);
        fill(10);
        text("Loading stuff... please wait", width/2 - 212, height / 2 - 20);
		
		
	

		
		logInScreen = new LogInScreen(this);
		gameOverScreen = new GameOver(this);
		registrationScreen = new RegistrationScreen(this);
		
		
		loadingStuffThread = new LoadingStuff(this);
		RunnerConsumer.getRunnerConsumer().consumeRunner(loadingStuffThread);
		
	}

	public void draw() {
		if(currentScreen != null)
			currentScreen.draw();	
		
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
	
	public void camLogIn() {
		if(cam != null)
			changeScreen(camLoginScreen);
		else
			startMariella();
	}

	@Override
	public void callback() {
		arduino = loadingStuffThread.getArduino();
		cam = loadingStuffThread.getCam();
		coreScreen = new CoreGameplay(this, arduino);
		
		if(cam != null)
			camLoginScreen = new CamLogInScreen(this, cam, arduino);
		
		changeScreen(logInScreen);
		
	}

}
