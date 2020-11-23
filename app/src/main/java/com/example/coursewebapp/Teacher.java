package com.example.coursewebapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursewebapp.Database.DBHelper;

public class Teacher extends AppCompatActivity {

    TextView tvUserNameTeacher;
    EditText etSubjectTeacher, etMessageTeacher;
    Button btnSendTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        tvUserNameTeacher = findViewById(R.id.tvUserNameTeacher);

        etSubjectTeacher = findViewById(R.id.etSubjectTeacher);
        etMessageTeacher = findViewById(R.id.etMessageTeacher);

        btnSendTeacher = findViewById(R.id.btnSendTeacher);

        tvUserNameTeacher.setText(getIntent().getStringExtra("UserName"));

        final String mUsername =   tvUserNameTeacher.getText().toString();

        btnSendTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                boolean insertStatus = dbHelper.sendMessage(mUsername, etSubjectTeacher.getText().toString(), etMessageTeacher.getText().toString());

                if (insertStatus){
                    Toast.makeText(Teacher.this, "Message Sent", Toast.LENGTH_SHORT).show();
                    etSubjectTeacher.getText().clear();
                    etMessageTeacher.getText().clear();
                }else{
                    Toast.makeText(Teacher.this, "Message Not Sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}