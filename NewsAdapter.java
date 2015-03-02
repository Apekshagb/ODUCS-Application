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

public class NewsAdapter extends ArrayAdapter<NewsStructure> {

	List<NewsStructure> news;
	Context mContext;
	int textViewResourceId;

	public NewsAdapter(Context ctx, int textViewResourceId,
			List<NewsStructure> news) {
		super(ctx, textViewResourceId, news);
		/*
		 * this.directories = directories; this.mContext = ctx;
		 * this.textViewResourceId = textViewResourceId;
		 */

	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		// Log.i("Directory", "getView pos = " + pos);
		LinearLayout row = (LinearLayout) convertView;
		if (null == row) {
			// No recycled View, we have to inflate one.
			/*
			 * LayoutInflater inflater = ((Activity)
			 * mContext).getLayoutInflater(); convertView =
			 * inflater.inflate(textViewResourceId, parent, false);
			 */

			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = (LinearLayout) inflater.inflate(R.layout.news_view,
					null);
		}

		// Get our View References

		TextView news_title = (TextView) row.findViewById(R.id.news_title);
		TextView keyword = (TextView) row.findViewById(R.id.keyword);
		TextView author = (TextView) row.findViewById(R.id.author);
		TextView highlights = (TextView) row.findViewById(R.id.highlights);
		TextView picture = (TextView) row.findViewById(R.id.picture);
		

		// Set the relavent text in our TextViews
		news_title.setText(getItem(pos).getNews_title());
		keyword.setText(getItem(pos).getKeyword());
		author.setText(getItem(pos).getAuthor());
		highlights.setText(getItem(pos).getHighlights());
		picture.setText(getItem(pos).getPicture());
		

		return row;
	}
}
