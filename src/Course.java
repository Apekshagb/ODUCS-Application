package com.example.oducs_mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class Course extends Activity implements OnClickListener
{

	CourseAdapter cAdapter;
	ListView courseList;
	String Tag = "Directory";
	Button addcourse;
	
	static final String KEY_COURSE= "course";
	static final String KEY_CRN     = "crn";
	static final String KEY_COURSETITLE     = "coursetitle";
	static final String KEY_CREDITHOURS = "credithours";
	static final String KEY_DAYS   = "days";
	static final String KEY_TIME    = "time";
	static final String KEY_CLASSLOCATION    = "classlocation";
	static final String KEY_INSTRUCTOR   = "instructor";
	static final String KEY_TA    = "ta";
	static final String KEY_OFFICEHOURS    = "officehours";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);
		courseList = (ListView) findViewById(R.id.course_list);

		cAdapter = new CourseAdapter(getApplicationContext(), -1,
				getCourseListFromFile(Course.this));

		courseList.setAdapter(cAdapter);

		courseList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				// mAdapter.getItem(position); 
				 String crn=cAdapter.getItem(position).getCrn();
				 String course_title=cAdapter.getItem(position).getCourse_title(); 
				 String credit_hours=cAdapter.getItem(position).getCredit_hours(); 
				 String days=cAdapter.getItem(position).getDays(); 
				 String time=cAdapter.getItem(position).getTime(); 
				 String class_location=cAdapter.getItem(position).getClass_location();
				 String instructor=cAdapter.getItem(position).getInstructor();
				 String ta=cAdapter.getItem(position).getTa();
				 String office_hours=cAdapter.getItem(position).getOffice_hours();
				 

				// Log.d("vlaue",type);
				Intent editCourse= new Intent("com.example.oducs_mobile.EDITCOURSE");
				
				
				editCourse.putExtra("crn", crn);
				editCourse.putExtra("course_title", course_title);
				editCourse.putExtra("credit_hours", credit_hours);
				editCourse.putExtra("days", days);
				editCourse.putExtra("time", time);
				editCourse.putExtra("class_location", class_location);
				editCourse.putExtra("instructor", instructor);
				editCourse.putExtra("ta", ta);
				editCourse.putExtra("office_hours", office_hours);
				
				startActivity(editCourse);

			
			}
		});

		addcourse = (Button) findViewById(R.id.add_course);
		addcourse.setOnClickListener(this);
	}

	public void onClick(View v) {

		Intent add_course = new Intent(this, AddCourse.class);

		if (v.getId() == R.id.add_course) {
			// Log.d("parsiing", "xyz");
			startActivity(add_course);

		}
	}
	
	public static ArrayList<CourseStructure> getCourseListFromFile(Context ctx) 
	{
	ArrayList<CourseStructure> courseList;
	courseList = new ArrayList<CourseStructure>();
	
	CourseStructure curCourseList = null;
	String curText = "";
	
	try {
		// Get our factory and PullParser
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser xpp = factory.newPullParser();

		// Open up InputStream and Reader of our file.
		FileInputStream fis = ctx.openFileInput("course.xml");
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

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
				if (tagname.equalsIgnoreCase(KEY_COURSE)) {
					// If we are starting a new <site> block we need
					//a new StackSite object to represent it
					curCourseList = new CourseStructure();
				}
				break;

			case XmlPullParser.TEXT:
				//grab the current text so we can use it in END_TAG event
				curText = xpp.getText();
				break;

			case XmlPullParser.END_TAG:
				if (tagname.equalsIgnoreCase(KEY_COURSE)) {
					// if </site> then we are done with current Site
					// add it to the list.
					courseList.add(curCourseList);
				} else if (tagname.equalsIgnoreCase(KEY_CRN)) {
					// if </name> use setName() on curSite
					curCourseList.setCrn(curText);
				} else if (tagname.equalsIgnoreCase(KEY_COURSETITLE)) {
					// if </link> use setLink() on curSite
					curCourseList.setCourse_title(curText);
				} else if (tagname.equalsIgnoreCase(KEY_CREDITHOURS)) {
					// if </about> use setAbout() on curSite
					curCourseList.setCredit_hours(curText);
				} else if (tagname.equalsIgnoreCase(KEY_DAYS)) {
					// if </about> use setAbout() on curSite
					curCourseList.setDays(curText);
				}else if (tagname.equalsIgnoreCase(KEY_TIME)) {
					// if </image> use setImgUrl() on curSite
					curCourseList.setTime(curText);
				}else if (tagname.equalsIgnoreCase(KEY_CLASSLOCATION)) {
					// if </image> use setImgUrl() on curSite
					curCourseList.setClass_location(curText);
				}else if (tagname.equalsIgnoreCase(KEY_INSTRUCTOR)) {
					// if </image> use setImgUrl() on curSite
					curCourseList.setInstructor(curText);
				}else if (tagname.equalsIgnoreCase(KEY_TA)) {
					// if </image> use setImgUrl() on curSite
					curCourseList.setTa(curText);
				}else if (tagname.equalsIgnoreCase(KEY_OFFICEHOURS)) {
					// if </image> use setImgUrl() on curSite
					curCourseList.setOffice_hours(curText);
				}
				break;

			default:
				break;
			}
			//move on to next iteration
			eventType = xpp.next();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

	Log.d( "SIZE", ("" + courseList.size()) );
	
	// return the populated list.
	Log.d("List",""+courseList.toString());
	return courseList;

	

}


}
