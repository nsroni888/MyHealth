package com.pialroni.myhealth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity implements DataAdapter.ListClickListener {


    private List<UserData> userDataList;
    private DataAdapter dataAdapter;
    private FloatingActionButton floatingActionButton;
    private RecyclerView review_recycler;



    private DataRepository repository;



    public ExpresssoIdlingResource mIdlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);


        // ------------- FIND VIEW BY ID --------------- //
        getIdlingResource();
        mIdlingResource.setIdleState(false);

        floatingActionButton = findViewById(R.id.floating_id);
        review_recycler = findViewById(R.id.user_data_recycler);

        userDataList = new ArrayList<>();

        // -- Database -- //

        repository = new DataRepository(getApplication());
        mIdlingResource.setIdleState(false);
        userDataList = repository.getAllData();


        review_recycler.setLayoutManager(new LinearLayoutManager(this));

        dataAdapter = new DataAdapter(this, this);



        Collections.reverse(userDataList);
        dataAdapter.setList(userDataList);
        dataAdapter.notifyDataSetChanged();
        mIdlingResource.setIdleState(true);

        review_recycler.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowData.class);

                startActivity(intent);


            }
        });


    }

    /**
     * Click on Adapter
     * @param UserData
     */

    @Override
    public void onListClick(UserData UserData) {
        Intent intent = new Intent(MainActivity.this, Details.class);
        intent.putExtra("UserData", UserData);
        Log.d(UserData.getDi() + " ", "Details Main" + UserData.getDi());
        startActivity(intent);

    }


    /**
     * Initializing Idling Resource
     * @return
     */
    @VisibleForTesting

    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new ExpresssoIdlingResource();
        }
        return mIdlingResource;
    }


}