package com.example.evolvedactivity;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton launchPhoneCallButton;
    private ImageButton openWebPageButton;
    private ImageButton openPersoActivityButton;
    private EditText phoneNumberEditText;
    private EditText urlEditText;

    private int CALL_Perm = 1;
    private String defaultUrl = "https://www.emi.ac.ma/";
    private boolean isUserLoggedIn=false;
    private static final int REQUEST_CODE = 1;

    private String phoneNumberToCall;

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
                        if (!isUserLoggedIn()){
                            openLoginActivity();
                        } else {
                            launchPhoneCall();
                        }
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

    private void openLoginActivity() {
        Intent loginActivityIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(loginActivityIntent, REQUEST_CODE);
    }

    private void launchPhoneCall() {
        String phoneNumber = phoneNumberEditText.getText().toString();
        Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
        phoneCallIntent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(phoneCallIntent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            boolean isUserLoggedIn = data.getExtras().getBoolean("isLoggedIn");
            setUserLoggedIn(isUserLoggedIn);
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("pendingPhoneCall", phoneNumberEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String pendingPhoneCall = savedInstanceState.getString("pendingPhoneCall");
       phoneNumberEditText.setText(pendingPhoneCall);
    }

    //GETTERS AND SETTERS
    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
    }

    public String getPhoneNumberToCall() {
        return phoneNumberToCall;
    }

    public void setPhoneNumberToCall(String phoneNumberToCall) {
        this.phoneNumberToCall = phoneNumberToCall;
    }
}