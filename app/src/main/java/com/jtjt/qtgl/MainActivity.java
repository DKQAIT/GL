package com.jtjt.qtgl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jtjt.qtgl.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bycw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ins = new Intent(MainActivity.this,BmonthActiviy.class);
                startActivity(ins);
            }
        });

        findViewById(R.id.xiangqing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,DetailsActivity.class);
                startActivity(in);

            }
        });
        findViewById(R.id.tgrz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ins = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(ins);
            }
        });


    }
}
