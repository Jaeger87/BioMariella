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

import apiengine.AbstractCallToServer;
import apiengine.AbstractRunAndCall;
import apiengine.Callback;



public class DetectionAndSendFace extends AbstractRunAndCall {

	private static String SHELL_COMMAND = "python FaceDetection.py ";
	private static String nickname;
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
	
	public void APICall() {
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
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

}
