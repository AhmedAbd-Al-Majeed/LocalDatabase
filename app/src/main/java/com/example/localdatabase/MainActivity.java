package com.example.localdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    UserDatabaseHelper userDb;

    Button btnAddData, btnViewData,btnUpdateData,btnDelete,btnDeleteAll,btnListAll;
    EditText nameEt,ageEt,jobTitleEt,genderEt, idEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userDb = new UserDatabaseHelper(this);

        idEt = (EditText) findViewById(R.id.id_et);
        nameEt = (EditText) findViewById(R.id.name_et);
        ageEt = (EditText) findViewById(R.id.age_et);
        jobTitleEt = (EditText) findViewById(R.id.job_title_et);
        genderEt = (EditText) findViewById(R.id.gender_et);

        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnViewData= (Button) findViewById(R.id.btnViewData);
        btnUpdateData= (Button) findViewById(R.id.btnUpdateData);
        btnDelete= (Button) findViewById(R.id.btnDelete);
        btnDeleteAll= (Button) findViewById(R.id.btnDeleteALL);
        btnListAll= (Button) findViewById(R.id.btnListAll);



        btnListAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListUsers.class);
                startActivity(intent);
            }
        });

        AddData();
        ViewData();
        UpdateData();
        DeleteData();
        deleteAllData();

    }



    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEt.getText().toString().trim();
                String age = ageEt.getText().toString().trim();
                String jobTitle = jobTitleEt.getText().toString().trim();
                String gender = genderEt.getText().toString().trim();

                boolean insertData = userDb.addData(name, age, jobTitle, gender);

                if (insertData == true) {
                    Toast.makeText(MainActivity.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void ViewData(){
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = userDb.showData();

                if (data.getCount() == 0) {
                    display("Error", "No Data Found.");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("Name: " + data.getString(1) + "\n");
                    buffer.append("Age: " + data.getInt(2) + "\n");
                    buffer.append("Job Title: " + data.getString(3) + "\n");
                    buffer.append("Gender : " + data.getString(4) + "\n");
                }
                display("All Stored Data:", buffer.toString());
            }
        });
    }
    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void UpdateData(){
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = idEt.getText().toString().length();
                if (temp > 0) {
                    boolean update = userDb.updateData(idEt.getText().toString(), nameEt.getText().toString(),
                            ageEt.getText().toString(), jobTitleEt.getText().toString(),genderEt.getText().toString());
                    // if update == true
                    if (update) {
                        Toast.makeText(MainActivity.this, "Successfully Updated Data!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Something Went Wrong :(.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "You Must Enter An ID to Update :(.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = idEt.getText().toString().length();
                if(temp > 0){
                    Integer deleteRow = userDb.deleteData(idEt.getText().toString());
                    if(deleteRow > 0){
                        Toast.makeText(MainActivity.this, "Successfully Deleted The Data!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "You Must Enter An ID to Delete :(.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteAllData(){
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = idEt.getText().toString().length();
                userDb.deleteAll();
                if(temp == 0){
                    Toast.makeText(MainActivity.this, "Successfully Deleted All Data!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Failed to Delete all data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}



