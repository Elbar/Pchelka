package com.kg.vista.beeserviceclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.model.Category;

import java.util.ArrayList;

/**
 * Created by fen on 07.05.2017.
 */

public class UsersAdapter extends ArrayAdapter<Category> {
    public UsersAdapter(Context context, ArrayList<Category> categoryArrayList) {
        super(context, 0, categoryArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Category category = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.category_name_tv);
        // Populate the data into the template view using the data object
        tvName.setText(category.category_name);

        // Return the completed view to render on screen
        return convertView;
    }
}