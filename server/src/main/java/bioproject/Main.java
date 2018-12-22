package bioproject;

import static spark.Spark.*;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.*;
import bioproject.bll.UsersBLL;
import bioproject.types.User;

public class Main {
	
    public static void main(String[] args) {
    	
    	//registration web service
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
        	if(obj.has("username")) 
        	{
        		String username = obj.get("username").getAsString();
        		if(UsersBLL.insertUser(username))
        		{
        			return new Gson().toJson("Subscription completed successfully.");
        		}
        		else {
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
        	
        	if(obj.has("ID") || obj.has("newScore")) {
        		/*
        		 * For now, assume that ID is passed by the user.
        		 * In future will be passed the face features.
        		 */
        		String ID = obj.get("ID").getAsString();
        		int newScore = obj.get("newScore").getAsInt();
        		if(UsersBLL.updateScore(ID, newScore)) 
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
        		return new Gson().toJson("Username field is missing");
        	}
        	
        });
        
        
        //get global scores
        get("/ranking", (req, res)->{
        	res.type("/application/json");
        	return new Gson().toJson(UsersBLL.getRanking());
        });
        
        
        
        
    }
}
