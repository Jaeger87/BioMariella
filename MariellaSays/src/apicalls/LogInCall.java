package apicalls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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
	private String imagePath;

	
	
	public LogInCall(Callback callback, String imagePath) {
		super(callback);
		this.imagePath = imagePath;

	}
	
	@Override
	public void callbackRun() {
		APICall();
		
		
	}

	public void APICall() {
		File image = new File(imagePath);
		String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(image);
           	byte[] bytes = new byte[(int)image.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.getEncoder().encodeToString(bytes));             
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(LOCAL_URI);
        JSONObject obj = new JSONObject();
        obj.put("image", encodedfile);
        try {
			StringEntity entity = new StringEntity(obj.toString());
			request.setHeader("Content-type", "application/json");
			request.setEntity(entity);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			System.out.println(responseString);
			
			Gson gson = new Gson();
			
			loggedUser = gson.fromJson(responseString, UserProfile.class);
			
		} catch (ClientProtocolException e) {
		
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}


	
	public UserProfile getUserProfile()
	{
		return loggedUser;
	}

}
