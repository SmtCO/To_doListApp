package com.example.sco.to_dolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditFieldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do);
    }

    public void SaveClick(View v){
        String msgText = ((EditText)findViewById(R.id.msg)).getText().toString();
        if(msgText.equals("")){

        }
        else{
            Intent intent = new Intent();
            intent.putExtra(Intent_Constants.INTENT_MESSAGE_FIELD, msgText);
            setResult(Intent_Constants.INTENT_RESULT_CODE, intent);
            finish();
        }
    }
}
