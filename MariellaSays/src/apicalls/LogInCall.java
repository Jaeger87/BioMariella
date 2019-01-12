package apicalls;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import apiengine.AbstractRunAndCall;
import apiengine.Callback;
import apimodel.UserProfile;

public class LogInCall extends AbstractRunAndCall{
	
	
	private static String LOCAL_URI = "http://localhost:4567/authentication";
	private UserProfile loggedUser;
	private String basePath;

	
	
	public LogInCall(Callback callback, String imagePath) {
		super(callback);
		this.basePath = imagePath;

	}
	
	@Override
	public void callbackRun() {
		APICall();
	}

	private void APICall() {
		
		//loading the sample image
		
		List<File> images = new ArrayList<>();
		int i = 1;
		do {
			File image = new File(basePath + i +".jpg");
			if(!image.exists()) break;
			images.add(image);
			i++;
		} while(true);
		
		
		List<String> encodedfiles = new ArrayList<>();
		
        try {
        	for(File f : images) {
        	//encoding the sample
            FileInputStream fileInputStreamReader = new FileInputStream(f);
           	byte[] bytes = new byte[(int)f.length()];
            fileInputStreamReader.read(bytes);
            String encodedfile = new String(Base64.getEncoder().encodeToString(bytes));
	   		encodedfiles.add(encodedfile); 
        	}
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        //HTTP request
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(LOCAL_URI);
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray(encodedfiles);
        obj.put("images", arr);
        try {
        	//creating the json 
			StringEntity strEntity = new StringEntity(obj.toString());
			request.setHeader("Content-type", "application/json");
			request.setEntity(strEntity);
			
			//HTTP response
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity);
		
			//creating the user istance
			System.out.println(responseString);
			if(!responseString.equals(""))
			{
				Gson gson = new Gson();
				loggedUser = gson.fromJson(responseString, UserProfile.class);
			
				System.out.println("The identified user is " 
					+ loggedUser.getUsername() + " and his score is " 
				+ loggedUser.getScore());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
		for(File f : images)
			if (f.exists())
				f.delete();
	}


	
	public UserProfile getUserProfile()
	{
		return loggedUser;
	}

}
