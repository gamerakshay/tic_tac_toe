package com.gamerakshay.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] button=new Button[3][3];
    private boolean player1turn = true;
    private int roundcount;
    private int khiladi1points;
    private int khiladi2points;
    private TextView textViewkhiladi1;
    private TextView textViewkhiladi2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewkhiladi1=findViewById(R.id.text_khiladi1);
        textViewkhiladi2=findViewById(R.id.text_khiladi2);

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String buttonid = "button"+i+j;
                int restid=getResources().getIdentifier(buttonid,"id",getPackageName());
                button[i][j]=findViewById(restid);
                button[i][j].setOnClickListener(this);

            }
        }
        Button buttonreset=findViewById(R.id.button_reset);
        buttonreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetgame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")) {
            return;
        }
        if(player1turn)
        {
            ((Button) v).setText("X");
        }
        else
        {
            ((Button) v).setText("O");
        }
        roundcount++;

        if(checkwin())
        {
            if(player1turn)
            {
                khiladi_1_wins();
            }
            else
                khiladi_2_wins();

        }
        else if(roundcount==9)
            draw();
        else
            player1turn=!player1turn;


    }

    private void khiladi_1_wins() {
        khiladi1points++;
        Toast.makeText(this,"Khiladi_1_wins",Toast.LENGTH_SHORT).show();
        updatepointtext();
        resetboard();
    }

    private void resetboard() {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                button[i][j].setText("");
            }
        }
        roundcount=0;
        player1turn=true;
    }
    private void updatepointtext()
    {
        textViewkhiladi1.setText("Khiladi 1:" +khiladi1points);
        textViewkhiladi2.setText("Khiladi 2:" +khiladi2points);
    }

    private void khiladi_2_wins()
    {
        khiladi2points++;
        Toast.makeText(this,"Khiladi_2_wins",Toast.LENGTH_SHORT).show();
        updatepointtext();
        resetboard();
    }
    private void draw()
    {
        Toast.makeText(this,"Draw",Toast.LENGTH_SHORT).show();
        resetboard();
    }

    private  boolean checkwin()
    {
        String[][] block=new String[3][3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                 block[i][j]=button[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++)
        {
            if(block[i][0].equals(block[i][1])
                    && block[i][0].equals(block[i][2])
            && !block[i][0].equals(""))
            {
                return true;
            }
        }
        for(int i=0;i<3;i++)
        {
            if(block[0][i].equals(block[1][i])
                    && block[0][i].equals(block[2][i])
                    && !block[0][i].equals(""))
            {
                return true;
            }
        }
        for(int i=0;i<3;i++)
        {
            if(block[0][0].equals(block[1][1])
                    && block[0][0].equals(block[2][2])
                    && !block[0][0].equals(""))
            {
                return true;
            }
        }
        for(int i=0;i<3;i++)
        {
            if(block[0][2].equals(block[1][1])
                    && block[0][2].equals(block[2][0])
                    && !block[0][2].equals(""))
            {
                return true;
            }
        }
        return false;
    }
    private void resetgame()
    {
        khiladi1points=0;
        khiladi2points=0;
        updatepointtext();
        resetboard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundcount",roundcount);
        outState.putInt("khiladi1points",khiladi1points);
        outState.putInt("khiladi2points",khiladi2points);
        outState.putBoolean("player1turn",player1turn);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundcount=savedInstanceState.getInt("roundcount");
        player1turn=savedInstanceState.getBoolean("player1turn");
        khiladi2points=savedInstanceState.getInt("khiladi2points");
        khiladi1points=savedInstanceState.getInt("khiladi1points");
    }
}
