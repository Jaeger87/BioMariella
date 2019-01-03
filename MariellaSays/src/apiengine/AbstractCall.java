package apiengine;

public abstract class AbstractCall implements Runnable{

	private Callback callback;
	
	public AbstractCall(Callback callback) {
		this.callback = callback;
	}


	public abstract void callbackRun();
	
	
	@Override
	public final void run() {
		callbackRun();
		callback.callback();	
	}

	
}
