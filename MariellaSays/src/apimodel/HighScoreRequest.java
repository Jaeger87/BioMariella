package apimodel;

public class HighScoreRequest {

	private int newScore;
	private String username;
	
	
	public HighScoreRequest(int newScore, String username) {
		this.newScore = newScore;
		this.username = username;
	}
	
	public HighScoreRequest() {

	}

	public int getNewScore() {
		return newScore;
	}

	public String getUsername() {
		return username;
	}
	
	
}
