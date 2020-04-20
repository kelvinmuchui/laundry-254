package com.example.banice.laundry254;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banice.laundry254.user.The_user_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class Main extends AppCompatActivity {

    TextView newacc,forgotpassword;
    EditText password, email;
    Button button;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Map<String, Object> tok;
    CollectionReference collectionReference;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.mail);
        password = findViewById(R.id.pass);
        button = findViewById(R.id.login);
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Users");
        newacc = findViewById(R.id.newacc);
        forgotpassword=findViewById(R.id.forgotpassword);
        firebaseAuth = FirebaseAuth.getInstance();


        email.setText("customer1n@gmail.com");
        password.setText("hello1234");
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Main.this,ForgotPassword.class);

                intent.putExtra("email",email.getText().toString());

                startActivity(intent);
                overridePendingTransition(R.anim.fromright,R.anim.toright);

            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Validate_email(email.getText().toString())) {
                    email.setError("Email Incorrect");
                    email.requestFocus();
                } else if (!Validate_password(password.getText().toString())) {
                    password.setError("Password Incorrect");
                    email.requestFocus();
                } else {

                    final AlertDialog alertDialog = new SpotsDialog.Builder()
                            .setContext(Main.this)
                            .setMessage("Processing")
                            .setCancelable(false)
                            .setTheme(R.style.Custom)
                            .build();
                    final ProgressDialog progressDialog = new ProgressDialog(Main.this, R.style.MyAlertDialogStyle);
                    progressDialog.setMessage("Processing");
                    progressDialog.setTitle("Please wait");
                    //progressDialog.show();
                    alertDialog.show();

                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        String token = FirebaseInstanceId.getInstance().getToken();

                                        tok = new HashMap<>();
                                        tok.put("token", token);
                                        Log.i("token", token);
                                        Log.i("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());


                                        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(tok).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                alertDialog.dismiss();
                                                //progressDialog.dismiss();

                                                    Toast.makeText(Main.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    startActivity(new Intent(getApplicationContext(), The_user_profile.class));
                                                    overridePendingTransition(R.anim.fromright,R.anim.toright);

//                                                 else {
//                                                    finish();
//                                                    startActivity(new Intent(getApplicationContext(), EmailVerification.class));
//                                                    overridePendingTransition(R.anim.fromright,R.anim.toright);
//                                                }

                                            }

                                        });


                                    } else {
                                        Toast.makeText(Main.this, "Email And Password mismatch", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        alertDialog.dismiss();
                                    }
                                }
                            });

                }
            }
        });

    }


    public void Clickme(View view) {
        {
            Intent intent = new Intent(this, newaccount.class);
            startActivity(intent);
            overridePendingTransition(R.anim.top2,R.anim.top1);
        }
    }

    private boolean Validate_password(String s) {
        if (password != null && password.length() > 6)
            return true;
        else
            return false;
    }

    private boolean Validate_email(String s) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email.getText().toString());

        return matcher.matches();

    }


}
