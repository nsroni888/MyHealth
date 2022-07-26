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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Details extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();

    DataRepository dataRepository;
    String dateString;
    String timeString;
    public UserData userData = new UserData();
    String id;

    ImageView deleteBtn;
    EditText heartRate;
    EditText systolicRate;
    EditText diastoloicRate;
    EditText commentEditText;
    EditText dateEditText;
    EditText timeEditText;

    TextView heartText ;
    TextView systolicText;
    TextView diastolicText;

    CardView heartCard;
    CardView systolicCard;
    CardView diastolicCard;


    Button SaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        deleteBtn = findViewById(R.id.delete_imageBtn);
        heartRate = findViewById(R.id.heart_rate_details);
        systolicRate = findViewById(R.id.systolic_rate_details);
        diastoloicRate = findViewById(R.id.diastolic_rate_details);
        commentEditText = findViewById(R.id.comment_details_text);
        SaveBtn = findViewById(R.id.edit_save);
        dateEditText = findViewById(R.id.date_details);
        timeEditText = findViewById(R.id.time_details);

        heartText = findViewById(R.id.heart_rate_text_details) ;
        systolicText = findViewById(R.id.systolic_rate_text_details) ;
        diastolicText = findViewById(R.id.diastolic_rate_text_details) ;

        heartCard = findViewById(R.id.heart_rate_card_details) ;
        systolicCard = findViewById(R.id.systolic_rate_card_details) ;
        diastolicCard = findViewById(R.id.diastolic_rate_card_details) ;


        userData = (UserData) getIntent().getSerializableExtra("UserData");
        Log.v(id, "Details intent" + userData.getDi());


        // -- database -- //
        dataRepository = new DataRepository(getApplication());



        //-----------------------------------//

        heartRate.setText(String.valueOf(userData.getHeartRate()));
        systolicRate.setText(String.valueOf(userData.getSystolic()));
        diastoloicRate.setText(String.valueOf(userData.getDiastolic()));
        commentEditText.setText(userData.getComment());

        dateEditText.setText(String.valueOf(userData.getDate()));
        timeEditText.setText(String.valueOf(userData.getTimestamp()));

        setCardColor();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }


        };


        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Details.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataRepository.delete(userData);
                Intent intent = new Intent(Details.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String systolicString = systolicRate.getText().toString() ;
                String diastolicString = diastoloicRate.getText().toString() ;
                String heartRateString = heartRate.getText().toString() ;
                String commentString = commentEditText.getText().toString();

                Log.d(dateString, "Show data") ;
                Log.d(timeString, "Show data") ;
                Log.d(systolicString, "Show data") ;
                Log.d(diastolicString, "Show data") ;
                Log.d(heartRateString, "Show data") ;

                int systolic = 0 , diastolic = 0 , heartRate_int = 0 ;

                if(!systolicString.isEmpty())
                    systolic = Integer.parseInt(systolicString);
                else{
                    systolicRate.setError("Please enter systolic pressure");
                    systolicRate.requestFocus();
                }

                if(!diastolicString.isEmpty())
                    diastolic = Integer.parseInt(diastolicString);
                else
                {
                    diastoloicRate.setError("Please enter diastolic pressure");
                    diastoloicRate.requestFocus();
                }

                if(!heartRateString.isEmpty())
                    heartRate_int = Integer.parseInt(heartRateString);
                else
                {
                    heartRate.setError("Please enter heart rate");
                    heartRate.requestFocus();
                }

                if(dateEditText.getText().toString().isEmpty()){
                    dateEditText.setError("Please enter a date");
                    dateEditText.requestFocus();
                }

                if (timeEditText.getText().toString().isEmpty()) {
                    timeEditText.setError("Please enter a date");
                    timeEditText.requestFocus();
                }


                if (systolic <= 0) {
                    systolicRate.setError("Please enter a valid number");
                    systolicRate.requestFocus();
                }
                if (diastolic <= 0) {
                    diastoloicRate.setError("Please enter a valid number");
                    diastoloicRate.requestFocus();
                }
                if (heartRate_int <= 0) {
                    heartRate.setError("Please enter a valid number");
                    heartRate.requestFocus();
                }

                UserData userData_new = new UserData(dateEditText.getText().toString(), timeEditText.getText().toString(),
                        systolic, diastolic, heartRate_int, commentString);

                userData_new.setDi(userData.getDi());
                dataRepository.update(userData_new);

                Intent intent = new Intent(Details.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });


    }

    private void setCardColor() {
        if (userData.getSystolic() >= 90 && userData.getSystolic() <= 140) {
            // NORMAL SYSTOLIC
            systolicText.setTextColor(getResources().getColor(R.color.black));
            systolicRate.setTextColor(getResources().getColor(R.color.black));

        } else {
            systolicText.setTextColor(getResources().getColor(R.color.deepRed));
            systolicRate.setTextColor(getResources().getColor(R.color.deepRed));
            systolicCard.setCardBackgroundColor(getResources().getColor(R.color.lightRed));

        }

        // ---- Diastolic ------ //
        if (userData.getDiastolic() >= 60 && userData.getDiastolic() <= 90) {
            // NORMAL Diastolic
             diastolicText.setTextColor(getResources().getColor(R.color.black));
             diastoloicRate.setTextColor(getResources().getColor(R.color.black));

        } else {
            diastolicText.setTextColor(getResources().getColor(R.color.deepRed));
            diastoloicRate.setTextColor(getResources().getColor(R.color.deepRed));
            diastolicCard.setCardBackgroundColor(getResources().getColor(R.color.lightRed));
        }
    }


    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(dateFormat.format(myCalendar.getTime()));
        dateString = dateEditText.getText().toString();

    }

    private void showTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Details.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeEditText.setText(selectedHour + ":" + selectedMinute);
                timeString = timeEditText.getText().toString();
            }
        }, hour, minute, true);

        mTimePicker.show();
    }
}