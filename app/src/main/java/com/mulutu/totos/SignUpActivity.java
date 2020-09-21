package com.mulutu.totos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private AppCompatEditText emailBox, passwordBox;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar){
            actionBar.hide();
        }


        AppCompatTextView signUpLink = (AppCompatTextView)findViewById(R.id.signup_link);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });


        //usernameBox = (AppCompatEditText)findViewById(R.id.username);
        emailBox = (AppCompatEditText)findViewById(R.id.email);
        passwordBox = (AppCompatEditText)findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();


        AppCompatButton signUpBtn = (AppCompatButton)findViewById(R.id.signup_btn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String username = Objects.requireNonNull(usernameBox.getText()).toString();
                String password = Objects.requireNonNull(passwordBox.getText()).toString();
                String email = Objects.requireNonNull(emailBox.getText()).toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)){
                    Toast.makeText(SignUpActivity.this, "Login Fields must not be empty", Toast.LENGTH_SHORT).show();
                }else{
                    //todo authenticate with Firestore
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                }
            }
        });

    }
}
