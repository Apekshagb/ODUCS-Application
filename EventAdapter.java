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

public class EventAdapter extends ArrayAdapter<EventStructure>{
	
	List<EventStructure> events;
	Context mContext;
	int textViewResourceId;

	
	public EventAdapter(Context ctx, int textViewResourceId, List<EventStructure> events) {
		super(ctx, textViewResourceId, events);
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
			row = (LinearLayout)inflater.inflate(R.layout.event_view, null);
		}
		
		//Get our View References
		
		TextView event_type = (TextView)row.findViewById(R.id.event_type);
		TextView time = (TextView)row.findViewById(R.id.event_time);
		TextView duration = (TextView)row.findViewById(R.id.event_duration);
		TextView day = (TextView)row.findViewById(R.id.event_day);
		TextView note = (TextView)row.findViewById(R.id.event_note);
		
		
		//Set the relavent text in our TextViews
		event_type.setText(getItem(pos).geteType());
		time.setText(getItem(pos).getTime());
		duration.setText(getItem(pos).getDuration());
		day.setText(getItem(pos).getDay());
		note.setText(getItem(pos).getNote());
		
		return row;
	}
}

