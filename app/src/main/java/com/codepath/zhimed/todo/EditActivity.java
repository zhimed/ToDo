package com.codepath.zhimed.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {


    EditText etItem2;
    Button btnSave;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etItem2 =findViewById(R.id.etItem2);
        btnSave =findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit Item");

        etItem2.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an intent which will contain the results of whatever the user has modified
                Intent intent =new Intent();
                //pass results user has made
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, etItem2.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION,getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                //set the results of intent
                setResult(RESULT_OK,intent);
                //finish activity, close screen and go back
                finish();
            }
        });

    }
}