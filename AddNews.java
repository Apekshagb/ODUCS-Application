package com.example.oducs_mobile;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddNews extends Activity implements OnClickListener {

	EditText news_title, keyword, author, highlights, picture;
	Button add_news;

	Document doc;
	Element rootElement;
	File folder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		folder = getFilesDir();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnews);

		news_title = (EditText) findViewById(R.id.news_title);
		keyword = (EditText) findViewById(R.id.keyword);
		author = (EditText) findViewById(R.id.author);
		highlights = (EditText) findViewById(R.id.highlights);
		picture = (EditText) findViewById(R.id.picture);
		

		add_news = (Button) findViewById(R.id.addnews);
		add_news.setOnClickListener(this);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
			rootElement = doc.createElement("news_root");
			doc.appendChild(rootElement);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onClick(View v) {

		Log.d("status", "Fileoutputstrem");

		try {
			ArrayList<NewsStructure> news_list = News
					.getNewsListFromFile(AddNews.this);

			for (NewsStructure nl : news_list) {
				rootElement.appendChild(getNews(
						doc, 
						nl.getNews_title(),
						nl.getKeyword(),
						nl.getAuthor(),
						nl.getHighlights(),
						nl.getPicture()));
			}

			
			// append first child element to root element
			rootElement.appendChild(getNews(
						doc,
						news_title.getText().toString(), 
						keyword.getText().toString(),
						author.getText().toString(), 
						highlights.getText().toString(),  
						picture.getText().toString()
					));

			// for output to file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			// write to console or file

			StreamResult streamResult = new StreamResult(new File(folder,
					"news.xml"));
			transformer.transform(source, streamResult);

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {

			e.printStackTrace();
		}

		Intent addNews = new Intent(this, News.class);

		if (v.getId() == R.id.addnews) {
			// Log.d("parsiing", "xyz");
			startActivity(addNews);
		}
	}

	private static Node getNews(Document doc, String event_title, String keyword,String author, String highlights, String picture) {
		
		Element news = doc.createElement("news");
		
		// create title element
		news.appendChild(getNewsElements(doc, news, "title", event_title));

		// create keyword element
		news.appendChild(getNewsElements(doc, news, "keyword", keyword));

		// create author element
		news.appendChild(getNewsElements(doc, news, "author",author));

		// create highlights element
		news.appendChild(getNewsElements(doc, news, "highlights", highlights));

		// create picture element
		news.appendChild(getNewsElements(doc, news, "picture", picture));


		return news;
	}

	private static Node getNewsElements(Document doc, Element element,String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}

