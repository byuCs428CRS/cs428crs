package database;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.MongoCollection;

import java.net.UnknownHostException;

/**
 * A class to access the test database
 */
public class TestDatabase
{
	public static DB getDB()
	{
		try {
			MongoClient client = new MongoClient("localhost", 27017);
			DB db = client.getDB("test");
			return db;
		} catch (UnknownHostException e) {
			System.out.println("COULDN\'T GET THE DB");
			e.printStackTrace();
		}
		return null;
	}

	public static void dropCollection(MongoCollection collection)
	{
		collection.drop();
	}
}
