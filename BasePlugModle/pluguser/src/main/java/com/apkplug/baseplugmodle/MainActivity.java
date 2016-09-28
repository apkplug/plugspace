package com.apkplug.baseplugmodle;

import android.content.Intent;
import android.opengl.GLES30;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apkplug.trust.PlugManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void startActivity(String activity) {
        Intent intent = new Intent();
        intent.setClassName(MainActivity.this,activity);
        startActivity(intent);
    }

    private void callDispatch(){

    }
}
