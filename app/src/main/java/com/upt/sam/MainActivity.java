package com.upt.sam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    Button buttonTop;
    Button buttonLow;
    Button buttonAsync;
    private static final int MY_CAMERA_REQUEST_CODE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            Log.d(TAG, savedInstanceState.toString());

        buttonTop = findViewById(R.id.buttonTop);

        buttonTop.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), SecondActivity.class);
            startActivity(intent);
        });

        buttonLow = findViewById(R.id.buttonLow);
        buttonLow.setOnClickListener(this::enableCameraPermission);

        buttonAsync = findViewById(R.id.buttonAsync);
        buttonAsync.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), AsyncActivity.class);
            startActivity(intent);
        });
    }

    private void enableCameraPermission(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        else {
            Snackbar.make(v, "You already have the permission to access the camera!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission allowed!",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Camera permission denied!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}