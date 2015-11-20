package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/19/2015.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);
    }

    public void onClick3(View view){
        Intent mDataReturnIntent = new Intent();

        EditText txt_username = (EditText) findViewById(R.id.txt_username);

        mDataReturnIntent.setData(Uri.parse(txt_username.getText().toString()));
        setResult(RESULT_OK,mDataReturnIntent);
        finish();
    }

}
