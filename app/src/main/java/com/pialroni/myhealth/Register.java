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

public class Register extends AppCompatActivity {

    EditText emailEdit;
    EditText passEdit;
    EditText confirmPassEdit;
    Button singUpBtn;
    public FirebaseAuth firebaseAuth;
    public ExpresssoIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEdit = findViewById(R.id.email_register);
        passEdit = findViewById(R.id.password_register);
        confirmPassEdit = findViewById(R.id.confirm_password_register);
        singUpBtn = findViewById(R.id.signUpbtn);


        firebaseAuth = FirebaseAuth.getInstance();
        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailEdit.getText().toString().isEmpty()) {
                    emailEdit.setError("Enter your email");
                    emailEdit.requestFocus();
                    singUpBtn.setEnabled(true);
                } else if (passEdit.getText().toString().isEmpty()) {
                    passEdit.setError("Enter your password");
                    passEdit.requestFocus();
                    passEdit.setEnabled(true);

                } else if (confirmPassEdit.getText().toString().isEmpty()) {
                    confirmPassEdit.setError("Enter your password");
                    confirmPassEdit.requestFocus();
                    confirmPassEdit.setEnabled(true);

                } else {

                    if (passEdit.getText().toString().equals(confirmPassEdit.getText().toString())) {
                        getIdlingResource();
                        mIdlingResource.setIdleState(false);
                        firebaseAuth.createUserWithEmailAndPassword(emailEdit.getText().toString(), passEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    singUpBtn.setEnabled(true);
                                    Snackbar.make(v, "Please try again", Snackbar.LENGTH_SHORT).show();
                                    mIdlingResource.setIdleState(true);
                                    return;
                                } else {
                                    mIdlingResource.setIdleState(true);
                                    Intent intent = new Intent(Register.this,  MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    } else {
                        passEdit.setError("Password doesn't match");
                        confirmPassEdit.setError("Password doesn't match");
                    }

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