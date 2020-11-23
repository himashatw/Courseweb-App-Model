package com.example.coursewebapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursewebapp.Database.DBHelper;

public class LoginActivity extends AppCompatActivity {

    EditText etUserNameLogin, etPasswordLogin;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserNameLogin = findViewById(R.id.etUserNameLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                String userType = dbHelper.checkLogin(etUserNameLogin.getText().toString(), etPasswordLogin.getText().toString());


                if(userType.equals("Teacher")){
                    Toast.makeText(LoginActivity.this, "Teacher Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Teacher.class);
                    intent.putExtra("UserName", etUserNameLogin.getText().toString());
                    startActivity(intent);
                    etUserNameLogin.getText().clear();
                    etPasswordLogin.getText().clear();
                }
                else if (userType.equals("Student")){
                    Toast.makeText(LoginActivity.this, "Student Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Student.class);
                    intent.putExtra("UserName", etUserNameLogin.getText().toString());
                    startActivity(intent);
                    etUserNameLogin.getText().clear();
                    etPasswordLogin.getText().clear();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Unsuccessful : "+userType, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}