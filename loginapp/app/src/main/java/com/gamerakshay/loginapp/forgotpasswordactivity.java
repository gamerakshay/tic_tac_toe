package com.gamerakshay.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotpasswordactivity extends AppCompatActivity {
    private EditText femail;
    private Button resetpassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpasswordactivity);
        femail=(EditText)findViewById(R.id.etfemail);
        resetpassword=(Button)findViewById(R.id.btpasswordreset);
        firebaseAuth=FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=femail.getText().toString().trim();

                if(useremail.equals(""))
                {
                    Toast.makeText(forgotpasswordactivity.this,"please enter your registered email id",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(forgotpasswordactivity.this,"reset link is sent to registered email",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgotpasswordactivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(forgotpasswordactivity.this,"error in sending reset link",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });
    }
}
