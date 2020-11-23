package com.example.coursewebapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursewebapp.Database.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText etUserNameReg, etPasswordReg;
    CheckBox cbTeacherReg, cbStudentReg;
    Button btnRegisterReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUserNameReg= findViewById(R.id.etUserNameReg);
        etPasswordReg = findViewById(R.id.etPasswordReg);
        cbTeacherReg = findViewById(R.id.cbTeacherReg);
        cbStudentReg= findViewById(R.id.cbStudentReg);
        btnRegisterReg = findViewById(R.id.btnRegisterReg);

        btnRegisterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());

                String userType = null;

                if (cbStudentReg.isChecked()){
                    userType = "Student";
                }
                else if (cbTeacherReg.isChecked()){
                    userType = "Teacher";
                }
                else{
                    userType = null;
                }

                boolean regStatus = dbHelper.registerUser(etUserNameReg.getText().toString(),
                        etPasswordReg.getText().toString(), userType);

                if (regStatus){
                    Toast.makeText(RegisterActivity.this, "Resiter Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Resiter Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}