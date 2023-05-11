package com.ousl.lab5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText edName, edCourse, edMarks, edUpdateDelete;
    Button btnInsert, btnViewAll, btnUpdate, btnDelete;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Student Marks");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        edName = (EditText) findViewById(R.id.edName);
        edCourse = (EditText) findViewById(R.id.edCourse);
        edMarks = (EditText) findViewById(R.id.edMarks);
        edUpdateDelete = (EditText) findViewById(R.id.edUpdateDelete);

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnViewAll = (Button) findViewById(R.id.btnViewAll);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        insertData();
        viewAllData();
        updateData();
        deleteData();
    }

    //Insert Data
    public void insertData(){
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString();
                String course = edCourse.getText().toString();
                String marks = edMarks.getText().toString();

                boolean isInserted = databaseHelper.insertData(name, course, marks);
                if(isInserted==true){
                    Toast.makeText(MainActivity.this, "Insert Successfully", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Insert Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //View All Data
    public void viewAllData(){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data = databaseHelper.getAllData();
                if(data.getCount() == 0){
                    showMessage("Error", "No Data in Database");
                    return;
                }else {
                    StringBuffer buffer = new StringBuffer();
                    while (data.moveToNext()){
                        buffer.append("ID : " + data.getString(0) + "\n");
                        buffer.append("Name : " + data.getString(1) + "\n");
                        buffer.append("Course : " + data.getString(2) + "\n");
                        buffer.append("Marks : " + data.getString(3) + "\n\n");
                    }
                    showMessage("Data In Database", buffer.toString());
                }
            }
        });
    }

    //Show Message Alert box
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

    //Update Data
    public void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edUpdateDelete.getText().toString();
                String name = edName.getText().toString();
                String course = edCourse.getText().toString();
                String marks = edMarks.getText().toString();

                boolean isUpdated = databaseHelper.updateData(id, name, course, marks);
                if(isUpdated){
                    Toast.makeText(MainActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Delete Data
    public void deleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edUpdateDelete.getText().toString();

                int deletedRow = databaseHelper.deleteData(id);

                if(deletedRow > 0){
                    Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Deleted Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}