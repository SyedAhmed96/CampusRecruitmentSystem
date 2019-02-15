package com.example.ahmed.campusrecruitmentsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ishaq Hassan on 7/23/2017.
 */

public class StudentCustomAdapter extends ArrayAdapter {
    private ArrayList<String> contents;
    public StudentCustomAdapter(@NonNull Context context, ArrayList<String> contents) {
        super(context, 0,contents);
        this.contents = contents;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_student_list_item,parent,false);
        }else{
            view = convertView;
        }
        String content = contents.get(position);

        //From activity_student_list_item
        TextView textView = view.findViewById(R.id.TextView);

        textView.setText(content);

        return view;
    }

}
