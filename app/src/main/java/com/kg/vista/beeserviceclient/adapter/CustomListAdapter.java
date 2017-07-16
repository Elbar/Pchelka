package com.kg.vista.beeserviceclient.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kg.vista.beeserviceclient.R;

/**
 * Created by root on 6/18/17.
 */

public class CustomListAdapter extends ArrayAdapter {


    //to reference the Activity
    private final Activity context;

    //to store the animal images
    private final Integer[] imageIDarray;

    //to store the list of countries
    private final String[] categoryArray;


    public CustomListAdapter(Activity context, String[] categoryArrayParam, Integer[] imageIDArrayParam) {

        super(context, R.layout.listview_row, categoryArrayParam);

        this.context = context;
        this.imageIDarray = imageIDArrayParam;
        this.categoryArray = categoryArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null, true);

        //this code gets references to objects in the listview_row.xml file

        ImageView imageView = (ImageView) rowView.findViewById(R.id.categoryImageID);
        TextView textView = (TextView) rowView.findViewById(R.id.categoryText);

        //this code sets the values of the objects to values from the arrays

        imageView.setImageResource(imageIDarray[position]);
        textView.setText(categoryArray[position]);

        return rowView;

    }
}
