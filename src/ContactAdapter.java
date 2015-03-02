package com.example.oducs_mobile;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactAdapter extends ArrayAdapter<ContactStructure>{
	
	List<ContactStructure> contacts;
	Context mContext;
	int textViewResourceId;

	
	public ContactAdapter(Context ctx, int textViewResourceId, List<ContactStructure> contacts) {
		super(ctx, textViewResourceId, contacts);
		/*this.directories = directories;
		this.mContext = ctx;
		this.textViewResourceId = textViewResourceId;*/
		
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		//Log.i("Directory", "getView pos = " + pos);
		LinearLayout row = (LinearLayout)convertView;
		if(null == row){
			//No recycled View, we have to inflate one.
			/*LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(textViewResourceId, parent, false);*/
			
			LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = (LinearLayout)inflater.inflate(R.layout.contact_view, null);
		}
		
		//Get our View References
		
		TextView contact_name = (TextView)row.findViewById(R.id.contact_name);
		TextView contact_office = (TextView)row.findViewById(R.id.contact_office);
		TextView contact_email = (TextView)row.findViewById(R.id.contact_email);
		TextView contact_phone = (TextView)row.findViewById(R.id.contact_phone);
		
		
		

		//Set the relavent text in our TextViews
		contact_name.setText(getItem(pos).getCon_name());
		contact_office.setText(getItem(pos).getCon_office());
		contact_email.setText(getItem(pos).getCon_email());
		contact_phone.setText(getItem(pos).getCont_phone());
		
		
	
		return row;
	}
}

