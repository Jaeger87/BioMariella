package bioproject;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.Base64;

import com.google.gson.*;
import bioproject.bll.UsersBLL;
import bioproject.types.User;

public class Main {
	
	private static String SHELL_COMMAND = 
			"python FaceRecMul.py";
	
    public static void main(String[] args) 
    {
    	
    	//registration service
        post("/registration", (req, res) -> {
        	res.type("application/json");
        	JsonParser parser = new JsonParser();
        	JsonElement json = parser.parse(req.body());
        	if(!json.isJsonObject()) 
        	{
        		res.status(HttpStatus.BAD_REQUEST_400);
        		return new Gson().toJson("Json file expected");
        	}
        	JsonObject obj = json.getAsJsonObject();
        	if(obj.has("username") && obj.has("images")) 
        	{
        		String username = obj.get("username").getAsString();
        		if(UsersBLL.alreadyExist(username)) 
        		{
        			res.status(HttpStatus.PRECONDITION_FAILED_412);
        			return new Gson().toJson("Nickname already used");
        		}
        		JsonArray encodedfiles = obj.get("images").getAsJsonArray();
        		if(UsersBLL.insertUser(username))
        		{
        			//adjourning the gallery with the new user
        			for(int i=0; i <  encodedfiles.size(); i++) 
        			{
        				File dir = new File("Images/" + username);
        				if(!dir.exists()) dir.mkdir();
        				String encoded = encodedfiles.get(i).getAsString();
        				String path = "Images/" + username +"/" + (i+1) + ".jpg";
        				fromBase64ToImage(encoded, path);
        			}
        			
        			//training on the gallery
        			Process p = Runtime.getRuntime().exec("python walkImage.py");
        			
        			return new Gson().toJson("Subscription completed successfully.");
        		}
        		else 
        		{
        			res.status(HttpStatus.EXPECTATION_FAILED_417);
        			return new Gson().toJson("Registration failed.");
        		}
        		
        	}
        	else {
        		res.status(HttpStatus.BAD_REQUEST_400);
        		return new Gson().toJson("Username field is missing.");
        	}
        	
        });
        
        //submit new score
        put("/scores", (req, res)->{
        	res.type("/application/json");
        	JsonParser parser = new JsonParser();
        	JsonElement json = parser.parse(req.body());
        	if(!json.isJsonObject()) {
        		res.status(HttpStatus.BAD_REQUEST_400);
        		return new Gson().toJson("Json file expected.");
        	}
        	JsonObject obj = json.getAsJsonObject();
        	System.out.println(obj.toString());
        	if(obj.has("username") && obj.has("newScore")) {
        		String username = obj.get("username").getAsString();
        		int newScore = obj.get("newScore").getAsInt();
        		if(UsersBLL.updateScore(username, newScore)) 
        		{
        			return new Gson().toJson("Score updated successfully.");
        		}
        		else {
        			res.status(HttpStatus.EXPECTATION_FAILED_417);
        			return new Gson().toJson("Update failed.");
        		}
        		
        	}
        	else {
        		res.status(HttpStatus.BAD_REQUEST_400);
        		return new Gson().toJson("Missing fields. Required: username, newScore");
        	}
        	
        });
        
        
        //get global scores
        get("/ranking", (req, res)->{
        	res.type("/application/json");
        	JSONObject outobj = new JSONObject();
        	JSONArray jsonArr = new JSONArray(UsersBLL.getRanking());
        	outobj.put("ranking", jsonArr);
        	return outobj;
        });
        
      //login and recognition of the user with single image
        post("/authentication", (req, res)->{
        	res.type("/application/json");
        	JsonParser parser = new JsonParser();
        	JsonElement json = parser.parse(req.body());
        	if(!json.isJsonObject()) 
        	{
        		res.status(HttpStatus.BAD_REQUEST_400);
        		return new Gson().toJson("Json file expected");
        	}
        	JsonObject obj = json.getAsJsonObject();
        	if(obj.has("image")) {
        		String encoded = obj.get("image").getAsString();
        		fromBase64ToImage(encoded, "probes/probe.jpg");
        		
        		Process p = Runtime.getRuntime()
        				.exec(SHELL_COMMAND);
        		
        		p.waitFor();  // wait for process to finish then continue.

        		BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));

        		String output = "";
        		String line = "";
        		while ((line = bri.readLine()) != null) {
        		    output+=line;
        		}
        		
        		JSONObject outobj = new JSONObject();
        		outobj.put("username", output);
        		User u = UsersBLL.getUser(output);
        		int score = 0;
        		if(u != null) score = u.getScore();
        		outobj.put("score", score);
        		return outobj;
        	}
        	else {
        		res.status(HttpStatus.BAD_REQUEST_400);
    		    return new Gson().toJson("Missing fields. Required: image");
    	    }
        });
        
        //login and recognition of the user with multiple images
        post("/multiauth", (req, res)->{
        	res.type("/application/json");
        	JsonParser parser = new JsonParser();
        	JsonElement json = parser.parse(req.body());
        	if(!json.isJsonObject()) 
        	{
        		res.status(HttpStatus.BAD_REQUEST_400);
        		return new Gson().toJson("Json file expected");
        	}
        	JsonObject obj = json.getAsJsonObject();
        	if(obj.has("images")) {
        		
        		List<File> toDelete = new ArrayList<>();
        		
        		JsonArray jsonArr = obj.get("images").getAsJsonArray();
        		for(int i = 0; i < jsonArr.size(); i++) {
        			
        			//get encoded image
        			String encoded = jsonArr.get(i).getAsString();
        			
        			//get image path to create)
        			String path = "probes/" + i + ".jpg";
        			
        			//saves the image
            		fromBase64ToImage(encoded, path);
            		
            		//add the image to the list to be deleted
            		toDelete.add(new File(path));
            		
        		}
        		
        		//run face recognition
        		Process p = Runtime.getRuntime()
        				.exec(SHELL_COMMAND);
        		
        		p.waitFor();  // wait for process to finish then continue.

        		BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));

        		String output = "";
        		String line = "";
        		while ((line = bri.readLine()) != null) {
        		    output+=line;
        		}
        		
        		//deletes the images
        		for(File f : toDelete) f.delete();
        		
        		JSONObject outobj = new JSONObject();
        		outobj.put("username", output);
        		User u = UsersBLL.getUser(output);
        		int score = 0;
        		if(u != null) score = u.getScore();
        		outobj.put("score", score);
        		return outobj;
        	}
        	else {
        		res.status(HttpStatus.BAD_REQUEST_400);
    		    return new Gson().toJson("Missing fields. Required: image");
    	    }
        });
        
    }
    
    //conversion and storing of the images
    public static void fromBase64ToImage(String encoded, String path) 
    {
    	byte[] imageByteArray = Base64.decode(encoded);
    	FileOutputStream imageOutFile;
		try {
			imageOutFile = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		try {
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
