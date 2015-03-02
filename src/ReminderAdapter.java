package com.example.oducs_mobile;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReminderAdapter  extends ArrayAdapter<EventStructure>{
	
	//List<EventStructure> events;
	Context mContext;
	int textViewResourceId;

	
	public ReminderAdapter(Context ctx, int textViewResourceId, List<EventStructure> reminders) 
	{
		super(ctx, textViewResourceId, reminders);
		
		
		
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		//Log.i("Directory", "getView pos = " + pos);
		LinearLayout row = (LinearLayout)convertView;
		if(null == row){
			
			
			LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = (LinearLayout)inflater.inflate(R.layout.reminder_view, null);
		}
		
		//Get our View References
		
		TextView event_type = (TextView)row.findViewById(R.id.reminder_type);
		TextView time = (TextView)row.findViewById(R.id.reminder_time);
		TextView duration = (TextView)row.findViewById(R.id.reminder_duration);
		TextView day = (TextView)row.findViewById(R.id.reminder_day);
		TextView note = (TextView)row.findViewById(R.id.reminder_note);
		TextView reminder = (TextView)row.findViewById(R.id.reminder_reminder);
		
		//Set the relavent text in our TextViews
		
		event_type.setText(getItem(pos).geteType());
		time.setText(getItem(pos).getTime());
		duration.setText(getItem(pos).getDuration());
		day.setText(getItem(pos).getDay());
		note.setText(getItem(pos).getNote());
		reminder.setText(getItem(pos).getReminderAlarm());
		
		return row;
	}

}
