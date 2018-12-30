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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * This is how looks like the code for 
 * HTTP POST requests carried out by the
 * client in authentication phase.
 * The submitted sample is recorded by the game.
 */

public class AuthenticationTest {
	
	
	//CHANGE IT: path within the sample image is stored
	private static String LOCAL_PATH = 
			"C:\\Users\\Sergio\\Downloads\\ImagesMarielNet\\Mariella.jpg";

	//server URI
	private static String LOCAL_URI = 
			"http://localhost:4567/authentication";
	
	public static void main(String[] args) {
		
		
		File image = new File(LOCAL_PATH);
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
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

