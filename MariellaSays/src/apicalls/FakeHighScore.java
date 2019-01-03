package apicalls;

import java.util.ArrayList;
import java.util.List;

import apiengine.AbstractCallToServer;
import apimodel.HighScoreEntry;
import screens.GameOver;

public class FakeHighScore extends AbstractCallToServer{

	public FakeHighScore(GameOver gameOver) {
		super(gameOver);
		// TODO Auto-generated constructor stub
	}

	private List<HighScoreEntry> highScores;

	
	
	
	public List<HighScoreEntry> getHighScores() {
		return highScores;
	}




	@Override
	protected void parseData(String json) {
		highScores = new ArrayList<HighScoreEntry>();
		
		highScores.add(new HighScoreEntry(20, "Samus"));
		highScores.add(new HighScoreEntry(18, "Brega"));
		highScores.add(new HighScoreEntry(16, "Alexa"));
		highScores.add(new HighScoreEntry(15, "Hill House spacca"));
		highScores.add(new HighScoreEntry(11, "Super Mario"));
		highScores.add(new HighScoreEntry(8, "Odio jS"));
		highScores.add(new HighScoreEntry(6, "Scoffield"));
		highScores.add(new HighScoreEntry(6, "Chun Li"));
		highScores.add(new HighScoreEntry(6, "Devil"));
		highScores.add(new HighScoreEntry(2, "Di maio"));
		
	}
	
	
	
	
}
