package com.example.androidlesson_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Khai báo đối tượng Handler
    private Handler handler;
    private TextView txtNumber;
    private Button btnStart;
    private static final int UP_NUMBER = 100;
    private static final int NUMBER_DONE = 101;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        processHandler();
    }

    private void getViews() {
        txtNumber = findViewById(R.id.txtNumber);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    private void processHandler() {
        // Khởi tạo đối tượng Handler
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UP_NUMBER:
                        // Thực hiện cập nhật giá trị lên UI
                        isUpdate = true;
                        // Cập nhật UI với giá trị mới
                        txtNumber.setText(String.valueOf(msg.arg1));
                        break;
                    case NUMBER_DONE:
                        // Cập nhật lại giao diện hiển thị khi kết thúc cập nhật
                        txtNumber.setText("SUCCESS!");
                        isUpdate = false;
                        break;
                    default:
                        break;
                }
            }
        };

    }

    @Override
    public void onClick(View view) {
        // Thực thi công việc khi Click vào đối tượng button
        switch (view.getId()) {
            case R.id.btnStart:
                // Thực hiện công việc khi ấn button
                updNNumber();
                break;
            default:
                break;
        }
    }

    private void updNNumber() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Viết xử lý cập nhật giá trị ở luồng này
                for(int i=0; i<10; i++) {
                    // Khai báo message chứa nội dung tin nhắn cần đưa vào msg pool
                    Message msg = new Message();
                    // Gán công việc cho đối tượng msg
                    msg.what = UP_NUMBER;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Gửi tin nhắn vào message pool
                }
                // Gửi thông báo hoàn thành công việc
                handler.sendEmptyMessage(NUMBER_DONE);
            }
        }).start();
    }

}