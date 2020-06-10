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
    /**ArrayList object*/
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
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (listItemView == null) {
            listItemView = inflater.inflate(
                    R.layout.custom_list_view, parent, false);
        }

        //find the TextViews where the data would be shown.
        TextView t1_id = (TextView)listItemView.findViewById(R.id.id_text);
        TextView t2_name = (TextView)listItemView.findViewById(R.id.name_text);
        TextView t3_age = (TextView)listItemView.findViewById(R.id.age_text);
        TextView t4_jobTitle = (TextView)listItemView.findViewById(R.id.job_title_text);
        TextView t5_gender = (TextView)listItemView.findViewById(R.id.gender_text);

        // Get the User object located at this position in the list
        User user = arrayList.get(position);

        //set the values entered by the user to the textViews
        t1_id.setText(String.valueOf(user.getId()));
        t2_name.setText(user.getName());
        t3_age.setText(user.getAge());
        t4_jobTitle.setText(user.getJobTitle());
        t5_gender.setText(user.getGender());
        return listItemView;
    }
    @Override
    //return the size of the ArrayList
    public int getCount() {
        return this.arrayList.size();
    }
}
