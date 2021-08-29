package com.example.androidlesson_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AsyncActivity extends AppCompatActivity implements View.OnClickListener {
    // Khai báo đối tượng progressBar
    private ProgressBar progressBar;
    private Button btnShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async2);
        getViews();
    }

    private void getViews() {
        progressBar = findViewById(R.id.progressBar);
        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShow:
                // Thực hiện tính năng cập nhật progressBar
                // Khởi tạo một đối tượng của lớp ProgressAsyncTask
                new ProgressAsyncTask().execute();
                break;
            default:
                break;
        }
    }

    // Xử lý AsyncTask
    private class  ProgressAsyncTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            // Thực thi công việc dưới Background ở đây
            for(int i=0; i<=100; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "DONE";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(AsyncActivity.this, s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Cập nhật giao diện trên progressBar
            progressBar.setProgress(values[0]);
        }
    }
}