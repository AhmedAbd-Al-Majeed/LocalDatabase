package com.example.localdatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ListUsers extends AppCompatActivity {

    /** Database helper object */
    UserDatabaseHelper userDB;
    /** ArrayList object */
    ArrayList<User> arrayList;
    /** Custom adapter UserAdapter object */
    UserAdapter userAdapter;
    /** ListView for listing the data */
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_list);

        /**create new ArrayList object*/
        arrayList= new ArrayList<>();
        //find the ListView where the data would be shown
        listView = (ListView) findViewById(R.id.list);
        /**create new database object*/
        userDB = new UserDatabaseHelper(this);
        //show the data in the ListView
        loadDataInListView();

    }
    //A method to view the data in the ListView
    private void loadDataInListView(){
        //get all data using getAllData method from the database
        arrayList = userDB.getAllData();
        //if statement to warn the user if the database is empty
        if (arrayList.isEmpty()) {
            Toast.makeText(ListUsers.this, "Database is empty", Toast.LENGTH_LONG).show();
        }else {
            userAdapter = new UserAdapter(this, arrayList);
            //set the custom adapter on the ListView
            listView.setAdapter(userAdapter);
            //Notifies the attached observers that the underlying data
            //has been changed and any View reflecting the data set should refresh itself.
            userAdapter.notifyDataSetChanged();
        }
    }
}
