/*
I got help from that site by watching on youtube:
https://www.youtube.com/watch?v=3QHgJnPPnqQ
 */

package com.example.sco.to_dolist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    ListView lstViev;
    ArrayList<String> arrList;
    ArrayAdapter<String> arrAdapter;
    String msgText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstViev = (ListView) findViewById(R.id.Lview);
        arrList = new ArrayList<>();
        arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrList);
        lstViev.setAdapter(arrAdapter);

        lstViev.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
            // TODO Auto-generated method stub
            arrList.remove(pos);
            arrAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "To-do Item Deleted", Toast.LENGTH_LONG).show();
            return true;
        }

        });

        lstViev.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditMessageActivity.class);
                intent.putExtra(Intent_Constants.INTENT_MESSAGE_DATA, arrList.get(position).toString());
                intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION, position);
                startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE_2);
            }
        });

        try {
            Scanner sc = new Scanner(openFileInput("Todo.txt"));
            while(sc.hasNextLine()) {
                String data = sc.nextLine();
                arrAdapter.add(data);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

  @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("What would you like to do");
        String options[]={"Delete", "Cancel"};
        for(String option: options){
            menu.add(option);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            PrintWriter pw = new PrintWriter(openFileOutput("Todo.txt", Context.MODE_PRIVATE));
            for (String data : arrList){
                pw.println(data);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        try {
            PrintWriter pw = new PrintWriter(openFileOutput("Todo.txt", Context.MODE_PRIVATE));
            for (String data : arrList){
                pw.println(data);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finish();
        super.onDestroy();
    }

    public void delClick(View v){

    }

    public void addClick(View v){
        Intent intent = new Intent();
        intent.setClass(this, EditFieldActivity.class);
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Intent_Constants.INTENT_REQUEST_CODE){
            msgText = data.getStringExtra(Intent_Constants.INTENT_MESSAGE_FIELD);
            arrList.add(msgText);
            arrAdapter.notifyDataSetChanged();
        }
        else if(resultCode==Intent_Constants.INTENT_REQUEST_CODE_2){
            msgText = data.getStringExtra(Intent_Constants.INTENT_CHANGED_MESSAGE);
            position = data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION, -1);
            arrList.remove(position);
            arrList.add(position, msgText);
            arrAdapter.notifyDataSetChanged();
        }
    }
}
