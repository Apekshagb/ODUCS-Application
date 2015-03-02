package com.example.oducs_mobile;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CourseAdapter extends ArrayAdapter<CourseStructure>{
	
	List<CourseStructure> courses;
	Context mContext;
	int textViewResourceId;

	
	public CourseAdapter(Context ctx, int textViewResourceId, List<CourseStructure> courses) {
		super(ctx, textViewResourceId, courses);
		
		
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		//Log.i("Directory", "getView pos = " + pos);
		RelativeLayout row = (RelativeLayout)convertView;
		if(null == row){
			//No recycled View, we have to inflate one.
			/*LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(textViewResourceId, parent, false);*/
			
			LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = (RelativeLayout)inflater.inflate(R.layout.course_view, null);
		}
		
		//Get our View References
		
		TextView crn = (TextView)row.findViewById(R.id.crn);
		TextView course_title = (TextView)row.findViewById(R.id.course_title);
		TextView credit_hours = (TextView)row.findViewById(R.id.credit_hours);
		TextView days = (TextView)row.findViewById(R.id.days);
		TextView time = (TextView)row.findViewById(R.id.time);
		TextView class_location = (TextView)row.findViewById(R.id.class_location);
		TextView instructor = (TextView)row.findViewById(R.id.instructor);
		TextView ta = (TextView)row.findViewById(R.id.ta);
		TextView office_hours = (TextView)row.findViewById(R.id.office_hours);
		

		//Set the relavent text in our TextViews
		crn.setText(getItem(pos).getCrn());
		course_title.setText(getItem(pos).getCourse_title());
		credit_hours.setText(getItem(pos).getCredit_hours());
		days.setText(getItem(pos).getDays());
		time.setText(getItem(pos).getTime());
		class_location.setText(getItem(pos).getClass_location());
		instructor.setText(getItem(pos).getInstructor());
		ta.setText(getItem(pos).getTa());
		office_hours.setText(getItem(pos).getOffice_hours());
		
	
		return row;
	}
}

