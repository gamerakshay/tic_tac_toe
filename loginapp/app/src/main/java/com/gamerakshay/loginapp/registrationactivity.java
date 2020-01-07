package com.gamerakshay.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrationactivity extends AppCompatActivity {
    private EditText username,password,email,userage;
    private Button register;
    private TextView already;
    private FirebaseAuth firebaseAuth;
    private ImageView userprofilepic;
    String name,Email,Password,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationactivity);
        setupuiview();
        firebaseAuth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    String useremail=email.getText().toString().trim();
                    String userpassword=password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {


                                /*Toast.makeText(registrationactivity.this, "Registration successfull", Toast.LENGTH_SHORT).show;
                                startActivity(new Intent(registrationactivity.this,MainActivity.class));*/
                                sendemailverification();
                            }
                            else
                            {
                                Toast.makeText(registrationactivity.this, "Registration unsuccessfull", Toast.LENGTH_SHORT).show();
                            }
                            }
                    });
                }
            }
        });
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registrationactivity.this,MainActivity.class));
            }
        });
    }
    private void setupuiview()
    {
        username=(EditText)findViewById(R.id.etusername);
        password=(EditText)findViewById(R.id.etmpassword);
        email=(EditText)findViewById(R.id.etmemail);
        register=(Button)findViewById(R.id.btregister);
        already=(TextView)findViewById(R.id.tvlogin);
        userage=(EditText)findViewById(R.id.etage);
        userprofilepic=(ImageView)findViewById(R.id.ivprofile);
    }
    private  boolean validate()
    {
        boolean result=false;
         name=username.getText().toString();
         Password=password.getText().toString();
         Email=email.getText().toString();
         age=userage.getText().toString();

        if(name.isEmpty()||Password.isEmpty()||Email.isEmpty()||age.isEmpty())
        {
            Toast.makeText(this,"please fill the details",Toast.LENGTH_SHORT).show();
        }
        else
            result=true;

        return result;
    }
    private void sendemailverification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {   senduserdata();
                        Toast.makeText(registrationactivity.this,"Successfully registered, A verification mail has been send",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(registrationactivity.this,MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(registrationactivity.this,"Verification has not been successfull",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private  void senduserdata()
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myref=firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userprofile= new UserProfile(age,Email,name);
        myref.setValue(userprofile);
    }
}
