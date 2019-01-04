package apiengine;

public abstract class AbstractRunAndCall implements Runnable{

	private Callback callback;
	
	public AbstractRunAndCall(Callback callback) {
		this.callback = callback;
	}


	public abstract void callbackRun();
	
	
	@Override
	public final void run() {
		callbackRun();
		callback.callback();	
	}

	
}
