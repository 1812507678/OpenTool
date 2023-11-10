package com.haijun.opentool.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haijun.opentool.R;
import com.haijun.opentool.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    private TextView tv_xl_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_xl_result = findViewById(R.id.tv_xl_result);
        findViewById(R.id.bt_xlrz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,WebActivity.class),100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            String educationData = data.getStringExtra("educationData");
            String[] split = educationData.split(",");
            if (split.length>=5) {
                final String name = split[0];
                final String school = split[1];
                final String learningFormat = split[2];
                final String degree = split[3];
                final String degreeNo = split[4];
                String showStr =  "姓名：" + name + "\n学校：" + school + "\n学习形式：" + learningFormat +
                        "\n学历：" + degree + "\n证书编号：" + degreeNo;
                tv_xl_result.setText(showStr);
            }
        }
    }
}