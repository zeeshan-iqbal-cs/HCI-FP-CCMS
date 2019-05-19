package com.example.haf.myfreeze;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{
    private EditText InputUserPhoneNumber, InputUserVerificationCode;
    private Button SendVerificationCodeButton, VerifyButton;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        InputUserPhoneNumber = (EditText) findViewById(R.id.PhoneNumberEditText);
        InputUserVerificationCode = (EditText) findViewById(R.id.CodeEditText);
        SendVerificationCodeButton = (Button) findViewById(R.id.getCodeButon);
        VerifyButton = (Button) findViewById(R.id.VerifyButton);
        loadingBar = new ProgressDialog(this);


        SendVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String phoneNumber = InputUserPhoneNumber.getText().toString();

                if (TextUtils.isEmpty(phoneNumber))
                {
                    Toast.makeText(MainActivity.this, "Please enter your phone number first...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setTitle("Phone Verification");
                    loadingBar.setMessage("Please wait, while we are authenticating using your phone...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, MainActivity.this, callbacks);
                }
            }
        });



        VerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InputUserPhoneNumber.setVisibility(View.INVISIBLE);
                SendVerificationCodeButton.setVisibility(View.INVISIBLE);


                String verificationCode = InputUserVerificationCode.getText().toString();

                if (TextUtils.isEmpty(verificationCode))
                {
                    Toast.makeText(MainActivity.this, "Please write verification code first...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setTitle("Verification Code");
                    loadingBar.setMessage("Please wait, while we are verifying verification code...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });


        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
            {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {
                Toast.makeText(MainActivity.this, "Invalid Phone Number, Please enter correct phone number with your country code...", Toast.LENGTH_LONG).show();
                loadingBar.dismiss();

                InputUserPhoneNumber.setVisibility(View.VISIBLE);
                SendVerificationCodeButton.setVisibility(View.VISIBLE);

                InputUserVerificationCode.setVisibility(View.INVISIBLE);
                VerifyButton.setVisibility(View.INVISIBLE);
            }

            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token)
            {
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;


                Toast.makeText(MainActivity.this, "Code has been sent, please check and verify...", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

                InputUserPhoneNumber.setVisibility(View.INVISIBLE);
                SendVerificationCodeButton.setVisibility(View.INVISIBLE);

                InputUserVerificationCode.setVisibility(View.VISIBLE);
                VerifyButton.setVisibility(View.VISIBLE);
            }
        };
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Congratulations, you're logged in Successfully.", Toast.LENGTH_SHORT).show();
                            SendUserToMainActivity();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }




    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(MainActivity.this,Devices.class);
        startActivity(mainIntent);
        finish();
    }
}

