
# Mongo DB Java API 

## Dependency

	<dependency>
		<groupId>org.mongodb</groupId>
		<artifactId>mongo-java-driver</artifactId>
		<version>3.6.0</version>
	</dependency>

## Get a connection

* The code below shows the preferred way of connecting to MongoDB ( as of 3.6 )

	String mongoServer = "mongodb://127.0.0.1";
	Builder options = MongoClientOptions.builder().writeConcern(WriteConcern.ACKNOWLEDGED);
	MongoClientURI uri = new MongoClientURI(mongoServer, options);
	MongoClient mongo = new MongoClient(uri);
	MongoDatabase db = mongo.getDatabase("portfolio");
	MongoCollection<Document> stocks = db.getCollection("stocks");
			
    Note : The URI mode is a better choice since it can accomodate any deployment types
	       ( Standalone, Replica set & sharderd ) without changing the code
	
* MongoClient is a MongoDB client with internal connection pooling. For most applications, 
  you should have one MongoClient instance for the entire JVM.
  
* The default value of the connectionsPerHost option has been increased to 100
  from the old default value of 10 used by the superseded Mongo class
  
* By default, all read and write operations will be made on the primary,
  but it's possible to read from secondaries by changing the read preference:

  mongoClient.setReadPreference(ReadPreference.secondaryPreferred());
 
* By default, all write operations will wait for acknowledgment by the server, 
  as the default write concern is WriteConcern.ACKNOWLEDGED

## Insert Document

Document d = new Document();
d.append("symbol", "INFY");
d.append("price", 40.0);
stocks.insertOne(d);

## Update Document

Document update = new Document();
update.append("symbol", "TCS");
update.append("price", 60.0);

stocks.updateOne(Filters.eq("symbol", "TCS"), new Document("$set", update));
		
## Find document

// Create a document with search criteria
Document ds = new Document();
d.append("symbol", "TCS");

FindIterable<Document> fi = stocks.find(ds);

for (Document d1 : fi) {
	...
}

## Delete Document

Document filter = new Document();
filter.append("symbol", "INFY");
filter.append("price", 40.0);

stocks.deleteOne(filter);

Notes
======
* An Instance of MongoConnection is actually an instance of the pool,
  not an individual connection

* The pool can be tuned with an instance of the MongoOptions class 
  passed to a new MongoConnection
 
Links
=====
https://examples.javacodegeeks.com/software-development/mongodb/java-mongodb-update-document-example/

