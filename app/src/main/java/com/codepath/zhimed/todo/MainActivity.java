package com.codepath.zhimed.todo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button buttonadd;
    EditText etItem;
    RecyclerView rvitems;
    ItemsAdapter itemsAdapter;

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonadd=findViewById(R.id.buttonadd);
        etItem = findViewById(R.id.etItem);
        rvitems=findViewById(R.id.rvitems);

        etItem.setText("Hello there");


        loadItems();
        

        ItemsAdapter.OnLongClickListener onLongClickListener =  new ItemsAdapter.OnLongClickListener(){

            @Override
            public void onItemLongClicked(int position) {
                //Delete item from the model
                items.remove(position);
                //NOtify adapter at which position we deleted item from
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplication(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("Main Activity", "Single click at position");
                //create new activity
                Intent i =new Intent(MainActivity.this, EditActivity.class);
                //pass data being edited
                i.putExtra(KEY_ITEM_TEXT,items.get(position));
                i.putExtra(KEY_ITEM_TEXT,position);
                //display the activity
                startActivityForResult(i,EDIT_TEXT_CODE);
            }
        };
        itemsAdapter=new ItemsAdapter(items, onLongClickListener, onClickListener);
        rvitems.setAdapter(itemsAdapter);
        rvitems.setLayoutManager(new LinearLayoutManager(this));

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem =etItem.getText().toString();
                //Add Item to Model
                items.add(todoItem);
                //notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });


    }

    //handle the result of edit activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && requestCode==EDIT_TEXT_CODE){
            //retrieve updated text value
            String itemText= data.getStringExtra(KEY_ITEM_TEXT);

            //extract the original position of edited item
            int position=data.getExtras().getInt(KEY_ITEM_POSITION);

            //update model at right position with new item text
            items.set(position,itemText);
            //notify adapter
            itemsAdapter.notifyItemChanged(position);
            //persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(),"Item updated successfully!",Toast.LENGTH_SHORT).show();
        }
        else{
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }


    //loads items by reading every line of data.txt file
    private void loadItems(){
        try {
            items=new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error Reading Items",e);
            items=new ArrayList<>();
        }
    }

    //Writes to the file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing items",e);
        }
    }

}