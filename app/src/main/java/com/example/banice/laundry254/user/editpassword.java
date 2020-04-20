package com.example.banice.laundry254.user;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banice.laundry254.R;
import com.example.banice.laundry254.get_details;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editpassword extends AppCompatActivity {

    EditText oldpass,newpass,renewpass;
    Button save;
    String userid,password;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.newpass);
        renewpass = findViewById(R.id.renewpass);
        save = findViewById(R.id.chngpass);

        dataref = FirebaseDatabase.getInstance().getReference().child("1");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("1").child("User details").child(userid);

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    get_password d = new get_password();
                    d.setPassword(dataSnapshot1.child(userid).getValue(get_details.class).getPassword());
                    password=d.getPassword().toString();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if(password.equals(oldpass.getText().toString()))
                {
                    if(!Validate_password(newpass.getText().toString()))
                    {
                        newpass.setError("Password must be greater than 6 characters");
                        newpass.requestFocus();
                    }
                    else {
                        if(!newpass.getText().toString().equals(renewpass.getText().toString()))
                        {
                            renewpass.setError("Passwords do not match");
                        }
                        else
                        {
                            final ProgressDialog progressDialog=ProgressDialog.show(editpassword.this,"Please wait","Updating Password");
                            firebaseAuth.getCurrentUser().updatePassword(renewpass.getText().toString());
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().child("password").setValue(renewpass.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(editpassword.this,"Password Changed",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
                else
                {oldpass.setError("Wrong Old Password"); oldpass.requestFocus();}


            }

        });
    }
    private boolean Validate_password(String s) {
        if (s != null && s.length() > 6)
            return true;
        else
            return false;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fromright, R.anim.toright);
    }

    }


class get_password
        {
            String password;

            public get_password(String password) {
                this.password = password;
            }

            public get_password() {
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }
        }