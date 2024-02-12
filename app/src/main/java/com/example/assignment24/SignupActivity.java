package com.example.assignment24;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        auth = FirebaseAuth.getInstance();

        signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = signupName.getText().toString().trim();
                final String email = signupEmail.getText().toString().trim();
                final String username = signupUsername.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();

                // Input validation for name
                if (name.isEmpty()) {
                    signupName.setError("Name cannot be empty");
                    return;
                } else if (name.length() < 3) {
                    signupName.setError("Name should be at least 3 characters");
                    return;
                } else if (name.length() > 50) {
                    signupName.setError("Name cannot be more than 50 characters");
                    return;
                }

                // Input validation for email
                if (email.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                    return;
                } else if (!email.endsWith("@gmail.com") && !email.endsWith("@yahoo.com")) {
                    signupEmail.setError("Invalid email format");
                    return;
                }

                // Input validation for username
                if (username.isEmpty()) {
                    signupUsername.setError("Username cannot be empty");
                    return;
                } else if (username.length() <= 3 || !username.matches(".*[a-zA-Z].*\\d.*|.*\\d.*[a-zA-Z].*")) {
                    signupUsername.setError("Username should be more than 3 characters and have a mix of letters and numbers");
                    return;
                }

                // Input validation for password
                if (password.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                    return;
                } else if (password.length() <= 6 || !password.matches(".*[a-zA-Z].*\\d.*|.*\\d.*[a-zA-Z].*")) {
                    signupPassword.setError("Password should be more than 6 characters and have a mix of letters and numbers");
                    return;
                }

                // Initialize the database reference
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                // Register user with Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Save additional user information in Firebase Realtime Database
                            HelperClass helperClass = new HelperClass(name, email, username, password);
                            reference.child(username).setValue(helperClass);

                            Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignupActivity.this, "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
