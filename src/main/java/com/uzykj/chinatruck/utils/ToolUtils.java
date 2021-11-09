package com.uzykj.chinatruck.utils;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.MongoClient;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
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

    //Object转Map
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();
            Object value = field.get(obj);
            if (value == null)
                value = "";
            map.put(keyName, value);
        }
        return map;
    }


    public static void main(String[] args) {
//        int i = 0;
//        while (i < 20) {
//            System.out.println(getVercode(24));
//            i++;
//        }
        ServerAddress serverAddress1 = new ServerAddress("79.143.52.130", 27017);

        ServerAddress serverAddress2 = new ServerAddress("198.16.43.7", 27017);

        MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder()
                .connectionsPerHost(1000)
                .minConnectionsPerHost(5)
                .connectTimeout(60 * 1000)
                .maxWaitTime(2 * 60 * 1000)
                .build();

        MongoCredential mongoCredential = MongoCredential.createCredential("chinatruck", "admin", "zxcASDqwe".toCharArray());

        MongoClient client1 = new MongoClient(serverAddress1, mongoCredential, mongoClientOptions);
        MongoClient client2 = new MongoClient(serverAddress2, mongoCredential, mongoClientOptions);

        MongoDatabase database1 = client1.getDatabase("chinatruck");

        MongoDatabase database2 = client2.getDatabase("chinatruck");

        //要转移数据的表名
        MongoCollection<Document> collection = database1.getCollection("parts_info");
        //
        MongoCollection<Document> collection2 = database2.getCollection("parts_info");

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
