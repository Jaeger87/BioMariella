package apiengine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APIConsumer {

	private ExecutorService pool;
	private int threads = 2;
	private static APIConsumer me; 
	
	private APIConsumer()
	{
		pool = Executors.newFixedThreadPool(threads);
	}
	
	public static APIConsumer getAPIConsumer()
	{
		if (me == null)
			me = new APIConsumer();
		return me;
	}
	
	public void consumeAPI(AbstractCallToServer acs)
	{
		pool.execute(acs);
	}
	
}
