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

public class Contact extends Activity implements OnClickListener{
	
	ContactAdapter contactAdapter;
	ListView contactList;
	String Tag = "Directory";
	Button addContact;
	
	
	static final String KEY_CONTACTS= "contacts";
	static final String KEY_NAME     = "name";
	static final String KEY_OFFICE     = "office";
	static final String KEY_EMAIL    = "email";
	static final String KEY_PHONE    = "phone";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		
		contactList = (ListView) findViewById(R.id.contact_list);

		contactAdapter = new ContactAdapter(getApplicationContext(), -1,
				getContactListFromFile(Contact.this));

		contactList.setAdapter(contactAdapter);

		contactList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				// mAdapter.getItem(position); 
				 String contact_name=contactAdapter.getItem(position).getCon_name(); 
				 String contact_office=contactAdapter.getItem(position).getCon_office(); 
				 String contact_email=contactAdapter.getItem(position).getCon_email(); 
				 String contact_phone=contactAdapter.getItem(position).getCont_phone();
				 

				// Log.d("vlaue",type);
				Intent editcontact= new Intent("com.example.oducs_mobile.EDITCONTACT");
				
				
				editcontact.putExtra("contact_name", contact_name);
				editcontact.putExtra("contact_office", contact_office);
				editcontact.putExtra("contact_email", contact_email);
				editcontact.putExtra("contact_phone", contact_phone);
				
				startActivity(editcontact);

			}
		});

		addContact=(Button) findViewById(R.id.addcontact);
		addContact.setOnClickListener(this);
	}
	

public void onClick(View v) {

	Intent add_contact = new Intent(this, AddContact.class);

	if (v.getId() == R.id.addcontact) {
		// Log.d("parsiing", "xyz");
		startActivity(add_contact);

	}
}

public static ArrayList<ContactStructure> getContactListFromFile(Context ctx) 
{
ArrayList<ContactStructure> contactList;
contactList = new ArrayList<ContactStructure>();

ContactStructure curContactList = null;
String curText = "";

try {
	// Get our factory and PullParser
	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	XmlPullParser xpp = factory.newPullParser();

	// Open up InputStream and Reader of our file.
	FileInputStream fis = ctx.openFileInput("contact.xml");
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
			if (tagname.equalsIgnoreCase(KEY_CONTACTS)) {
				// If we are starting a new <site> block we need
				//a new StackSite object to represent it
				curContactList = new ContactStructure();
			}
			break;

		case XmlPullParser.TEXT:
			//grab the current text so we can use it in END_TAG event
			curText = xpp.getText();
			break;

		case XmlPullParser.END_TAG:
			if (tagname.equalsIgnoreCase(KEY_CONTACTS)) {
				// if </site> then we are done with current Site
				// add it to the list.
				contactList.add(curContactList);
			} else if (tagname.equalsIgnoreCase(KEY_NAME)) {
				// if </name> use setName() on curSite
				curContactList.setCon_name(curText);
			} else if (tagname.equalsIgnoreCase(KEY_OFFICE)) {
				// if </about> use setAbout() on curSite
				curContactList.setCon_office(curText);
			}else if (tagname.equalsIgnoreCase(KEY_EMAIL)) {
				// if </image> use setImgUrl() on curSite
				curContactList.setCon_email(curText);
			}else if (tagname.equalsIgnoreCase(KEY_PHONE)) {
				// if </image> use setImgUrl() on curSite
				curContactList.setCont_phone(curText);
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

Log.d( "SIZE", ("" + contactList.size()) );

// return the populated list.
Log.d("List",""+contactList.toString());
return contactList;



}

}
