package com.example.student.project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddhwActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhw);
        Intent i = this.getIntent();

        final Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = Integer.parseInt(Integer.toString(c.get(Calendar.YEAR)).substring(2,4));

        int hr = c.get(Calendar.HOUR_OF_DAY);
        String min = c.get(Calendar.MINUTE) +"";


        if(min.length()==1){
            min = "0"+min;
        }

        ((TextView)findViewById(R.id.dueDate)).setText(date + "/" + month + "/" + year);
        ((TextView)findViewById(R.id.dueTime)).setText(hr+":"+min);


    }
    public void addClicked(View v) {
        EditText course = (EditText)findViewById(R.id.course);
        EditText title = (EditText)findViewById(R.id.title);
        EditText detail = (EditText)findViewById(R.id.detail);

        String course1 = course.getText().toString();
        String title1 = title.getText().toString();
        String detail1 = detail.getText().toString();
        String dueDate = ((TextView)findViewById(R.id.dueDate)).getText().toString();
        String dueTime = ((TextView)findViewById(R.id.dueTime)).getText().toString();


        if (title1.trim().length() == 0 || course1.trim().length() == 0) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Title and course are necessary.",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            Intent result = new Intent();
            result.putExtra("course", course1);
            result.putExtra("title", title1);
            result.putExtra("detail", detail1);
            result.putExtra("dueDate", dueDate);
            result.putExtra("dueTime",dueTime);

            this.setResult(RESULT_OK, result);
            this.finish();
        }
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}