package bioproject.types;

public class User {

	private String ID;
	private String username;
	private int score;
	
	public void setID(String string) {
		// TODO Auto-generated method stub
		this.ID = string;
		
	}

	public void setUsername(String username) {
		// TODO Auto-generated method stub
		this.username = username;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return ID;
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
