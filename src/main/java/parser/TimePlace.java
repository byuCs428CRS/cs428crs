package parser;

import com.mongodb.BasicDBObject;

public class TimePlace {

	public String day;
	public String startTime;
	public String endTime;
	public String location;
	
	public TimePlace(String day, String startTime, String endTime, String location) {
		
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
	}
	
	public void print(String append) {
		
		System.out.println(append + day + " / " + startTime + " / " + endTime + " / " + location);
	}
	
	/* create a BasicDBObject out of this TimePlace object and return it */
	public BasicDBObject getDBObject() {
		
		BasicDBObject curTP = new BasicDBObject();
		
		curTP.put("day", day);
		curTP.put("startTime", startTime);
		curTP.put("endTime", endTime);
		curTP.put("location", location);
		
		return curTP;
	}
}