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

    }
    private void loadDataInListView(){
        arrayList = userDB.getAllData();
        if (arrayList.isEmpty()) {
            Toast.makeText(ListUsers.this, "Database is empty", Toast.LENGTH_LONG).show();
        }else {
            userAdapter = new UserAdapter(this, arrayList);
            listView.setAdapter(userAdapter);
            userAdapter.notifyDataSetChanged();
        }
        }
    }
