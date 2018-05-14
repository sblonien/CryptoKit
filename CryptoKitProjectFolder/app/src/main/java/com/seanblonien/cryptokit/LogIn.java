package com.seanblonien.cryptokit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The type Log in.
 */
public class LogIn extends AppCompatActivity {
    // UI references.
    private EditText mEmail, mPassword;
    private Button mBtnSignup, mBtnLogin, mBtnReset;
    private ProgressBar mProgressbar;
    private FirebaseAuth mAuth;
    /**
     * The constant signOut.
     */
    public static boolean signOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.log_in);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
        if(signOut) {
            signOut();
        }

        // Go to main task if user is already logged in
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LogIn.this, PriceChecker.class));
            finish();
        }

        // Now set the view
        setContentView(R.layout.log_in);
        // Get the corresponding
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mProgressbar = findViewById(R.id.progressBar);
        mBtnSignup = findViewById(R.id.btn_signup);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnReset = findViewById(R.id.btn_reset_password);


        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        // Initialize the sign up button to go to that activity if clicked
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, Signup.class));
            }
        });

        // Initialize the reset button to go to that activity if clicked
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, ResetPassword.class));
            }
        });

        // Implement the login feature
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT);
                    View view = toast.getView();

                    //To change the Background of Toast
                    view.setBackgroundColor(Color.TRANSPARENT);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    //Shadow of the Of the Text Color
                    text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
                    text.setTextColor(Color.BLACK);
                    toast.show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressbar.setVisibility(View.VISIBLE);

                final int length = password.length();
                // Authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                mProgressbar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (length < 6) {
                                        mPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LogIn.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    startNextActivity();
                                }
                            }
                        });
            }
        });
    }

    private void startNextActivity() {
        startActivity(new Intent(LogIn.this, PriceChecker.class));
        finish();
    }

    /**
     * Sign out.
     */
    private void signOut() {
        signOut = false;
        mAuth.signOut();
    }
}

