package com.example.localdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    Context context;
    ArrayList <User> arrayList;


    public UserAdapter(Context context, ArrayList<User> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_view, null);

            TextView t1_id = (TextView)convertView.findViewById(R.id.id_text);
            TextView t2_name = (TextView)convertView.findViewById(R.id.name_text);
            TextView t3_age = (TextView)convertView.findViewById(R.id.age_text);
            TextView t4_jobTitle = (TextView)convertView.findViewById(R.id.job_title_text);
            TextView t5_gender = (TextView)convertView.findViewById(R.id.gender_text);

            User user = arrayList.get(position);

            t1_id.setText(String.valueOf(user.getId()));
            t2_name.setText(user.getName());
            t3_age.setText(user.getAge());
            t4_jobTitle.setText(user.getJobTitle());
            t5_gender.setText(user.getGender());



        return convertView;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }
}
