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
/*import android.provider.DocumentsContract.Document;
 import android.renderscript.Element;*/
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddMember extends Activity implements OnClickListener {

	EditText fullname, position, office, email, phone, type;
	Button addprofile;

	Document doc;
	Element rootElement;
	File folder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		folder = getFilesDir();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_profile);

		type = (EditText) findViewById(R.id.type);
		fullname = (EditText) findViewById(R.id.fullname);
		position = (EditText) findViewById(R.id.position);
		office = (EditText) findViewById(R.id.office);
		email = (EditText) findViewById(R.id.email);
		phone = (EditText) findViewById(R.id.phone);

		addprofile = (Button) findViewById(R.id.addprofile);
		addprofile.setOnClickListener(this);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
			rootElement = doc.createElement("directory_root");
			doc.appendChild(rootElement);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onClick(View v) {

		Log.d("status", "Fileoutputstrem");

		try {
			ArrayList<DirectoryList> directory_list = Directory
					.getDirectoryListFromFile(AddMember.this);

			for (DirectoryList dl : directory_list) {
				rootElement.appendChild(getMember(
						doc, 
						dl.getType(),
						dl.getName(),
						dl.getPosition(),
						dl.getOffice(),
						dl.getEmail(), 
						dl.getPhone()));
			}

			
			// append first child element to root element
			rootElement.appendChild(getMember(
						doc,
						type.getText().toString(), 
						fullname.getText().toString(),
						position.getText().toString(), 
						office.getText().toString(), 
						email.getText().toString(), 
						phone.getText().toString()
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
					"directory.xml"));
			transformer.transform(source, streamResult);

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {

			e.printStackTrace();
		}

		Intent addprofile = new Intent(this, Directory.class);

		if (v.getId() == R.id.addprofile) {
			// Log.d("parsiing", "xyz");
			startActivity(addprofile);
		}
	}

	private static Node getMember(Document doc, String type, String name,
			String position, String office, String email, String phone) {
		Element members = doc.createElement("members");

		members.appendChild(getMemberElements(doc, members, "type", type));

		// create name element
		members.appendChild(getMemberElements(doc, members, "name", name));

		// create position element
		members.appendChild(getMemberElements(doc, members, "position",
				position));

		// create office element
		members.appendChild(getMemberElements(doc, members, "office", office));

		// create email element
		members.appendChild(getMemberElements(doc, members, "email", email));

		// create phone element
		members.appendChild(getMemberElements(doc, members, "phone", phone));

		return members;
	}

	private static Node getMemberElements(Document doc, Element element,
			String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}
