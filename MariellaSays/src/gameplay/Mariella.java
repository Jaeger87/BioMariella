package gameplay;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Mariella {

	private List<Integer> sequence;
	
	public Mariella()
	{
		sequence = new ArrayList<Integer>();
	}

	public List<Integer> getSequence() 
	{
		return new ArrayList<>(sequence);
	}
	
	public void extendSequence()
	{
		sequence.add(ThreadLocalRandom.current().nextInt(0, 3 + 1));
	}
	
	public int getScore()
	{
		return sequence.size() - 1;
	}
	
}
