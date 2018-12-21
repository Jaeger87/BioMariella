package bioproject;

import static spark.Spark.*;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.*;
import bioproject.bll.UsersBLL;
import bioproject.types.User;

public class Main {
	
    public static void main(String[] args) {
    	
    	//registration web service
        post("/users", (req, res) -> {
        	res.type("application/json");
        	JsonParser parser = new JsonParser();
        	JsonElement json = parser.parse(req.body());
        	if(!json.isJsonObject()) {
        		res.status(HttpStatus.BAD_REQUEST_400);
        		return new Gson().toJson("Json file expected");
        	}
        	JsonObject obj = json.getAsJsonObject();
        	if(obj.has("username")) {
        		String username = obj.get("username").getAsString();
        		User u = new User();
        		u.setUsername(username);
        		if(UsersBLL.insertUser(u)) {
        			return new Gson().toJson("Subscription completed successfully");
        		}
        		else {
        			res.status(HttpStatus.EXPECTATION_FAILED_417);
        			return new Gson().toJson("Registration failed.");
        		}
        		
        	}
        	else {
        		res.status(HttpStatus.BAD_REQUEST_400);
        		return new Gson().toJson("Username field is missing");
        	}
        	
        });
        
        
        
        
    }
}
