package com.pialroni.myhealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.IdlingResource;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText emailEdit;
    EditText passEdit;
    Button loginBtn;
    FirebaseAuth firebaseAuth;
    public ExpresssoIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdit = findViewById(R.id.email_login);
        passEdit = findViewById(R.id.password_login);
        loginBtn = findViewById(R.id.loginBtn);

        firebaseAuth = FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (emailEdit.getText().toString().isEmpty()) {
                    emailEdit.setError("Enter your email");
                    emailEdit.requestFocus();

                } else if (passEdit.getText().toString().isEmpty()) {
                    passEdit.setError("Enter your password");
                    passEdit.requestFocus();


                } else {
                    getIdlingResource();
                    mIdlingResource.setIdleState(false);
                    firebaseAuth.signInWithEmailAndPassword(emailEdit.getText().toString(), passEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {

                                Snackbar.make(v, "Please try again", Snackbar.LENGTH_SHORT).show();
                                getIdlingResource();
                                mIdlingResource.setIdleState(true);
                                return;

                            } else {
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                mIdlingResource.setIdleState(true);
                            }
                        }


                    });


                }


            }
        });

    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new ExpresssoIdlingResource();
        }
        return mIdlingResource;
    }
}