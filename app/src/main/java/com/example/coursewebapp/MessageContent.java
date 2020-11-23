package com.example.coursewebapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MessageContent extends AppCompatActivity {

    TextView tvSubjectMSG, tvTeacherMSG ,tvContentMSG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content);

        tvSubjectMSG = findViewById(R.id.tvSubjectMSG);
        tvTeacherMSG = findViewById(R.id.tvTeacherMSG);
        tvContentMSG = findViewById(R.id.tvContentMSG);

        tvSubjectMSG.setText(getIntent().getStringExtra("Subject"));
        tvTeacherMSG.setText(getIntent().getStringExtra("TeacherName"));
        tvContentMSG.setText(getIntent().getStringExtra("Message"));
    }
}