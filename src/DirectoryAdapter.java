package com.example.oducs_mobile;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class DirectoryAdapter extends ArrayAdapter<DirectoryList>{
	
	List<DirectoryList> directories;
	Context mContext;
	int textViewResourceId;

	
	public DirectoryAdapter(Context ctx, int textViewResourceId, List<DirectoryList> directories) {
		super(ctx, textViewResourceId, directories);
		/*this.directories = directories;
		this.mContext = ctx;
		this.textViewResourceId = textViewResourceId;*/
		
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
			row = (RelativeLayout)inflater.inflate(R.layout.directory_list, null);
		}
		
		//Get our View References
		
		TextView typeTxt = (TextView)row.findViewById(R.id.typeTxt);
		TextView nameTxt = (TextView)row.findViewById(R.id.nameTxt);
		TextView positionTxt = (TextView)row.findViewById(R.id.positionTxt);
		TextView officeTxt = (TextView)row.findViewById(R.id.officeTxt);
		TextView emailTxt = (TextView)row.findViewById(R.id.emailTxt);
		TextView phoneTxt = (TextView)row.findViewById(R.id.phoneTxt);
		
		

		//Set the relavent text in our TextViews
		typeTxt.setText(getItem(pos).getType());
		nameTxt.setText(getItem(pos).getName());
		positionTxt.setText(getItem(pos).getPosition());
		officeTxt.setText(getItem(pos).getOffice());
		emailTxt.setText(getItem(pos).getEmail());
		phoneTxt.setText(getItem(pos).getPhone());
		
	
		return row;
	}
}
