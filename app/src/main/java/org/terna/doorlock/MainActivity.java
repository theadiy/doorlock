package org.terna.doorlock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static org.terna.doorlock.NotificationApp.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    Button lockButton, unlockButton;

    private NotificationManagerCompat notificationManagerCompat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lockButton = findViewById(R.id.lockButton);
        unlockButton = findViewById(R.id.unlockButton);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        DatabaseReference flag = FirebaseDatabase.getInstance().getReference().child("safe_flag/door/lock");
        flag.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.e("MainActivity","value changed to - "+dataSnapshot.getValue());

                if(dataSnapshot.getValue().equals("true")){
                    Log.e("MainActivity","value changed to ------------ "+dataSnapshot.getValue());
                    sendOnChannel1("Door is lock");
                }else if(dataSnapshot.getValue().equals("false")){
                    Log.e("MainActivity","value changed to ------------ "+dataSnapshot.getValue());
                    sendOnChannel1("Door is unlock");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendOnChannel1(v,"Door is lock");
                FirebaseDatabase.getInstance().getReference().child("safe_flag/door/lock").setValue("true");
            }
        });

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel1(v,"Door is unlock");
                FirebaseDatabase.getInstance().getReference().child("safe_flag/door/lock").setValue("false");

            }
        });

    }

    public void sendOnChannel1(View view, String note){
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_icon)
                .setContentText("DOOR-LOCk")
                .setContentText(note)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Uri.parse("android.resource://org.terna.doorlock/" + R.raw.inflicted))
                .build();

        notificationManagerCompat.notify(1,notification);
    }

    public void sendOnChannel1(String note){
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_icon)
                .setContentText("DOOR-LOCk")
                .setContentText(note)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Uri.parse("android.resource://org.terna.doorlock/" + R.raw.inflicted))
                .build();
        notificationManagerCompat.notify(1,notification);
    }
}
