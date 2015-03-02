package com.example.oducs_mobile;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ODUCS_MobileMainActivity extends Activity implements
		OnClickListener {

	// defining the buttons
	Button about, directory, courses, news, contact, events;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oducs__mobile_main);

		// initializing the buttons
		about = (Button) findViewById(R.id.about);
		directory = (Button) findViewById(R.id.directory);
		courses = (Button) findViewById(R.id.courses);
		news = (Button) findViewById(R.id.news);
		contact = (Button) findViewById(R.id.contact);
		events = (Button) findViewById(R.id.events);

		// on click for about button
		about.setOnClickListener(this);
		directory.setOnClickListener(this);
		courses.setOnClickListener(this);
		news.setOnClickListener(this);
		contact.setOnClickListener(this);
		events.setOnClickListener(this);

		File folder = getFilesDir();

		// Generate XML file for Directory
		File f = new File(folder, "directory.xml");
		if (!folder.exists()) {
			folder.mkdir();
			Log.i("Debug", "Error in creating the folder ");
		}
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				Log.e("IOException", "Error in creating the file");
			} finally {

			}

		}

		// Generate XML file for Course
		File f1 = new File(folder, "course.xml");

		if (!folder.exists()) {
			folder.mkdir();
			Log.i("Debug", "Error in creating the folder ");
		}
		if (!f1.exists()) {
			try {
				f1.createNewFile();
			} catch (IOException e) {
				Log.e("IOException", "Error in creating the file");
			} finally {

			}

		}

		// Generate XML file for News
		File f2 = new File(folder, "news.xml");

		if (!folder.exists()) {

			folder.mkdir();
			Log.i("Debug", "Error in creating the folder ");
		}

		if (!f2.exists()) {
			try {
				f2.createNewFile();
				Log.i("Debug", "Error in creating the file ");
			} catch (IOException e) {

			} finally {

			}

		}

		// Generate XML file for Events
		File f3 = new File(folder, "events.xml");

		if (!folder.exists()) {

			folder.mkdir();
			Log.i("Debug", "Error in creating the folder ");
		}

		if (!f3.exists()) {
			try {
				f3.createNewFile();
				Log.i("Debug", "Error in creating the file ");
			} catch (IOException e) {

			} finally {

			}

		}

		File f4 = new File(folder, "contact.xml");

		if (!folder.exists()) {
			folder.mkdir();
			Log.i("Debug", "Error in creating the folder ");
		}
		if (!f4.exists()) {
			try {
				f4.createNewFile();
			} catch (IOException e) {
				Log.e("IOException", "Error in creating the file");
			} finally {

			}

		}

		File f5 = new File(folder, "reminder.xml");

		if (!folder.exists()) {
			folder.mkdir();
			Log.i("Debug", "Error in creating the folder ");
		}
		if (!f5.exists()) {
			try {
				f5.createNewFile();
			} catch (IOException e) {
				Log.e("IOException", "Error in creating the file");
			} finally {

			}

		}

	}

	public void onClick(View v) {

		Intent about = new Intent(this, About.class);

		if (v.getId() == R.id.about) {
			startActivity(about);
			// Log.d("parsiing", "xyz");
			// about.putExtra("id", "about");

		}

		Intent directory = new Intent(this, Directory.class);

		if (v.getId() == R.id.directory) {
			startActivity(directory);
			// Log.d("parsiing", "xyz");
			// directory.putExtra("id", "directory");
		}

		Intent courses = new Intent(this, Course.class);
		if (v.getId() == R.id.courses) {
			startActivity(courses);
			// Log.d("parsiing", "xyz");
			// courses.putExtra("id", "courses");
		}

		Intent news = new Intent(this, News.class);
		if (v.getId() == R.id.news) {
			startActivity(news);
			// Log.d("parsiing", "xyz");
			// news.putExtra("id", "news");
		}

		Intent events = new Intent(this, Events.class);
		if (v.getId() == R.id.events) {
			startActivity(events);
			// Log.d("parsiing", "xyz");
			// events.putExtra("id", "events");
		}

		Intent contact = new Intent(this, Contact.class);
		if (v.getId() == R.id.contact) {
			startActivity(contact);
			// Log.d("parsiing", "xyz");
			// contact.putExtra("id", "contact");
		}
	}

}
