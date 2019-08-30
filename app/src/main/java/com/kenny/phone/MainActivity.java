package com.kenny.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kenny.phone.files.activities.FileActivity;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tvShow;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialView();
    }


    private void initialView() {
        tvShow = (TextView) findViewById(R.id.tv_main_show);
        btnSend = (Button) findViewById(R.id.btn_main_send);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_send:
                // 发送数据
                Intent intent = new Intent(MainActivity.this, FileActivity.class);
                startActivity(intent);
                break;
        }

    }

}
