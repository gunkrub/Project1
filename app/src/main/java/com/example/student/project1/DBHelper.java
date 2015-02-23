package com.example.student.project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String name = "courses.sqlite3";
    private static final int version = 5;


    public DBHelper(Context ctx) {
        super(ctx, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE hwList (" +
                "_id integer primary key autoincrement," +
                "title text not null," +             // course code
                "course text not null," +           // credit
                "detail text not null," +
                "achieved text not null," +
                "dueDate text not null," +
                "dueTime text not null);";         // grade value e.g. 4, 3.5
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS hwList;";
        db.execSQL(sql);
        this.onCreate(db);
    }
}
