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

public class AddCourse extends Activity implements OnClickListener {

	EditText crn, course_title, credit_hours, days, time, class_location,instructor,ta,office_hours;
	Button addcourse;

	Document doc;
	Element rootElement;
	File folder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		folder = getFilesDir();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcourse);

		crn = (EditText) findViewById(R.id.crn);
		course_title = (EditText) findViewById(R.id.course_title);
		credit_hours = (EditText) findViewById(R.id.credit_hours);
		days = (EditText) findViewById(R.id.days);
		time = (EditText) findViewById(R.id.time);
		class_location = (EditText) findViewById(R.id.class_location);
		instructor = (EditText) findViewById(R.id.instructor);
		ta = (EditText) findViewById(R.id.ta);
		office_hours = (EditText) findViewById(R.id.office_hours);

		addcourse = (Button) findViewById(R.id.addcourse);
		addcourse.setOnClickListener(this);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
			rootElement = doc.createElement("course_root");
			doc.appendChild(rootElement);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onClick(View v) {

		Log.d("status", "Fileoutputstrem");

		try {
			ArrayList<CourseStructure> course_list = Course
					.getCourseListFromFile(AddCourse.this);

			for (CourseStructure cl : course_list) {
				rootElement.appendChild(getEvent(
						doc, 
						cl.getCrn(),
						cl.getCourse_title(),
						cl.getCredit_hours(),
						cl.getDays(),
						cl.getTime(),
						cl.getClass_location(),
						cl.getInstructor(),
						cl.getTa(),
						cl.getOffice_hours()));
			}

			// append first child element to root element
			rootElement.appendChild(getEvent(
						doc, 
						crn.getText().toString(),
						course_title.getText().toString(),
						credit_hours.getText().toString(), 
						days.getText().toString(),
						time.getText().toString(),
						class_location.getText().toString(),
						instructor.getText().toString(),
						ta.getText().toString(),
						office_hours.getText().toString()));

			// for output to file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			// write to console or file

			StreamResult streamResult = new StreamResult(new File(folder,
					"course.xml"));
			transformer.transform(source, streamResult);

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {

			e.printStackTrace();
		}

		Intent addcourse = new Intent(this, Course.class);

		if (v.getId() == R.id.addcourse) {
			// Log.d("parsiing", "xyz");
			startActivity(addcourse);
		}
	}

	private static Node getEvent(Document doc, String crn, String course_title,
			String credit_hours, String days, String time,String class_location,String instructor,String ta,String office_hours) {
		Element course = doc.createElement("course");

		course.appendChild(getEventElements(doc, course, "crn", crn));

		// create name element
		course.appendChild(getEventElements(doc, course, "coursetitle", course_title));

		// create position element
		course.appendChild(getEventElements(doc, course, "credithours",credit_hours));

		// create office element
		course.appendChild(getEventElements(doc, course, "days", days));

		// create email element
		course.appendChild(getEventElements(doc, course, "time", time));

		// create phone element
		course.appendChild(getEventElements(doc, course, "classlocation", class_location));
		
		course.appendChild(getEventElements(doc, course, "instructor", instructor));
		
		course.appendChild(getEventElements(doc, course, "ta", ta));
		
		course.appendChild(getEventElements(doc, course, "officehours", office_hours));

		return course;
	}

	private static Node getEventElements(Document doc, Element element,
			String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}
