package com.example.coursewebapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursewebapp.Database.DBHelper;
import com.example.coursewebapp.Models.MessageObj;

import java.util.ArrayList;
import java.util.List;

public class Student extends AppCompatActivity {

    TextView tvUserNameStudent;
    ListView lvMessageList;
    int sizeList;
    List<MessageObj> MessageObjectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        tvUserNameStudent = findViewById(R.id.tvUserNameStudent);
        lvMessageList = findViewById(R.id.lvMessageList);

        tvUserNameStudent.setText(getIntent().getStringExtra("UserName"));



        DBHelper dbHelper = new DBHelper(getApplicationContext());

        MessageObjectsList = dbHelper.readAllMessages();

        sizeList = MessageObjectsList.size();

        createNotificationChannel();

        Toast.makeText(this, "size : "+sizeList, Toast.LENGTH_SHORT).show();

        List<String> SubjectList = new ArrayList<>();

        for (MessageObj obj : MessageObjectsList) {
            SubjectList.add(obj.getSubject());
        }


        ArrayAdapter<String> adapterSubjects = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, SubjectList);
        // Specify the layout to use when the list of choices appears
        adapterSubjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        lvMessageList.setAdapter(adapterSubjects);

        lvMessageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                MessageObj messageObj = MessageObjectsList.get(i);
                String TeacherName = messageObj.getTeacherName();
                String Subject = messageObj.getSubject();
                String Message = messageObj.getMessage();

                Toast.makeText(Student.this, "Clicked on : " + messageObj.getSubject(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MessageContent.class);
                intent.putExtra("TeacherName", TeacherName);
                intent.putExtra("Subject", Subject);
                intent.putExtra("Message", Message);

                startActivity(intent);

            }
        });


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String CHANNEL_ID = "Message Alerts";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(this, MessageContent.class);

            MessageObj messageObj = MessageObjectsList.get(sizeList-1);

            String TeacherName = messageObj.getTeacherName();
            String Subject = messageObj.getSubject();
            String Message = messageObj.getMessage();

            intent.putExtra("TeacherName", TeacherName);
            intent.putExtra("Subject", Subject);
            intent.putExtra("Message", Message);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("New Message")
                    .setContentText("Click here to open")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            notificationManager.notify(023, builder.build());


        }
    }


}