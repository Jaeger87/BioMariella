package apicalls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import apiengine.AbstractRunAndCall;
import apiengine.Callback;



public class DetectionAndSendFace extends AbstractRunAndCall {

	private static String SHELL_COMMAND = "python FaceDetection.py ";
	private String nickname;
	//API resource identifier
	private static String LOCAL_URI = "http://localhost:4567/registration";
	
	
	public DetectionAndSendFace(Callback callback, String nickname) {
		super(callback);
		this.nickname = nickname;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void callbackRun() {
		// TODO Auto-generated method stub
		try {
			String completeCommand =  SHELL_COMMAND + nickname;
			Process p = Runtime.getRuntime().exec(completeCommand);
			p.waitFor();
			APICall();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void APICall() 
	{
		//loading the ROI images
		List<File> images = new ArrayList<>();
		int i = 1;
		do {
			File image = new File(i +".jpg");
			if(!image.exists()) break;
			images.add(image);
			i++;
		} while(true);
				
		//encoding the ROI images
		List<String> encodedfiles = new ArrayList<>();
		try {
		   	for(File f : images) {
		   		FileInputStream fileInputStreamReader = new FileInputStream(f);
		   		byte[] bytes = new byte[(int)f.length()];
		   		fileInputStreamReader.read(bytes);
		   		String encodedfile = new String(Base64.getEncoder().encodeToString(bytes));
		   		encodedfiles.add(encodedfile);
		    }		            
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
		JSONArray arr = new JSONArray(encodedfiles);
		        
		//user we want to register
		obj.put("username", nickname);
		        
		//ROI images of the user
		obj.put("images", arr);
		        
		        try {
		        	//HTTP request
					StringEntity strEntity = new StringEntity(obj.toString());
					request.setHeader("Content-type", "application/json");
					request.setEntity(strEntity);
					
					//HTTP response
					HttpResponse response = httpClient.execute(request);
					HttpEntity entity = response.getEntity();
					String responseString = EntityUtils.toString(entity, "UTF-8");
					
					//print the result of the response
					System.out.println(responseString);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		      //delete all the images
				for(File f : images) f.delete();
		        
	}

}
