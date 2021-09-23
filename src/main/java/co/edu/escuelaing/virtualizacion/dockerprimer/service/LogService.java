package co.edu.escuelaing.virtualizacion.dockerprimer.service;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.json.JSONObject;

public class LogService {
    public JSONObject saveData(JSONObject data) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("AREP");
        DBCollection collection = database.getCollection("logs");
        collection.insert(new BasicDBObject("data",data.getString("data")).append("date", new Date()));
        return new JSONObject();
    }
}
