package bioproject;

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

/*
 * This is how looks like the code for 
 * HTTP POST requests carried out by the
 * client in registration phase.
 * The submitted samples are the output of FaceDetection.py 
 */

public class RegistrationTest {
	
	
	//CHANGE IT: directory path within the images are stored
	private static String LOCAL_PATH = 
			"C:\\Users\\Sergio\\Downloads\\ImagesMarielNet\\Andrea\\";
	
	//API resource identifier
	private static String LOCAL_URI = 
			"http://localhost:4567/registration";

	public static void main(String[] args) {
		
		//loading the ROI images
		List<File> images = new ArrayList<>();
		for(int i = 1; i <= 5; i++) {
			File image = new File(LOCAL_PATH + i +".jpg");
			images.add(image);
		}
		
		//encoding the ROI images
		List<String> encodedfiles = new ArrayList<>();
        try {
            for(File f : images) {
            	FileInputStream fileInputStreamReader = new FileInputStream(f);
            	byte[] bytes = new byte[(int)f.length()];
                fileInputStreamReader.read(bytes);
                String encodedfile = 
                		new String(Base64.getEncoder().encodeToString(bytes));
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
        obj.put("username", "andrea");
        
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
