package apicalls;

import apiengine.APITypes;
import apiengine.AbstractCallToServer;
import apiengine.Callback;
import apimodel.HighScoreRequest;
import processing.MariellaSaysMain;

public class UpdateScoreAPI extends AbstractCallToServer{

	private boolean ok = false;
	
	public UpdateScoreAPI(Callback callback, int newScore, String username) {
		super(callback, APITypes.PUT, "http://localhost:4567/scores", new HighScoreRequest(newScore, username));
		
	}

	public boolean everythingOK()
	{
		return ok;
	}
	
	@Override
	protected void parseData(String json) {
		ok = true;
		MariellaSaysMain.println(json);
	}

}
