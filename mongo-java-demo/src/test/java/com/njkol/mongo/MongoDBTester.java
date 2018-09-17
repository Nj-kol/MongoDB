package com.njkol.mongo;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.njkol.mongo.utils.MongoDBConnectionManager;

/**
 * 
 * @author Nilanjan Sarkar
 *
 */
public class MongoDBTester {

	private static MongoCollection<Document> stocks;

	@BeforeClass
	public static void setUp() throws IOException {
		MongoDBConnectionManager.init();
		stocks = MongoDBConnectionManager.getCollection("portfolio", "stocks");
	}

	@Test
	public void testAdd() {

		MongoCollection<Document> stocks = MongoDBConnectionManager.getCollection("portfolio", "stocks");
		Document d = new Document();
		d.append("symbol", "TCS");
		d.append("price", 40.0);
		stocks.insertOne(d);

		Document ds = new Document();
		d.append("symbol", "TCS");

		FindIterable<Document> fi = stocks.find(ds);

		String res = "";

		for (Document d1 : fi) {
			res = (String) d1.get("symbol");
		}
		assertEquals("TCS", res);
	}

	@Test
	public void testUpdate() {

		Document update = new Document();
		update.append("symbol", "TCS");
		update.append("price", 60.0);

		stocks.updateOne(Filters.eq("symbol", "TCS"), new Document("$set", update));

		Double res = 0d;

		for (Document d1 : stocks.find(update)) {
			res = (Double) d1.get("price");
		}

		assertEquals(new Double(60), res);
	}

	@Test
	public void testDelete() {

		Document filter = new Document();
		filter.append("symbol", "INFY");
		filter.append("price", 40.0);

		stocks.deleteOne(filter);

		FindIterable<Document> e = stocks.find(filter);
		Document d = e.first();

		assertNull(d);
	}

	@AfterClass
	public static void tearDown() {
		MongoDBConnectionManager.closeConnection();
	}

}
