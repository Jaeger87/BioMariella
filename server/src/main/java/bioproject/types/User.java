package bioproject.types;

public class User implements Comparable{

	private String username;
	private int score;

	public void setUsername(String username) {
		// TODO Auto-generated method stub
		this.username = username;
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

	@Override
	public int compareTo(Object anotherUser) {
		// TODO Auto-generated method stub
		User user = (User) anotherUser;
		if(getScore() < user.getScore()) return 1;
		else if (getScore() == user.getScore()) return 0;
		return -1;
	}

}
