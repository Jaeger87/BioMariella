package apicalls;

import java.util.List;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import apiengine.APITypes;
import apiengine.AbstractCallToServer;
import apiengine.Callback;
import apimodel.HighScoreEntry;
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
		
		Type containerType = new TypeToken<List<HighScoreEntry>>(){}.getType();
		
		highScores = gson.fromJson(json, containerType);
		
		if(highScores.size() > 10)
			highScores = highScores.subList(0, 10);
	}

	public List<HighScoreEntry> getHighScores() {
		return highScores;
	}

	
	
	
}
