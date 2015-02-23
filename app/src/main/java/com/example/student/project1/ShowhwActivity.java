package com.example.student.project1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by student on 2/23/15 AD.
 */
public class ShowhwActivity extends ActionBarActivity {
    DBHelper helper;
    SimpleCursorAdapter adapter;
    long selectedId;
    ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showhw);
        helper = new DBHelper(this.getApplicationContext());
        Intent i = this.getIntent();
        Long id = i.getLongExtra("_id",0);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT title, course,dueDate,dueTime,detail FROM HWList WHERE _id='" + id + "';", null);
        cursor.moveToFirst();
        String title1 = cursor.getString(0);
        String course1 = cursor.getString(1);
        String dueDate1 = cursor.getString(2);
        String dueTime1 = cursor.getString(3);
        String detail1 = cursor.getString(4);

        ((TextView)findViewById(R.id.title)).setText(title1);
        ((TextView)findViewById(R.id.course)).setText(course1);
        ((TextView)findViewById(R.id.detail)).setText(detail1);
        ((TextView)findViewById(R.id.dueDate)).setText(dueDate1);
        ((TextView)findViewById(R.id.dueTime)).setText(dueTime1);
    }
}

