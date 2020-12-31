package com.uzykj.chinatruck.utils;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.MongoClient;
import org.bson.Document;

import java.util.Random;

/**
 * @author ghostxbh
 */
public class ToolUtils {
    public static String getVercode(int size) {
        String chars = "abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
        String vercode = "";
        Random r = new Random();
        for (int i = 0; i < size; ++i) {
            vercode = vercode + chars.charAt(r.nextInt(chars.length()));
        }
        return vercode;
    }

    public static void main(String[] args) {
//        int i = 0;
//        while (i < 20) {
//            System.out.println(getVercode(24));
//            i++;
//        }
        MongoClient client1 = new MongoClient("94.191.18.113", 27017);

        ServerAddress serverAddress = new ServerAddress("79.143.52.130", 27017);

        MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder()
                .connectionsPerHost(1000)
                .minConnectionsPerHost(5)
                .connectTimeout(60 * 1000)
                .maxWaitTime(2 * 60 * 1000)
                .build();

        MongoCredential mongoCredential = MongoCredential.createCredential("chinatruck", "admin", "zxcASDqwe".toCharArray());

        MongoClient client2 = new MongoClient(serverAddress, mongoCredential, mongoClientOptions);

        MongoDatabase database1 = client1.getDatabase("chinatruck");

        MongoDatabase database2 = client2.getDatabase("chinatruck");

        //要转移数据的表名
        MongoCollection<Document> collection = database1.getCollection("component");
        //
        MongoCollection<Document> collection2 = database2.getCollection("component");

        FindIterable<Document> findIterable = collection.find(); //iterator——迭代
        MongoCursor<Document> mongoCursor = findIterable.iterator();  //游标
        while (mongoCursor.hasNext()) {
            Document d = mongoCursor.next(); //遍历每一条数据
            collection2.insertOne(d);
            // System.out.println( mongoCursor.next() );
        }
        System.out.println("转移成功");
    }
}
