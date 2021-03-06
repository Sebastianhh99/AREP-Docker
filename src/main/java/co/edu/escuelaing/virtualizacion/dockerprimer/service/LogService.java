package co.edu.escuelaing.virtualizacion.dockerprimer.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.json.JSONObject;

/**
 * Log service class
 */
public class LogService {
    /**
     * Saves the log and return the last 10
     * @param data new Log
     * @return last 10 logs on a List
     * @throws UnknownHostException Exception for mongodb
     */
    public static List<DBObject> saveData(JSONObject data) throws UnknownHostException{
        List<DBObject> response = new ArrayList<>();
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://ec2-100-25-159-8.compute-1.amazonaws.com:27017"));
        DB database = mongoClient.getDB("AREP");
        DBCollection collection = database.getCollection("logs");
        collection.insert(new BasicDBObject("data",data.getString("data")).append("date", new Date()));
        DBCursor cursor = collection.find();
        int size = cursor.count();
        cursor = cursor.skip(size-10);
        while(cursor.hasNext()){
            response.add(cursor.next());
        }
        return response;
    }
}
