package co.edu.escuelaing.virtualizacion.dockerprimer;

import spark.Request;
import spark.Response;
import static spark.Spark.*;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DBObject;

import org.json.JSONObject;

import co.edu.escuelaing.virtualizacion.dockerprimer.service.LogService;

public class SparkWebServer {
    
    public static void main(String... args){
          port(getPort());
          get("status", (req,res) -> "Running");
          post("logservice","application/json",(req,res)->logService(req,res));
    }

    public static List<DBObject> logService(Request req,Response res){
        JSONObject data = new JSONObject(req.body());
        try {
            return LogService.saveData(data);
        } catch (UnknownHostException e) {
            return null;
        }
    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
}
