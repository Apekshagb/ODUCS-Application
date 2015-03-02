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

public class AddContact extends Activity implements OnClickListener {

	EditText contact_name, contact_office, contact_email, contact_phone;
	Button addContact;

	Document doc;
	Element rootElement;
	File folder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		folder = getFilesDir();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontact);

		contact_name = (EditText) findViewById(R.id.contact_name);
		contact_office = (EditText) findViewById(R.id.contact_office);
		contact_email = (EditText) findViewById(R.id.contact_email);
		contact_phone = (EditText) findViewById(R.id.contact_phone);
		

		addContact = (Button) findViewById(R.id.addcontacts);
		addContact.setOnClickListener(this);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
			rootElement = doc.createElement("contact_root");
			doc.appendChild(rootElement);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onClick(View v) {

		Log.d("status", "Fileoutputstrem");

		try {
			ArrayList<ContactStructure> contact_list = Contact
					.getContactListFromFile(AddContact.this);

			for (ContactStructure cl : contact_list) {
				rootElement.appendChild(getContact(
						doc, 
						cl.getCon_name(),
						cl.getCon_office(),
						cl.getCon_email(),
						cl.getCont_phone()));
			}

			
			// append first child element to root element
			rootElement.appendChild(getContact(
						doc,
						contact_name.getText().toString(), 
						contact_office.getText().toString(),
						contact_email.getText().toString(), 
						contact_phone.getText().toString()
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
					"contact.xml"));
			transformer.transform(source, streamResult);

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {

			e.printStackTrace();
		}

		Intent add_contact = new Intent(this, Contact.class);

		if (v.getId() == R.id.addcontacts) {
			// Log.d("parsiing", "xyz");
			startActivity(add_contact);
		}
	}

	private static Node getContact(Document doc, String name, String office,String email, String phone) {
		Element contacts = doc.createElement("contacts");

		// create name element
		contacts.appendChild(getContactElements(doc, contacts, "name", name));

		// create office element
		contacts.appendChild(getContactElements(doc, contacts, "office", office));

		// create email element
		contacts.appendChild(getContactElements(doc, contacts, "email", email));

		// create phone element
		contacts.appendChild(getContactElements(doc, contacts, "phone", phone));

		return contacts;
	}

	private static Node getContactElements(Document doc, Element element,
			String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}

