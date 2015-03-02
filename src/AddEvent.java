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

public class AddEvent extends Activity implements OnClickListener {

	EditText event_type, event_time, event_duration, event_day, event_note;
	Button addevent;

	Document doc;
	Element rootElement;
	File folder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		folder = getFilesDir();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.addevent);

		event_type = (EditText) findViewById(R.id.event_type);
		event_time = (EditText) findViewById(R.id.event_time);
		event_duration = (EditText) findViewById(R.id.event_duration);
		event_day = (EditText) findViewById(R.id.event_day);
		event_note = (EditText) findViewById(R.id.event_note);
		

		addevent = (Button) findViewById(R.id.addevent);
		addevent.setOnClickListener(this);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
			rootElement = doc.createElement("event_root");
			doc.appendChild(rootElement);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onClick(View v) {

		Log.d("status", "Fileoutputstrem");

		try {
			ArrayList<EventStructure> event_list = Events
					.getEventListFromFile(AddEvent.this);

			for (EventStructure el : event_list) {
				rootElement.appendChild(getEvent(
						doc, 
						el.geteType(),
						el.getTime(),
						el.getDuration(),
						el.getDay(),
						el.getNote()));
			}

			
			// append first child element to root element
			rootElement.appendChild(getEvent(
						doc,
						event_type.getText().toString(), 
						event_time.getText().toString(),
						event_duration.getText().toString(), 
						event_day.getText().toString(), 
						event_note.getText().toString() 
						
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
					"events.xml"));
			transformer.transform(source, streamResult);

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {

			e.printStackTrace();
		}

		Intent addevent = new Intent(this, Events.class);

		if (v.getId() == R.id.addevent) {
			// Log.d("parsiing", "xyz");
			startActivity(addevent);
		}
	}

	private static Node getEvent(Document doc, String type, String time,String duration, String day, String note) {
		Element events = doc.createElement("events");
		
		// create name type
		events.appendChild(getEventElements(doc, events, "type", type));

		// create name time
		events.appendChild(getEventElements(doc, events, "time", time));

		// create position duration
		events.appendChild(getEventElements(doc, events, "duration",duration));

		// create office day
		events.appendChild(getEventElements(doc, events, "day", day));

		// create email note
		events.appendChild(getEventElements(doc, events, "note", note));

		

		return events;
	}

	private static Node getEventElements(Document doc, Element element,
			String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}
