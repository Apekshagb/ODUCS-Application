package com.example.oducs_mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Directory extends Activity implements OnClickListener

{
	
	DirectoryAdapter mAdapter;
	ListView directoryList;
	String Tag = "Directory";
	Button add;
	
	
	static final String KEY_MEMBERS= "members";
	static final String KEY_TYPE     = "type";
	static final String KEY_NAME     = "name";
	static final String KEY_POSITION = "position";
	static final String KEY_OFFICE   = "office";
	static final String KEY_EMAIL    = "email";
	static final String KEY_PHONE    = "phone";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directory);
		directoryList = (ListView) findViewById(R.id.directory_list);

		mAdapter = new DirectoryAdapter(getApplicationContext(), -1,
				getDirectoryListFromFile(Directory.this));

		directoryList.setAdapter(mAdapter);

		directoryList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				
				// mAdapter.getItem(position); 
				 String type=mAdapter.getItem(position).getType();
				 String name=mAdapter.getItem(position).getName(); 
				 String pos=mAdapter.getItem(position).getPosition(); 
				 String office=mAdapter.getItem(position).getOffice(); 
				 String email=mAdapter.getItem(position).getEmail(); 
				 String phone=mAdapter.getItem(position).getPhone();
				 

				// Log.d("vlaue",type);
				Intent editView= new Intent("com.example.oducs_mobile.EDITPROFILE");
				
				
				editView.putExtra("type", type);
				editView.putExtra("name", name);
				editView.putExtra("pos", pos);
				editView.putExtra("office", office);
				editView.putExtra("email", email);
				editView.putExtra("phone", phone);
				
				startActivity(editView);

			}
		});

		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(this);
	}

	public void onClick(View v) {

		Intent add = new Intent(this, AddMember.class);

		if (v.getId() == R.id.add) {
			// Log.d("parsiing", "xyz");
			startActivity(add);

		}
	}

	public static ArrayList<DirectoryList> getDirectoryListFromFile(Context ctx) 
	{
	ArrayList<DirectoryList> directoryList;
	directoryList = new ArrayList<DirectoryList>();
	
	DirectoryList curDirectoryList = null;
	String curText = "";
	
	try {
		// Get our factory and PullParser
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser xpp = factory.newPullParser();

		// Open up InputStream and Reader of our file.
		FileInputStream fis = ctx.openFileInput("directory.xml");
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
				if (tagname.equalsIgnoreCase(KEY_MEMBERS)) {
					// If we are starting a new <site> block we need
					//a new StackSite object to represent it
					curDirectoryList = new DirectoryList();
				}
				break;

			case XmlPullParser.TEXT:
				//grab the current text so we can use it in END_TAG event
				curText = xpp.getText();
				break;

			case XmlPullParser.END_TAG:
				if (tagname.equalsIgnoreCase(KEY_MEMBERS)) {
					// if </site> then we are done with current Site
					// add it to the list.
					directoryList.add(curDirectoryList);
				} else if (tagname.equalsIgnoreCase(KEY_TYPE)) {
					// if </name> use setName() on curSite
					curDirectoryList.setType(curText);
				} else if (tagname.equalsIgnoreCase(KEY_NAME)) {
					// if </link> use setLink() on curSite
					curDirectoryList.setName(curText);
				} else if (tagname.equalsIgnoreCase(KEY_POSITION)) {
					// if </about> use setAbout() on curSite
					curDirectoryList.setPosition(curText);
				} else if (tagname.equalsIgnoreCase(KEY_OFFICE)) {
					// if </about> use setAbout() on curSite
					curDirectoryList.setOffice(curText);
				}else if (tagname.equalsIgnoreCase(KEY_EMAIL)) {
					// if </image> use setImgUrl() on curSite
					curDirectoryList.setEmail(curText);
				}else if (tagname.equalsIgnoreCase(KEY_PHONE)) {
					// if </image> use setImgUrl() on curSite
					curDirectoryList.setPhone(curText);
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

	Log.d( "SIZE", ("" + directoryList.size()) );
	
	// return the populated list.
	Log.d("List",""+directoryList.toString());
	return directoryList;

	

}

}
