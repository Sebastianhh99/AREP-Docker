package co.edu.escuelaing.virtualizacion.dockerprimer;

import spark.Request;
import spark.Response;
import static spark.Spark.*;

import org.json.JSONObject;

public class SparkWebServer {
    
    public static void main(String... args){
          port(getPort());
          get("status", (req,res) -> "Running");
          post("logservice","application/json",(req,res)->logService(req,res));
    }

    public static JSONObject logService(Request req,Response res){
        JSONObject data = new JSONObject(req.body());
        return new JSONObject();
    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
}
