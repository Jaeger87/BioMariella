package apiengine;

import java.util.concurrent.TimeUnit;

public abstract class AbstractCallToServer extends AbstractCall{

	public AbstractCallToServer(Callback callback) {
		super(callback);
	}

	@Override
	public void callbackRun() {
		
		try 
		{
			TimeUnit.MILLISECONDS.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		parseData("");
	}
	
	protected abstract void parseData(String json);

}
