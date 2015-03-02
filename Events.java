package com.example.oducs_mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Events extends Activity implements OnClickListener {

	EventAdapter eAdapter;
	ListView eventList;
	String Tag = "Directory";
	Button addEvent, reminderList, setTime;
	EditText shake_time;
	String shakeTime;

	static final String KEY_EVENTS = "events";
	static final String KEY_TYPE = "type";
	static final String KEY_TIME = "time";
	static final String KEY_DURATION = "duration";
	static final String KEY_DAY = "day";
	static final String KEY_NOTE = "note";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events);

		/*
		 * Intent intent=getIntent(); String id =
		 * getIntent().getStringExtra("id");
		 * 
		 * if(id.equals("evnets")) { setContentView(R.layout.directory); }
		 */

		eventList = (ListView) findViewById(R.id.event_list);

		eAdapter = new EventAdapter(getApplicationContext(), -1,
				getEventListFromFile(Events.this));

		eventList.setAdapter(eAdapter);

		eventList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// mAdapter.getItem(position);
				String event_type = eAdapter.getItem(position).geteType();
				String event_time = eAdapter.getItem(position).getTime();
				String event_duration = eAdapter.getItem(position)
						.getDuration();
				String event_days = eAdapter.getItem(position).getDay();
				String event_note = eAdapter.getItem(position).getNote();

				// Log.d("vlaue",type);
				Intent editEvent = new Intent(
						"com.example.oducs_mobile.EDITEVENT");

				editEvent.putExtra("event_type", event_type);
				editEvent.putExtra("event_time", event_time);
				editEvent.putExtra("event_duration", event_duration);
				editEvent.putExtra("event_days", event_days);
				editEvent.putExtra("event_note", event_note);

				startActivity(editEvent);
			}
		});

		addEvent = (Button) findViewById(R.id.add_event);
		addEvent.setOnClickListener(this);

		reminderList = (Button) findViewById(R.id.view_reminders);
		reminderList.setOnClickListener(this);

		setTime = (Button) findViewById(R.id.set_time);
		setTime.setOnClickListener(this);

		Intent intent = getIntent();
		shakeTime = intent.getStringExtra("Time");

		// Log.d("SHAKETIME",shakeTime);

		shake_time = (EditText) findViewById(R.id.shake_time);
		shake_time.setText(shakeTime);

	}

	public void onClick(View v) {

		Intent addevent = new Intent(this, AddEvent.class);

		if (v.getId() == R.id.add_event) {
			// Log.d("parsiing", "xyz");
			startActivity(addevent);

		}

		Intent reminderList = new Intent(this, ReminderList.class);

		if (v.getId() == R.id.view_reminders) {
			// Log.d("parsiing", "xyz");
			startActivity(reminderList);

		}
		
		Intent shakeTime = new Intent(this, InsertTime.class);

		if (v.getId() == R.id.set_time) {
			// Log.d("parsiing", "xyz");

			shakeTime.putExtra("ShakeTime", shake_time.getText().toString());
			startActivity(shakeTime);

		}
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
					curText = xpp.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase(KEY_EVENTS)) {
						// if </site> then we are done with current Site
						// add it to the list.
						eventList.add(curEventList);
					} else if (tagname.equalsIgnoreCase(KEY_TYPE)) {
						// if </name> use setName() on curSite
						curEventList.seteType(curText);
						;
					} else if (tagname.equalsIgnoreCase(KEY_TIME)) {
						// if </link> use setLink() on curSite
						curEventList.setTime(curText);
						;
					} else if (tagname.equalsIgnoreCase(KEY_DURATION)) {
						// if </about> use setAbout() on curSite
						curEventList.setDuration(curText);
						;
					} else if (tagname.equalsIgnoreCase(KEY_DAY)) {
						// if </about> use setAbout() on curSite
						curEventList.setDay(curText);
					} else if (tagname.equalsIgnoreCase(KEY_NOTE)) {
						// if </image> use setImgUrl() on curSite
						curEventList.setNote(curText);
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
		return eventList;

	}
}
