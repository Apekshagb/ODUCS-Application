package com.example.oducs_mobile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ReminderList extends Activity  {

	ReminderAdapter eAdapter;
	ListView eventList;
	String Tag = "Directory";	

	static final String KEY_EVENTS = "events";
	static final String KEY_TYPE = "type";
	static final String KEY_TIME = "time";
	static final String KEY_DURATION = "duration";
	static final String KEY_DAY = "day";
	static final String KEY_NOTE = "note";
	static final String REMINDER = "reminder";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder_list);

		eventList = (ListView) findViewById(R.id.reminders_list);	
		eAdapter = new ReminderAdapter(getApplicationContext(), -1,
				getEventListFromFile(ReminderList.this));

		eventList.setAdapter(eAdapter);

	}


	public static ArrayList<EventStructure> getEventListFromFile(Context ctx) {
		ArrayList<EventStructure> eventList;
		eventList = new ArrayList<EventStructure>();

		EventStructure curEventList = null;
		String curText = "";

		try {
			// Get our factory and PullParser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Open up InputStream and Reader of our file.
			FileInputStream fis = ctx.openFileInput("events.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));

			// point the parser to our file.
			xpp.setInput(reader);

			// get initial eventType
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// Get the current tag
				String tagname = xpp.getName();

				// React to different event types appropriately
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagname.equalsIgnoreCase(KEY_EVENTS)) {
						// If we are starting a new <site> block we need
						// a new StackSite object to represent it
						curEventList = new EventStructure();
					}
					break;

				case XmlPullParser.TEXT:
					// grab the current text so we can use it in END_TAG event
					curText = xpp.getText().trim();
					break;

				case XmlPullParser.END_TAG:
					
					if (tagname.equalsIgnoreCase(KEY_EVENTS)) {
						if (!(curEventList.getReminderAlarm() == null || curEventList.getReminderAlarm().equals("")))
								
						eventList.add(curEventList);
						
						
					} else if (tagname.equalsIgnoreCase(KEY_TYPE)) {
						// if </name> use setName() on curSite
						curEventList.seteType(curText);
					} else if (tagname.equalsIgnoreCase(KEY_TIME)) {
						
						curEventList.setTime(curText);
					} else if (tagname.equalsIgnoreCase(KEY_DURATION)) {
						
						curEventList.setDuration(curText);
					} else if (tagname.equalsIgnoreCase(KEY_DAY)) {
						
						curEventList.setDay(curText);
					} else if (tagname.equalsIgnoreCase(KEY_NOTE)) {
						
						curEventList.setNote(curText);
					}else if (tagname.equalsIgnoreCase(REMINDER)) {
						//Log.d("read alarm", "");
						
					
							curEventList.setReminderAlarm(curText);

					}  
					break;

				default:
					break;
				}
				// move on to next iteration
				eventType = xpp.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Log.d("SIZE", ("" + eventList.size()));

		// return the populated list.
		Log.d("List", "" + eventList.toString());
		
	
		Log.d("Remove events", "" + eventList.toString());
		
		return eventList;
		
	}
}
