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

public class InsertTime extends Activity{
		
		Document doc;
		Element rootElement;
		File folder;
		
		String shake_time;
		
		EventAdapter eAdapter;
		ArrayList<TimeStructure> time;
		
		static final String SHAKE_TIME = "shake_time";
		static final String TIME = "time";
		
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub

			folder = getFilesDir();

			super.onCreate(savedInstanceState);
			setContentView(R.layout.events);
		
			Intent intent=getIntent();
			shake_time=intent.getStringExtra("ShakeTime");
			Log.d("Time",shake_time);
		
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.newDocument();
				rootElement = doc.createElement("time_root");
				doc.appendChild(rootElement);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
			
				rootElement.appendChild(getEvent(
							doc,
							shake_time
							
						));

		
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
			

				StreamResult streamResult = new StreamResult(new File(folder,
						"shaketime.xml"));
				transformer.transform(source, streamResult);

			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {

				e.printStackTrace();
			}
			
			Intent time_intent=new Intent(this,Events.class);
			time_intent.putExtra("Time",shake_time);
			startActivity(time_intent);

		}
		
		private static Node getEvent(Document doc, String shake_time) {
			Element time = doc.createElement("time");
			
			
			time.appendChild(getEventElements(doc, time, "reminder", shake_time));

			return time;
		}
		
		private static Node getEventElements(Document doc, Element element,
				String name, String value) {
			Element node = doc.createElement(name);
			node.appendChild(doc.createTextNode(value));
			return node;
		}

}
