package com.example.evolvedactivity;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton launchPhoneCallButton;
    ImageButton openWebPageButton;
    ImageButton openPersoActivityButton;
    EditText phoneNumberEditText;
    EditText urlEditText;


    int CALL_Perm = 1;
    String defaultUrl = "https://www.emi.ac.ma/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, CALL_Perm);

        launchPhoneCallButton = (ImageButton) findViewById(R.id.LaunchPhoneCallButton);
        phoneNumberEditText = (EditText) findViewById(R.id.editTextPhone);

        openWebPageButton = (ImageButton) findViewById(R.id.openWebPageButton);
        urlEditText = (EditText) findViewById(R.id.urlEditText);

        openPersoActivityButton = (ImageButton) findViewById(R.id.openPersoActivityButton);


        launchPhoneCallButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phoneNumber = phoneNumberEditText.getText().toString();

                        Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);

                        phoneCallIntent.setData(Uri.parse("tel:"+phoneNumber));

                        startActivity(phoneCallIntent);
                    }
                }
        );

        openWebPageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = urlEditText.getText().toString();
                        if (url.equals("")){
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(defaultUrl)));
                        } else {
                            String completeUrl = "https://"+url;
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(completeUrl)));
                        }
                    }
                }
        );

        openPersoActivityButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent persoActivityIntent = new Intent(getApplicationContext(), PersoActivity.class);
                        startActivity(persoActivityIntent);
                    }
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check the permission type using the requestCode
        if (requestCode == CALL_Perm) {
            //the array is empty if not granted
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "GRANTED CALL", Toast.LENGTH_SHORT).show();
            }
        }
    }
}