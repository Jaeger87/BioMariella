package apimodel;

public class HighScoreEntry {

	private int score;
	private String userName;
	

	public HighScoreEntry() {

	}

	
	public HighScoreEntry(int score, String nickname) {
		this.score = score;
		this.userName = nickname;
	}

	public int getScore() {
		return score;
	}

	public String getNickname() {
		return userName;
	}



	@Override
	public String toString() {
		return userName + ":" + score;
	}
	
}