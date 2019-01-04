package apiengine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnerConsumer {

	private ExecutorService pool;
	private int threads = 2;
	private static RunnerConsumer me; 
	
	private RunnerConsumer()
	{
		pool = Executors.newFixedThreadPool(threads);
	}
	
	public static RunnerConsumer getRunnerConsumer()
	{
		if (me == null)
			me = new RunnerConsumer();
		return me;
	}
	
	public void consumeRunner(AbstractRunAndCall arc)
	{
		pool.execute(arc);
	}
	
}
