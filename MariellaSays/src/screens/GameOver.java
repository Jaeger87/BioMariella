package screens;
import java.util.ArrayList;
import java.util.List;

import apicalls.RankingAPI;
import apiengine.Callback;
import apiengine.RunnerConsumer;
import apimodel.HighScoreEntry;
import apimodel.UserProfile;
import processing.MariellaSaysMain;

public class GameOver implements PScreen, Callback{

	private MariellaSaysMain parent;
	
	private List<HighScoreEntry> highScores;
	private RankingAPI ranking; 
	
	private UserProfile up;
	
	public GameOver(MariellaSaysMain parent) {
		super();
		this.parent = parent;
	}
	
	@Override
	public void setup() {
		
		if(up != null)
		{
			//chiama api
		}
		
		ranking = new RankingAPI(this);
		highScores = new ArrayList<>();
		RunnerConsumer.getRunnerConsumer().consumeRunner(ranking);
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
		{
			up = null;
			parent.logIn();	
		}
		
	}

	@Override
	public void callback() {
		MariellaSaysMain.println(ranking.getHighScores().toString());
		highScores = ranking.getHighScores();
	}
	
	
	public void setUserprofile(UserProfile up) {
		this.up = up;
	}
	

}
