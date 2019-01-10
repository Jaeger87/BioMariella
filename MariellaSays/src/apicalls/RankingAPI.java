package apicalls;

import java.util.List;

import com.google.gson.Gson;

import apiengine.APITypes;
import apiengine.AbstractCallToServer;
import apiengine.Callback;
import apimodel.HighScoreEntry;
import apimodel.Ranking;
import processing.MariellaSaysMain;

public class RankingAPI extends AbstractCallToServer{

	private List<HighScoreEntry> highScores;
	
	public RankingAPI(Callback callback) {
		super(callback, APITypes.GET, "http://localhost:4567/ranking");
		
	}

	@Override
	protected void parseData(String json) {
	
		Gson gson = new Gson();
		MariellaSaysMain.println(json);
		
		Ranking ranking = gson.fromJson(json, Ranking.class);
		
		if(ranking != null)
		{
		
			highScores = ranking.getRanking();
		
			if(highScores.size() > 10)
			highScores = highScores.subList(0, 10);
		}
	}

	public List<HighScoreEntry> getHighScores() {
		return highScores;
	}

	
	
	
}
