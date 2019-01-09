package apiengine;

import java.io.IOException;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import processing.MariellaSaysMain;

public abstract class AbstractCallToServer extends AbstractRunAndCall{

	private APITypes APItype;
	private String url;
	
	private Object bodyToSend;
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	public AbstractCallToServer(Callback callback, APITypes APItype, String url, Object bodyToSend) {
		super(callback);
		this.APItype = APItype;
		this.url = url;
		this.bodyToSend = bodyToSend; 
	}
	
	
	public AbstractCallToServer(Callback callback, APITypes APItype, String url) {
		super(callback);
		this.APItype = APItype;
		this.url = url;
	}
	

	@Override
	public void callbackRun() {
		RequestBody body = null;
		Request.Builder requestBuilder = new Request.Builder().url(url);
		
		Gson gson = new Gson();
		
        body = RequestBody.create(JSON, gson.toJson(bodyToSend));
       
        Request request = null;
        
        if(APItype == APITypes.GET)
            request = requestBuilder.get().build();
        if(APItype == APITypes.POST)
            request = requestBuilder.post(body).build();
        if(APItype == APITypes.PUT)
            request = requestBuilder.put(body).build();
        if(APItype == APITypes.DELETE)
            if(body != null)
                request = requestBuilder.delete(body).build();
            else
                request = requestBuilder.delete().build();
		
        
        OkHttpClient client = new OkHttpClient();
        String json = null;
        
        try (Response response = client.newCall(request).execute()) {
            json = response.body().string();
            
        } catch (IOException e1) {
            MariellaSaysMain.println(e1.toString());
        }
        

		parseData(json);
	}
	
	protected abstract void parseData(String json);

}
