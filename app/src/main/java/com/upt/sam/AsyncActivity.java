package com.upt.sam;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AsyncActivity extends AppCompatActivity {
    private EditText time;
    private TextView finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        time = findViewById(R.id.in_time);
        Button button = findViewById(R.id.btn_run);
        finalResult = findViewById(R.id.tv_result);
        button.setOnClickListener(v -> {
            AsyncTaskRunner runner = new AsyncTaskRunner();
            String sleepTime = time.getText().toString();
            runner.execute(sleepTime);
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // ProgressUpdate() running
            try {
                int time = Integer.parseInt(params[0])*1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // result of the application after a long time
            progressDialog.dismiss();
            finalResult.setText(result);
        }

        
        @Override
        protected void onProgressUpdate(String... text) {
            finalResult.setText(text[0]);

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AsyncActivity.this,
                    "ProgressDialog",
                    "Wait for "+time.getText().toString()+ " seconds");
        }

    }
}