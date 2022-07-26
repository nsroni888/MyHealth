package com.pialroni.myhealth;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShowData extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    final Date myTime = Calendar.getInstance().getTime();
    DataRepository dataRepository;
    String dateString;
    String timeString;


    // ---- UI VARIABLES --- //
    EditText dateEdit;
    EditText timeEdit;
    EditText systolicEdit;
    EditText diastolicEdit;
    EditText heartRateEdit;
    EditText commentEdit;
    Button saveBtn;
    // -------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        // --------- FIND VIEW BY ID ------------- //
        dateEdit = findViewById(R.id.date_edit_text_id);
        timeEdit = findViewById(R.id.time_edit_text);
        heartRateEdit = findViewById(R.id.heartRate_edit_text);
        systolicEdit = findViewById(R.id.systolic_edit_text);
        diastolicEdit = findViewById(R.id.diastolic_edit_text);
        commentEdit = findViewById(R.id.comment_edit_text);
        saveBtn = findViewById(R.id.save_button);

        // ----------------------------------------------------- //

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }


        };


        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ShowData.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        dataRepository = new DataRepository(getApplication());



     /*   String dateString = dateEdit.getText().toString();
        String timeString = timeEdit.getText().toString();*/


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String systolicString = systolicEdit.getText().toString();
                String diastolicString = diastolicEdit.getText().toString();
                String heartRateString = heartRateEdit.getText().toString();
                String commentString = commentEdit.getText().toString();

                Log.d(dateString, "Show data");
                Log.d(timeString, "Show data");
                Log.d(systolicString, "Show data");
                Log.d(diastolicString, "Show data");
                Log.d(heartRateString, "Show data");

                int systolic = 0, diastolic = 0, heartRate = 0;

                if (!systolicString.isEmpty()) {
                    systolic = Integer.parseInt(systolicString);

                    if (!diastolicString.isEmpty()) {
                        diastolic = Integer.parseInt(diastolicString);

                        if (!heartRateString.isEmpty()) {
                            heartRate = Integer.parseInt(heartRateString);
                            if (dateEdit.getText().toString().isEmpty()) {
                                dateEdit.setError("Please enter a date");
                                dateEdit.requestFocus();
                            } else {
                                if (timeEdit.getText().toString().isEmpty()) {
                                    timeEdit.setError("Please enter a date");
                                    timeEdit.requestFocus();
                                } else {

                                    if (systolic <= 0) {
                                        systolicEdit.setError("Please enter a valid number");
                                        systolicEdit.requestFocus();
                                    } else {
                                        if (diastolic <= 0) {
                                            diastolicEdit.setError("Please enter a valid number");
                                            diastolicEdit.requestFocus();
                                        } else {
                                            if (heartRate <= 0) {
                                                heartRateEdit.setError("Please enter a valid number");
                                                heartRateEdit.requestFocus();
                                            } else {
                                                UserData userData = new UserData(dateEdit.getText().toString(), timeEdit.getText().toString(),
                                                        systolic, diastolic, heartRate, commentString);

                                                userData.setDi(System.currentTimeMillis());
                                                dataRepository.insert(userData);

                                                Intent intent = new Intent(ShowData.this, MainActivity.class);

                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }
                                    }


                                }
                            }
                        } else {
                            heartRateEdit.setError("Please enter heart rate");
                            heartRateEdit.requestFocus();
                        }
                    } else {
                        diastolicEdit.setError("Please enter diastolic pressure");
                        diastolicEdit.requestFocus();
                    }
                } else {
                    systolicEdit.setError("Please enter systolic pressure");
                    systolicEdit.requestFocus();
                }


            }
        });

    }


    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateEdit.setText(dateFormat.format(myCalendar.getTime()));
        dateString = dateEdit.getText().toString();

    }

    private void showTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ShowData.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeEdit.setText(selectedHour + ":" + selectedMinute);
                timeString = timeEdit.getText().toString();
            }
        }, hour, minute, true);

        mTimePicker.show();
    }
}