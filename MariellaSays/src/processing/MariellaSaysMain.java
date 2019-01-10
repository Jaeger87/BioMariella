package processing;

import java.util.ArrayList;
import java.util.List;

import apiengine.Callback;
import apiengine.RunnerConsumer;
import apimodel.UserProfile;
import backgroundstuff.LoadingStuff;
import processing.core.PApplet;
import processing.core.PFont;
import screens.CamLogInScreen;
import screens.CamRegistrationScreen;
import screens.CoreGameplay;
import screens.GameOver;
import screens.LogInScreen;
import screens.PScreen;
import screens.RegistrationScreen;
import screens.SelectPlayerScreen;
import processing.video.*;

public class MariellaSaysMain extends PApplet implements Callback{

	private PFont font;
	private PScreen currentScreen;
	private PScreen logInScreen;
	private PScreen registrationScreen;
	private CoreGameplay coreScreen;
	private GameOver gameOverScreen;
	private PScreen camLoginScreen;
	private CamRegistrationScreen camRegistrationScreen;
	private SelectPlayerScreen selectPlayerScreen;
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
        text("Loading stuff... please wait", width/2 - 195, height / 2 - 20);
		
		
		
		logInScreen = new LogInScreen(this);
		gameOverScreen = new GameOver(this);
		registrationScreen = new RegistrationScreen(this);
		selectPlayerScreen = new SelectPlayerScreen(this);
		
		
		loadingStuffThread = new LoadingStuff(this);
		RunnerConsumer.getRunnerConsumer().consumeRunner(loadingStuffThread);
		
	}

	public void draw() {
		if(currentScreen != null)
			currentScreen.draw();	
		
	}

	private void changeScreen(PScreen screen) {
		screen.setup();
		currentScreen = screen;
	}

	public void keyPressed() {
		currentScreen.keyPressed();
	}

	public void keyReleased() {
		if((this.key == 'S' || this.key == 's') && currentScreen != registrationScreen)
			saveFrame("screenshots/" + System.currentTimeMillis() + ".png");
		currentScreen.keyReleased();
	}

	public void gameOver() {
		changeScreen(gameOverScreen);//
	}
	
	public void gameOver(UserProfile up) {
		gameOverScreen.setUserprofile(up);
		changeScreen(gameOverScreen);
	}

	public void startMariella() {
		changeScreen(coreScreen);
	}

	public void startMariella(UserProfile up) {
		coreScreen.setUserprofile(up);
		startMariella();
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
	
	public void selectPlayer(List<UserProfile> userProfiles) {
		selectPlayerScreen.setUserProfiles(userProfiles);
		changeScreen(selectPlayerScreen);
	}
	
	public void fakeSelectPlayer() {
		List<UserProfile> userProfiles = new ArrayList<UserProfile>();
		
		userProfiles.add(new UserProfile("Jaeger", 200));
		userProfiles.add(new UserProfile("Jaeger", 200));
		userProfiles.add(new UserProfile("Jaeger", 200));
		userProfiles.add(new UserProfile("Jaeger", 200));
		userProfiles.add(new UserProfile("Jaeger", 200));
		userProfiles.add(new UserProfile("Jaeger", 200));
		userProfiles.add(new UserProfile("gfffhggkgjgkggkgkgkggkgkggyyy", 200));
		userProfiles.add(new UserProfile("Jaeger", 200));
		userProfiles.add(new UserProfile("Jaeger", 200));
		
		selectPlayerScreen.setUserProfiles(userProfiles);
		changeScreen(selectPlayerScreen);
	}
	
	
	public void camRegistration(String nickname)
	{
		if(cam != null) 
		{
			changeScreen(camRegistrationScreen);
			camRegistrationScreen.setNickname(nickname);
		}
			
		else
			logIn();
	}

	@Override
	public void callback() {
		arduino = loadingStuffThread.getArduino();
		cam = loadingStuffThread.getCam();
		coreScreen = new CoreGameplay(this, arduino);
		
		if(cam != null)
		{
			camLoginScreen = new CamLogInScreen(this, cam, arduino);
			camRegistrationScreen = new CamRegistrationScreen(this, cam, arduino);
		}
		
		changeScreen(logInScreen);
		
	}
	
	
	public boolean isInCamLogIn()
	{
		return currentScreen instanceof CamLogInScreen;
	}

	
	
}
