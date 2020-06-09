package com.example.localdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    UserDatabaseHelper userDb;

    Button btnAddData, btnViewData,btnUpdateData,btnDelete;
    EditText nameEt,ageEt,jobTitleEt,genderEt, idEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDb = new UserDatabaseHelper(this);

        nameEt = (EditText) findViewById(R.id.name_et);
        ageEt = (EditText) findViewById(R.id.age_et);
        jobTitleEt = (EditText) findViewById(R.id.job_title_et);
        genderEt = (EditText) findViewById(R.id.gender_et);
        idEt = (EditText) findViewById(R.id.id_et);

        btnAddData = (Button) findViewById(R.id.btnAddData);

        AddData();

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
}
