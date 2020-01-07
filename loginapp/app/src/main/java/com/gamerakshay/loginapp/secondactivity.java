package com.gamerakshay.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class secondactivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);

        firebaseAuth=FirebaseAuth.getInstance();
        logout=(Button)findViewById(R.id.btlogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                 }
        });
    }
    private void logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(secondactivity.this,MainActivity.class));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logoutmenu:
            {
                logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
