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

    UserDatabaseHelper userDB;

    ArrayList<User> arrayList;
    UserAdapter userAdapter;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_list);

        arrayList= new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
        userDB = new UserDatabaseHelper(this);

        loadDataInListView();
        //ArrayList <String> theList = new ArrayList<>();
        //Cursor data = userDB.showData();

//        if(data.getCount() == 0){
//            Toast.makeText(ListUsers.this, "the database is empty", Toast.LENGTH_LONG).show();
//        }else{
//            while (data.moveToNext()){
//                theList.add(data.getString(1));
//                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
//                listView.setAdapter(listAdapter);
//            }
//        }

    }
    private void loadDataInListView(){
        arrayList = userDB.getAllData();
        userAdapter = new UserAdapter(this, arrayList);
        listView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }
}
