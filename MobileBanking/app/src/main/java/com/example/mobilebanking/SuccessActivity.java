package com.example.mobilebanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Button home = findViewById(R.id.btnHomeSuccess);
        TextView tv = findViewById(R.id.msg);
        TextView cb = findViewById(R.id.txtCurrentbalance);

        Bundle b = getIntent().getExtras();
        tv.setText(b.get("msg").toString()+" !!!");
        cb.setText(b.get("currentBalance").toString()+" Tk");

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuccessActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}