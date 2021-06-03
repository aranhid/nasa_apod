package com.aranhid.nasa_apod;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryListAdapter extends BaseAdapter {
    public static final String EXTRA_DATE = "com.aranhid.guessthenumber.DATE";

    Context context;
    List<SearchHistoryItem> items;

    public  HistoryListAdapter(Context context, List<SearchHistoryItem> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position)._id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchHistoryItem item = items.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        ImageView image = convertView.findViewById(R.id.history_item_image);
        TextView title = convertView.findViewById(R.id.history_item_title);
        TextView date = convertView.findViewById(R.id.history_item_date);
        convertView.setOnClickListener(this::onClick);

        title.setText(item.title);
        date.setText(item.date);
        Picasso.get().load(item.image_url).into(image);
        return convertView;
    }

    private void onClick(View view) {
        Intent intent = new Intent(context, HistoryItem.class);
        TextView text = view.findViewById(R.id.history_item_date);
        String date =  text.getText().toString();
        intent.putExtra(EXTRA_DATE, date);
        context.startActivity(intent);
    }
}
