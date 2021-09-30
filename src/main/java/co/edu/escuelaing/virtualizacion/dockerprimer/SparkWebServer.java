package co.edu.escuelaing.virtualizacion.dockerprimer;

import spark.Request;
import spark.Response;
import static spark.Spark.*;
import spark.Filter;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DBObject;

import org.json.JSONObject;

import co.edu.escuelaing.virtualizacion.dockerprimer.service.LogService;

/**
 * Class for the server
 */
public class SparkWebServer {
    
    /**
     * Main class it 
     * @param args arguments
     */
    public static void main(String... args){
        staticFiles.location("/public");
        port(getPort());
        options("/*",
        (request, response) -> {

            String accessControlRequestHeaders = request
                    .headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers",
                        accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request
                    .headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods",
                        accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        get("status", (req,res) -> "Running");
        post("logservice","application/json",(req,res)->logService(req,res));
    }

    /**
     * 
     * @param req Request
     * @param res Response
     * @return last 10 logs on a List
     */
    public static List<DBObject> logService(Request req,Response res){
        JSONObject data = new JSONObject(req.body());
        try {
            return LogService.saveData(data);
        } catch (UnknownHostException e) {
            return null;
        }
    }

    /**
     * Get env port
     * @return int the port
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
}
