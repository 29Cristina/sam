package com.upt.sam;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    final static String TAG = "SecondActivity";

    Button buttonShare, buttonFile;
    EditText editText;

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_frame);
        if(savedInstanceState != null)
            Log.d(TAG, savedInstanceState.toString());

        editText =  findViewById(R.id.editText);

        buttonShare = findViewById(R.id.button);
        buttonShare.setOnClickListener(v -> {
            String url=editText.getText().toString();

            if (url.trim().equals("")) {
                Toast.makeText(this, "It is not allowed to have an empty file!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, url);
            intent.setType("text/plain");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        });

        buttonFile = findViewById(R.id.buttonFile);
        buttonFile.setOnClickListener(v -> {

            String url=editText.getText().toString();

            if (url.trim().equals("")) {
                Toast.makeText(this, "It is not allowed to have an empty field!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            try {

                if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                    buttonFile.setEnabled(false);
                }
                else {
                    String filename = mySamApp.txt";
                    String filepath = Environment.getExternalStorageDirectory()+"/"+DIRECTORY_DOWNLOADS;
                    file = new File(filepath, filename);
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(editText.getText().toString().getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "Text file saved ",
                    Toast.LENGTH_LONG).show();
        });

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            buttonFile.setEnabled(false);
        }
        else {
            String filename = "mySamApp.txt";
            String filepath = Environment.getExternalStorageDirectory()+"/"+DIRECTORY_DOWNLOADS;
            file = new File(filepath, filename);
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
    }