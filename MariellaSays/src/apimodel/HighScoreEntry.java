package apimodel;

public class HighScoreEntry {

	private int score;
	private String nickname;
	

	public HighScoreEntry() {

	}

	
	public HighScoreEntry(int score, String nickname) {
		this.score = score;
		this.nickname = nickname;
	}

	public int getScore() {
		return score;
	}

	public String getNickname() {
		return nickname;
	}



	@Override
	public String toString() {
		return nickname + ":" + score;
	}
	
}