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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class News extends Activity implements OnClickListener {

	NewsAdapter nAdapter;
	ListView newsList;
	String Tag = "Directory";
	Button add_news;

	static final String KEY_NEWS = "news";
	static final String KEY_TITLE = "title";
	static final String KEY_KEYWORD = "keyword";
	static final String KEY_AUTHOR = "author";
	static final String KEY_HIGHLIGHTS = "highlights";
	static final String KEY_PICTURE = "picture";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);

		/*
		 * Intent intent=getIntent(); String id = intent.getStringExtra("id");
		 * 
		 * if(id.equals("news")) { setContentView(R.layout.directory); }
		 */

		newsList = (ListView) findViewById(R.id.news_list);

		nAdapter = new NewsAdapter(getApplicationContext(), -1,
				getNewsListFromFile(News.this));

		newsList.setAdapter(nAdapter);

		newsList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				// mAdapter.getItem(position); 
				 String news_title=nAdapter.getItem(position).getNews_title();
				 String keyword=nAdapter.getItem(position).getKeyword(); 
				 String author=nAdapter.getItem(position).getAuthor(); 
				 String highlights=nAdapter.getItem(position).getHighlights(); 
				 String picture=nAdapter.getItem(position).getPicture(); 
				
				 

				// Log.d("vlaue",type);
				Intent editNews= new Intent("com.example.oducs_mobile.EDITNEWS");
				
				
				editNews.putExtra("news_title", news_title);
				editNews.putExtra("keyword", keyword);
				editNews.putExtra("author", author);
				editNews.putExtra("highlights", highlights);
				editNews.putExtra("picture", picture);
				
				
				startActivity(editNews);
			}
		});

		add_news = (Button) findViewById(R.id.add_news);
		add_news.setOnClickListener(this);

	}

	public void onClick(View v) {

		Intent add_news = new Intent(this, AddNews.class);

		if (v.getId() == R.id.add_news) {
			// Log.d("parsiing", "xyz");
			startActivity(add_news);

		}
	}

	public static ArrayList<NewsStructure> getNewsListFromFile(Context ctx) {
		ArrayList<NewsStructure> newsList;
		newsList = new ArrayList<NewsStructure>();

		NewsStructure curNewsList = null;
		String curText = "";

		try {
			// Get our factory and PullParser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Open up InputStream and Reader of our file.
			FileInputStream fis = ctx.openFileInput("news.xml");
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
					if (tagname.equalsIgnoreCase(KEY_NEWS)) {
						// If we are starting a new <site> block we need
						// a new StackSite object to represent it
						curNewsList = new NewsStructure();
					}
					break;

				case XmlPullParser.TEXT:
					// grab the current text so we can use it in END_TAG event
					curText = xpp.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase(KEY_NEWS)) {
						// if </site> then we are done with current Site
						// add it to the list.
						newsList.add(curNewsList);
					} else if (tagname.equalsIgnoreCase(KEY_TITLE)) {
						// if </name> use setName() on curSite
						curNewsList.setNews_title(curText);
					} else if (tagname.equalsIgnoreCase(KEY_KEYWORD)) {
						// if </link> use setLink() on curSite
						curNewsList.setKeyword(curText);
					} else if (tagname.equalsIgnoreCase(KEY_AUTHOR)) {
						// if </about> use setAbout() on curSite
						curNewsList.setAuthor(curText);
					} else if (tagname.equalsIgnoreCase(KEY_HIGHLIGHTS)) {
						// if </about> use setAbout() on curSite
						curNewsList.setHighlights(curText);
					} else if (tagname.equalsIgnoreCase(KEY_PICTURE)) {
						// if </image> use setImgUrl() on curSite
						curNewsList.setPicture(curText);
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

		Log.d("SIZE", ("" + newsList.size()));

		// return the populated list.
		Log.d("List", "" + newsList.toString());
		return newsList;

	}

}
