package com.example.localdatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /** Database helper object */
    UserDatabaseHelper userDb;

    /**buttons to add data, view data, update data, delete a user, delete all users, open a ListView */
    Button btnAddData, btnViewData,btnUpdateData,btnDelete,btnDeleteAll,btnListAll;

    /** EditTexts for the input fields*/
    EditText nameEt,ageEt,jobTitleEt, idEt;

    /** Spinner field to choose the user's gender */
    private Spinner mGenderSpinner;

    /**to save the gender choice*/
    private String mGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** initialize Database helper */
        userDb = new UserDatabaseHelper(this);

        //find the EditTexts where the data would be entered.
        idEt = (EditText) findViewById(R.id.id_et);
        nameEt = (EditText) findViewById(R.id.name_et);
        ageEt = (EditText) findViewById(R.id.age_et);
        jobTitleEt = (EditText) findViewById(R.id.job_title_et);

        //find the spinner where the gender would be chosen.
        mGenderSpinner = (Spinner) findViewById(R.id.gender_spinner);

        //find the layout buttons.
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnViewData= (Button) findViewById(R.id.btnViewData);
        btnUpdateData= (Button) findViewById(R.id.btnUpdateData);
        btnDelete= (Button) findViewById(R.id.btnDelete);
        btnDeleteAll= (Button) findViewById(R.id.btnDeleteALL);
        btnListAll= (Button) findViewById(R.id.btnListAll);

        //setting onClickListener on the btnListAll button
        btnListAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start a new intent by clicking on the btnListAll button to open the ListUsers activity.
                Intent intent = new Intent(MainActivity.this, ListUsers.class);
                startActivity(intent);
            }
        });
        //add the data that the user entered into the database.
        AddData();
        //view the data that the user entered into the database.
        ViewData();
        //update the data that the user entered into the database.
        UpdateData();
        //delete user's data that the user entered into the database.
        DeleteData();
        //delete all data from the database that the user entered.
        deleteAllData();
        //a method where the spinner is setup.
        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = "Male"; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = "Female"; // Female
                    }
                    else{
                        mGender = "Unknown"; // Unknown
                    }
                }
            }
            //Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = ""; // Unknown
            }
        });
    }

    //A method that adds the data to the database
    public void AddData() {
        //setting onClickListener on the btnAddData button to start adding data to the database.
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the name from the nameEt EditText.
                String name = nameEt.getText().toString().trim();
                //get the age from the ageEt EditText.
                String age = ageEt.getText().toString().trim();
                //get the job title from the jobTitleEt EditText.
                String jobTitle = jobTitleEt.getText().toString().trim();

                /**
                 * Insert data we got into the database.
                 * @return boolean
                 */
                boolean insertData = userDb.addData(name, age, jobTitle, mGender);

                /**
                 * to make sure that the data were Successfully inserted
                 */
                if (insertData) {
                    Toast.makeText(MainActivity.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error Inserting Data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void ViewData(){
        //setting onClickListener on the btnViewData button to view the data in the database.
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cursor to move through the database data.
                Cursor data = userDb.showData();

                if (data.getCount() == 0) {
                    display("Error.", "Please Enter a New User.");
                    return;
                }
                //StringBuffer to get all data and display it
                StringBuffer buffer = new StringBuffer();

                //getting the data through the cursor
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("Name: " + data.getString(1) + "\n");
                    buffer.append("Age: " + data.getInt(2) + "\n");
                    buffer.append("Job Title: " + data.getString(3) + "\n");
                    buffer.append("Gender : " + data.getString(4) + "\n");
                }
                //call display method.
                display("All Stored Users:", buffer.toString());
            }
        });
    }

    //a method to display the data
    public void display(String title, String message){
        //AlertDialog to view the data in the database
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //a method to update a user's data using the id
    public void UpdateData(){
        //setting onClickListener on the btnUpdate Data button to update the data in the database.
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the id entered in the idET EditText
                int temp = idEt.getText().toString().length();
                //if there is an ID
                if (temp > 0) {

                    //get the new user's info and update the database
                    boolean update = userDb.updateData(idEt.getText().toString(), nameEt.getText().toString(),
                            ageEt.getText().toString(), jobTitleEt.getText().toString(),mGender);
                    // if update == true
                    if (update) {
                        Toast.makeText(MainActivity.this, "Successfully Updated Data!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Date is not updated.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //if no ID was entered show this toast message.
                    Toast.makeText(MainActivity.this, "You Must Enter An ID to Update.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //a method to delete a user's data using the id
    public void DeleteData(){
        //setting onClickListener on the btnUpdate Data button to update the data in the database.
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the id entered in the idET EditText
                int temp = idEt.getText().toString().length();
                //if there is an ID
                if(temp > 0){
                    //getting the ID of the deleted row
                    Integer deleteRow = userDb.deleteData(idEt.getText().toString());
                    //if there was an ID show this toast message
                    if(deleteRow > 0){
                        Toast.makeText(MainActivity.this, "Successfully Deleted The Data!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Date is not updated.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    //if no ID was entered show this toast message
                    Toast.makeText(MainActivity.this, "You Must Enter An ID to delete.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //a method to update a user's data using the id
    public void deleteAllData(){
        //setting onClickListener on the btnUpdate Data button to update the data in the database.
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the id entered in the idET EditText
                int temp = idEt.getText().toString().length();
                //delete all data
                userDb.deleteAll();
                //if statement to make sure that the data was deleted
                if(temp == 0){
                    Toast.makeText(MainActivity.this, "Successfully Deleted All Data!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Failed to Delete all data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}