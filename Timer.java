package com.example.oducs_mobile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

public class Timer {
	
	static final String TIME = "time";
	static final String REMINDER = "reminder";
	
	//ArrayList<ShakeTime> time= getTimeListFromFile(aContext);
	
	
	public interface TimerListener{
		public void timerStopped();
	}
	
	private static ScheduledExecutorService scheduledExecutorService;
	private static ScheduledFuture monitorTask;
	
	private static boolean accelStopped = false;
	
	private static long firstTime = -1;
	
	static {
		scheduledExecutorService = Executors.newScheduledThreadPool(1);
	}
	
	public static ArrayList<TimeStructure> getTimeListFromFile(Context ctx) {
		ArrayList<TimeStructure> timetList;
		timetList = new ArrayList<TimeStructure>();

		TimeStructure curTimetList = null;
		String curText = "";

		try {
			// Get our factory and PullParser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Open up InputStream and Reader of our file.
			FileInputStream fis = ctx.openFileInput("shaketime.xml");
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
					if (tagname.equalsIgnoreCase(TIME)) {
						// If we are starting a new <site> block we need
						// a new StackSite object to represent it
						curTimetList = new TimeStructure();
					}
					break;

				case XmlPullParser.TEXT:
					// grab the current text so we can use it in END_TAG event
					curText = xpp.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase(TIME)) {
						
						timetList.add(curTimetList);
					} else if (tagname.equalsIgnoreCase(REMINDER)) {
						// if </name> use setName() on curSite
						curTimetList.setShake_time(curText);
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

		Log.d("SIZE", ("" + timetList.size()));

		// return the populated list.
		Log.d("List", "" + timetList.toString());
		return timetList;

	}
	

	
	
	private static TimerListener timerListener;
	
	public static void start(TimerListener timerListener){
		Timer.timerListener = timerListener;
		accelStopped = false;
		
		
		if (monitorTask == null){
			firstTime = System.currentTimeMillis();
			monitorTask = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
				
				@Override
				public void run() {
					checkIfStopped();
					
				}
			}, 0, 1, TimeUnit.SECONDS);
		}
		
	}
	
	public static void checkIfStopped(){
		
		if (firstTime != -1 && ((System.currentTimeMillis() - firstTime)>5000)){
			if (!accelStopped && Timer.timerListener != null){
				Timer.timerListener.timerStopped();
				stopTimer();
			}
		}
	}
	
	public static void stopTimer(){
		Log.v("timer", "timer stopped");
		if (monitorTask != null && ! monitorTask.isCancelled()){
			monitorTask.cancel(true);
			monitorTask = null;
		}
	}
	
	public static void acceStoped(){
		accelStopped = true;
		stopTimer();
	}

}

