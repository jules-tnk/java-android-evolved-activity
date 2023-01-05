package com.example.evolvedactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ContactInteractionActivity extends AppCompatActivity {

    Button contactIdButton;
    Button contactDetailButton;
    Button callContactButton;

    int Perm_CTC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_interaction);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, Perm_CTC);

        contactIdButton = (Button) findViewById(R.id.contactIdButton);
        contactDetailButton = (Button) findViewById(R.id.contactDetailButton);
        callContactButton = (Button) findViewById(R.id.callContactButton);

        contactIdButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
                        startActivity(pickContactIntent);
                    }
                }
        );

        contactDetailButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

        callContactButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        //check the permission type using the requestCode
        if (requestCode == Perm_CTC) {
            //the array is empty if not granted
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "GRANTED CALL",Toast.LENGTH_SHORT).show();
            }
        }
    }


}