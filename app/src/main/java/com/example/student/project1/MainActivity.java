package com.example.student.project1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, ActionMode.Callback {

    DBHelper helper;
    SimpleCursorAdapter adapter;
    long selectedId;
    ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showList();

    }

    public void addClicked(View v){
        Intent i;
        i = new Intent(this, AddhwActivity.class);
        startActivityForResult(i, 88);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 88) {
            if (resultCode == RESULT_OK) {
                String title = data.getStringExtra("title");
                String course = data.getStringExtra("course");
                String detail = data.getStringExtra("detail");
                String dueDate = data.getStringExtra("dueDate");
                String dueTime = data.getStringExtra("dueTime");

                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues r = new ContentValues();
                r.put("title", title);
                r.put("course", course);
                r.put("detail", detail);
                r.put("dueDate", dueDate);
                r.put("dueTime", dueTime);
                r.put("achieved",0);
                long newId = db.insert("hwList", null, r);

                if (newId != -1) {
                    Toast t = Toast.makeText(this.getApplicationContext(),
                            "Successfully added",
                            Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    Toast t = Toast.makeText(this.getApplicationContext(),
                            "Unable to add",
                            Toast.LENGTH_SHORT);
                    t.show();
                }

                showList();
            }
        }
    }

    public void showList(){
        helper = new DBHelper(this.getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, title, " +
                "('[Course: ' || course || '] Due date: ' || dueDate || ' ' || dueTime) subtitle FROM hwList WHERE achieved='0';", null);

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {"title", "subtitle"},
                new int[] {android.R.id.text1, android.R.id.text2},
                0);
        ListView lv = (ListView)findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(this);
        lv.setOnItemClickListener(this);
        db.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                deleteClicked();
                mode.finish();
                break;
            case R.id.menu_mark:
                markClicked();
                mode.finish();
                break;
            default:
                return false;
        }
        return true;
    }

    private void deleteClicked() {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowCount = db.delete("hwList", "_id = ?",
                new String[]{Long.toString(selectedId)});
        if (rowCount == 1) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Deleted Homework", Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Unable to delete",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        db.close();
        showList();
    }
    private void markClicked() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues r = new ContentValues();
        r.put("achieved", "1");
        long newId = db.update("hwList", r, "_id = ?",
                new String[]{Long.toString(selectedId)});

        if (newId != -1) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Achieved a homework",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        showList();
    }

    public void achievedClicked(View v){
        Intent i;
        i = new Intent(this, AchievedActivity.class);
        startActivity(i);
    }
    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        // Save the selected
        selectedId = id;
        // Start the ActionMode with an item is long-clicked
        actionMode = this.startActionMode(this);
        return true;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_actionmode, menu);

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedId = id;
        Intent i;
        i = new Intent(this, ShowhwActivity.class);
        i.putExtra("_id", id);
        startActivity(i);

    }
}
