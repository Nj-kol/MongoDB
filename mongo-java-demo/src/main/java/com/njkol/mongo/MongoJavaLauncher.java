package com.njkol.mongo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.njkol.mongo.utils.MongoDBConnectionManager;

/**
 * 
 * @author Nilanjan Sarkar
 *
 */
public class MongoJavaLauncher {

	public static void main(String[] args) throws IOException {

		MongoDBConnectionManager.init();
		MongoCollection<Document> stocks = MongoDBConnectionManager.getCollection("portfolio", "stocks");

		List<Document> docs = new ArrayList<Document>();

		Document d1 = new Document();
		d1.append("symbol", "APPL");
		d1.append("price", 600.0);

		Document d2 = new Document();
		d2.append("symbol", "GOOG");
		d2.append("price", 650.0);

		Document d3 = new Document();
		d2.append("symbol", "NFLX");
		d2.append("price", 60.0);

		Document d4 = new Document();
		d2.append("symbol", "INFY");
		d2.append("price", 50.0);

		Document d5 = new Document();
		d2.append("symbol", "TCS");
		d2.append("price", 100.0);

		docs.add(d1);
		docs.add(d2);
		docs.add(d3);
		docs.add(d4);
		docs.add(d5);

		stocks.insertMany(docs);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutdown Hook is running !");
				MongoDBConnectionManager.closeConnection();
			}
		});

		System.exit(0);
	}
}
