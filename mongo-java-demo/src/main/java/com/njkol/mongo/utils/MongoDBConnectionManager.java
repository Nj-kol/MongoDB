package com.njkol.mongo.utils;

import java.io.IOException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * A connection manager for MongoDB
 * 
 * @author Nilanjan Sarkar
 *
 */
public final class MongoDBConnectionManager {

	private static MongoClient mongo;

	public static void init() throws IOException {

		if (mongo == null) {
			String mongoServer = PropertyValues.getPropValues("mongouri");
			Builder options = MongoClientOptions.builder().writeConcern(WriteConcern.ACKNOWLEDGED);
			MongoClientURI uri = new MongoClientURI(mongoServer, options);
			mongo = new MongoClient(uri);
		}
	}

	private MongoDBConnectionManager() {
	}

	/**
	 * 
	 * @param dbName
	 * @param colName
	 * @return
	 */
	public static MongoCollection<Document> getCollection(String dbName, String colName) {
		MongoDatabase db = mongo.getDatabase(dbName);
		return db.getCollection(colName);
	}

	public static void closeConnection() {
		mongo.close();
	}
}
