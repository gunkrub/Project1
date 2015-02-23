package com.example.student.project1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AchievedActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, ActionMode.Callback{
    DBHelper helper;
    SimpleCursorAdapter adapter;
    long selectedId;
    ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieved);
        Intent i = this.getIntent();

        showList();
    }
    public void showList(){
        helper = new DBHelper(this.getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, title, " +
                "('[Course: ' || course || '] Due date: ' || dueDate || ' ' || dueTime) subtitle FROM hwList WHERE achieved='1';", null);

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {"title", "subtitle"},
                new int[] {android.R.id.text1, android.R.id.text2},
                0);
        ListView lv = (ListView)findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(this);
        db.close();

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionmode, menu);
        return true;
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
}