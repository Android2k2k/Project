package com.example.bloodproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    TextView register;
    FirebaseAuth auth;
    GoogleSignInClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

    }


    public void login(View view) {
        String umail = email.getText().toString();
        String upass = password.getText().toString();
        if (umail.isEmpty() | upass.isEmpty()) {
            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(umail, upass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void register(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }



    public void clickhere(View view) {

        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    public void reset(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.reset,null);
        builder.setView(v);
        final EditText email = v.findViewById(R.id.umail);
        builder.setCancelable(false);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                String mail = email.getText().toString();
                auth.sendPasswordResetEmail(mail).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Reset email sent", Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}