package screens;
import java.util.ArrayList;
import java.util.List;

import apicalls.FakeHighScore;
import apiengine.APIConsumer;
import apiengine.Callback;
import apimodel.HighScoreEntry;
import processing.MariellaSaysMain;

public class GameOver implements PScreen, Callback{

	private MariellaSaysMain parent;
	
	private List<HighScoreEntry> highScores;
	private FakeHighScore fake; 
	
	public GameOver(MariellaSaysMain parent) {
		super();
		this.parent = parent;
	}
	
	@Override
	public void setup() {
		
		fake = new FakeHighScore(this);
		highScores = new ArrayList<>();
		APIConsumer.getAPIConsumer().consumeAPI(fake);
		parent.background(240);
		parent.textSize(65);
        parent.fill(10);
        parent.text("GAME OVER", parent.width/2 - 95, parent.height / 2 - 260);
        parent.textSize(45);
        parent.text("Press start to continue", parent.width/2 - 150, parent.height / 2 + 170);
	}

	@Override
	public void draw() {
		int indexScore = 0;
		parent.textSize(33);
		for(HighScoreEntry score : highScores)
		{
			parent.text(score.getScore() + "   " + score.getNickname(), parent.width/2 - 67, 128 + indexScore * 38);
			indexScore++;
		}
		
	}

	@Override
	public void keyPressed() {
		
		
	}

	@Override
	public void keyReleased() {
		if(parent.key == MariellaSaysMain.ENTER)
			parent.logIn();	
		
	}

	@Override
	public void callback() {
		MariellaSaysMain.println(fake.getHighScores().toString());
		highScores = fake.getHighScores();
	}

}
